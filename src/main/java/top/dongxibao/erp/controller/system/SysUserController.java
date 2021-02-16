package top.dongxibao.erp.controller.system;

import top.dongxibao.erp.annotation.Log;
import top.dongxibao.erp.common.BaseController;
import top.dongxibao.erp.common.Result;
import top.dongxibao.erp.common.ResultPage;
import top.dongxibao.erp.entity.system.SysUser;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.dongxibao.erp.enums.BusinessType;
import top.dongxibao.erp.exception.ServiceException;
import top.dongxibao.erp.service.system.SysUserService;
import top.dongxibao.erp.util.SqlUtil;

import java.util.Date;
import java.util.List;

/**
 * 用户信息表
 *
 * @author Dongxibao
 * @date 2021-01-05
 */
@Slf4j
@Api(tags = "用户信息表")
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 查询用户信息表
     */
    @ApiOperation("查询用户信息表列表")
    @GetMapping
    public Result page(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
                       @RequestParam(value = "deptId", required = false) Long deptId,
                       @RequestParam(value = "orderByColumn", required = false) String orderByColumn,
                       @RequestParam(value = "isAsc", required = false, defaultValue = "desc") String isAsc,
                       @RequestParam(value = "username", required = false) String username,
                       @RequestParam(value = "nickName", required = false) String nickName,
                       @RequestParam(value = "status", required = false) Integer status,
                       @RequestParam(value = "beginTime", required = false) Date beginTime,
                       @RequestParam(value = "endTime", required = false) Date endTime) {
        SysUser sysUser = new SysUser();
        sysUser.setBeginTime(beginTime);
        sysUser.setEndTime(endTime);
        if (StrUtil.isNotBlank(username)) {
            sysUser.setUsername("%" + username + "%");
        }
        if (StrUtil.isNotBlank(nickName)) {
            sysUser.setNickName("%" + nickName + "%");
        }
        sysUser.setStatus(status);
        sysUser.setDeptId(deptId);
        String orderBy = SqlUtil.escapeOrderBySql(super.packOrderBy(orderByColumn, isAsc));
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<SysUser> list = sysUserService.selectByCondition(sysUser);
        return Result.ok(ResultPage.restPage(list));
    }

    /**
     * 获取用户信息表详细信息
     */
    @ApiOperation("获取用户信息表详细信息")
    @GetMapping("/{id}")
    public Result get(@PathVariable("id") Long id) {
        return Result.ok(sysUserService.getById(id));
    }

    /**
     * 新增用户信息表
     */
    @Log(title = "用户信息", businessType = BusinessType.INSERT)
    @ApiOperation("新增用户信息表")
    @PostMapping
    public Result insert(@Validated @RequestBody SysUser sysUser) {
        if (sysUserService.checkUsernameExist(sysUser)) {
            throw new ServiceException("新增用户'" + sysUser.getUsername() + "'失败，登录账号已存在");
        }
        sysUser = sysUserService.save(sysUser);
        return Result.ok(sysUser);
    }

    /**
     * 修改用户信息表
     */
    @Log(title = "用户信息", businessType = BusinessType.UPDATE)
    @ApiOperation("修改用户信息表")
    @PutMapping("/{id}")
    public Result update(@PathVariable("id") Long id,
                         @Validated @RequestBody SysUser sysUser) {
        sysUser.setId(id);
        if (sysUserService.checkUsernameExist(sysUser)) {
            throw new ServiceException("修改用户'" + sysUser.getUsername() + "'失败，登录账号已存在");
        }
        sysUser = sysUserService.updateById(sysUser);
        return Result.ok(sysUser);
    }

    /**
     * 删除用户信息表
     */
    @Log(title = "用户信息", businessType = BusinessType.DELETE)
    @ApiOperation("删除用户信息表")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Long id) {
        if (id == null) {
            return Result.fail("id为空");
        }
        boolean delete = sysUserService.removeById(id);
        return Result.of(delete, null);
    }

    /**
     * 批量删除用户信息表
     *
     * @return 结果
     */
    @Log(title = "用户信息", businessType = BusinessType.DELETE)
    @ApiOperation("批量删除用户信息表")
    @DeleteMapping("/deleteAll")
    public Result deleteAll(@RequestBody List<Long> idList) {
        if (idList == null || idList.size() < 1) {
            return Result.fail("id为空");
        }
        boolean delete = sysUserService.removeByIds(idList);
        return Result.of(delete, null);
    }
}
