package top.dongxibao.erp.service.system.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.dongxibao.erp.constant.WorkflowConstants;
import top.dongxibao.erp.entity.system.ProcessSetting;
import top.dongxibao.erp.entity.system.SysUser;
import top.dongxibao.erp.entity.vo.ActivitiTaskInfoVO;
import top.dongxibao.erp.entity.vo.SubmitVariablesVO;
import top.dongxibao.erp.entity.vo.TaskHistoryVO;
import top.dongxibao.erp.enums.ApproveStatus;
import top.dongxibao.erp.exception.ServiceException;
import top.dongxibao.erp.mapper.system.ActivitiMapper;
import top.dongxibao.erp.mapper.system.ProcessSettingMapper;
import top.dongxibao.erp.mapper.system.SysUserMapper;
import top.dongxibao.erp.security.SecurityUtils;
import top.dongxibao.erp.service.system.ActivitiService;
import top.dongxibao.erp.util.SpringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 *@ClassName ActivitiServiceImpl
 *@Description service
 *@Author Dongxibao
 *@Date 2021/1/26
 *@Version 1.0
 */
@Slf4j
@Service
public class ActivitiServiceImpl implements ActivitiService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private ProcessSettingMapper processSettingMapper;
    @Autowired
    private ActivitiMapper activitiMapper;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String submit(SubmitVariablesVO submitVariables) {
        // 1.查询关联表找到key
        ProcessSetting processSetting = processSettingMapper.selectByModuleId(Long.valueOf(submitVariables.getModuleId()));
        if (processSetting == null) {
            throw new ServiceException("请联系管理员配置[业务模块关联流程]");
        }
        Map<String, Object> variables = new HashMap<>();
        variables.put(WorkflowConstants.moduleId, submitVariables.getModuleId());
        variables.put(WorkflowConstants.moduleName, submitVariables.getModuleName());
        variables.put(WorkflowConstants.businessId, submitVariables.getBusinessId());
        variables.put(WorkflowConstants.businessNo, submitVariables.getBusinessNo());
        variables.put(WorkflowConstants.amount, submitVariables.getAmount());
        variables.put(WorkflowConstants.tableName, submitVariables.getTableName());
        variables.put(WorkflowConstants.initiator, SecurityUtils.getCurrentUsername());
        variables.putAll(submitVariables.getCondition());
        // 2.根据key启动流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processSetting.getProcessDefinitionKey(), variables);
        // 3.修改业务状态
        String updateSql = "update " + submitVariables.getTableName() +
                " set process_instance_id = '" + processInstance.getId() + "'"
                + "," + "placed = '" + ApproveStatus.SUBMITTED.code + "'"
                + "," + "update_time = " + "sysdate()"
                + "," + "update_by = '" + SecurityUtils.getCurrentUsername() + "'"
                + " where id = " + submitVariables.getBusinessId();
        activitiMapper.update(updateSql);
        return processInstance.getId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void complete(String taskId, String processInstanceId, String commentText, boolean agree, Map<String, Object> variables) {
        Map<String, Object> submitVariables = runtimeService.getVariables(processInstanceId);
        taskService.addComment(taskId, processInstanceId, commentText);
        // 添加意见
        if (StrUtil.isNotBlank(processInstanceId) && StringUtils.isNotBlank(commentText)) {
            taskService.addComment(taskId, processInstanceId, commentText);
        }
        // 设置流程变量
        if (variables == null) {
            variables = new HashMap<>(4);
        }
        String placed = ApproveStatus.SUBMITTED.code;
        if (agree) {
            variables.put("flag", true);
            // 被委派人处理完成任务
            // p.s. 被委托的流程需要先 resolved 这个任务再提交。
            // 所以在 complete 之前需要先 resolved
            // resolveTask() 要在 claim() 之前，不然 act_hi_taskinst 表的 assignee 字段会为 null
            taskService.resolveTask(taskId, variables);
            // 只有签收任务，act_hi_taskinst 表的 assignee 字段才不为 null
            taskService.claim(taskId, SecurityUtils.getCurrentUsername());
            taskService.complete(taskId, variables);
            if (processIsFinish(processInstanceId)) {
                placed = ApproveStatus.APPROVED.code;
            }
        } else {
            placed = ApproveStatus.REJECTED.code;
            variables.put("flag", false);

            taskService.resolveTask(taskId, variables);
            taskService.claim(taskId, SecurityUtils.getCurrentUsername());
//            taskService.complete(taskId, variables);

            runtimeService.deleteProcessInstance(processInstanceId, "当前审批人驳回");
        }
        String businessId = submitVariables.get(WorkflowConstants.businessId).toString();
        String tableName = submitVariables.get(WorkflowConstants.tableName).toString();
        String updateSql = "update " + tableName +
                " set placed = '" + placed + "'"
                + "," + "update_time = " + "sysdate()"
                + "," + "update_by = '" + SecurityUtils.getCurrentUsername() + "'"
                + " where id = " + businessId
                + " and process_instance_id = " + processInstanceId;
        activitiMapper.update(updateSql);
    }

    @Override
    public List<ActivitiTaskInfoVO> selectTodoTasks(Map<String, Object> variables) {
        TaskQuery taskQuery = taskService.createTaskQuery()
                .includeProcessVariables()
                .taskCandidateOrAssigned(SecurityUtils.getCurrentUsername());
        Set<String> keys = variables.keySet();
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            String key = it.next();
            if (!key.equals("beginTime") && !key.equals("endTime")) {
                String value = variables.get(key) + "";
                taskQuery.processVariableValueLikeIgnoreCase(key, "%" + value + "%");
            }
        }
        taskQuery.includeProcessVariables();
        if (variables.get("beginTime") != null) {
            taskQuery.taskCreatedAfter((Date) variables.get("beginTime"));
        }
        if (variables.get("endTime") != null) {
            taskQuery.taskCreatedBefore((Date) variables.get("endTime"));
        }
        List<Task> list = taskQuery.orderByTaskCreateTime().desc().list();
        List<ActivitiTaskInfoVO> activitiTaskInfoVOList = list.stream().map(task -> new ActivitiTaskInfoVO(task)).collect(Collectors.toList());
        return activitiTaskInfoVOList;
    }

    @Override
    public List<ActivitiTaskInfoVO> selectFinishTasks(Map<String, Object> variables) {
        HistoricTaskInstanceQuery taskInstanceQuery = historyService.createHistoricTaskInstanceQuery()
                .includeProcessVariables()
                .taskAssignee(SecurityUtils.getCurrentUsername())
                .finished();
        Set<String> keys = variables.keySet();
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            String key = it.next();
            if (!key.equals("beginTime") && !key.equals("endTime")) {
                String value = variables.get(key) + "";
                taskInstanceQuery.processVariableValueLikeIgnoreCase(key, "%" + value + "%");
            }
        }
        taskInstanceQuery.includeProcessVariables();
        if (variables.get("beginTime") != null) {
            taskInstanceQuery.taskCreatedAfter((Date) variables.get("beginTime"));
        }
        if (variables.get("endTime") != null) {
            taskInstanceQuery.taskCreatedBefore((Date) variables.get("endTime"));
        }
        List<HistoricTaskInstance> historicTaskInstances = taskInstanceQuery.orderByHistoricTaskInstanceEndTime().desc().list();
        List<ActivitiTaskInfoVO> list = historicTaskInstances.stream().map(historicTaskInstance -> new ActivitiTaskInfoVO(historicTaskInstance))
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public List<TaskHistoryVO> listHistory(String businessId, String moduleId) {
        List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                .includeProcessVariables()
                .processVariableValueEqualsIgnoreCase(WorkflowConstants.businessId, businessId)
                .processVariableValueEqualsIgnoreCase(WorkflowConstants.moduleId, moduleId)
                .orderByTaskCreateTime()
                .asc()
                .finished()
                .list();
        List<TaskHistoryVO> taskHistoryVOS = null;
        if (CollUtil.isNotEmpty(historicTaskInstances)) {
            taskHistoryVOS = historicTaskInstances.stream().map(historicTaskInstance -> {
                TaskHistoryVO taskHistoryVO = new TaskHistoryVO();
                String assignee = historicTaskInstance.getAssignee();
                taskHistoryVO.setAssignee(assignee);
                SysUser sysUser = sysUserMapper.selectUserByUserName(assignee);
                if (sysUser != null) {
                    taskHistoryVO.setAssigneeName(sysUser.getNickName());
                }
                List<Comment> comment = taskService.getTaskComments(historicTaskInstance.getId(), "comment");
                if (!CollUtil.isEmpty(comment)) {
                    taskHistoryVO.setCommentMessage(comment.get(0).getFullMessage());
                }
                taskHistoryVO.setCreateTime(historicTaskInstance.getCreateTime());
                taskHistoryVO.setDurationTime(historicTaskInstance.getDurationInMillis());
                taskHistoryVO.setStartTime(historicTaskInstance.getStartTime());
                taskHistoryVO.setEndTime(historicTaskInstance.getEndTime());
                taskHistoryVO.setTaskName(historicTaskInstance.getName());
                Map<String, Object> processVariables = historicTaskInstance.getProcessVariables();
                if (MapUtil.isNotEmpty(processVariables)) {
                    taskHistoryVO.setInitiator(Optional.ofNullable(processVariables.get(WorkflowConstants.initiator)).orElse("").toString());
                }
                return taskHistoryVO;
            }).collect(Collectors.toList());
        }
        return taskHistoryVOS;
    }

    /**
     * 流程是否已经结束
     *
     * @param processInstanceId
     * @return
     */
    public static boolean processIsFinish(String processInstanceId) {
        RuntimeService runtimeService = SpringUtils.getBean(RuntimeService.class);
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processDefinitionId(processInstanceId).singleResult();
        return processInstance == null || processInstance.isEnded();
    }
}
