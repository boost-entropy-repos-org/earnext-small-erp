package top.dongxibao.erp.entity.system;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.dongxibao.erp.common.BaseEntity;

/**
 * 用户和角色关联对象 sys_user_role2
 * 
 * @author Dongxibao
 * @date 2020-06-13
 */
@ApiModel(value="用户和角色关联对象", description="sys_user_role")
@Data
public class SysUserRole extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    @ApiModelProperty(value = "用户ID")
    private Long userId;
    /** 角色ID */
    @ApiModelProperty(value = "角色ID")
    private Long roleId;
}
