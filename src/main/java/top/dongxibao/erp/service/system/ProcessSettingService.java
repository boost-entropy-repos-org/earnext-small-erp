package top.dongxibao.erp.service.system;


import top.dongxibao.erp.entity.system.ProcessSetting;

import java.util.List;

/**
 * 业务模块关联流程
 *
 * @author Dongxibao
 * @date 2021-01-25
 */
public interface ProcessSettingService {

    /**
     * 查询业务模块关联流程
     *
     * @param id
     * @return 业务模块关联流程
     */
    ProcessSetting getById(Long id);

    /**
     * 根据条件查询
     * @param processSetting
     * @return
     */
    List<ProcessSetting> selectByCondition(ProcessSetting processSetting);

    /**
     * 新增业务模块关联流程
     *
     * @param processSetting 业务模块关联流程
     * @return 结果
     */
    ProcessSetting save(ProcessSetting processSetting);

    /**
     * 修改业务模块关联流程
     *
     * @param processSetting 业务模块关联流程
     * @return 结果
     */
    ProcessSetting updateById(ProcessSetting processSetting);

    /**
     * 删除业务模块关联流程
     *
     * @param id 业务模块关联流程ID
     * @return 结果
     */
    boolean removeById(Long id);

    /**
     * 批量删除业务模块关联流程
     *
     * @param idList 需要删除的数据ID集合
     * @return 结果
     */
    boolean removeByIds(List<Long> idList);
}

