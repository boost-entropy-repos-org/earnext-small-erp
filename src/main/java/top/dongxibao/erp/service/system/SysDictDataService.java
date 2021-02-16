package top.dongxibao.erp.service.system;

import top.dongxibao.erp.entity.system.SysDictData;

import java.util.List;

/**
 * @ClassName SysDictDataService
 * @description 1
 * @author Dongxibao
 * @date 2020/7/5
 * @Version 1.0
 */
public interface SysDictDataService {
    /**
     * 查询数据字典子表
     *
     * @param id
     * @return 数据字典子表
     */
    SysDictData getById(Long id);

    /**
     * 根据条件查询
     * @param sysDictData
     * @return
     */
    List<SysDictData> selectByCondition(SysDictData sysDictData);

    /**
     * 新增数据字典子表
     *
     * @param sysDictData 数据字典子表
     * @return 结果
     */
    SysDictData save(SysDictData sysDictData);

    /**
     * 修改数据字典子表
     *
     * @param sysDictData 数据字典子表
     * @return 结果
     */
    SysDictData updateById(SysDictData sysDictData);

    /**
     * 删除数据字典子表
     *
     * @param id 数据字典子表ID
     * @return 结果
     */
    boolean removeById(Long id);

    /**
     * 批量删除数据字典子表
     *
     * @param idList 需要删除的数据ID集合
     * @return 结果
     */
    boolean removeByIds(List<Long> idList);
}
