package top.dongxibao.erp.controller.system;

import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.dongxibao.erp.annotation.Log;
import top.dongxibao.erp.common.BaseController;
import top.dongxibao.erp.common.Result;
import top.dongxibao.erp.common.ResultPage;
import top.dongxibao.erp.entity.system.SysDept;
import top.dongxibao.erp.enums.BusinessType;
import top.dongxibao.erp.service.system.SysDeptService;
import top.dongxibao.erp.util.SqlUtil;

import java.util.Date;
import java.util.List;

/**
 * 部门Controller
 * 
 * @author Dongxibao
 * @date 2020-06-14
 */
@Api(tags = "部门")
@RestController
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController {
    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 查询部门列表
     */
    @Log(title = "部门模块", businessType = BusinessType.SELECT)
    @ApiOperation("查询部门列表")
    @PreAuthorize("@ss.hasPermi('system:dept:page')")
    @GetMapping("/page")
    public Result page(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                       @RequestParam(value = "orderByColumn", required = false) String orderByColumn,
                       @RequestParam(value = "isAsc", required = false, defaultValue = "asc") String isAsc,
                       @RequestParam(value = "beginTime", required = false) Date beginTime,
                       @RequestParam(value = "endTime", required = false) Date endTime) {
        SysDept sysDept = new SysDept();
        sysDept.setBeginTime(beginTime);
        sysDept.setEndTime(endTime);
        String orderBy = SqlUtil.escapeOrderBySql(super.packOrderBy(orderByColumn, isAsc));

        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<SysDept> list = sysDeptService.selectByCondition(sysDept);
        return new Result(ResultPage.restPage(list));
    }

    /**
     * 部门列表树形
     */
    @Log(title = "部门模块", businessType = BusinessType.SELECT)
    @ApiOperation("部门列表树形")
    @PreAuthorize("@ss.hasPermi('system:dept:tree')")
    @GetMapping("/tree")
    public Result tree() {
        List<SysDept> list = sysDeptService.selectTree();
        return new Result(list);
    }

    /**
     * 获取部门详细信息
     */
    @Log(title = "部门模块", businessType = BusinessType.SELECT)
    @ApiOperation("获取部门详细信息")
    @PreAuthorize("@ss.hasPermi('system:dept:get')")
    @GetMapping("/{id}")
    public Result get(@PathVariable("id") Long id) {
        return new Result(sysDeptService.getById(id));
    }

    /**
     * 新增部门
     */
    @Log(title = "部门模块", businessType = BusinessType.INSERT)
    @ApiOperation("新增部门")
    @PreAuthorize("@ss.hasPermi('system:dept:insert')")
    @PostMapping
    public Result insert(@RequestBody SysDept sysDept) {
        boolean insert = sysDeptService.save(sysDept);
        return new Result(sysDept, insert);
    }

    /**
     * 修改部门
     */
    @Log(title = "部门模块", businessType = BusinessType.UPDATE)
    @ApiOperation("修改部门")
    @PreAuthorize("@ss.hasPermi('system:dept:update')")
    @PutMapping("/{id}")
    public Result update(@RequestBody SysDept sysDept, @PathVariable("id") Long id) {
        sysDept.setId(id);
        boolean update = sysDeptService.updateById(sysDept);
        return new Result(sysDept, update);
    }

    /**
     * 删除部门
     */
    @Log(title = "部门模块", businessType = BusinessType.DELETE)
    @ApiOperation("删除部门")
    @PreAuthorize("@ss.hasPermi('system:dept:delete')")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Long id) {
        boolean delete = sysDeptService.removeById(id);
        return new Result(null, delete);
    }
}
