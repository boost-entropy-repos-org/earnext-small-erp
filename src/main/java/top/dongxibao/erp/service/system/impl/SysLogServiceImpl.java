package top.dongxibao.erp.service.system.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.dongxibao.erp.entity.system.SysLog;
import top.dongxibao.erp.entity.system.SysLog;
import top.dongxibao.erp.mapper.system.SysLogMapper;
import top.dongxibao.erp.service.system.SysLogService;

/**
 * 系统操作日志记录Service业务层处理
 * 
 * @author Dongxibao
 * @date 2020-06-21
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Override
    public List<SysLog> selectByCondition(SysLog sysLog) {
        return baseMapper.selectByCondition(sysLog);
    }
}
