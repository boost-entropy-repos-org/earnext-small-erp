package top.dongxibao.erp.mapper.system;

import org.apache.ibatis.annotations.Param;
import top.dongxibao.erp.entity.system.SysDictData;

import java.util.List;

/**
 * 数据字典子Mapper 接口
 *
 * @author Dongxibao
 * @date 2020-07-05
 */
public interface SysDictDataMapper {
    /**
     * 查询数据字典子表
     *
     * @param id 数据字典子表ID
     * @return 数据字典子表
     */
    SysDictData getById(Long id);

    /**
     * 查询数据字典子表列表
     *
     * @param sysDictData 数据字典子表
     * @return 数据字典子表集合
     */
    List<SysDictData> selectByCondition(SysDictData sysDictData);

    /**
     * 新增数据字典子表
     *
     * @param sysDictData 数据字典子表
     * @return 结果
     */
    int insert(SysDictData sysDictData);

    /**
     * 修改数据字典子表
     *
     * @param sysDictData 数据字典子表
     * @return 结果
     */
    int update(SysDictData sysDictData);

    /**
     * 删除数据字典子表
     *
     * @param id 数据字典子表ID
     * @return 结果
     */
    int deleteById(Long id);

    /**
     * 批量删除数据字典子表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBatchIds(@Param("ids") List<Long> ids);

    /**
     * 根据主表id删除
     * @param typeId
     * @return
     */
    int deleteByTypeId(@Param("typeId") Long typeId);
}
