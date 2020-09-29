package top.dongxibao.erp.service.system.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.dongxibao.erp.entity.system.SysNotifice;
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
public class SysNotificeServiceImpl extends ServiceImpl<SysNotificeMapper, SysNotifice> implements SysNotificeService {

    @Override
    public List<SysNotifice> selectByCondition(SysNotifice sysNotifice) {
        return baseMapper.selectByCondition(sysNotifice);
    }
}
