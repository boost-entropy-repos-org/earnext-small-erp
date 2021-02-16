package top.dongxibao.erp.service.system.impl;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.dongxibao.erp.entity.system.SysDictData;
import top.dongxibao.erp.entity.system.SysDictType;
import top.dongxibao.erp.exception.ServiceException;
import top.dongxibao.erp.mapper.system.SysDictDataMapper;
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
public class SysDictTypeServiceImpl implements SysDictTypeService {
    // todo:测试
    @Autowired
    private SysDictDataMapper sysDictDataMapper;
    @Autowired
    private SysDictTypeMapper sysDictTypeMapper;

    @Override
    public SysDictType getById(Long id) {
        SysDictType sysDictType = sysDictTypeMapper.getById(id);
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictTypeId(id);
        List<SysDictData> sysDictDataList =
                sysDictDataMapper.selectByCondition(sysDictData);
        sysDictType.setSysDictDataList(sysDictDataList);
        return sysDictType;
    }

    @Override
    public List<SysDictType> selectByCondition(SysDictType sysDictType) {
        return sysDictTypeMapper.selectByCondition(sysDictType);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysDictType save(SysDictType entity) {
        // 校验是否存在
        List<SysDictType> sysDictTypes = sysDictTypeMapper.checkDictType(entity);
        if (CollectionUtils.isNotEmpty(sysDictTypes)) {
            throw new ServiceException(entity.getDictType() + " 已存在", 400);
        }
        // 保存子表
        List<SysDictData> sysDictDataList = entity.getSysDictDataList();
        if (CollectionUtils.isNotEmpty(sysDictDataList)) {
            sysDictDataList.forEach(sysDictData -> {
                sysDictData.setDictTypeId(entity.getId());
                sysDictDataMapper.insert(sysDictData);
            });
        }
        sysDictTypeMapper.insert(entity);
        return entity;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysDictType updateById(SysDictType entity) {
        // 校验是否存在
        List<SysDictType> sysDictTypes = sysDictTypeMapper.checkDictType(entity);
        if (CollectionUtils.isNotEmpty(sysDictTypes)) {
            throw new ServiceException(entity.getDictType() + " 已存在", 400);
        }
        // 修改字表
        List<SysDictData> sysDictDataList = entity.getSysDictDataList();
        if (CollectionUtils.isNotEmpty(sysDictDataList)) {
            sysDictDataList.forEach(sysDictData -> {
                sysDictData.setDictTypeId(entity.getId());
                if (sysDictData.getId() == null) {
                    sysDictDataMapper.insert(sysDictData);
                } else {
                    sysDictDataMapper.update(sysDictData);
                }
            });
        }
        sysDictTypeMapper.update(entity);
        return entity;
    }

    @Override
    public boolean removeById(Long id) {
        sysDictDataMapper.deleteByTypeId(id);
        return sysDictTypeMapper.deleteById(id) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeByIds(List<Long> idList) {
        idList.forEach(id -> sysDictDataMapper.deleteByTypeId(id));
        return sysDictTypeMapper.deleteBatchIds(idList) > 0;
    }
}
