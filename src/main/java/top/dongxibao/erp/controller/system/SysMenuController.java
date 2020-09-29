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
import top.dongxibao.erp.entity.system.SysMenu;
import top.dongxibao.erp.enums.BusinessType;
import top.dongxibao.erp.service.system.SysMenuService;
import top.dongxibao.erp.util.SqlUtil;

import java.util.Date;
import java.util.List;

/**
 * 菜单权限Controller
 *
 * @author Dongxibao
 * @date 2020-06-13
 */
@Api(tags = "菜单权限")
@RestController
@RequestMapping("/system/menu")
public class SysMenuController extends BaseController {
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 查询菜单权限列表
     */
    @Log(title = "菜单权限模块", businessType = BusinessType.SELECT)
    @ApiOperation("查询菜单权限列表")
    @PreAuthorize("@ss.hasPermi('system:menu:page')")
    @GetMapping("/page")
    public Result page(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                       @RequestParam(value = "orderByColumn", required = false) String orderByColumn,
                       @RequestParam(value = "isAsc", required = false, defaultValue = "asc") String isAsc,
                       @RequestParam(value = "beginTime", required = false) Date beginTime,
                       @RequestParam(value = "endTime", required = false) Date endTime) {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setBeginTime(beginTime);
        sysMenu.setEndTime(endTime);
        String orderBy = SqlUtil.escapeOrderBySql(super.packOrderBy(orderByColumn, isAsc));

        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<SysMenu> list = sysMenuService.selectByCondition(sysMenu);
        return new Result(ResultPage.restPage(list));
    }

    /**
     * 获取菜单权限详细信息
     */
    @Log(title = "菜单权限模块", businessType = BusinessType.SELECT)
    @ApiOperation("获取菜单权限详细信息")
    @PreAuthorize("@ss.hasPermi('system:menu:get')")
    @GetMapping("/{id}")
    public Result get(@PathVariable("id") Long id) {
        return new Result(sysMenuService.getById(id));
    }

    /**
     * 新增菜单权限
     */
    @Log(title = "菜单权限模块", businessType = BusinessType.INSERT)
    @ApiOperation("新增菜单权限")
    @PreAuthorize("@ss.hasPermi('system:menu:insert')")
    @RepeatSubmit
    @PostMapping
    public Result insert(@RequestBody SysMenu sysMenu) {
        boolean insert = sysMenuService.save(sysMenu);
        return new Result(sysMenu, insert);
    }

    /**
     * 修改菜单权限
     */
    @Log(title = "菜单权限模块", businessType = BusinessType.UPDATE)
    @ApiOperation("修改菜单权限")
    @PreAuthorize("@ss.hasPermi('system:menu:update')")
    @PutMapping("/{id}")
    public Result update(@RequestBody SysMenu sysMenu, @PathVariable("id") Long id) {
        sysMenu.setId(id);
        boolean update = sysMenuService.updateById(sysMenu);
        return new Result(sysMenu, update);
    }

    /**
     * 删除菜单权限
     */
    @Log(title = "菜单权限模块", businessType = BusinessType.DELETE)
    @ApiOperation("删除菜单权限")
    @PreAuthorize("@ss.hasPermi('system:menu:delete')")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Long id) {
        boolean delete = sysMenuService.removeById(id);
        return new Result(null, delete);
    }
}
