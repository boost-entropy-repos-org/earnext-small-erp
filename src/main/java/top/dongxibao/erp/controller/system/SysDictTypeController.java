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
import top.dongxibao.erp.entity.system.SysDictType;
import top.dongxibao.erp.enums.BusinessType;
import top.dongxibao.erp.service.system.SysDictTypeService;
import top.dongxibao.erp.util.SqlUtil;

import java.util.Date;
import java.util.List;

/**
 * 数据字典Controller
 * 
 * @author Dongxibao
 * @date 2020-07-05
 */
@Api(tags = "数据字典")
@RestController
@RequestMapping("/system/dict")
public class SysDictTypeController extends BaseController {
    @Autowired
    private SysDictTypeService sysDictTypeService;

    /**
     * 查询数据字典主列表
     */
    @Log(title = "数据字典主模块", businessType = BusinessType.SELECT)
    @ApiOperation("查询数据字典主列表")
    @PreAuthorize("@ss.hasPermi('system:dict:page')")
    @GetMapping("/page")
    public Result page(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                       @RequestParam(value = "dictType", required = false) String dictType,
                       @RequestParam(value = "orderByColumn", required = false) String orderByColumn,
                       @RequestParam(value = "isAsc", required = false, defaultValue = "asc") String isAsc,
                       @RequestParam(value = "beginTime", required = false) Date beginTime,
                       @RequestParam(value = "endTime", required = false) Date endTime) {
        SysDictType sysDictType = new SysDictType();
        sysDictType.setDictType(dictType);
        sysDictType.setBeginTime(beginTime);
        sysDictType.setEndTime(endTime);
        String orderBy = SqlUtil.escapeOrderBySql(super.packOrderBy(orderByColumn, isAsc));

        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<SysDictType> list = sysDictTypeService.selectByCondition(sysDictType);
        return new Result(ResultPage.restPage(list));
    }

    /**
     * 获取数据字典主详细信息
     */
    @Log(title = "数据字典主模块", businessType = BusinessType.SELECT)
    @ApiOperation("获取数据字典主详细信息")
    @PreAuthorize("@ss.hasPermi('system:dict:get')")
    @GetMapping("/{id}")
    public Result get(@PathVariable("id") Long id) {
        return new Result(sysDictTypeService.getById(id));
    }

    /**
     * 新增数据字典主
     */
    @Log(title = "数据字典主模块", businessType = BusinessType.INSERT)
    @ApiOperation("新增数据字典主")
    @PreAuthorize("@ss.hasPermi('system:dict:insert')")
    @RepeatSubmit
    @PostMapping
    public Result insert(@RequestBody SysDictType sysDictType) {
        boolean insert = sysDictTypeService.save(sysDictType);
        return new Result(sysDictType, insert);
    }

    /**
     * 修改数据字典主
     */
    @Log(title = "数据字典主模块", businessType = BusinessType.UPDATE)
    @ApiOperation("修改数据字典主")
    @PreAuthorize("@ss.hasPermi('system:dict:update')")
    @PutMapping("/{id}")
    public Result update(@RequestBody SysDictType sysDictType, @PathVariable("id") Long id) {
        sysDictType.setId(id);
        boolean update = sysDictTypeService.updateById(sysDictType);
        return new Result(sysDictType, update);
    }

    /**
     * 删除数据字典主
     */
    @Log(title = "数据字典主模块", businessType = BusinessType.DELETE)
    @ApiOperation("删除数据字典主")
    @PreAuthorize("@ss.hasPermi('system:dict:delete')")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Long id) {
        boolean delete = sysDictTypeService.removeById(id);
        return new Result(null, delete);
    }
}
