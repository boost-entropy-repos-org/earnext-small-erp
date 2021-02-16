package top.dongxibao.erp.controller.system;

import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.dongxibao.erp.annotation.Log;
import top.dongxibao.erp.common.BaseController;
import top.dongxibao.erp.common.Result;
import top.dongxibao.erp.entity.system.SysMenu;
import top.dongxibao.erp.entity.system.TreeSelect;
import top.dongxibao.erp.enums.BusinessType;
import top.dongxibao.erp.security.dto.JwtUserDto;
import top.dongxibao.erp.security.service.TokenService;
import top.dongxibao.erp.service.system.SysMenuService;
import top.dongxibao.erp.util.ServletUtils;
import top.dongxibao.erp.util.SqlUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单权限表
 *
 * @author Dongxibao
 * @date 2021-01-05
 */
@Slf4j
@Api(tags = "菜单权限表")
@RestController
@RequestMapping("sys/menu")
public class SysMenuController extends BaseController {
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private TokenService tokenService;

    /**
     * 查询菜单权限表
     */
    @ApiOperation("查询菜单权限表列表")
    @GetMapping
    public Result page(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize,
                       @RequestParam(value = "orderByColumn", required = false) String orderByColumn,
                       @RequestParam(value = "isAsc", required = false, defaultValue = "desc") String isAsc,
                       @RequestParam(value = "menuName", required = false) String menuName,
                       @RequestParam(value = "permsCode", required = false) String permsCode,
                       @RequestParam(value = "menuType", required = false) String menuType,
                       @RequestParam(value = "visible", required = false) Integer visible,
                       @RequestParam(value = "beginTime", required = false) Date beginTime,
                       @RequestParam(value = "endTime", required = false) Date endTime) {
        SysMenu sysMenu = new SysMenu();
        sysMenu.setBeginTime(beginTime);
        sysMenu.setEndTime(endTime);
        sysMenu.setMenuName(menuName);
        sysMenu.setPermsCode(permsCode);
        sysMenu.setMenuType(menuType);
        sysMenu.setVisible(visible);
        String orderBy = SqlUtil.escapeOrderBySql(super.packOrderBy(orderByColumn, isAsc));
//        PageHelper.startPage(pageNum, pageSize, orderBy);
        PageHelper.orderBy(orderBy);
        List<SysMenu> list = sysMenuService.selectByCondition(sysMenu);
        return Result.ok(/*ResultPage.restPage(list)*/list);
    }

    /**
     * 菜单权限表所有 树形列表
     */
    @ApiOperation("菜单权限表所有 树形列表")
    @GetMapping("tree_list")
    public Result treeList(SysMenu sysMenu) {
        List<SysMenu> list = sysMenuService.selectTreeList(sysMenu);
        return Result.ok(list);
    }

    /**
     * 查询label菜单权限表所有 树形列表
     */
    @ApiOperation("查询label菜单权限表所有 树形列表")
    @GetMapping("tree_select")
    public Result treeSelect(SysMenu sysMenu) {
        List<SysMenu> list = sysMenuService.selectByCondition(sysMenu);
        List<TreeSelect> treeSelects = sysMenuService.buildMenuTreeSelect(list);
        return Result.ok(treeSelects);
    }

    /**
     * 加载对应角色菜单列表树
     */
    @ApiOperation("加载对应角色菜单列表树")
    @GetMapping(value = "/role_menu_tree/{roleId}")
    public Result roleMenuTree(@PathVariable("roleId") Long roleId) {
        JwtUserDto jwtUserDto = tokenService.getLoginUser(ServletUtils.getRequest());
        List<SysMenu> menus = sysMenuService.selectMenuList(jwtUserDto.getUser().getId());
        Map<String,Object> map = new HashMap<>();
        map.put("checkedKeys", sysMenuService.selectMenuListByRoleId(roleId));
        map.put("menus", sysMenuService.buildMenuTreeSelect(menus));
        return Result.ok(map);
    }

    /**
     * 获取菜单权限表详细信息
     */
    @ApiOperation("获取菜单权限表详细信息")
    @GetMapping("/{id}")
    public Result get(@PathVariable("id") Long id) {
        return Result.ok(sysMenuService.getById(id));
    }

    /**
     * 新增菜单权限表
     */
    @Log(title = "菜单权限", businessType = BusinessType.INSERT)
    @ApiOperation("新增菜单权限表")
    @PostMapping
    public Result insert(@RequestBody SysMenu sysMenu) {
        sysMenu = sysMenuService.save(sysMenu);
        return Result.ok(sysMenu);
    }

    /**
     * 修改菜单权限表
     */
    @Log(title = "菜单权限", businessType = BusinessType.UPDATE)
    @ApiOperation("修改菜单权限表")
    @PutMapping("/{id}")
    public Result update(@PathVariable("id") Long id,
                         @RequestBody SysMenu sysMenu) {
        sysMenu.setId(id);
        sysMenu = sysMenuService.updateById(sysMenu);
        return Result.ok(sysMenu);
    }

    /**
     * 删除菜单权限表
     */
    @Log(title = "菜单权限", businessType = BusinessType.DELETE)
    @ApiOperation("删除菜单权限表")
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") Long id) {
        if (id == null) {
            return Result.fail("id为空");
        }
        boolean delete = sysMenuService.removeById(id);
        return Result.of(delete, null);
    }

    /**
     * 批量删除菜单权限表
     *
     * @return 结果
     */
    @Log(title = "菜单权限", businessType = BusinessType.DELETE)
    @ApiOperation("批量删除菜单权限表")
    @DeleteMapping("/deleteAll")
    public Result deleteAll(@RequestBody List<Long> idList) {
        if (idList == null || idList.size() < 1) {
            return Result.fail("id为空");
        }
        boolean delete = sysMenuService.removeByIds(idList);
        return Result.of(delete, null);
    }
}
