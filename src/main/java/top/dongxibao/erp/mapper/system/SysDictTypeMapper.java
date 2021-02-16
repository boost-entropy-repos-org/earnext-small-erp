package top.dongxibao.erp.mapper.system;

import org.apache.ibatis.annotations.Param;
import top.dongxibao.erp.entity.system.SysDictType;

import java.util.List;

/**
 * 数据字典主Mapper 接口
 * 
 * @author Dongxibao
 * @date 2020-07-05
 */
public interface SysDictTypeMapper {

    /**
     * 检查是否存在
     * @param entity
     * @return
     */
    List<SysDictType> checkDictType(SysDictType entity);
    /**
     * 查询数据字典主表
     *
     * @param id 数据字典主表ID
     * @return 数据字典主表
     */
    SysDictType getById(Long id);

    /**
     * 查询数据字典主表列表
     *
     * @param sysDictType 数据字典主表
     * @return 数据字典主表集合
     */
    List<SysDictType> selectByCondition(SysDictType sysDictType);

    /**
     * 新增数据字典主表
     *
     * @param sysDictType 数据字典主表
     * @return 结果
     */
    int insert(SysDictType sysDictType);

    /**
     * 修改数据字典主表
     *
     * @param sysDictType 数据字典主表
     * @return 结果
     */
    int update(SysDictType sysDictType);

    /**
     * 删除数据字典主表
     *
     * @param id 数据字典主表ID
     * @return 结果
     */
    int deleteById(Long id);

    /**
     * 批量删除数据字典主表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBatchIds(@Param("ids") List<Long> ids);
}
