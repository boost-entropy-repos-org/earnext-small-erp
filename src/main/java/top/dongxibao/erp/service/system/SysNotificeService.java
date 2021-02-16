package top.dongxibao.erp.service.system;

import top.dongxibao.erp.entity.system.SysNotifice;

import java.util.List;

/**
 * 系统通知Service 接口
 * 
 * @author Dongxibao
 * @date 2020-06-28
 */
public interface SysNotificeService {
    /**
     * 查询系统通知
     *
     * @param id
     * @return 系统通知
     */
    SysNotifice getById(Long id);

    /**
     * 根据条件查询
     * @param sysNotifice
     * @return
     */
    List<SysNotifice> selectByCondition(SysNotifice sysNotifice);

    /**
     * 新增系统通知
     *
     * @param sysNotifice 系统通知
     * @return 结果
     */
    SysNotifice save(SysNotifice sysNotifice);

    /**
     * 修改系统通知
     *
     * @param sysNotifice 系统通知
     * @return 结果
     */
    SysNotifice updateById(SysNotifice sysNotifice);

    /**
     * 删除系统通知
     *
     * @param id 系统通知ID
     * @return 结果
     */
    boolean removeById(Long id);

    /**
     * 批量删除系统通知
     *
     * @param idList 需要删除的数据ID集合
     * @return 结果
     */
    boolean removeByIds(List<Long> idList);
}
