package top.dongxibao.erp.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.TaskInfo;
import top.dongxibao.erp.constant.WorkflowConstants;

import java.util.Date;
import java.util.Map;

/**
 * 自定义 Activiti Task Info
 * @author Dongxibao
 * @date 2021-01-27
 */
@ApiModel("Activiti - 任务信息")
@Data
public class ActivitiTaskInfoVO {

    /**
     * DB id of the task.
     */
    @ApiModelProperty("任务id")
    private String taskId;

    /**
     * Name or title of the task.
     */
    @ApiModelProperty("任务名称")
    private String taskName;

    /**
     * Free text description of the task.
     */
    @ApiModelProperty("任务详情")
    private String description;

    /**
     * Indication of how important/urgent this task is
     */
    @ApiModelProperty("优先级")
    private int priority;

    /**
     * The {@link User.getId() userId} of the person that is responsible for this task.
     */
    @ApiModelProperty("所有者ID")
    private String owner;

    /**
     * The {@link User.getId() userId} of the person to which this task is delegated.
     */
    @ApiModelProperty("分配给")
    private String assignee;

    /**
     * Reference to the process instance or null if it is not related to a process instance.
     */
    @ApiModelProperty("流程实例ID")
    private String processInstanceId;

    /**
     * Reference to the path of execution or null if it is not related to a process instance.
     */
    @ApiModelProperty("运行ID")
    private String executionId;

    /**
     * Reference to the process definition or null if it is not related to a process.
     */
    @ApiModelProperty("流程定义ID")
    private String processDefinitionId;

    /**
     * The date/time when this task was created
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * The id of the activity in the process defining this task or null if this is not related to a process
     */
    @ApiModelProperty("任务定义Key")
    private String taskDefinitionKey;

    /**
     * Due date of the task.
     */
    @ApiModelProperty("过期日期")
    private Date dueDate;

    /**
     * The category of the task. This is an optional field and allows to 'tag' tasks as belonging to a certain category.
     */
    @ApiModelProperty("类别")
    private String category;

    /**
     * The parent task for which this task is a subtask
     */
    @ApiModelProperty("父任务ID")
    private String parentTaskId;

    /**
     * The tenant identifier of this task
     */
    private String tenantId;

    /**
     * The form key for the user task
     */
    private String formKey;

    /**
     * Returns the local task variables if requested in the task query
     */
    private Map<String, Object> taskLocalVariables;

    /**
     * Returns the process variables if requested in the task query
     */
    private Map<String, Object> processVariables;

    /**
     * The claim time of this task
     */
    @ApiModelProperty("声称时间")
    private Date claimTime;

    @ApiModelProperty("审批时间")
    private Date endTime;

    @ApiModelProperty("模块名称")
    private String  moduleName;

    @ApiModelProperty("单据号")
    private String businessNo;

    @ApiModelProperty("模块Id")
    private Long moduleId;

    @ApiModelProperty("发起人")
    private String initiator;

    public ActivitiTaskInfoVO() {
    }

    public ActivitiTaskInfoVO(TaskInfo taskInfo) {
        this.taskId = taskInfo.getId();
        this.taskName = taskInfo.getName();
        this.description = taskInfo.getDescription();
        this.priority = taskInfo.getPriority();
        this.owner = taskInfo.getOwner();
        this.assignee = taskInfo.getAssignee();
        this.processInstanceId = taskInfo.getProcessInstanceId();
        this.executionId = taskInfo.getExecutionId();
        this.processDefinitionId = taskInfo.getProcessDefinitionId();
        this.createTime = taskInfo.getCreateTime();
        this.taskDefinitionKey = taskInfo.getTaskDefinitionKey();
        this.dueDate = taskInfo.getDueDate();
        this.category = taskInfo.getCategory();
        this.parentTaskId = taskInfo.getParentTaskId();
        this.tenantId = taskInfo.getTenantId();
        this.formKey = taskInfo.getFormKey();
        this.taskLocalVariables = taskInfo.getTaskLocalVariables();
        Map<String, Object> processVariables = taskInfo.getProcessVariables();
        this.processVariables = processVariables;
        if (processVariables != null) {
            this.initiator = processVariables.get(WorkflowConstants.initiator) != null ? processVariables.get(WorkflowConstants.initiator).toString() : "";
            this.moduleId = processVariables.get(WorkflowConstants.moduleId) != null ? (Long) processVariables.get(WorkflowConstants.moduleId) : 0L;
            this.moduleName = processVariables.get(WorkflowConstants.moduleName) != null ? processVariables.get(WorkflowConstants.moduleName).toString() : "";
            this.businessNo = processVariables.get(WorkflowConstants.businessNo) != null ? processVariables.get(WorkflowConstants.businessNo).toString() : "";
        }
        this.claimTime = taskInfo.getClaimTime();
    }

    public ActivitiTaskInfoVO(HistoricTaskInstance taskInfo) {
        this.taskId = taskInfo.getId();
        this.taskName = taskInfo.getName();
        this.description = taskInfo.getDescription();
        this.priority = taskInfo.getPriority();
        this.owner = taskInfo.getOwner();
        this.assignee = taskInfo.getAssignee();
        this.processInstanceId = taskInfo.getProcessInstanceId();
        this.executionId = taskInfo.getExecutionId();
        this.processDefinitionId = taskInfo.getProcessDefinitionId();
        this.createTime = taskInfo.getCreateTime();
        this.taskDefinitionKey = taskInfo.getTaskDefinitionKey();
        this.dueDate = taskInfo.getDueDate();
        this.category = taskInfo.getCategory();
        this.parentTaskId = taskInfo.getParentTaskId();
        this.tenantId = taskInfo.getTenantId();
        this.formKey = taskInfo.getFormKey();
        this.taskLocalVariables = taskInfo.getTaskLocalVariables();
        Map<String, Object> processVariables = taskInfo.getProcessVariables();
        this.processVariables = processVariables;
        if (processVariables != null) {
            this.initiator = processVariables.get(WorkflowConstants.initiator) != null ? processVariables.get(WorkflowConstants.initiator).toString() : "";
            this.moduleId = processVariables.get(WorkflowConstants.moduleId) != null ? (Long) processVariables.get(WorkflowConstants.moduleId) : 0L;
            this.moduleName = processVariables.get(WorkflowConstants.moduleName) != null ? processVariables.get(WorkflowConstants.moduleName).toString() : "";
            this.businessNo = processVariables.get(WorkflowConstants.businessNo) != null ? processVariables.get(WorkflowConstants.businessNo).toString() : "";
        }
        this.claimTime = taskInfo.getClaimTime();
        this.endTime = taskInfo.getEndTime();
    }
}
