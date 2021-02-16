package top.dongxibao.erp.service.system;

import top.dongxibao.erp.entity.vo.ActivitiTaskInfoVO;
import top.dongxibao.erp.entity.vo.SubmitVariablesVO;
import top.dongxibao.erp.entity.vo.TaskHistoryVO;

import java.util.List;
import java.util.Map;

/**
 *@ClassName ActivitiService
 *@Description
 *@Author Dongxibao
 *@Date 2021/1/26
 *@Version 1.0
 */
public interface ActivitiService {

    /**
     * 通用提交
     * @param submitVariables
     * @return
     */
    String submit(SubmitVariablesVO submitVariables);

    /**
     * 通用审批
     * @param taskId
     * @param processInstanceId
     * @param commentText
     * @param agree
     * @param variables
     */
    void complete(String taskId, String processInstanceId, String commentText, boolean agree, Map<String, Object> variables);

    /**
     * 当前用户待审批查询
     * @param variables
     * @return
     */
    List<ActivitiTaskInfoVO> selectTodoTasks(Map<String, Object> variables);

    /**
     * 当前用户已审批查询
     * @param variables
     * @return
     */
    List<ActivitiTaskInfoVO> selectFinishTasks(Map<String, Object> variables);

    /**
     * 当前用户审批历史查询
     * @param businessId
     * @param moduleId
     * @return
     */
    List<TaskHistoryVO> listHistory(String businessId, String moduleId);
}
