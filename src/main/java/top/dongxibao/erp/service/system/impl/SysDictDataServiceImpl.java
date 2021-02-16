package top.dongxibao.erp.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.dongxibao.erp.entity.system.SysDictData;
import top.dongxibao.erp.mapper.system.SysDictDataMapper;
import top.dongxibao.erp.service.system.SysDictDataService;

import java.util.List;

/**
 * @ClassName SysDictDataServiceImpl
 * @description 1
 * @author Dongxibao
 * @date 2020/7/5
 * @Version 1.0
 */
@Service
public class SysDictDataServiceImpl implements SysDictDataService {
    @Autowired
    private SysDictDataMapper sysDictDataMapper;

    @Override
    public SysDictData getById(Long id) {
        SysDictData sysDictData = sysDictDataMapper.getById(id);
        return sysDictData;
    }

    @Override
    public List<SysDictData> selectByCondition(SysDictData sysDictData) {
        return sysDictDataMapper.selectByCondition(sysDictData);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysDictData save(SysDictData sysDictData) {
        sysDictDataMapper.insert(sysDictData);
        return sysDictData;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysDictData updateById(SysDictData sysDictData) {
        sysDictDataMapper.update(sysDictData);
        return sysDictData;
    }

    @Override
    public boolean removeById(Long id) {
        return sysDictDataMapper.deleteById(id) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeByIds(List<Long> idList) {
        return sysDictDataMapper.deleteBatchIds(idList) > 0;
    }
}
