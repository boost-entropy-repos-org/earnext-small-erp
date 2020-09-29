package top.dongxibao.erp.entity.system;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.dongxibao.erp.common.BaseEntity;

/**
 * 角色和部门关联对象 sys_role_dept
 * 
 * @author Dongxibao
 * @date 2020-06-13
 */
@ApiModel(value="角色和部门关联对象", description="sys_role_dept")
@Data
public class SysRoleDept extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    @ApiModelProperty(value = "用户ID")
    private Long roleId;
    /** 岗位ID */
    @ApiModelProperty(value = "岗位ID")
    private Long deptId;
    /** 1:删除;0：正常 */
}
