package top.dongxibao.erp.service.system;


import top.dongxibao.erp.entity.system.SysMenu;
import top.dongxibao.erp.entity.system.TreeSelect;
import top.dongxibao.erp.entity.vo.RouterVO;

import java.util.List;
import java.util.Set;

/**
 * 菜单权限表
 *
 * @author Dongxibao
 * @date 2021-01-05
 */
public interface SysMenuService {

    /**
     * 查询菜单权限表
     *
     * @param id
     * @return 菜单权限表
     */
    SysMenu getById(Long id);

    /**
     * 根据条件查询
     * @param sysMenu
     * @return
     */
    List<SysMenu> selectByCondition(SysMenu sysMenu);

    /**
     * 新增菜单权限表
     *
     * @param sysMenu 菜单权限表
     * @return 结果
     */
    SysMenu save(SysMenu sysMenu);

    /**
     * 修改菜单权限表
     *
     * @param sysMenu 菜单权限表
     * @return 结果
     */
    SysMenu updateById(SysMenu sysMenu);

    /**
     * 删除菜单权限表
     *
     * @param id 菜单权限表ID
     * @return 结果
     */
    boolean removeById(Long id);

    /**
     * 批量删除菜单权限表
     *
     * @param idList 需要删除的数据ID集合
     * @return 结果
     */
    boolean removeByIds(List<Long> idList);

    List<SysMenu> selectTreeList(SysMenu sysMenu);

    Set<String> selectMenuPermsByUserId(Long userId);

    List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus);
    /**
     * 构建前端所需要树结构
     *
     * @param menus 菜单列表
     * @return 树结构列表
     */
    List<SysMenu> buildMenuTree(List<SysMenu> menus);

    List<SysMenu> selectMenuList(Long userId);

    List<Long> selectMenuListByRoleId(Long roleId);

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户名称
     * @return 菜单列表
     */
    List<SysMenu> selectMenuTreeByUserId(Long userId);

    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    List<RouterVO> buildMenus(List<SysMenu> menus);
}

