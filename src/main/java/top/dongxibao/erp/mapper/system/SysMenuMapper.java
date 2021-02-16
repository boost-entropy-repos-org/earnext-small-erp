package top.dongxibao.erp.mapper.system;
import org.apache.ibatis.annotations.Param;
import top.dongxibao.erp.entity.system.SysMenu;

import java.util.List;
import java.util.Set;

/**
 * 菜单权限表
 * 
 * @author Dongxibao
 * @date 2021-01-05
 */
public interface SysMenuMapper {

    /**
     * 查询菜单权限表
     *
     * @param id 菜单权限表ID
     * @return 菜单权限表
     */
    SysMenu getById(Long id);

    /**
     * 查询菜单权限表列表
     *
     * @param sysMenu 菜单权限表
     * @return 菜单权限表集合
     */
    List<SysMenu> selectByCondition(SysMenu sysMenu);

    /**
     * 校验唯一
     * @param entity
     * @return
     */
    SysMenu checkSysMenu(SysMenu entity);
    /**
     * 校验checkRoutingPath唯一
     * @param entity
     * @return
     */
    SysMenu checkRoutingPath(SysMenu entity);

    /**
     * 新增菜单权限表
     *
     * @param sysMenu 菜单权限表
     * @return 结果
     */
    int insert(SysMenu sysMenu);

    /**
     * 修改菜单权限表
     *
     * @param sysMenu 菜单权限表
     * @return 结果
     */
    int update(SysMenu sysMenu);

    /**
     * 删除菜单权限表
     *
     * @param id 菜单权限表ID
     * @return 结果
     */
    int deleteById(Long id);

    /**
     * 批量删除菜单权限表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBatchIds(@Param("ids") List<Long> ids);

    int hasChildByMenuId(@Param("id") Long id);

    Set<String> selectMenuPermsByUserId(@Param("userId") Long userId);

    List<SysMenu> selectMenuListByUserId(SysMenu menu);

    List<Long> selectMenuListByRoleId(@Param("roleId") Long roleId);

    List<SysMenu> selectMenuTreeByUserId(@Param("userId") Long userId);
}
