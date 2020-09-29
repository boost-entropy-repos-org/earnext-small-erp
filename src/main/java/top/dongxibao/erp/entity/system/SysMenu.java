package top.dongxibao.erp.entity.system;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.dongxibao.erp.common.BaseEntity;

import javax.validation.constraints.NotEmpty;

/**
 * 菜单权限对象 sys_menu2
 * 
 * @author Dongxibao
 * @date 2020-06-13
 */
@ApiModel(value="菜单权限对象", description="sys_menu")
@Data
public class SysMenu extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 菜单名称 */
    @ApiModelProperty(value = "菜单名称")
    private String menuName;
    /** 权限标识 */
    @NotEmpty(message = "权限标识不能为空")
    @ApiModelProperty(value = "权限标识")
    private String permsCode;
    /** 父菜单ID */
    @ApiModelProperty(value = "父菜单ID")
    private Long parentId;
    /** 显示顺序 */
    @ApiModelProperty(value = "显示顺序")
    private Integer orderNum;
    /** 请求地址 */
    @ApiModelProperty(value = "请求地址")
    private String url;
    /** 类型:M目录,C菜单,F按钮 */
    @ApiModelProperty(value = "类型:M目录,C菜单,F按钮")
    private String menuType;
    /** 菜单状态:0显示,1隐藏 */
    @ApiModelProperty(value = "菜单状态:0显示,1隐藏")
    private Integer visible;
    /** 菜单图标 */
    @ApiModelProperty(value = "菜单图标")
    private String icon;
}
