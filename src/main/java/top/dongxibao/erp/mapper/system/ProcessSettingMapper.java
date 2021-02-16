package top.dongxibao.erp.mapper.system;

import org.apache.ibatis.annotations.Param;
import top.dongxibao.erp.entity.system.ProcessSetting;

import java.util.List;

/**
 * 业务模块关联流程
 * 
 * @author Dongxibao
 * @date 2021-01-25
 */
public interface ProcessSettingMapper {

    /**
     * 查询业务模块关联流程
     *
     * @param id 业务模块关联流程ID
     * @return 业务模块关联流程
     */
    ProcessSetting getById(Long id);

    /**
     * 查询业务模块关联流程列表
     *
     * @param processSetting 业务模块关联流程
     * @return 业务模块关联流程集合
     */
    List<ProcessSetting> selectByCondition(ProcessSetting processSetting);

    /**
     * 新增业务模块关联流程
     *
     * @param processSetting 业务模块关联流程
     * @return 结果
     */
    int insert(ProcessSetting processSetting);

    /**
     * 修改业务模块关联流程
     *
     * @param processSetting 业务模块关联流程
     * @return 结果
     */
    int update(ProcessSetting processSetting);

    /**
     * 删除业务模块关联流程
     *
     * @param id 业务模块关联流程ID
     * @return 结果
     */
    int deleteById(Long id);

    /**
     * 批量删除业务模块关联流程
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBatchIds(@Param("ids") List<Long> ids);

    /**
     * 根据模块id查询
     * @param moduleId
     * @return
     */
    ProcessSetting selectByModuleId(@Param("moduleId") Long moduleId);
}
