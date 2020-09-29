package top.dongxibao.erp.service.system;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.extension.service.IService;
import top.dongxibao.erp.entity.system.SysRole;
import top.dongxibao.erp.entity.system.SysRole;

/**
 * 角色信息Service 接口
 * 
 * @author Dongxibao
 * @date 2020-06-13
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 根据条件查询
     * @param sysRole
     * @return
     */
    List<SysRole> selectByCondition(SysRole sysRole);

    boolean save(SysRole entity);

    @Override
    boolean removeById(Serializable id);

    boolean updateById(SysRole entity);

    /**
     * 根据用户id查询权限
     * @param id
     * @return
     */
    Set<String> selectRolePermissionByUserId(Long id);
}
