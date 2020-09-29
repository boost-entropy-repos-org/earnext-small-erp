package top.dongxibao.erp.entity.system;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.dongxibao.erp.common.BaseEntity;

/**
 * 角色和菜单关联对象 sys_role_menu
 * 
 * @author Dongxibao
 * @date 2020-06-13
 */
@ApiModel(value="角色和菜单关联对象", description="sys_role_menu")
@Data
public class SysRoleMenu extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 角色ID */
    @ApiModelProperty(value = "角色ID")
    private Long roleId;
    /** 菜单ID */
    @ApiModelProperty(value = "菜单ID")
    private Long menuId;
}
