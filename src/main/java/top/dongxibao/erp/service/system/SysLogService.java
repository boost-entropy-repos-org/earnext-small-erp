package top.dongxibao.erp.service.system;


import com.baomidou.mybatisplus.extension.service.IService;
import top.dongxibao.erp.entity.system.SysLog;
import top.dongxibao.erp.entity.system.SysLog;

import java.util.List;

/**
 * 系统操作日志记录Service 接口
 * 
 * @author Dongxibao
 * @date 2020-06-21
 */
public interface SysLogService extends IService<SysLog> {
    /**
     * 根据条件查询
     * @param sysLog
     * @return
     */
    List<SysLog> selectByCondition(SysLog sysLog);
}
