package top.dongxibao.erp.entity.system;

import lombok.Data;

/**
 * 当前在线会话
 *
 * @author Dongxibao
 * @date 2020-07-04
 */
@Data
public class SysUserOnline {
    /** 会话编号 */
    private String tokenId;

    /** 部门名称 */
    private String deptName;

    /** 用户名称 */
    private String userName;

    /** 登录IP地址 */
    private String ipaddr;

    /** 浏览器类型 */
    private String browser;

    /** 操作系统 */
    private String os;

    /** 登录时间 */
    private Long loginTime;
}
