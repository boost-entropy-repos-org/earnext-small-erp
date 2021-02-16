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
 * 部门表
 * 
 * @author Dongxibao
 * @date 2021-01-12
 */
@ApiModel("部门表")
@Data
public class SysDept extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/** 父部门id */
	@ApiModelProperty("父部门id")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentId;
	/** 部门编码 */
	@ApiModelProperty("部门编码")
	@NotBlank(message = "部门编码不能为空")
	private String deptCode;
	/** 部门名称 */
	@ApiModelProperty("部门名称")
	@NotBlank(message = "部门名称不能为空")
	private String deptName;
	/** 显示顺序 */
	@ApiModelProperty("显示顺序")
	private Integer deptSort;
	/** 负责人 */
	@ApiModelProperty("负责人")
	private String leader;
	/** 联系电话 */
	@ApiModelProperty("联系电话")
	private String phone;
	/** 邮箱 */
	@ApiModelProperty("邮箱")
	private String email;
	/** 部门状态:1正常,0停用 */
	@ApiModelProperty("部门状态:1正常,0停用")
	@JsonSerialize(using = ToStringSerializer.class)
	private Integer status;

	@ApiModelProperty("子列表")
	List<SysDept> children = new ArrayList<>();
}
