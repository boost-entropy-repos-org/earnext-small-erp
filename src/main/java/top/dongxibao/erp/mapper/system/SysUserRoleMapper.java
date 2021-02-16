package top.dongxibao.erp.mapper.system;

import org.apache.ibatis.annotations.Param;
import top.dongxibao.erp.entity.system.SysUserRole;

import java.util.List;

/**
 * 用户和角色关联表
 *
 * @author Dongxibao
 * @date 2021-01-05
 */
public interface SysUserRoleMapper {

    /**
     * 查询用户和角色关联表
     *
     * @param id 用户和角色关联表ID
     * @return 用户和角色关联表
     */
    SysUserRole getById(Long id);

    /**
     * 查询用户和角色关联表列表
     *
     * @param sysUserRole 用户和角色关联表
     * @return 用户和角色关联表集合
     */
    List<SysUserRole> selectByCondition(SysUserRole sysUserRole);

    /**
     * 新增用户和角色关联表
     *
     * @param sysUserRole 用户和角色关联表
     * @return 结果
     */
    int insert(SysUserRole sysUserRole);

    /**
     * 修改用户和角色关联表
     *
     * @param sysUserRole 用户和角色关联表
     * @return 结果
     */
    int update(SysUserRole sysUserRole);

    /**
     * 删除用户和角色关联表
     *
     * @param id 用户和角色关联表ID
     * @return 结果
     */
    int deleteById(Long id);

    /**
     * 批量删除用户和角色关联表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBatchIds(@Param("ids") List<Long> ids);

    int deleteUserRoleByUserId(Long userId);
}
