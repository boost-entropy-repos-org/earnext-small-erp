package top.dongxibao.erp.security.handle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import top.dongxibao.erp.common.Result;
import top.dongxibao.erp.constant.Constants;
import top.dongxibao.erp.security.JWTUser;
import top.dongxibao.erp.security.SecurityUtils;
import top.dongxibao.erp.security.service.TokenUtils;
import top.dongxibao.erp.util.JsonUtils;
import top.dongxibao.erp.util.LoginLogUtils;
import top.dongxibao.erp.util.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义退出处理类 返回成功
 *
 * @author Dongxibao
 * @date 2020-06-21
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Autowired
    private TokenUtils tokenUtils;


    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) {
        JWTUser jwtUser = tokenUtils.getLoginUser(request);
        if (jwtUser != null) {
            String username = jwtUser.getUsername();
            // 删除用户缓存记录
            tokenUtils.delLoginUser(jwtUser.getJwtRedisKey());
            // 记录用户退出日志
            LoginLogUtils.packageSysLoginLog(username, Constants.SUCCESS, "退出成功");
        }
        ServletUtils.renderString(response, JsonUtils.obj2Json(new Result<>(HttpStatus.OK.value(), "退出成功")));
    }
}
