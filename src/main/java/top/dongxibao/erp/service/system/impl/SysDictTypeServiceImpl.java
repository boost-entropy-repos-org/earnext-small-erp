package top.dongxibao.erp.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.dongxibao.erp.entity.system.SysDictData;
import top.dongxibao.erp.entity.system.SysDictType;
import top.dongxibao.erp.exception.ServiceException;
import top.dongxibao.erp.mapper.system.SysDictTypeMapper;
import top.dongxibao.erp.service.system.SysDictDataService;
import top.dongxibao.erp.service.system.SysDictTypeService;

import java.io.Serializable;
import java.util.List;

/**
 * 数据字典主Service业务层处理
 * 
 * @author Dongxibao
 * @date 2020-07-05
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictType> implements SysDictTypeService {
// todo:测试
    @Autowired
    private SysDictDataService sysDictDataService;

    @Override
    public SysDictType getById(Serializable id) {
        SysDictType sysDictType = super.getById(id);
        List<SysDictData> sysDictDataList =
                sysDictDataService.list(new LambdaQueryWrapper<SysDictData>().eq(SysDictData::getDictTypeId, id));
        sysDictType.setSysDictDataList(sysDictDataList);
        return sysDictType;
    }

    @Override
    public List<SysDictType> selectByCondition(SysDictType sysDictType) {
        return baseMapper.selectByCondition(sysDictType);
    }

    @Override
    public boolean save(SysDictType entity) {
        // 校验是否存在
        List<SysDictType> sysDictTypes = baseMapper.checkDictType(entity);
        if (CollectionUtils.isNotEmpty(sysDictTypes)) {
            throw new ServiceException(entity.getDictType() + " 已存在", 400);
        }
        // 保存子表
        List<SysDictData> sysDictDataList = entity.getSysDictDataList();
        if (CollectionUtils.isNotEmpty(sysDictDataList)) {
            sysDictDataList.forEach(sysDictData -> {
                sysDictData.setDictTypeId(entity.getId());
                sysDictDataService.saveOrUpdate(sysDictData);
            });
        }
        return super.save(entity);
    }

    @Override
    public boolean updateById(SysDictType entity) {
        // 校验是否存在
        List<SysDictType> sysDictTypes = baseMapper.checkDictType(entity);
        if (CollectionUtils.isNotEmpty(sysDictTypes)) {
            throw new ServiceException(entity.getDictType() + " 已存在", 400);
        }
        // 修改字表
        List<SysDictData> sysDictDataList = entity.getSysDictDataList();
        if (CollectionUtils.isNotEmpty(sysDictDataList)) {
            sysDictDataList.forEach(sysDictData -> {
                sysDictData.setDictTypeId(entity.getId());
                sysDictDataService.saveOrUpdate(sysDictData);
            });
        }
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        // 删除字表
        sysDictDataService.remove(new LambdaQueryWrapper<SysDictData>().eq(SysDictData::getDictTypeId, id));
        return super.removeById(id);
    }
}
