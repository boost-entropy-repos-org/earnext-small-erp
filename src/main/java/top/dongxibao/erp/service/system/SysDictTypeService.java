package top.dongxibao.erp.service.system;

import top.dongxibao.erp.entity.system.SysDictType;
import java.io.Serializable;
import java.util.List;

/**
 * 数据字典主Service 接口
 * 
 * @author Dongxibao
 * @date 2020-07-05
 */
public interface SysDictTypeService {

    /**
     * 查询数据字典主表
     *
     * @param id
     * @return 数据字典主表
     */
    SysDictType getById(Long id);

    /**
     * 根据条件查询
     * @param sysDictType
     * @return
     */
    List<SysDictType> selectByCondition(SysDictType sysDictType);

    /**
     * 新增数据字典主表
     *
     * @param sysDictType 数据字典主表
     * @return 结果
     */
    SysDictType save(SysDictType sysDictType);

    /**
     * 修改数据字典主表
     *
     * @param sysDictType 数据字典主表
     * @return 结果
     */
    SysDictType updateById(SysDictType sysDictType);

    /**
     * 删除数据字典主表
     *
     * @param id 数据字典主表ID
     * @return 结果
     */
    boolean removeById(Long id);

    /**
     * 批量删除数据字典主表
     *
     * @param idList 需要删除的数据ID集合
     * @return 结果
     */
    boolean removeByIds(List<Long> idList);
}
