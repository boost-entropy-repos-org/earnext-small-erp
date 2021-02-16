package top.dongxibao.erp.mapper.system;

import org.apache.ibatis.annotations.Param;
import top.dongxibao.erp.entity.system.SysRoleDept;

import java.util.List;

/**
 * 角色和部门关联表
 * 
 * @author Dongxibao
 * @date 2021-01-12
 */
public interface SysRoleDeptMapper {

    /**
     * 查询角色和部门关联表
     *
     * @param id 角色和部门关联表ID
     * @return 角色和部门关联表
     */
    SysRoleDept getById(Long id);

    /**
     * 查询角色和部门关联表列表
     *
     * @param sysRoleDept 角色和部门关联表
     * @return 角色和部门关联表集合
     */
    List<SysRoleDept> selectByCondition(SysRoleDept sysRoleDept);

    /**
     * 新增角色和部门关联表
     *
     * @param sysRoleDept 角色和部门关联表
     * @return 结果
     */
    int insert(SysRoleDept sysRoleDept);

    /**
     * 修改角色和部门关联表
     *
     * @param sysRoleDept 角色和部门关联表
     * @return 结果
     */
    int update(SysRoleDept sysRoleDept);

    /**
     * 删除角色和部门关联表
     *
     * @param id 角色和部门关联表ID
     * @return 结果
     */
    int deleteById(Long id);

    /**
     * 批量删除角色和部门关联表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBatchIds(@Param("ids") List<Long> ids);

    /**
     * 根据角色id删除
     * @param roleId
     * @return
     */
    int deleteRoleDeptByRoleId(@Param("roleId") Long roleId);
}
