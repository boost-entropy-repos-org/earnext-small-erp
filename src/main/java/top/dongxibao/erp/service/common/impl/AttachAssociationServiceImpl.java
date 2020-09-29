package top.dongxibao.erp.service.common.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.dongxibao.erp.entity.common.AttachAssociation;
import top.dongxibao.erp.mapper.common.AttachAssociationMapper;
import top.dongxibao.erp.service.common.AttachAssociationService;

import java.util.List;

/**
 * 附件关联Service业务层处理
 * 
 * @author Dongxibao
 * @date 2020-07-05
 */
@Service
public class AttachAssociationServiceImpl extends ServiceImpl<AttachAssociationMapper, AttachAssociation> implements AttachAssociationService {

    @Override
    public List<AttachAssociation> selectByCondition(AttachAssociation attachAssociation) {
        return baseMapper.selectByCondition(attachAssociation);
    }
}
