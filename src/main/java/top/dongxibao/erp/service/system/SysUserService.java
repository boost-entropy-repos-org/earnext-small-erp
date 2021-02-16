package top.dongxibao.erp.service.system;


import top.dongxibao.erp.entity.system.SysUser;

import java.util.List;

/**
 * 用户信息表
 *
 * @author Dongxibao
 * @date 2021-01-05
 */
public interface SysUserService {

    /**
     * 查询用户信息表
     *
     * @param id
     * @return 用户信息表
     */
    SysUser getById(Long id);

    /**
     * 根据条件查询
     * @param sysUser
     * @return
     */
    List<SysUser> selectByCondition(SysUser sysUser);

    /**
     * 新增用户信息表
     *
     * @param sysUser 用户信息表
     * @return 结果
     */
    SysUser save(SysUser sysUser);

    /**
     * 修改用户信息表
     *
     * @param sysUser 用户信息表
     * @return 结果
     */
    SysUser updateById(SysUser sysUser);

    /**
     * 删除用户信息表
     *
     * @param id 用户信息表ID
     * @return 结果
     */
    boolean removeById(Long id);

    /**
     * 批量删除用户信息表
     *
     * @param idList 需要删除的数据ID集合
     * @return 结果
     */
    boolean removeByIds(List<Long> idList);

    /**
     * 检验用户是否存在
     * @param sysUser
     * @return
     */
    boolean checkUsernameExist(SysUser sysUser);

    /**
     *
     * @param username
     * @return
     */
    SysUser selectUserByUserName(String username);

    /**
     * 超级管理员重置密码
     * @param userName
     * @param encryptNewPassword
     * @return
     */
    int resetUserPwd(String userName, String encryptNewPassword);
}

