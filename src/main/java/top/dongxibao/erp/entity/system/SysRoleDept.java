package top.dongxibao.erp.entity.system;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.dongxibao.erp.common.BaseEntity;

/**
 * 角色和部门关联表
 * 
 * @author Dongxibao
 * @date 2021-01-12
 */
@ApiModel("角色和部门关联表")
@Data
public class SysRoleDept extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/** 用户ID */
	@ApiModelProperty("用户ID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long roleId;
	/** 岗位ID */
	@ApiModelProperty("岗位ID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long deptId;
}
