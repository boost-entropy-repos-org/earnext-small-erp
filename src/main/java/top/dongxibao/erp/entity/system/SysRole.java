package top.dongxibao.erp.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.dongxibao.erp.common.BaseEntity;

import javax.validation.constraints.NotEmpty;

/**
 * 角色信息对象 sys_role2
 * 
 * @author Dongxibao
 * @date 2020-06-13
 */
@ApiModel(value="角色信息对象", description="sys_role")
@Data
public class SysRole extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 角色名称 */
    @NotEmpty(message = "角色名称不能为空")
    @ApiModelProperty(value = "角色名称")
    private String roleName;
    /** 角色权限标识 */
    @NotEmpty(message = "角色权限标识不能为空")
    @ApiModelProperty(value = "角色权限标识")
    private String roleCode;
    /** 显示顺序 */
    @ApiModelProperty(value = "显示顺序")
    private Integer roleSort;
    /** 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限；5：仅本人数据权限） */
    @ApiModelProperty(value = "数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限；5：仅本人数据权限）")
    private String dataScope;
    /** 角色状态:0正常,1禁用 */
    @ApiModelProperty(value = "角色状态:0正常,1禁用")
    private Integer status;

    // *************************
    /** 菜单组 */
    @ApiModelProperty(value = "菜单组")
    @TableField(exist = false)
    private Long[] menuIds;

    /** 部门组（数据权限） */
    @ApiModelProperty(value = "部门组")
    @TableField(exist = false)
    private Long[] deptIds;
}
