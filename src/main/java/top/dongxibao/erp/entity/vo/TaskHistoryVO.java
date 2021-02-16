package top.dongxibao.erp.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 *@ClassName TaskHistoryVO
 *@Description 审批历史
 *@Author Dongxibao
 *@Date 2021/1/28
 *@Version 1.0
 */
@ApiModel("审批记录")
@Data
public class TaskHistoryVO {
    @ApiModelProperty("任务名称")
    private String taskName;
    @ApiModelProperty("处理人")
    private String assignee;
    @ApiModelProperty("处理人名称")
    private String assigneeName;
    @ApiModelProperty("审批意见")
    private String commentMessage;
    @ApiModelProperty("开始时间")
    private Date startTime;
    @ApiModelProperty("结束时间")
    private Date endTime;
    @ApiModelProperty("耗时")
    private Long durationTime;
    @ApiModelProperty("提交人")
    private String initiator;
    @ApiModelProperty("提交时间")
    private Date createTime;
}
