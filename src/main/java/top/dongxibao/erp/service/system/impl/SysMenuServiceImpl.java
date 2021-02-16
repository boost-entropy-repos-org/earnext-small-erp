package top.dongxibao.erp.service.system.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.dongxibao.erp.constant.UserConstants;
import top.dongxibao.erp.entity.system.SysMenu;
import top.dongxibao.erp.entity.system.SysUser;
import top.dongxibao.erp.entity.system.TreeSelect;
import top.dongxibao.erp.exception.ServiceException;
import top.dongxibao.erp.mapper.system.SysMenuMapper;
import top.dongxibao.erp.mapper.system.SysRoleMenuMapper;
import top.dongxibao.erp.security.SecurityUtils;
import top.dongxibao.erp.entity.vo.MetaVO;
import top.dongxibao.erp.entity.vo.RouterVO;
import top.dongxibao.erp.service.system.SysMenuService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单权限表
 *
 * @author Dongxibao
 * @date 2021-01-05
 */
@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public SysMenu getById(Long id) {
        SysMenu sysMenu = sysMenuMapper.getById(id);
        return sysMenu;
    }

    @Override
    public List<SysMenu> selectByCondition(SysMenu sysMenu) {
        return sysMenuMapper.selectByCondition(sysMenu);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysMenu save(SysMenu sysMenu) {
        if (sysMenuMapper.checkSysMenu(sysMenu) != null) {
            throw new ServiceException("新增菜单'" + sysMenu.getPermsCode() + "'失败，菜单权限已存在");
        }
        if (sysMenuMapper.checkRoutingPath(sysMenu) != null) {
            throw new ServiceException("新增菜单'" + sysMenu.getRoutingPath() + "'失败，路由地址已存在");
        }
        if (UserConstants.TYPE_DIR.equals(sysMenu.getMenuType())) {
            sysMenu.setComponent("Loyout");
        } else {
            sysMenu.setComponent("ComponentList");
        }
        sysMenuMapper.insert(sysMenu);
        return sysMenu;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysMenu updateById(SysMenu sysMenu) {
        if (sysMenuMapper.checkSysMenu(sysMenu) != null) {
            throw new ServiceException("修改菜单'" + sysMenu.getPermsCode() + "'失败，菜单权限已存在");
        } else if (sysMenu.getId().equals(sysMenu.getParentId())) {
            throw new ServiceException("修改菜单'" + sysMenu.getMenuName() + "'失败，上级菜单不能选择自己");
        } else if (sysMenuMapper.checkRoutingPath(sysMenu) != null) {
            throw new ServiceException("修改菜单'" + sysMenu.getRoutingPath() + "'失败，路由地址已存在");
        }
        sysMenuMapper.update(sysMenu);
        return sysMenu;
    }

    @Override
    public boolean removeById(Long id) {
        if (sysMenuMapper.hasChildByMenuId(id) > 0) {
            throw new ServiceException("存在子菜单,不允许删除");
        }
        if (sysRoleMenuMapper.checkMenuExistRole(id) > 0) {
            throw new ServiceException("菜单已分配,不允许删除");
        }
        return sysMenuMapper.deleteById(id) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeByIds(List<Long> idList) {
        idList.forEach(id -> {
            if (id != null) {
                if (sysMenuMapper.hasChildByMenuId(id) > 0) {
                    throw new ServiceException("存在子菜单,不允许删除");
                }
                if (sysRoleMenuMapper.checkMenuExistRole(id) > 0) {
                    throw new ServiceException("菜单已分配,不允许删除");
                }
            }
        });
        return sysMenuMapper.deleteBatchIds(idList) > 0;
    }

    @Override
    public List<SysMenu> selectTreeList(SysMenu sysMenu) {
        try {
            List<SysMenu> sysMenus = sysMenuMapper.selectByCondition(sysMenu);
            return getChildPerms(sysMenus, 0);
        } catch (Exception e) {
            e.printStackTrace(
            );
            return null;
        }
    }

    @Override
    public Set<String> selectMenuPermsByUserId(Long userId) {
        Set<String> perms = sysMenuMapper.selectMenuPermsByUserId(userId);
        return perms;
    }

    @Override
    public List<TreeSelect> buildMenuTreeSelect(List<SysMenu> menus) {
        List<SysMenu> menuTrees = buildMenuTree(menus);
        return menuTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 构建前端所需要树结构
     *
     * @param menus 菜单列表
     * @return 树结构列表
     */
    @Override
    public List<SysMenu> buildMenuTree(List<SysMenu> menus) {
        List<SysMenu> returnList = new ArrayList<>();
        List<Long> tempList = new ArrayList<>();
        for (SysMenu menu : menus) {
            tempList.add(menu.getId());
        }
        for (Iterator<SysMenu> iterator = menus.iterator(); iterator.hasNext(); ) {
            SysMenu menu = iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(menu.getParentId())) {
                recursionFn(menus, menu);
                returnList.add(menu);
            }
        }
        if (returnList.isEmpty()) {
            returnList = menus;
        }
        return returnList;
    }

    @Override
    public List<SysMenu> selectMenuList(Long userId) {
        return selectMenuList(new SysMenu(), userId);
    }

    @Override
    public List<Long> selectMenuListByRoleId(Long roleId) {
        return sysMenuMapper.selectMenuListByRoleId(roleId);
    }

    @Override
    public List<SysMenu> selectMenuTreeByUserId(Long userId) {
        List<SysMenu> menus = null;
        if (SecurityUtils.isAdmin(userId)) {
            menus = sysMenuMapper.selectByCondition(null);
        } else {
            menus = sysMenuMapper.selectMenuTreeByUserId(userId);
        }
        return getChildPerms(menus, 0);
    }

    @Override
    public List<RouterVO> buildMenus(List<SysMenu> menus) {
        List<RouterVO> routers = new LinkedList<>();
        for (SysMenu menu : menus) {
            RouterVO router = new RouterVO();
            router.setFormConfigId(menu.getFormConfigId());
            router.setHidden(Integer.valueOf(0).equals(menu.getVisible()));
            router.setName(getRouteName(menu));
            router.setPath(getRouterPath(menu));
            router.setComponent(/*getComponent(menu)*/menu.getComponent());
            router.setMeta(new MetaVO(menu.getMenuName(), menu.getIcon(), false));
            List<SysMenu> cMenus = menu.getChildren();
            if (!cMenus.isEmpty() && cMenus.size() > 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType())) {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            }
            routers.add(router);
        }
        return routers;
    }

    /**
     * 获取路由名称
     *
     * @param menu 菜单信息
     * @return 路由名称
     */
    public String getRouteName(SysMenu menu) {
        String routerName = menu.getRoutingPath();
        // 是一级目录（类型为目录）
        if (menu.getParentId().intValue() == 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType())) {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenu menu) {
        String routerPath = menu.getRoutingPath();
        // 是一级目录（类型为目录）
        if (0 == menu.getParentId().intValue() && UserConstants.TYPE_DIR.equals(menu.getMenuType())) {
            routerPath = "/" + menu.getRoutingPath();
        }
        // 是一级目录（类型为菜单）
        else if (0 == menu.getParentId().intValue() && UserConstants.TYPE_MENU.equals(menu.getMenuType())) {
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(SysMenu menu) {
        String component = UserConstants.LOYOUT;
        return component;
    }

    /**
     * 查询系统菜单列表
     *
     * @param menu 菜单信息
     * @return 菜单列表
     */
    public List<SysMenu> selectMenuList(SysMenu menu, Long userId) {
        List<SysMenu> menuList = null;
        // 管理员显示所有菜单信息
        if (SysUser.isAdmin(userId)) {
            menuList = sysMenuMapper.selectByCondition(menu);
        } else {
//            menu.getParams().put("userId", userId);
            menuList = sysMenuMapper.selectMenuListByUserId(menu);
        }
        return menuList;
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<SysMenu> getChildPerms(List<SysMenu> list, int parentId) {
        List<SysMenu> returnList = new ArrayList<>();
        for (Iterator<SysMenu> iterator = list.iterator(); iterator.hasNext(); ) {
            SysMenu t = iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<SysMenu> list, SysMenu t) {
        // 得到子节点列表
        List<SysMenu> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenu tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<SysMenu> getChildList(List<SysMenu> list, SysMenu t) {
        List<SysMenu> tlist = new ArrayList<>();
        Iterator<SysMenu> it = list.iterator();
        while (it.hasNext()) {
            SysMenu n = it.next();
            if (n.getParentId().longValue() == t.getId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenu> list, SysMenu t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}
