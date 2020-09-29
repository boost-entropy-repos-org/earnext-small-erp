package top.dongxibao.erp.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.dongxibao.erp.entity.system.SysNotifice;

import java.util.List;

/**
 * 系统通知Mapper 接口
 * 
 * @author Dongxibao
 * @date 2020-06-28
 */
public interface SysNotificeMapper extends BaseMapper<SysNotifice> {
    /**
     * 根据条件查询
     * @param sysNotifice
     * @return
     */
    List<SysNotifice> selectByCondition(SysNotifice sysNotifice);
}
