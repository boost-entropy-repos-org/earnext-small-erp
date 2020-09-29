package top.dongxibao.erp.controller.system;

import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.dongxibao.erp.annotation.Log;
import top.dongxibao.erp.annotation.RepeatSubmit;
import top.dongxibao.erp.common.BaseController;
import top.dongxibao.erp.common.Result;
import top.dongxibao.erp.common.ResultPage;
import top.dongxibao.erp.entity.system.SysLoginLog;
import top.dongxibao.erp.enums.BusinessType;
import top.dongxibao.erp.service.system.SysLoginLogService;
import top.dongxibao.erp.util.SqlUtil;

import java.util.Date;
import java.util.List;

/**
 * 系统访问记录Controller
 * 
 * @author Dongxibao
 * @date 2020-06-21
 */
@Api(tags = "系统访问记录")
@RestController
@RequestMapping("/system/log")
public class SysLoginLogController extends BaseController {
    @Autowired
    private SysLoginLogService sysLoginLogService;

    /**
     * 查询系统访问记录列表
     */
    @Log(title = "系统访问记录模块", businessType = BusinessType.SELECT)
    @ApiOperation("查询系统访问记录列表")
    @PreAuthorize("@ss.hasPermi('system:log:page')")
    @GetMapping("/page")
    public Result page(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                       @RequestParam(value = "orderByColumn", required = false) String orderByColumn,
                       @RequestParam(value = "isAsc", required = false, defaultValue = "asc") String isAsc,
                       @RequestParam(value = "beginTime", required = false) Date beginTime,
                       @RequestParam(value = "endTime", required = false) Date endTime) {
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setBeginTime(beginTime);
        sysLoginLog.setEndTime(endTime);
        String orderBy = SqlUtil.escapeOrderBySql(super.packOrderBy(orderByColumn, isAsc));

        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<SysLoginLog> list = sysLoginLogService.selectByCondition(sysLoginLog);
        return new Result(ResultPage.restPage(list));
    }

    /**
     * 获取系统访问记录详细信息
     */
    @Log(title = "系统访问记录模块", businessType = BusinessType.SELECT)
    @ApiOperation("获取系统访问记录详细信息")
    @PreAuthorize("@ss.hasPermi('system:log:get')")
    @GetMapping("/{id}")
    public Result get(@PathVariable("id") Long id) {
        return new Result(sysLoginLogService.getById(id));
    }

    /**
     * 新增系统访问记录
     */
    @Log(title = "系统访问记录模块", businessType = BusinessType.INSERT)
    @ApiOperation("新增系统访问记录")
    @PreAuthorize("@ss.hasPermi('system:log:insert')")
    @RepeatSubmit
    @PostMapping
    public Result insert(@RequestBody SysLoginLog sysLoginLog) {
        boolean insert = sysLoginLogService.save(sysLoginLog);
        return new Result(sysLoginLog, insert);
    }

    /**
     * 修改系统访问记录
     */
    @Log(title = "系统访问记录模块", businessType = BusinessType.UPDATE)
    @ApiOperation("修改系统访问记录")
    @PreAuthorize("@ss.hasPermi('system:log:update')")
    @PutMapping("/{id}")
    public Result update(@RequestBody SysLoginLog sysLoginLog, @PathVariable("id") Long id) {
        sysLoginLog.setId(id);
        boolean update = sysLoginLogService.updateById(sysLoginLog);
        return new Result(sysLoginLog, update);
    }

    /**
     * 删除系统访问记录
     */
    @Log(title = "系统访问记录模块", businessType = BusinessType.DELETE)
    @ApiOperation("删除系统访问记录")
    @PreAuthorize("@ss.hasPermi('system:log:delete')")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Long id) {
        boolean delete = sysLoginLogService.removeById(id);
        return new Result(null, delete);
    }
}
