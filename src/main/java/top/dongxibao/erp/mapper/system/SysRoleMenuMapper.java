package top.dongxibao.erp.mapper.system;

import org.apache.ibatis.annotations.Param;
import top.dongxibao.erp.entity.system.SysRoleMenu;

import java.util.List;

/**
 * 角色和菜单关联表
 * 
 * @author Dongxibao
 * @date 2021-01-05
 */
public interface SysRoleMenuMapper {

    /**
     * 查询角色和菜单关联表
     *
     * @param id 角色和菜单关联表ID
     * @return 角色和菜单关联表
     */
    SysRoleMenu getById(Long id);

    /**
     * 查询角色和菜单关联表列表
     *
     * @param sysRoleMenu 角色和菜单关联表
     * @return 角色和菜单关联表集合
     */
    List<SysRoleMenu> selectByCondition(SysRoleMenu sysRoleMenu);
    /**
     * 查询菜单使用数量
     *
     * @param menuId 菜单ID
     * @return 结果
     */
    int checkMenuExistRole(@Param("menuId") Long menuId);
    /**
     * 新增角色和菜单关联表
     *
     * @param sysRoleMenu 角色和菜单关联表
     * @return 结果
     */
    int insert(SysRoleMenu sysRoleMenu);

    /**
     * 修改角色和菜单关联表
     *
     * @param sysRoleMenu 角色和菜单关联表
     * @return 结果
     */
    int update(SysRoleMenu sysRoleMenu);

    /**
     * 删除角色和菜单关联表
     *
     * @param id 角色和菜单关联表ID
     * @return 结果
     */
    int deleteById(Long id);

    /**
     * 批量删除角色和菜单关联表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBatchIds(@Param("ids") List<Long> ids);

    int deleteRoleMenuByRoleId(@Param("roleId") Long roleId);
}
