package top.dongxibao.erp.entity.system;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import top.dongxibao.erp.common.BaseEntity;

/**
 * 业务模块关联流程
 * 
 * @author Dongxibao
 * @date 2021-01-25
 */
@ApiModel("业务模块关联流程")
@Data
public class ProcessSetting extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/** 关联模块id */
	@ApiModelProperty("关联模块id")
	private Long moduleId;
	/** 关联模块name */
	@ApiModelProperty("关联模块name")
	private String moduleName;
	/** 流程实例key */
	@ApiModelProperty("流程实例key")
	private String processDefinitionKey;
	@ApiModelProperty("会审审批人")
	private String assignees;
}
