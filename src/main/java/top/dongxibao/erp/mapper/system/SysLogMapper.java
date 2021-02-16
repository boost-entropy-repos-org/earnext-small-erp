package top.dongxibao.erp.mapper.system;

import top.dongxibao.erp.entity.system.SysLog;

import java.util.List;

/**
 * 系统日志记录
 * 
 * @author Dongxibao
 * @date 2021-01-04
 */
public interface SysLogMapper {

    /**
     * 查询系统日志记录
     *
     * @param id 系统日志记录ID
     * @return 系统日志记录
     */
    SysLog getById(Long id);

    /**
     * 查询系统日志记录列表
     *
     * @param sysLog 系统日志记录
     * @return 系统日志记录集合
     */
    List<SysLog> selectByCondition(SysLog sysLog);

    /**
     * 新增系统日志记录
     *
     * @param sysLog 系统日志记录
     * @return 结果
     */
    int insert(SysLog sysLog);

    /**
     * 修改系统日志记录
     *
     * @param sysLog 系统日志记录
     * @return 结果
     */
    int update(SysLog sysLog);

    /**
     * 删除系统日志记录
     *
     * @param id 系统日志记录ID
     * @return 结果
     */
    int deleteById(Long id);

    /**
     * 批量删除系统日志记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBatchIds(List ids);
}
