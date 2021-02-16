package top.dongxibao.erp.security.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户登录对象
 *
 * @author Dongxibao
 * @date 2021-01-08
 */
@ApiModel("用户登录信息")
@Data
public class AuthUserDto {
    /**
     * 用户名
     */
    @NotBlank
    private String username;

    /**
     * 用户密码
     */
    @NotBlank
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
