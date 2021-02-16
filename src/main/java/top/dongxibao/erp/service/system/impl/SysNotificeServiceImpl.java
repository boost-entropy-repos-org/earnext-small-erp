package top.dongxibao.erp.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.dongxibao.erp.entity.system.SysNotifice;
import top.dongxibao.erp.mapper.system.SysNotificeMapper;
import top.dongxibao.erp.service.system.SysNotificeService;

/**
 * 系统通知Service业务层处理
 * 
 * @author Dongxibao
 * @date 2020-06-28
 */
@Service
public class SysNotificeServiceImpl implements SysNotificeService {
    @Autowired
    private SysNotificeMapper sysNotificeMapper;

    @Override
    public SysNotifice getById(Long id) {
        SysNotifice sysNotifice = sysNotificeMapper.getById(id);
        return sysNotifice;
    }

    @Override
    public List<SysNotifice> selectByCondition(SysNotifice sysNotifice) {
        return sysNotificeMapper.selectByCondition(sysNotifice);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysNotifice save(SysNotifice sysNotifice) {
        sysNotificeMapper.insert(sysNotifice);
        return sysNotifice;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysNotifice updateById(SysNotifice sysNotifice) {
        sysNotificeMapper.update(sysNotifice);
        return sysNotifice;
    }

    @Override
    public boolean removeById(Long id) {
        return sysNotificeMapper.deleteById(id) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeByIds(List<Long> idList) {
        return sysNotificeMapper.deleteBatchIds(idList) > 0;
    }
}
