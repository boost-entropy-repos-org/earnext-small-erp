package top.dongxibao.erp.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.dongxibao.erp.entity.system.SysUser;
import top.dongxibao.erp.entity.system.SysUserRole;
import top.dongxibao.erp.exception.ServiceException;
import top.dongxibao.erp.mapper.system.SysUserMapper;
import top.dongxibao.erp.mapper.system.SysUserRoleMapper;
import top.dongxibao.erp.service.system.SysUserService;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author Dongxibao
 * @date 2020-06-14
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<SysUser> selectByCondition(SysUser sysUser) {
        return baseMapper.selectByCondition(sysUser);
    }

    @Override
    public boolean save(SysUser entity) {
        // 0.校验用户名
        List<SysUser> sysUsers = baseMapper.checkSysUser(entity);
        if (CollectionUtils.isNotEmpty(sysUsers)) {
            throw new ServiceException(entity.getUsername() + " 已存在", 400);
        }
        // 1.用户角色关联
        insertSysUserRole(entity);
        // 2.新增用户
        return super.save(entity);
    }

    @Override
    public boolean updateById(SysUser entity) {
        // 0.校验用户名
        List<SysUser> sysUsers = baseMapper.checkSysUser(entity);
        if (CollectionUtils.isNotEmpty(sysUsers)) {
            throw new ServiceException(entity.getUsername() + " 已存在", 400);
        }
        // 1.删除用户角色关联
        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId,entity.getId()));
        // 2.重新用户角色关联
        insertSysUserRole(entity);
        // 3.修改用户
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        // 1.删除用户角色关联
        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId,id));
        // 2.删除用户
        return super.removeById(id);
    }

    @Override
    public SysUser selectUserByUserName(String username) {
        return baseMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
    }

    @Override
    public int resetUserPwd(String username, String password) {
        return baseMapper.updateByUsername(username, password);
    }

    private void insertSysUserRole(SysUser entity) {
        Long[] roleIds = entity.getRoleIds();
        if (roleIds != null && roleIds.length > 0) {
            for (Long roleId : roleIds) {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setUserId(entity.getId());
                sysUserRole.setRoleId(roleId);
                sysUserRoleMapper.insert(sysUserRole);
            }
        }
    }
}
