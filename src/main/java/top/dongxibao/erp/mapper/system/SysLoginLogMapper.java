package top.dongxibao.erp.mapper.system;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.dongxibao.erp.entity.system.SysLoginLog;

import java.util.List;

/**
 * 系统访问记录Mapper 接口
 * 
 * @author Dongxibao
 * @date 2020-06-21
 */
public interface SysLoginLogMapper extends BaseMapper<SysLoginLog> {
    /**
     * 根据条件查询
     * @param sysLoginLog
     * @return
     */
    List<SysLoginLog> selectByCondition(SysLoginLog sysLoginLog);
}
