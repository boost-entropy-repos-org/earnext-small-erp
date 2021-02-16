package top.dongxibao.erp.entity.system;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.dongxibao.erp.common.BaseEntity;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单权限表
 *
 * @author Dongxibao
 * @date 2021-01-05
 */
@ApiModel("菜单权限表")
@Data
public class SysMenu extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /** 菜单名称 */
    @ApiModelProperty("菜单名称")
    @NotBlank(message = "菜单名称不能为空")
    private String menuName;
    /** 菜单名称-冗余 */
    @ApiModelProperty("菜单名称-冗余")
    private String label;
    /** 权限标识 */
    @ApiModelProperty("权限标识")
    @NotBlank(message = "权限标识不能为空")
    private String permsCode;
    /** 父菜单ID */
    @ApiModelProperty("父菜单ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;
    /** 父菜单name */
    @ApiModelProperty("父菜单name")
    private String parentName;
    /** 菜单级关联列表配置id */
    @ApiModelProperty("菜单级关联列表配置id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long formConfigId;
    /** 显示顺序 */
    @ApiModelProperty("显示顺序")
    private Integer orderNum;
    /** 路由地址 */
    @ApiModelProperty("路由地址")
    private String routingPath;
    /** 组件路径 */
    @ApiModelProperty("组件路径")
    private String component;
    /** 类型:M目录,C菜单,F按钮 */
    @ApiModelProperty("类型:M目录,C菜单,F按钮")
    private String menuType;
    /** 菜单状态:1显示,0隐藏 */
    @ApiModelProperty("菜单状态:1显示,0隐藏")
    @JsonSerialize(using = ToStringSerializer.class)
    private Integer visible;
    /** 菜单图标 */
    @ApiModelProperty("菜单图标")
    private String icon;


    /** 子菜单List */
    @ApiModelProperty("子菜单List")
    private List<SysMenu> children = new ArrayList<>();
}
