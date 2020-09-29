package top.dongxibao.erp.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import top.dongxibao.erp.entity.system.SysDictType;
import top.dongxibao.erp.entity.system.SysDictType;

import java.io.Serializable;
import java.util.List;

/**
 * 数据字典主Service 接口
 * 
 * @author Dongxibao
 * @date 2020-07-05
 */
public interface SysDictTypeService extends IService<SysDictType> {
    @Override
    SysDictType getById(Serializable id);

    /**
     * 根据条件查询
     * @param sysDictType
     * @return
     */
    List<SysDictType> selectByCondition(SysDictType sysDictType);

    @Override
    boolean save(SysDictType entity);

    @Override
    boolean updateById(SysDictType entity);

    @Override
    boolean removeById(Serializable id);
}
