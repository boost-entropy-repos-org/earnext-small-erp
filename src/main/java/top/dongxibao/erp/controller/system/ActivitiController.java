package top.dongxibao.erp.controller.system;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.dongxibao.erp.annotation.Log;
import top.dongxibao.erp.common.BaseController;
import top.dongxibao.erp.common.Page;
import top.dongxibao.erp.common.Result;
import top.dongxibao.erp.constant.WorkflowConstants;
import top.dongxibao.erp.entity.vo.ActivitiTaskInfoVO;
import top.dongxibao.erp.entity.vo.SubmitVariablesVO;
import top.dongxibao.erp.enums.BusinessType;
import top.dongxibao.erp.service.system.ActivitiService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@ClassName ActivitiController
 *@Description 审批流程
 *@Author Dongxibao
 *@Date 2021/1/26
 *@Version 1.0
 */
@Api(tags = "流程-审批流程")
@RestController("activitiController")
@RequestMapping("/activiti")
public class ActivitiController extends BaseController {

    @Autowired
    private ActivitiService activitiService;

    /**
     * 提交申请
     */
    @ApiOperation("提交申请")
    @Log(title = "审批流程", businessType = BusinessType.INSERT)
    @PostMapping("/submit")
    public Result submitApply(SubmitVariablesVO submitVariables) {
        try {
            String submit = activitiService.submit(submitVariables);
            return Result.ok(submit);
        } catch (Exception e) {
            return Result.fail("提交报错：" + e.getMessage());
        }
    }

    /**
     * 审批同意
     */
    @ApiOperation("审批同意")
    @Log(title = "审批流程", businessType = BusinessType.UPDATE)
    @PostMapping("/agree")
    public Result complete(@RequestParam(value = "taskId", required = false) String taskId,
                           @RequestParam(value = "processInstanceId", required = false) String processInstanceId,
                           @RequestParam(value = "commentText", required = false) String commentText) {
        try {
            if (StrUtil.isEmpty(commentText)) {
                commentText = "同意";
            }
            activitiService.complete(taskId, processInstanceId, commentText, true, null);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("审批通过报错：" + e.getMessage());
        }
    }

    /**
     * 审批驳回
     */
    @ApiOperation("审批驳回")
    @Log(title = "审批流程", businessType = BusinessType.UPDATE)
    @PostMapping("/reject")
    public Result reject(@RequestParam(value = "taskId", required = false) String taskId,
                         @RequestParam(value = "processInstanceId", required = false) String processInstanceId,
                         @RequestParam(value = "commentText", required = false) String commentText) {
        try {
            if (StrUtil.isEmpty(commentText)) {
                commentText = "驳回";
            }
            activitiService.complete(taskId, processInstanceId, commentText, false, null);
            return Result.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.ok("驳回报错：" + e.getMessage());
        }
    }

    @ApiOperation("待审批")
    @GetMapping("/todo_tasks")
    public Result selectTodoTasks(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
                                  @RequestParam(value = "initiator", required = false) String initiator,
                                  @RequestParam(value = "moduleName", required = false) String moduleName,
                                  @RequestParam(value = "businessNo", required = false) String businessNo,
                                  @RequestParam(value = "beginTime", required = false) Date beginTime,
                                  @RequestParam(value = "endTime", required = false) Date endTime) {
        Map<String, Object> variables = new HashMap<>(8);
        if (StrUtil.isNotEmpty(initiator)) {
            variables.put(WorkflowConstants.initiator, initiator);
        }
        if (StrUtil.isNotEmpty(moduleName)) {
            variables.put(WorkflowConstants.moduleName, moduleName);
        }
        if (StrUtil.isNotEmpty(businessNo)) {
            variables.put(WorkflowConstants.businessNo, businessNo);
        }
        if (beginTime != null) {
            variables.put("beginTime", beginTime);
        }
        if (endTime != null) {
            variables.put("endTime", endTime);
        }
        try {
            List<ActivitiTaskInfoVO> activitiTaskInfoVOS = activitiService.selectTodoTasks(variables);
            return Result.ok(new Page<>(activitiTaskInfoVOS, pageNum, pageSize));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("待审批报错：" + e.getMessage());
        }
    }

    @ApiOperation("已审批列表")
    @GetMapping("/finish_tasks")
    public Result selectFinishTasks(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                    @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
                                    @RequestParam(value = "initiator", required = false) String initiator,
                                    @RequestParam(value = "moduleName", required = false) String moduleName,
                                    @RequestParam(value = "businessNo", required = false) String businessNo,
                                    @RequestParam(value = "beginTime", required = false) Date beginTime,
                                    @RequestParam(value = "endTime", required = false) Date endTime) {
        Map<String, Object> variables = new HashMap<>(8);
        if (StrUtil.isNotEmpty(initiator)) {
            variables.put(WorkflowConstants.initiator, initiator);
        }
        if (StrUtil.isNotEmpty(moduleName)) {
            variables.put(WorkflowConstants.moduleName, moduleName);
        }
        if (StrUtil.isNotEmpty(businessNo)) {
            variables.put(WorkflowConstants.businessNo, businessNo);
        }
        if (beginTime != null) {
            variables.put("beginTime", beginTime);
        }
        if (endTime != null) {
            variables.put("endTime", endTime);
        }
        try {
            List<ActivitiTaskInfoVO> activitiTaskInfoVOS = activitiService.selectFinishTasks(variables);
            return Result.ok(new Page<>(activitiTaskInfoVOS, pageNum, pageSize));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail("已审批报错：" + e.getMessage());
        }
    }

    @ApiOperation("审批历史列表")
    @GetMapping("/list_history")
    public Result listHistory(@RequestParam("id")String businessId,
                              @RequestParam("moduleId") String moduleId) {
        return Result.ok(activitiService.listHistory(businessId, moduleId));
    }
}
