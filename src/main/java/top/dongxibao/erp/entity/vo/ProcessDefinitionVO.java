package top.dongxibao.erp.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 *@ClassName ProcessDefinitionVo
 *@Description 流程定义
 *@Author Dongxibao
 *@Date 2021/1/21
 *@Version 1.0
 */
@ApiModel("流程定义VO")
@Data
public class ProcessDefinitionVO {
    @ApiModelProperty(name = "流程定义id")
    private String processDefinitionId;

    @ApiModelProperty(name = "流程名称")
    private String name;

    @ApiModelProperty(name = "流程KEY")
    private String key;

    @ApiModelProperty(name = "流程版本")
    private int version;

    @ApiModelProperty(name = "所属分类")
    private String category;

    @ApiModelProperty(name = "流程描述")
    private String description;

    @ApiModelProperty("流程部署id")
    private String deploymentId;

    @ApiModelProperty(name = "部署时间")
    private Date deploymentTime;

    @ApiModelProperty(name = "流程图")
    private String diagramResourceName;

    @ApiModelProperty(name = "流程定义")
    private String resourceName;

    /** 流程实例状态 1 激活 2 挂起 */
    @ApiModelProperty("流程实例状态 1 激活 2 挂起")
    private String suspendState;

    private String suspendStateName;
}
