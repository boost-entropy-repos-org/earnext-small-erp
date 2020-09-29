package top.dongxibao.erp.security.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import top.dongxibao.erp.constant.Constants;
import top.dongxibao.erp.exception.ServiceException;
import top.dongxibao.erp.security.JWTUser;
import top.dongxibao.erp.util.LoginLogUtils;

/**
 * 登录校验方法
 *
 * @author Dongxibao
 * @date 2020-06-21
 */
@Component
public class SysLoginService {
    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid) {
        if (StringUtils.isNotEmpty(code) && StringUtils.isNotEmpty(uuid)) {
            String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
            String captcha = stringRedisTemplate.opsForValue().get(verifyKey);
            stringRedisTemplate.delete(verifyKey);
            if (captcha == null) {
                LoginLogUtils.packageSysLoginLog(username, Constants.FAIL, "验证码已过期");
                throw new ServiceException("验证码已过期");
            }
            if (!code.equalsIgnoreCase(captcha)) {
                LoginLogUtils.packageSysLoginLog(username, Constants.FAIL, "验证码错误");
                throw new ServiceException("验证码错误");
            }
        }
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                LoginLogUtils.packageSysLoginLog(username, Constants.FAIL, "用户名或密码错误");
                throw new ServiceException("用户名或密码错误", 400);
            } else {
                LoginLogUtils.packageSysLoginLog(username, Constants.FAIL, e.getMessage());
                throw new ServiceException(e.getMessage(), 400);
            }
        }
        // 记录登陆用户信息
        LoginLogUtils.packageSysLoginLog(username, Constants.SUCCESS, "登陆成功");

        JWTUser jwtUser = (JWTUser) authentication.getPrincipal();
        // 生成token
        return tokenUtils.createToken(jwtUser);
    }
}
