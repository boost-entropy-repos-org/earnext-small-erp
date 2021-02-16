package top.dongxibao.erp.service.system.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.dongxibao.erp.entity.system.SysRole;
import top.dongxibao.erp.entity.system.SysRoleDept;
import top.dongxibao.erp.entity.system.SysRoleMenu;
import top.dongxibao.erp.entity.system.SysUserRole;
import top.dongxibao.erp.exception.ServiceException;
import top.dongxibao.erp.mapper.system.SysRoleDeptMapper;
import top.dongxibao.erp.mapper.system.SysRoleMapper;
import top.dongxibao.erp.mapper.system.SysRoleMenuMapper;
import top.dongxibao.erp.mapper.system.SysUserRoleMapper;
import top.dongxibao.erp.service.system.SysRoleService;

import java.util.List;
import java.util.Set;

/**
 * 角色信息表
 *
 * @author Dongxibao
 * @date 2021-01-05
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysRoleDeptMapper sysRoleDeptMapper;

    @Override
    public SysRole getById(Long id) {
        SysRole sysRole = sysRoleMapper.getById(id);
        return sysRole;
    }

    @Override
    public List<SysRole> selectByCondition(SysRole sysRole) {
        List<SysRole> sysRoles = sysRoleMapper.selectByCondition(sysRole);
        return sysRoles;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysRole save(SysRole sysRole) {
        sysRoleMapper.insert(sysRole);
        insertRoleMenu(sysRole);
        // 新增角色和部门信息（数据权限）
        insertRoleDept(sysRole);
        return sysRole;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysRole updateById(SysRole sysRole) {
        sysRoleMapper.update(sysRole);
        // 删除角色与菜单关联
        sysRoleMenuMapper.deleteRoleMenuByRoleId(sysRole.getId());
        insertRoleMenu(sysRole);
        // 删除角色与部门关联
        sysRoleDeptMapper.deleteRoleDeptByRoleId(sysRole.getId());
        // 新增角色和部门信息（数据权限）
        insertRoleDept(sysRole);
        return sysRole;
    }

    @Override
    public boolean removeById(Long id) {
        checkRoleUsing(id);
        // 删除角色与菜单关联
        sysRoleMenuMapper.deleteRoleMenuByRoleId(id);
        return sysRoleMapper.deleteById(id) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeByIds(List<Long> idList) {
        idList.forEach(id -> {
            if (id != null) {
                checkRoleUsing(id);
                // 删除角色与菜单关联
                sysRoleMenuMapper.deleteRoleMenuByRoleId(id);
            }
        });
        return sysRoleMapper.deleteBatchIds(idList) > 0;
    }

    @Override
    public boolean checkSysRoleExist(SysRole sysRole) {
        return sysRoleMapper.checkSysRoleExist(sysRole) != null;
    }

    @Override
    public Set<String> selectRolePermissionByUserId(Long userId) {
        return sysRoleMapper.selectRolePermissionByUserId(userId);
    }

    /**
     * 新增角色部门信息(数据权限)
     *
     * @param role 角色对象
     */
    public int insertRoleDept(SysRole role) {
        int rows = 0;
        // 新增角色与部门（数据权限）管理
        if (ArrayUtil.isNotEmpty(role.getDeptIds())) {
            for (Long deptId : role.getDeptIds()) {
                SysRoleDept rd = new SysRoleDept();
                rd.setRoleId(role.getId());
                rd.setDeptId(deptId);
                rows += sysRoleDeptMapper.insert(rd);
            }
        }
        return rows;
    }

    private int insertRoleMenu(SysRole sysRole) {
        int rows = 0;
        // 新增用户与角色管理
        if (ArrayUtil.isNotEmpty(sysRole.getMenuIds())) {
            for (Long menuId : sysRole.getMenuIds()) {
                SysRoleMenu rm = new SysRoleMenu();
                rm.setRoleId(sysRole.getId());
                rm.setMenuId(menuId);
                rows += sysRoleMenuMapper.insert(rm);
            }
        }
        return rows;
    }

    /**
     * 检查角色是否被使用
     * @param id
     */
    private void checkRoleUsing(Long id) {
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setRoleId(id);
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectByCondition(sysUserRole);
        SysRole sysRole = sysRoleMapper.getById(id);
        if (CollUtil.isNotEmpty(sysUserRoles)) {
            throw new ServiceException(sysRole.getRoleName() + " 已分配,不能删除!");
        }
    }
}
