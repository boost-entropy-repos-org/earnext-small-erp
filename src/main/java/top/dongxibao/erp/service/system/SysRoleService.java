package top.dongxibao.erp.service.system;


import top.dongxibao.erp.entity.system.SysRole;

import java.util.List;
import java.util.Set;

/**
 * 角色信息表
 *
 * @author Dongxibao
 * @date 2021-01-05
 */
public interface SysRoleService {

    /**
     * 查询角色信息表
     *
     * @param id
     * @return 角色信息表
     */
    SysRole getById(Long id);

    /**
     * 根据条件查询
     * @param sysRole
     * @return
     */
    List<SysRole> selectByCondition(SysRole sysRole);

    /**
     * 新增角色信息表
     *
     * @param sysRole 角色信息表
     * @return 结果
     */
    SysRole save(SysRole sysRole);

    /**
     * 修改角色信息表
     *
     * @param sysRole 角色信息表
     * @return 结果
     */
    SysRole updateById(SysRole sysRole);

    /**
     * 删除角色信息表
     *
     * @param id 角色信息表ID
     * @return 结果
     */
    boolean removeById(Long id);

    /**
     * 批量删除角色信息表
     *
     * @param idList 需要删除的数据ID集合
     * @return 结果
     */
    boolean removeByIds(List<Long> idList);

    boolean checkSysRoleExist(SysRole sysRole);

    Set<String> selectRolePermissionByUserId(Long userId);
}

