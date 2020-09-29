package top.dongxibao.erp.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import top.dongxibao.erp.entity.system.SysNotifice;
import top.dongxibao.erp.entity.system.SysNotifice;

import java.util.List;

/**
 * 系统通知Service 接口
 * 
 * @author Dongxibao
 * @date 2020-06-28
 */
public interface SysNotificeService extends IService<SysNotifice> {
    /**
     * 根据条件查询
     * @param sysNotifice
     * @return
     */
    List<SysNotifice> selectByCondition(SysNotifice sysNotifice);
}
