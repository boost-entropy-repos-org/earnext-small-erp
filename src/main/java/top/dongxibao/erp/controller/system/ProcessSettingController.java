package top.dongxibao.erp.controller.system;

import top.dongxibao.erp.annotation.Log;
import top.dongxibao.erp.common.BaseController;
import top.dongxibao.erp.common.Result;
import top.dongxibao.erp.common.ResultPage;
import top.dongxibao.erp.entity.system.ProcessSetting;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.dongxibao.erp.enums.BusinessType;
import top.dongxibao.erp.service.system.ProcessSettingService;
import top.dongxibao.erp.util.SqlUtil;

import java.util.Date;
import java.util.List;

/**
 * 业务模块关联流程
 *
 * @author Dongxibao
 * @date 2021-01-25
 */
@Slf4j
@Api(tags = "业务模块关联流程")
@RestController
@RequestMapping("processsetting")
public class ProcessSettingController extends BaseController {
    @Autowired
    private ProcessSettingService processSettingService;

    /**
     * 查询业务模块关联流程
     */
    @ApiOperation("查询业务模块关联流程列表")
    @GetMapping
    public Result page(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
                       @RequestParam(value = "moduleName", required = false) String moduleName,
                       @RequestParam(value = "moduleId", required = false) Long moduleId,
                       @RequestParam(value = "processDefinitionKey", required = false) String processDefinitionKey,
                       @RequestParam(value = "orderByColumn", required = false) String orderByColumn,
                       @RequestParam(value = "isAsc", required = false, defaultValue = "desc") String isAsc,
                       @RequestParam(value = "beginTime", required = false) Date beginTime,
                       @RequestParam(value = "endTime", required = false) Date endTime) {
        ProcessSetting processSetting = new ProcessSetting();
        processSetting.setModuleId(moduleId);
        processSetting.setProcessDefinitionKey(processDefinitionKey);
        if (StrUtil.isNotBlank(moduleName)) {
            processSetting.setModuleName("%" + moduleName + "%");
        }
        processSetting.setBeginTime(beginTime);
        processSetting.setEndTime(endTime);
        String orderBy = SqlUtil.escapeOrderBySql(super.packOrderBy(orderByColumn, isAsc));
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<ProcessSetting> list = processSettingService.selectByCondition(processSetting);
        return Result.ok(ResultPage.restPage(list));
    }

    /**
     * 获取业务模块关联流程详细信息
     */
    @ApiOperation("获取业务模块关联流程详细信息")
    @GetMapping("/{id}")
    public Result get(@PathVariable("id") Long id) {
        return Result.ok(processSettingService.getById(id));
    }

    /**
     * 新增业务模块关联流程
     */
    @Log(title = "业务模块关联流程", businessType = BusinessType.INSERT)
    @ApiOperation("新增业务模块关联流程")
    @PostMapping
    public Result insert(@RequestBody ProcessSetting processSetting) {
        processSetting = processSettingService.save(processSetting);
        return Result.ok(processSetting);
    }

    /**
     * 修改业务模块关联流程
     */
    @Log(title = "业务模块关联流程", businessType = BusinessType.UPDATE)
    @ApiOperation("修改业务模块关联流程")
    @PutMapping("/{id}")
    public Result update(@PathVariable("id") Long id,
                         @RequestBody ProcessSetting processSetting) {
        processSetting.setId(id);
        processSetting = processSettingService.updateById(processSetting);
        return Result.ok(processSetting);
    }

    /**
     * 删除业务模块关联流程
     */
    @Log(title = "业务模块关联流程", businessType = BusinessType.DELETE)
    @ApiOperation("删除业务模块关联流程")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Long id) {
        if (id == null) {
            return Result.fail("id为空");
        }
        boolean delete = processSettingService.removeById(id);
        return Result.of(delete, null);
    }

    /**
     * 批量删除业务模块关联流程
     * @return 结果
     */
    @Log(title = "业务模块关联流程", businessType = BusinessType.DELETE)
    @ApiOperation("批量删除业务模块关联流程")
    @DeleteMapping
    public Result deleteAll(@RequestBody List<Long> idList) {
        if (idList == null || idList.size() < 1) {
            return Result.fail("id为空");
        }
        boolean delete = processSettingService.removeByIds(idList);
        return Result.of(delete, null);
    }
}
