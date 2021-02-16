package top.dongxibao.erp.mapper.system;

import org.apache.ibatis.annotations.Param;
import top.dongxibao.erp.entity.system.SysUser;

import java.util.List;

/**
 * 用户信息表
 *
 * @author Dongxibao
 * @date 2021-01-05
 */
public interface SysUserMapper {

    /**
     * 查询用户信息表
     *
     * @param id 用户信息表ID
     * @return 用户信息表
     */
    SysUser getById(Long id);

    /**
     * 查询用户信息表列表
     *
     * @param sysUser 用户信息表
     * @return 用户信息表集合
     */
    List<SysUser> selectByCondition(SysUser sysUser);

    /**
     * 新增用户信息表
     *
     * @param sysUser 用户信息表
     * @return 结果
     */
    int insert(SysUser sysUser);

    /**
     * 修改用户信息表
     *
     * @param sysUser 用户信息表
     * @return 结果
     */
    int update(SysUser sysUser);

    /**
     * 删除用户信息表
     *
     * @param id 用户信息表ID
     * @return 结果
     */
    int deleteById(Long id);

    /**
     * 批量删除用户信息表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBatchIds(@Param("ids") List<Long> ids);

    SysUser checkUsernameExist(SysUser sysUser);

    SysUser selectUserByUserName(@Param("username") String username);

    int resetUserPwd(@Param("username") String username, @Param("encryptNewPassword") String encryptNewPassword);
}
