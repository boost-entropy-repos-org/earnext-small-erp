package top.dongxibao.erp.util;

import eu.bitwalker.useragentutils.UserAgent;
import top.dongxibao.erp.entity.system.SysLoginLog;
import top.dongxibao.erp.service.system.SysLoginLogService;
import top.dongxibao.erp.util.ip.IpUtils;

/**
 * @ClassName LoginLogUtils
 * @description 登陆日志记录
 * @author Dongxibao
 * @date 2020/6/28
 * @Version 1.0
 */
public class LoginLogUtils {

    public static void packageSysLoginLog(final String username, final Integer status, final String message) {
        final String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        final UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        // 获取客户端操作系统
        String os = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setIpAddr(ip);
        sysLoginLog.setUsername(username);
        sysLoginLog.setOs(os);
        sysLoginLog.setBrowser(browser);
        sysLoginLog.setStatus(status);
        sysLoginLog.setMessage(message);
        // 插入数据
        SpringUtils.getBean(SysLoginLogService.class).save(sysLoginLog);
    }
}
