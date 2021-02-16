package top.dongxibao.erp.service.system.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.dongxibao.erp.entity.system.SysRole;
import top.dongxibao.erp.entity.system.SysUser;
import top.dongxibao.erp.entity.system.SysUserRole;
import top.dongxibao.erp.mapper.system.SysRoleMapper;
import top.dongxibao.erp.mapper.system.SysUserMapper;
import top.dongxibao.erp.mapper.system.SysUserRoleMapper;
import top.dongxibao.erp.security.SecurityUtils;
import top.dongxibao.erp.service.system.SysUserService;

import java.util.List;

/**
 * 用户信息表
 *
 * @author Dongxibao
 * @date 2021-01-05
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Override
    public SysUser getById(Long id) {
        SysUser sysUser = sysUserMapper.getById(id);
        SysRole sysRole = new SysRole();
        sysRole.setUserId(id);
        List<SysRole> sysRoles = sysRoleMapper.selectByCondition(sysRole);
        sysUser.setRoleList(sysRoles);
        return sysUser;
    }

    @Override
    public List<SysUser> selectByCondition(SysUser sysUser) {
        List<SysUser> sysUsers = sysUserMapper.selectByCondition(sysUser);
        if (CollUtil.isNotEmpty(sysUsers)) {
            sysUsers.forEach(sysUser1 -> {
                SysRole sysRole = new SysRole();
                sysRole.setUserId(sysUser1.getId());
                List<SysRole> sysRoles = sysRoleMapper.selectByCondition(sysRole);
                sysUser.setRoleList(sysRoles);
            });
        }
        return sysUsers;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysUser save(SysUser sysUser) {
        String encryptPassword = SecurityUtils.encryptPassword(sysUser.getPassword().trim());
        sysUser.setPassword(encryptPassword);
        sysUserMapper.insert(sysUser);
        // 新增用户与角色管理
        insertUserRole(sysUser);
        return sysUser;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysUser updateById(SysUser sysUser) {
        sysUserMapper.update(sysUser);
        Long userId = sysUser.getId();
        // 删除用户与角色关联
        sysUserRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(sysUser);
        return sysUser;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeById(Long id) {
        // 删除用户与角色关联
        sysUserRoleMapper.deleteUserRoleByUserId(id);
        sysUserMapper.deleteById(id);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeByIds(List<Long> idList) {
        // 删除用户与角色关联
        idList.forEach(id -> {
            if (id != null) {
                sysUserRoleMapper.deleteUserRoleByUserId(id);
            }
        });
        sysUserMapper.deleteBatchIds(idList);
        return true;
    }

    @Override
    public boolean checkUsernameExist(SysUser sysUser) {
        return sysUserMapper.checkUsernameExist(sysUser) != null;
    }

    @Override
    public SysUser selectUserByUserName(String username) {
        return sysUserMapper.selectUserByUserName(username);
    }

    @Override
    public int resetUserPwd(String userName, String encryptNewPassword) {
        return sysUserMapper.resetUserPwd(userName, encryptNewPassword);
    }

    private void insertUserRole(SysUser sysUser) {
        Long[] roles = sysUser.getRoleIds();
        if (ArrayUtil.isNotEmpty(roles)) {
            // 新增用户与角色管理
            for (Long roleId : roles) {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(sysUser.getId());
                ur.setRoleId(roleId);
                sysUserRoleMapper.insert(ur);
            }
        }
    }
}
