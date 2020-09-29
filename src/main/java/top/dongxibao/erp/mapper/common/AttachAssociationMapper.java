package top.dongxibao.erp.mapper.common;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.dongxibao.erp.entity.common.AttachAssociation;

import java.util.List;

/**
 * 附件关联Mapper 接口
 * 
 * @author Dongxibao
 * @date 2020-07-05
 */
public interface AttachAssociationMapper extends BaseMapper<AttachAssociation> {
    /**
     * 根据条件查询
     * @param attachAssociation
     * @return
     */
    List<AttachAssociation> selectByCondition(AttachAssociation attachAssociation);
}
