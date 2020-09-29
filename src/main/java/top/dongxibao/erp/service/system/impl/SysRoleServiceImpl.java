package top.dongxibao.erp.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.dongxibao.erp.entity.system.SysRole;
import top.dongxibao.erp.entity.system.SysRoleDept;
import top.dongxibao.erp.entity.system.SysRoleMenu;
import top.dongxibao.erp.entity.system.SysRole;
import top.dongxibao.erp.mapper.system.SysRoleDeptMapper;
import top.dongxibao.erp.mapper.system.SysRoleMapper;
import top.dongxibao.erp.mapper.system.SysRoleMenuMapper;
import top.dongxibao.erp.service.system.SysRoleService;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 角色信息Service业务层处理
 *
 * @author Dongxibao
 * @date 2020-06-13
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleDeptMapper sysRoleDeptMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysRole> selectByCondition(SysRole sysRole) {
        return baseMapper.selectByCondition(sysRole);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean save(SysRole entity) {
        // 1.角色部门关联
        insertSysRoleDept(entity);
        // 2.角色菜单关联
        insertSysRoleMenu(entity);
        // 3.新增角色
        return super.save(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeById(Serializable id) {
        // 1.删除角色部门关联
        sysRoleDeptMapper.delete(new LambdaQueryWrapper<SysRoleDept>().eq(SysRoleDept::getRoleId, id));
        // 2.删除角色菜单关联
        sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, id));
        // 3.删除角色表
        return super.removeById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateById(SysRole entity) {
        // 1.删除角色部门关联
        sysRoleDeptMapper.delete(new LambdaQueryWrapper<SysRoleDept>().eq(SysRoleDept::getRoleId, entity.getId()));
        // 2.删除角色菜单关联
        sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, entity.getId()));
        // 3.重新角色部门关联
        insertSysRoleDept(entity);
        // 4.重新角色菜单关联
        insertSysRoleMenu(entity);
        // 5.修改角色
        return super.updateById(entity);
    }

    @Override
    public Set<String> selectRolePermissionByUserId(Long id) {
        List<SysRole> perms = baseMapper.selectRolePermissionByUserId(id);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms) {
            if (perm != null) {
                permsSet.addAll(Arrays.asList(perm.getRoleCode().trim().split(",")));
            }
        }
        return permsSet;
    }


    private void insertSysRoleMenu(SysRole entity) {
        Long[] menuIds = entity.getMenuIds();
        if (menuIds != null && menuIds.length > 0) {
            for (Long menuId : menuIds) {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleId(entity.getId());
                sysRoleMenu.setMenuId(menuId);
                sysRoleMenuMapper.insert(sysRoleMenu);
            }
        }
    }

    private void insertSysRoleDept(SysRole entity) {
        Long[] deptIds = entity.getDeptIds();
        if (deptIds != null && deptIds.length > 0) {
            for (Long deptId : deptIds) {
                SysRoleDept sysRoleDept = new SysRoleDept();
                sysRoleDept.setRoleId(entity.getId());
                sysRoleDept.setDeptId(deptId);
                sysRoleDeptMapper.insert(sysRoleDept);
            }
        }
    }
}
