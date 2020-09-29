package top.dongxibao.erp.mapper.system;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.dongxibao.erp.entity.system.SysLog;

import java.util.List;

/**
 * 系统操作日志记录Mapper 接口
 * 
 * @author Dongxibao
 * @date 2020-06-21
 */
public interface SysLogMapper extends BaseMapper<SysLog> {
    /**
     * 根据条件查询
     * @param sysLog
     * @return
     */
    List<SysLog> selectByCondition(SysLog sysLog);
}
