package top.dongxibao.erp.security;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 用户登录对象
 *
 * @author Dongxibao
 * @date 2020-06-21
 */
@Data
public class LoginBody {
    /**
     * 用户名
     */
    @NotEmpty(message = "登录账号不能为空")
    @ApiModelProperty(value = "登录账号")
    private String username;

    /**
     * 用户密码
     */
    @NotEmpty(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 验证码
     */
    private String code;

    /**
     * 唯一标识
     */
    private String uuid = "";
}
