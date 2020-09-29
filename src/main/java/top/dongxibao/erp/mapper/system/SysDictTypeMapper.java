package top.dongxibao.erp.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.dongxibao.erp.entity.system.SysDictType;

import java.util.List;

/**
 * 数据字典主Mapper 接口
 * 
 * @author Dongxibao
 * @date 2020-07-05
 */
public interface SysDictTypeMapper extends BaseMapper<SysDictType> {
    /**
     * 根据条件查询
     * @param sysDictType
     * @return
     */
    List<SysDictType> selectByCondition(SysDictType sysDictType);

    /**
     * 检查是否存在
     * @param entity
     * @return
     */
    List<SysDictType> checkDictType(SysDictType entity);
}
