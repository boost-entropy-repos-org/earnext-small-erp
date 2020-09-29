package top.dongxibao.erp.util;

import top.dongxibao.erp.entity.system.SysNotifice;
import top.dongxibao.erp.mapper.system.SysNotificeMapper;
import top.dongxibao.erp.security.SecurityUtils;

import java.util.Set;

/**
 * @ClassName NotificeUtils
 * @description 系统通知工具类
 * @author Dongxibao
 * @date 2020/6/30
 * @Version 1.0
 */
public class NotificeUtils {

    private static SysNotificeMapper sysNotificeMapper = SpringUtils.getBean(SysNotificeMapper.class);

    public static void  sendSysNotifice(Set<String> userIds, String title, String content){
        String currentUsername = SecurityUtils.getUsername();
        for (String userId : userIds) {
            SysNotifice sysNotifice = new SysNotifice();
            sysNotifice.setFromUser(currentUsername);
            sysNotifice.setToUser(userId);
            sysNotifice.setTitle(title);
            sysNotifice.setContent(content);
            sysNotificeMapper.insert(sysNotifice);
        }
    }
}
