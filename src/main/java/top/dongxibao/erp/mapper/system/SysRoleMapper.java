package top.dongxibao.erp.mapper.system;

import org.apache.ibatis.annotations.Param;
import top.dongxibao.erp.entity.system.SysRole;

import java.util.List;
import java.util.Set;

/**
 * 角色信息表
 * 
 * @author Dongxibao
 * @date 2021-01-05
 */
public interface SysRoleMapper {

    /**
     * 查询角色信息表
     *
     * @param id 角色信息表ID
     * @return 角色信息表
     */
    SysRole getById(Long id);

    /**
     * 查询角色信息表列表
     *
     * @param sysRole 角色信息表
     * @return 角色信息表集合
     */
    List<SysRole> selectByCondition(SysRole sysRole);

    /**
     * 新增角色信息表
     *
     * @param sysRole 角色信息表
     * @return 结果
     */
    int insert(SysRole sysRole);

    /**
     * 修改角色信息表
     *
     * @param sysRole 角色信息表
     * @return 结果
     */
    int update(SysRole sysRole);

    /**
     * 删除角色信息表
     *
     * @param id 角色信息表ID
     * @return 结果
     */
    int deleteById(Long id);

    /**
     * 批量删除角色信息表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBatchIds(@Param("ids") List<Long> ids);

    SysRole checkSysRoleExist(SysRole sysRole);

    Set<String> selectRolePermissionByUserId(@Param("userId") Long userId);

    SysRole selectRoleById(@Param("roleId") Long roleId);
}
