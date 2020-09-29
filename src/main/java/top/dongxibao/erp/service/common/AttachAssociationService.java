package top.dongxibao.erp.service.common;

import com.baomidou.mybatisplus.extension.service.IService;
import top.dongxibao.erp.entity.common.AttachAssociation;

import java.util.List;

/**
 * 附件关联Service 接口
 * 
 * @author Dongxibao
 * @date 2020-07-05
 */
public interface AttachAssociationService extends IService<AttachAssociation> {
    /**
     * 根据条件查询
     * @param attachAssociation
     * @return
     */
    List<AttachAssociation> selectByCondition(AttachAssociation attachAssociation);
}
