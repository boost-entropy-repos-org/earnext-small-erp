package top.dongxibao.erp.mapper.system;
import org.apache.ibatis.annotations.Param;
import top.dongxibao.erp.entity.system.SysNotifice;

import java.util.List;

/**
 * 系统通知Mapper 接口
 * 
 * @author Dongxibao
 * @date 2020-06-28
 */
public interface SysNotificeMapper {
    /**
     * 查询系统通知
     *
     * @param id 系统通知ID
     * @return 系统通知
     */
    SysNotifice getById(Long id);

    /**
     * 查询系统通知列表
     *
     * @param sysNotifice 系统通知
     * @return 系统通知集合
     */
    List<SysNotifice> selectByCondition(SysNotifice sysNotifice);

    /**
     * 新增系统通知
     *
     * @param sysNotifice 系统通知
     * @return 结果
     */
    int insert(SysNotifice sysNotifice);

    /**
     * 修改系统通知
     *
     * @param sysNotifice 系统通知
     * @return 结果
     */
    int update(SysNotifice sysNotifice);

    /**
     * 删除系统通知
     *
     * @param id 系统通知ID
     * @return 结果
     */
    int deleteById(Long id);

    /**
     * 批量删除系统通知
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBatchIds(@Param("ids") List<Long> ids);
}
