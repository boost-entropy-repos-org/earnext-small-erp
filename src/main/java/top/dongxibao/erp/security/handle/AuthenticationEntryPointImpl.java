package top.dongxibao.erp.security.handle;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import top.dongxibao.erp.common.Result;
import top.dongxibao.erp.util.JsonUtils;
import top.dongxibao.erp.util.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * 认证失败处理类 返回未授权
 *
 * @author Dongxibao
 * @date 2021-1-7
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = 4972516347010952383L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        int code = HttpStatus.UNAUTHORIZED.value();
        String msg = "请求访问："+request.getRequestURI()+"，认证失败，无法访问系统资源";
        ServletUtils.renderString(response, JsonUtils.obj2Json(new Result(msg, false, code, null)));
    }
}
