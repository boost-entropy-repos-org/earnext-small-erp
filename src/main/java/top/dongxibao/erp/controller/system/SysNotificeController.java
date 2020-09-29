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
import top.dongxibao.erp.entity.system.SysNotifice;
import top.dongxibao.erp.enums.BusinessType;
import top.dongxibao.erp.security.SecurityUtils;
import top.dongxibao.erp.service.system.SysNotificeService;
import top.dongxibao.erp.util.SqlUtil;

import java.util.Date;
import java.util.List;

/**
 * 系统通知Controller
 *
 * @author Dongxibao
 * @date 2020-06-28
 */
@Api(tags = "系统通知")
@RestController
@RequestMapping("/system/notifice")
public class SysNotificeController extends BaseController {
    @Autowired
    private SysNotificeService sysNotificeService;

    /**
     * 查询系统通知列表
     */
    @Log(title = "系统通知模块", businessType = BusinessType.SELECT)
    @ApiOperation("查询系统通知列表")
    @PreAuthorize("@ss.hasPermi('system:notifice:page')")
    @GetMapping("/page")
    public Result page(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                       @RequestParam(value = "toUser", required = false) String toUser,
                       @RequestParam(value = "orderByColumn", required = false) String orderByColumn,
                       @RequestParam(value = "isAsc", required = false, defaultValue = "asc") String isAsc,
                       @RequestParam(value = "beginTime", required = false) Date beginTime,
                       @RequestParam(value = "endTime", required = false) Date endTime) {
        SysNotifice sysNotifice = new SysNotifice();
        sysNotifice.setToUser(SecurityUtils.getUsername());
        sysNotifice.setBeginTime(beginTime);
        sysNotifice.setEndTime(endTime);
        String orderBy = SqlUtil.escapeOrderBySql(super.packOrderBy(orderByColumn, isAsc));

        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<SysNotifice> list = sysNotificeService.selectByCondition(sysNotifice);
        return new Result(ResultPage.restPage(list));
    }

    /**
     * 获取系统通知详细信息
     */
    @Log(title = "系统通知模块", businessType = BusinessType.SELECT)
    @ApiOperation("获取系统通知详细信息")
    @PreAuthorize("@ss.hasPermi('system:notifice:get')")
    @GetMapping("/{id}")
    public Result get(@PathVariable("id") Long id) {
        return new Result(sysNotificeService.getById(id));
    }

    /**
     * 新增系统通知
     */
    @Log(title = "系统通知模块", businessType = BusinessType.INSERT)
    @ApiOperation("新增系统通知")
    @PreAuthorize("@ss.hasPermi('system:notifice:insert')")
    @RepeatSubmit
    @PostMapping
    public Result insert(@RequestBody SysNotifice sysNotifice) {
        boolean insert = sysNotificeService.save(sysNotifice);
        return new Result(sysNotifice, insert);
    }

    /**
     * 修改系统通知
     */
    @Log(title = "系统通知模块", businessType = BusinessType.UPDATE)
    @ApiOperation("修改系统通知")
    @PreAuthorize("@ss.hasPermi('system:notifice:update')")
    @PutMapping("/{id}")
    public Result update(@RequestBody SysNotifice sysNotifice, @PathVariable("id") Long id) {
        sysNotifice.setId(id);
        sysNotifice.setReadTime(new Date());
        boolean update = sysNotificeService.updateById(sysNotifice);
        return new Result(sysNotifice, update);
    }

    /**
     * 删除系统通知
     */
    @Log(title = "系统通知模块", businessType = BusinessType.DELETE)
    @ApiOperation("删除系统通知")
    @PreAuthorize("@ss.hasPermi('system:notifice:delete')")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Long id) {
        boolean delete = sysNotificeService.removeById(id);
        return new Result(null, delete);
    }
}
