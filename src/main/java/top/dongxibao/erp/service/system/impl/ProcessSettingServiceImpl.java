package top.dongxibao.erp.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.dongxibao.erp.entity.system.ProcessSetting;
import top.dongxibao.erp.exception.ServiceException;
import top.dongxibao.erp.mapper.system.ProcessSettingMapper;
import top.dongxibao.erp.service.system.ProcessSettingService;

import java.util.List;

/**
 * 业务模块关联流程
 *
 * @author Dongxibao
 * @date 2021-01-25
 */
@Service("processSettingService")
public class ProcessSettingServiceImpl implements ProcessSettingService {
    @Autowired
    private ProcessSettingMapper processSettingMapper;

    @Override
    public ProcessSetting getById(Long id) {
        ProcessSetting processSetting = processSettingMapper.getById(id);
        return processSetting;
    }

    @Override
    public List<ProcessSetting> selectByCondition(ProcessSetting processSetting) {
        return processSettingMapper.selectByCondition(processSetting);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProcessSetting save(ProcessSetting processSetting) {
        ProcessSetting exist = processSettingMapper.existByModuleId(processSetting);
        if (exist != null) {
            throw new ServiceException("关联模块id：" + processSetting.getModuleId() + " 已存在！");
        }
        processSettingMapper.insert(processSetting);
        return processSetting;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ProcessSetting updateById(ProcessSetting processSetting) {
        ProcessSetting exist = processSettingMapper.existByModuleId(processSetting);
        if (exist != null) {
            throw new ServiceException("关联模块id：" + processSetting.getModuleId() + " 已存在！");
        }
        processSettingMapper.update(processSetting);
        return processSetting;
    }

    @Override
    public boolean removeById(Long id) {
        return processSettingMapper.deleteById(id) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeByIds(List<Long> idList) {
        return processSettingMapper.deleteBatchIds(idList) > 0;
    }
}
