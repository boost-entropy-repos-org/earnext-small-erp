package top.dongxibao.erp.service.system;


import com.baomidou.mybatisplus.extension.service.IService;
import top.dongxibao.erp.entity.system.SysLoginLog;
import top.dongxibao.erp.entity.system.SysLoginLog;

import java.util.List;

/**
 * 系统访问记录Service 接口
 * 
 * @author Dongxibao
 * @date 2020-06-21
 */
public interface SysLoginLogService extends IService<SysLoginLog> {
    /**
     * 根据条件查询
     * @param sysLoginLog
     * @return
     */
    List<SysLoginLog> selectByCondition(SysLoginLog sysLoginLog);
}
