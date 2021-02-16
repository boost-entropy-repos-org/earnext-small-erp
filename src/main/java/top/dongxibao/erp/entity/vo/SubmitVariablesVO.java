package top.dongxibao.erp.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 *@ClassName SubmitVariables
 *@Description 提交参数
 *@Author Dongxibao
 *@Date 2021/1/26
 *@Version 1.0
 */
@ApiModel("提交参数")
@Data
public class SubmitVariablesVO {
    /** 模块id　**/
    @NotBlank(message = "模块id不能为空")
    @ApiModelProperty("模块id")
    private Long moduleId;
    /** 模块名称 **/
    @ApiModelProperty("模块名称")
    private String moduleName;
    /** 业务id **/
    @NotBlank(message = "业务id不能为空")
    @ApiModelProperty("业务id")
    private String businessId;
    /** 业务单据号 **/
    @ApiModelProperty("业务单据号")
    private String businessNo;
    /** 单据金额 **/
    @ApiModelProperty("单据金额")
    private BigDecimal amount;
    /** 表名 **/
    @NotBlank(message = "表名不能为空")
    @ApiModelProperty("表名")
    private String tableName;
    /** 提交人 **/
    @ApiModelProperty("提交人")
    private String initiator;
    /** 审批流条件 **/
    @ApiModelProperty("审批流条件")
    private Map<String, Object> condition = new HashMap<>();

    public Map<String, Object> getCondition() {
        if (condition == null) {
            return new HashMap<>();
        }
        return condition;
    }
}
