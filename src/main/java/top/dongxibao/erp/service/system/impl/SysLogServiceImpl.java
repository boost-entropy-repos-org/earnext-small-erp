package top.dongxibao.erp.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.dongxibao.erp.entity.system.SysLog;
import top.dongxibao.erp.mapper.system.SysLogMapper;
import top.dongxibao.erp.service.system.SysLogService;

import java.util.Date;
import java.util.List;

/**
 * 系统日志记录
 *
 * @author Dongxibao
 * @date 2021-01-04
 */
@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysLogMapper sysLogMapper;
    
    @Override
    public SysLog getById(Long id) {
        SysLog sysLog = sysLogMapper.getById(id);
        return sysLog;
    }

    @Override
    public List<SysLog> selectByCondition(SysLog sysLog) {
        return sysLogMapper.selectByCondition(sysLog);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysLog save(SysLog sysLog) {
        sysLog.setCreateTime(new Date());
        sysLogMapper.insert(sysLog);
        return sysLog;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysLog updateById(SysLog sysLog) {
        sysLog.setUpdateTime(new Date());
        sysLogMapper.update(sysLog);
        return sysLog;
    }

    @Override
    public boolean removeById(Long id) {
        return sysLogMapper.deleteById(id) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeByIds(List idList) {
        return sysLogMapper.deleteBatchIds(idList) > 0;
    }
}
