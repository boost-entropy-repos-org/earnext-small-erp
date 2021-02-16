package top.dongxibao.erp.controller.system;

import top.dongxibao.erp.annotation.Log;
import top.dongxibao.erp.common.Result;
import top.dongxibao.erp.constant.SecurityConstants;
import top.dongxibao.erp.entity.system.SysMenu;
import top.dongxibao.erp.entity.system.SysUser;
import cn.hutool.core.util.IdUtil;
import com.wf.captcha.base.Captcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.dongxibao.erp.enums.BusinessType;
import top.dongxibao.erp.exception.ServiceException;
import top.dongxibao.erp.security.SecurityUtils;
import top.dongxibao.erp.security.dto.AuthUserDto;
import top.dongxibao.erp.security.dto.JwtUserDto;
import top.dongxibao.erp.security.entity.LoginCodeEnum;
import top.dongxibao.erp.security.entity.LoginProperties;
import top.dongxibao.erp.security.entity.SecurityProperties;
import top.dongxibao.erp.security.service.OnlineUserService;
import top.dongxibao.erp.security.service.TokenService;
import top.dongxibao.erp.service.system.SysMenuService;
import top.dongxibao.erp.service.system.SysUserService;
import top.dongxibao.erp.util.ServletUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName AuthorizationController
 * @Description 系统授权接口
 * @Author Dongxibao
 * @Date 2021/1/7
 * @Version 1.0
 */
@Slf4j
@RestController
@Api(tags = "系统：系统授权接口")
public class AuthorizationController {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private LoginProperties loginProperties;
    @Autowired
    private SecurityProperties securityProperties;
    @Resource
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private OnlineUserService onlineUserService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation("登录授权")
    @PostMapping(value = "/login")
    public Result login(@Validated @RequestBody AuthUserDto authUser, HttpServletRequest request) {
        // 密码解密
//        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, authUser.getPassword());
        // 查询验证码
        String code = (String) redisTemplate.opsForValue().get(authUser.getUuid());
        // 清除验证码
        redisTemplate.delete(authUser.getUuid());
        if (StringUtils.isBlank(code)) {
            throw new ServiceException("验证码不存在或已过期");
        }
        if (StringUtils.isBlank(authUser.getCode()) || !authUser.getCode().equalsIgnoreCase(code)) {
            throw new ServiceException("验证码错误");
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authUser.getUsername(), authUser.getPassword());
        Authentication authentication = null;
        try {
            // 调用
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException e) {
            log.error("用户不存在/密码错误");
            throw new ServiceException("用户不存在/密码错误");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();
        // 生成令牌
        String token = tokenService.createToken(jwtUserDto);
        // 返回 token 与 用户信息
        Map<String, Object> authInfo = new HashMap<String, Object>(2) {{
            put("token", securityProperties.getTokenStartWith() + token);
            put("user", jwtUserDto);
        }};
        if (loginProperties.isSingleLogin()) {
            onlineUserService.checkLoginOnUser(authUser.getUsername(),
                    SecurityConstants.LOGIN_TOKEN_KEY + jwtUserDto.getLoginRedisKey());
        }
        return Result.ok(authInfo);
    }

    @ApiOperation("获取用户信息")
    @GetMapping(value = "/info")
    public Result getUserInfo() {
        JwtUserDto currentUser = SecurityUtils.getCurrentUser();
        return Result.ok(currentUser);
    }

    @ApiOperation("获取验证码")
    @GetMapping(value = "/captcha_image")
    public Result captchaImage() {
        // 获取运算的结果
        Captcha captcha = loginProperties.getCaptcha();
        String uuid = SecurityConstants.CAPTCHA_CODE_KEY + IdUtil.simpleUUID();
        //当验证码类型为 arithmetic时且长度 >= 2 时，captcha.text()的结果有几率为浮点型
        String captchaValue = captcha.text();
        if (captcha.getCharType() - 1 == LoginCodeEnum.arithmetic.ordinal() && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }
        log.debug("验证码结果：{}", captchaValue);
        // 保存
        redisTemplate.opsForValue().set(uuid, captchaValue, loginProperties.getLoginCode().getExpiration(),
                TimeUnit.MINUTES);
        // 验证码信息
        Map<String, Object> imgResult = new HashMap<String, Object>(2) {{
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        return Result.ok(imgResult);
    }

    /**
     * 超级管理员重置密码
     *
     * @return 结果
     */
    @Log(title = "系统授权", businessType = BusinessType.UPDATE)
    @ApiOperation("超级管理员重置密码")
    @DeleteMapping("/resetPassword")
    public Result resetPassword(@RequestParam(value = "username") String username,
                                @RequestParam(value = "newPassword") String newPassword) {
        JwtUserDto jwtUserDto = tokenService.getLoginUser(ServletUtils.getRequest());
        if (jwtUserDto.getUser().isAdmin()) {
            String encryptNewPassword = SecurityUtils.encryptPassword(newPassword);
            sysUserService.resetUserPwd(username, encryptNewPassword);
            return Result.ok();
        } else {
            return Result.fail("当前登录用户不是超级管理员");
        }
    }

    /**
     * 修改密码
     *
     * @return 结果
     */
    @Log(title = "系统授权", businessType = BusinessType.UPDATE)
    @ApiOperation("修改密码")
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestParam(value = "oldPassword") String oldPassword,
                                 @RequestParam(value = "newPassword") String newPassword) {
        JwtUserDto jwtUserDto = tokenService.getLoginUser(ServletUtils.getRequest());
        String userName = jwtUserDto.getUsername();
        String password = jwtUserDto.getPassword();
        if (!SecurityUtils.matchesPassword(oldPassword, password)) {
            return Result.fail("修改密码失败，旧密码错误");
        }
        if (SecurityUtils.matchesPassword(newPassword, password)) {
            return Result.ok("新密码不能与旧密码相同");
        }
        String encryptNewPassword = SecurityUtils.encryptPassword(newPassword);
        if (sysUserService.resetUserPwd(userName, encryptNewPassword) > 0) {
            // 更新缓存用户密码
            jwtUserDto.getUser().setPassword(encryptNewPassword);
            tokenService.setLoginUser(jwtUserDto);
            return Result.ok();
        }
        return Result.fail("修改密码异常，请联系管理员");
    }

    /**
     * 获取路由信息
     */
    @ApiOperation("获取路由信息")
    @GetMapping("getRouters")
    public Result getRouters() {
        JwtUserDto loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
        // 用户信息
        SysUser user = loginUser.getUser();
        try {
            List<SysMenu> menus = sysMenuService.selectMenuTreeByUserId(user.getId());
            return Result.ok(sysMenuService.buildMenus(menus));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(e.getMessage());
        }
    }
}
