package top.dongxibao.erp.service.system.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.dongxibao.erp.entity.system.SysLoginLog;
import top.dongxibao.erp.entity.system.SysLoginLog;
import top.dongxibao.erp.mapper.system.SysLoginLogMapper;
import top.dongxibao.erp.service.system.SysLoginLogService;

import java.util.List;

/**
 * 系统访问记录Service业务层处理
 * 
 * @author Dongxibao
 * @date 2020-06-21
 */
@Service
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements SysLoginLogService {

    @Override
    public List<SysLoginLog> selectByCondition(SysLoginLog sysLoginLog) {
        return baseMapper.selectByCondition(sysLoginLog);
    }
}
