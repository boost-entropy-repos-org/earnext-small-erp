package top.dongxibao.erp.entity.system;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import top.dongxibao.erp.common.BaseEntity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 用户信息表
 *
 * @author Dongxibao
 * @date 2021-01-05
 */
@ApiModel("用户信息表")
@Data
@ToString(callSuper = true)
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /** 登录账号 */
    @ApiModelProperty("登录账号")
    @NotBlank(message = "登录账号不能为空")
    private String username;
    /** 用户昵称 */
    @ApiModelProperty("用户昵称")
    @NotBlank(message = "用户昵称不能为空")
    private String nickName;
    /** 密码 */
    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;
    @JsonSerialize(using = ToStringSerializer.class)
    /** 部门ID */
    @ApiModelProperty("部门ID")
    private Long deptId;
    @JsonSerialize(using = ToStringSerializer.class)
    /** 岗位ID */
    @ApiModelProperty("岗位ID")
    private Long postId;
    /** 用户邮箱 */
    @ApiModelProperty("用户邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;
    /** 手机号码 */
    @ApiModelProperty("手机号码")
    @NotEmpty(message = "手机号码不能为空")
    private String phone;
    /** 性别(0未知;1男;2女) */
    @ApiModelProperty("性别(0未知;1男;2女)")
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer sex;
    /** 头像 */
    @ApiModelProperty("头像")
    private String avatar;
    /** 帐号状态:1正常,0禁用 */
    @ApiModelProperty("帐号状态:1正常,0禁用")
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer status;

    /** 冗余字段 */
    /** 角色对象List */
    @ApiModelProperty("角色对象List")
    private List<SysRole> roleList;
    /** 角色id数组 */
    @ApiModelProperty("角色id数组")
    private Long[] roleIds;
    private String deptName;
    private String leader;
    private Integer deptStatus;

    @JsonIgnore
    public boolean isAdmin() {
        return isAdmin(this.getId());
    }
    @JsonIgnore
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }
}
