package top.dongxibao.erp.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import top.dongxibao.erp.common.BaseEntity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author Dongxibao
 * @date 2020-06-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="SysUser对象", description="用户信息表")
public class SysUser extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "部门ID")
    private Long deptId;

    @NotEmpty(message = "登录账号不能为空")
    @ApiModelProperty(value = "登录账号")
    protected String username;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @NotEmpty(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String password;

    @Email(message = "邮箱格式不正确")
    @ApiModelProperty(value = "用户邮箱")
    private String email;

    @NotEmpty(message = "手机号码不能为空")
    @ApiModelProperty(value = "手机号码")
    private String phone;

    @ApiModelProperty(value = "性别(0未知;1男;2女)")
    private Integer sex;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "帐号状态:0正常,1禁用")
    private Integer status;

    /** 角色对象 */
    @ApiModelProperty(value = "角色对象")
    @TableField(exist = false)
    private List<SysRole> roleList;

    /** 角色组 */
    @ApiModelProperty(value = "角色组")
    @TableField(exist = false)
    private Long[] roleIds;

    /** 岗位组 */
    @ApiModelProperty(value = "岗位组")
    @TableField(exist = false)
    private Long[] postIds;

    /** 角色对象 */
    @ApiModelProperty(value = "角色对象")
    @TableField(exist = false)
    private List<SysRole> roles;
    /** 部门 */
    @ApiModelProperty(value = "部门")
    @TableField(exist = false)
    private SysDept dept;;

    @JsonIgnore
    public boolean isAdmin() {
        return username != null && "admin".equalsIgnoreCase(this.username);
    }

}
