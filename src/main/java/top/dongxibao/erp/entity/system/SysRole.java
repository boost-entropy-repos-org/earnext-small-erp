package top.dongxibao.erp.entity.system;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.dongxibao.erp.common.BaseEntity;

import javax.validation.constraints.NotEmpty;

/**
 * 角色信息表
 *
 * @author Dongxibao
 * @date 2021-01-05
 */
@ApiModel("角色信息表")
@Data
public class SysRole extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/** 角色名称 */
	@ApiModelProperty("角色名称")
	@NotEmpty(message = "角色名称不能为空")
	private String roleName;
	/** 角色权限字符串 */
	@ApiModelProperty("角色权限字符串")
	@NotEmpty(message = "角色权限不能为空")
	private String roleCode;
	/** 显示顺序 */
	@ApiModelProperty("显示顺序")
	private Integer roleSort;
	/** 数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限；5：仅本人数据权限） */
	@ApiModelProperty("数据范围（1：所有数据权限；2：自定义数据权限；3：本部门数据权限；4：本部门及以下数据权限；5：仅本人数据权限）")
	private Integer dataScope;
	/** 角色状态:1正常,0禁用 */
	@ApiModelProperty("角色状态:1正常,0禁用")
	@JsonSerialize(using = ToStringSerializer.class)
	private Integer status;

    /** 冗余 */
	@ApiModelProperty("用户表id")
	private Long userId;
	/** 菜单id数组 */
	@ApiModelProperty("菜单id数组")
	private Long[] menuIds;
	/** 部门组（数据权限） */
	@ApiModelProperty("部门组（数据权限）")
	private Long[] deptIds;
}
