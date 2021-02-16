package top.dongxibao.erp.service.common.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class AttachAssociationServiceImpl implements AttachAssociationService {

    @Autowired
    private AttachAssociationMapper attachAssociationMapper;

    @Override
    public AttachAssociation getById(Long id) {
        AttachAssociation attachAssociation = attachAssociationMapper.getById(id);
        return attachAssociation;
    }

    @Override
    public List<AttachAssociation> selectByCondition(AttachAssociation attachAssociation) {
        return attachAssociationMapper.selectByCondition(attachAssociation);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AttachAssociation save(AttachAssociation attachAssociation) {
        attachAssociationMapper.insert(attachAssociation);
        return attachAssociation;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AttachAssociation updateById(AttachAssociation attachAssociation) {
        attachAssociationMapper.update(attachAssociation);
        return attachAssociation;
    }

    @Override
    public boolean removeById(Long id) {
        return attachAssociationMapper.deleteById(id) > 0;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeByIds(List<Long> idList) {
        return attachAssociationMapper.deleteBatchIds(idList) > 0;
    }
}
