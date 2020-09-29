package top.dongxibao.erp.service.system;


import top.dongxibao.erp.entity.system.SysUserOnline;
import top.dongxibao.erp.security.JWTUser;

/**
 * 在线用户 服务层
 *
 * @author Dongxibao
 * @date 2020-07-04
 */
public interface SysUserOnlineService {
    /**
     * 通过登录地址查询信息
     *
     * @param ipaddr 登录地址
     * @param user 用户信息
     * @return 在线用户信息
     */
    SysUserOnline selectOnlineByIpaddr(String ipaddr, JWTUser user);

    /**
     * 通过用户名称查询信息
     *
     * @param userName 用户名称
     * @param user 用户信息
     * @return 在线用户信息
     */
    SysUserOnline selectOnlineByUserName(String userName, JWTUser user);

    /**
     * 通过登录地址/用户名称查询信息
     *
     * @param ipaddr 登录地址
     * @param userName 用户名称
     * @param user 用户信息
     * @return 在线用户信息
     */
    SysUserOnline selectOnlineByInfo(String ipaddr, String userName, JWTUser user);

    /**
     * 设置在线用户信息
     *
     * @param user 用户信息
     * @return 在线用户
     */
    SysUserOnline loginUserToUserOnline(JWTUser user);
}
