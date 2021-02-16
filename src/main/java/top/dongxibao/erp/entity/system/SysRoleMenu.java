package top.dongxibao.erp.entity.system;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.dongxibao.erp.common.BaseEntity;

/**
 * 角色和菜单关联表
 * 
 * @author Dongxibao
 * @date 2021-01-05
 */
@ApiModel("角色和菜单关联表")
@Data
public class SysRoleMenu extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/** 角色ID */
	@ApiModelProperty("角色ID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long roleId;
	/** 菜单ID */
	@ApiModelProperty("菜单ID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long menuId;
}
