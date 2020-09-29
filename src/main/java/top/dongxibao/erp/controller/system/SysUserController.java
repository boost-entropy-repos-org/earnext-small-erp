package top.dongxibao.erp.controller.system;

import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.dongxibao.erp.annotation.Log;
import top.dongxibao.erp.common.BaseController;
import top.dongxibao.erp.common.Result;
import top.dongxibao.erp.common.ResultPage;
import top.dongxibao.erp.constant.Constants;
import top.dongxibao.erp.entity.system.SysUser;
import top.dongxibao.erp.entity.system.SysUserOnline;
import top.dongxibao.erp.enums.BusinessType;
import top.dongxibao.erp.security.JWTUser;
import top.dongxibao.erp.security.SecurityUtils;
import top.dongxibao.erp.security.service.TokenUtils;
import top.dongxibao.erp.service.system.SysUserOnlineService;
import top.dongxibao.erp.service.system.SysUserService;
import top.dongxibao.erp.util.JsonUtils;
import top.dongxibao.erp.util.ServletUtils;
import top.dongxibao.erp.util.SqlUtil;

import java.util.*;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author Dongxibao
 * @date 2020-06-14
 */
@Api(tags = "系统-用户")
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private SysUserOnlineService sysUserOnlineService;

    /**
     * 获取分页列表
     */
    @Log(title = "用户模块", businessType = BusinessType.SELECT)
    @PreAuthorize("@ss.hasPermi('system:user:page')")
    @ApiOperation("用户列表分页查询")
    @GetMapping("/page")
    public Result list(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                       @RequestParam(value = "orderByColumn", required = false) String orderByColumn,
                       @RequestParam(value = "isAsc", required = false, defaultValue = "asc") String isAsc,
                       @RequestParam(value = "beginTime", required = false) Date beginTime,
                       @RequestParam(value = "endTime", required = false) Date endTime) {
        SysUser sysUser = new SysUser();
        sysUser.setBeginTime(beginTime);
        sysUser.setEndTime(endTime);
        String orderBy = SqlUtil.escapeOrderBySql(super.packOrderBy(orderByColumn, isAsc));

        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<SysUser> list = sysUserService.selectByCondition(sysUser);
        return new Result(ResultPage.restPage(list));
    }

    /**
     * 用户详细信息
     */
    @Log(title = "用户模块", businessType = BusinessType.SELECT)
    @PreAuthorize("@ss.hasPermi('system:user:get')")
    @ApiOperation("用户详细信息")
    @GetMapping("/{id}")
    public Result get(@PathVariable("id") Long id) {
        SysUser sysUser = sysUserService.getById(id);
        return new Result(sysUser);
    }

    /**
     * 用户新增
     */
    @Log(title = "用户模块", businessType = BusinessType.INSERT)
    @PreAuthorize("@ss.hasPermi('system:user:insert')")
    @ApiOperation("用户新增")
    @PostMapping
    public Result insert(@Validated @RequestBody SysUser sysUser) {
        boolean insert = sysUserService.save(sysUser);
        return new Result(sysUser, insert);
    }

    /**
     * 用户修改
     */
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("@ss.hasPermi('system:user:update')")
    @ApiOperation("用户修改")
    @PutMapping("/{id}")
    public Result update(@RequestBody SysUser sysUser, @PathVariable("id") Long id) {
        sysUser.setId(id);
        sysUser.setPassword(null);
        boolean update = sysUserService.updateById(sysUser);
        return new Result(sysUser, update);
    }

    /**
     * 用户删除
     */
    @Log(title = "用户模块", businessType = BusinessType.DELETE)
    @PreAuthorize("@ss.hasPermi('system:user:delete')")
    @ApiOperation("用户删除")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Long id) {
        boolean delete = sysUserService.removeById(id);
        return new Result(null, delete);
    }


    /**
     * 重置密码
     */
    @ApiOperation("重置密码")
    @Log(title = "用户模块", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    public Result updatePwd(@RequestParam(value = "oldPassword") String oldPassword,
                            @RequestParam(value = "newPassword") String newPassword) {
        JWTUser jwtUser = tokenUtils.getLoginUser(ServletUtils.getRequest());
        String userName = jwtUser.getUsername();
        String password = jwtUser.getPassword();
        if (!SecurityUtils.matchesPassword(oldPassword, password)) {
            return new Result("修改密码失败，旧密码错误", false, 400, null);
        }
        if (SecurityUtils.matchesPassword(newPassword, password)) {
            return new Result("新密码不能与旧密码相同", false, 400, null);
        }
        if (sysUserService.resetUserPwd(userName, SecurityUtils.encryptPassword(newPassword)) > 0) {
            // 更新缓存用户密码
            jwtUser.getUser().setPassword(SecurityUtils.encryptPassword(newPassword));
            tokenUtils.setLoginUser(jwtUser);
            return new Result("修改密码成功");
        }
        return new Result("修改密码异常，请联系管理员", false);
    }

    @ApiOperation("在线用户")
    @Log(title = "用户模块", businessType = BusinessType.SELECT)
    @GetMapping("/online")
    public Result list(@RequestParam(value = "ipaddr")String ipaddr,
                       @RequestParam(value = "userName")String userName) {
        Collection<String> keys = stringRedisTemplate.keys(Constants.LOGIN_TOKEN_KEY + "*");
        List<SysUserOnline> userOnlineList = new ArrayList<>();
        for (String key : keys) {
            JWTUser user = JsonUtils.json2Obj(stringRedisTemplate.opsForValue().get(key), JWTUser.class);
            if (StringUtils.isNotEmpty(ipaddr) && StringUtils.isNotEmpty(userName)) {
                if (StringUtils.equals(ipaddr, user.getIpaddr()) && StringUtils.equals(userName, user.getUsername())) {
                    userOnlineList.add(sysUserOnlineService.selectOnlineByInfo(ipaddr, userName, user));
                }
            } else if (StringUtils.isNotEmpty(ipaddr)) {
                if (StringUtils.equals(ipaddr, user.getIpaddr())) {
                    userOnlineList.add(sysUserOnlineService.selectOnlineByIpaddr(ipaddr, user));
                }
            } else if (StringUtils.isNotEmpty(userName) && null != user.getUser()) {
                if (StringUtils.equals(userName, user.getUsername())) {
                    userOnlineList.add(sysUserOnlineService.selectOnlineByUserName(userName, user));
                }
            } else {
                userOnlineList.add(sysUserOnlineService.loginUserToUserOnline(user));
            }
        }
        Collections.reverse(userOnlineList);
        userOnlineList.removeAll(Collections.singleton(null));
        return new Result(ResultPage.restPage(userOnlineList));
    }

    /**
     * 强退用户
     */
    @ApiOperation("强退用户")
    @PreAuthorize("@ss.hasPermi('system:online:forceLogout')")
    @Log(title = "用户模块", businessType = BusinessType.DELETE)
    @DeleteMapping("/{tokenId}")
    public Result forceLogout(@PathVariable("tokenId") String tokenId) {
        stringRedisTemplate.delete(Constants.LOGIN_TOKEN_KEY + tokenId);
        return new Result();
    }
}

