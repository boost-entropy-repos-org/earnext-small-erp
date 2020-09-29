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
import top.dongxibao.erp.entity.system.SysRole;
import top.dongxibao.erp.enums.BusinessType;
import top.dongxibao.erp.service.system.SysRoleService;
import top.dongxibao.erp.util.SqlUtil;

import java.util.Date;
import java.util.List;

/**
 * 角色信息Controller
 * 
 * @author Dongxibao
 * @date 2020-06-13
 */
@Api(tags = "角色信息")
@RestController
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 查询角色信息列表
     */
    @Log(title = "角色信息模块", businessType = BusinessType.SELECT)
    @ApiOperation("查询角色信息列表")
    @PreAuthorize("@ss.hasPermi('system:role:page')")
    @GetMapping("/page")
    public Result page(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                       @RequestParam(value = "orderByColumn", required = false) String orderByColumn,
                       @RequestParam(value = "isAsc", required = false, defaultValue = "asc") String isAsc,
                       @RequestParam(value = "beginTime", required = false) Date beginTime,
                       @RequestParam(value = "endTime", required = false) Date endTime) {
        SysRole sysRole = new SysRole();
        sysRole.setBeginTime(beginTime);
        sysRole.setEndTime(endTime);
        String orderBy = SqlUtil.escapeOrderBySql(super.packOrderBy(orderByColumn, isAsc));

        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<SysRole> list = sysRoleService.selectByCondition(sysRole);
        return new Result(ResultPage.restPage(list));
    }

    /**
     * 获取角色信息详细信息
     */
    @Log(title = "角色信息模块", businessType = BusinessType.SELECT)
    @ApiOperation("获取角色信息详细信息")
    @PreAuthorize("@ss.hasPermi('system:role:get')")
    @GetMapping("/{id}")
    public Result get(@PathVariable("id") Long id) {
        return new Result(sysRoleService.getById(id));
    }

    /**
     * 新增角色信息
     */
    @Log(title = "角色信息模块", businessType = BusinessType.INSERT)
    @ApiOperation("新增角色信息")
    @PreAuthorize("@ss.hasPermi('system:role:insert')")
    @RepeatSubmit
    @PostMapping
    public Result insert(@RequestBody SysRole sysRole) {
        boolean insert = sysRoleService.save(sysRole);
        return new Result(sysRole, insert);
    }

    /**
     * 修改角色信息
     */
    @Log(title = "角色信息模块", businessType = BusinessType.UPDATE)
    @ApiOperation("修改角色信息")
    @PreAuthorize("@ss.hasPermi('system:role:update')")
    @PutMapping("/{id}")
    public Result update(@RequestBody SysRole sysRole, @PathVariable("id") Long id) {
        sysRole.setId(id);
        boolean update = sysRoleService.updateById(sysRole);
        return new Result(sysRole, update);
    }

    /**
     * 删除角色信息
     */
    @Log(title = "角色信息模块", businessType = BusinessType.DELETE)
    @ApiOperation("删除角色信息")
    @PreAuthorize("@ss.hasPermi('system:role:delete')")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Long id) {
        boolean delete = sysRoleService.removeById(id);
        return new Result(null, delete);
    }
}
