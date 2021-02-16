package top.dongxibao.erp.controller.system;

import top.dongxibao.erp.common.BaseController;
import top.dongxibao.erp.common.Result;
import top.dongxibao.erp.common.ResultPage;
import top.dongxibao.erp.entity.system.SysLog;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.dongxibao.erp.service.system.SysLogService;
import top.dongxibao.erp.util.SqlUtil;

import java.util.Date;
import java.util.List;

/**
 * 系统日志Controller
 *
 * @author Dongxibao
 * @date 2020-11-27
 */
@Slf4j
@Api(tags = "系统日志")
@RestController
@RequestMapping("/sys/log")
public class SysLogController extends BaseController {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 查询系统日志列表
     */
    @ApiOperation("查询系统日志列表")
    @GetMapping
    public Result page(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
                       @RequestParam(value = "orderByColumn", required = false) String orderByColumn,
                       @RequestParam(value = "isAsc", required = false, defaultValue = "desc") String isAsc,
                       @RequestParam(value = "beginTime", required = false) Date beginTime,
                       @RequestParam(value = "endTime", required = false) Date endTime) {
        SysLog sysLog = new SysLog();
        sysLog.setBeginTime(beginTime);
        sysLog.setEndTime(endTime);
        String orderBy = SqlUtil.escapeOrderBySql(super.packOrderBy(orderByColumn, isAsc));
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<SysLog> list = sysLogService.selectByCondition(sysLog);
        return Result.ok(ResultPage.restPage(list));
    }

    /**
     * 获取系统日志详细信息
     */
    @ApiOperation("获取系统日志详细信息")
    @GetMapping("/{id}")
    public Result get(@PathVariable("id") Long id) {
        return Result.ok(sysLogService.getById(id));
    }

    /**
     * 新增系统日志
     */
//    @ApiOperation("新增系统日志")
//    @Log(title = "系统日志",businessType = BusinessType.INSERT)
//    @PostMapping
//    public Result insert(@RequestBody SysLog sysLog) {
//        return Result.ok(sysLogService.save(sysLog));
//    }
//
//    /**
//     * 修改系统日志
//     */
//    @ApiOperation("修改系统日志")
//    @Log(title = "系统日志",businessType = BusinessType.UPDATE)
//    @PutMapping("/{id}")
//    public Result update(@PathVariable("id") Long id, @RequestBody SysLog sysLog) {
//        sysLog.setId(id);
//        return Result.ok(sysLogService.updateById(sysLog));
//    }
//
//    /**
//     * 删除系统日志
//     */
//    @ApiOperation("删除系统日志")
//    @Log(title = "系统日志",businessType = BusinessType.DELETE)
//    @DeleteMapping("/{id}")
//    public Result delete(@PathVariable("id") Long id) {
//        if (id == null) {
//            throw new ServiceException("id为空",400);
//        }
//        boolean delete = sysLogService.removeById(id);
//        return Result.of(delete, null);
//    }
}
