package top.dongxibao.erp.service.system;

import top.dongxibao.erp.entity.system.SysLog;

import java.util.List;

/**
 * 系统日志记录
 *
 * @author Dongxibao
 * @date 2021-01-04
 */
public interface SysLogService {

    /**
     * 查询系统日志记录
     *
     * @param id
     * @return 系统日志记录
     */
    SysLog getById(Long id);

    /**
     * 根据条件查询
     * @param sysLog
     * @return
     */
    List<SysLog> selectByCondition(SysLog sysLog);

    /**
     * 新增系统日志记录
     *
     * @param sysLog 系统日志记录
     * @return 结果
     */
    SysLog save(SysLog sysLog);

    /**
     * 修改系统日志记录
     *
     * @param sysLog 系统日志记录
     * @return 结果
     */
    SysLog updateById(SysLog sysLog);

    /**
     * 删除系统日志记录
     *
     * @param id 系统日志记录ID
     * @return 结果
     */
    boolean removeById(Long id);

    /**
     * 批量删除系统日志记录
     *
     * @param idList 需要删除的数据ID集合
     * @return 结果
     */
    boolean removeByIds(List idList);
}

