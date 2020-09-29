package top.dongxibao.erp.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.dongxibao.erp.entity.system.SysDictData;

import java.util.List;

/**
 * 数据字典子Mapper 接口
 *
 * @author Dongxibao
 * @date 2020-07-05
 */
public interface SysDictDataMapper extends BaseMapper<SysDictData> {
    /**
     * 根据条件查询
     * @param sysDictDataTO
     * @return
     */
    List<SysDictData> selectByCondition(SysDictData sysDictDataTO);
}
