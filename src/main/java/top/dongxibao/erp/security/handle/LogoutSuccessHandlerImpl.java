package top.dongxibao.erp.security.handle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import top.dongxibao.erp.common.Result;
import top.dongxibao.erp.security.dto.JwtUserDto;
import top.dongxibao.erp.security.service.TokenService;
import top.dongxibao.erp.util.JsonUtils;
import top.dongxibao.erp.util.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义退出处理类 返回成功
 *
 * @author Dongxibao
 * @date 2021-1-7
 */
@Slf4j
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Autowired
    private TokenService tokenService;

    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        JwtUserDto loginUser = tokenService.getLoginUser(request);
        if (loginUser != null) {
            log.info("用户退出：{}", loginUser.getUsername());
            SecurityContextHolder.clearContext();
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getLoginRedisKey());
            // todo 记录用户退出日志，测试退出是否走
        }
        ServletUtils.renderString(response, JsonUtils.obj2Json(Result.ok("退出成功", null)));
    }
}
