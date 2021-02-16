package top.dongxibao.erp.service.common;

import top.dongxibao.erp.entity.common.AttachAssociation;

import java.util.List;

/**
 * 附件关联Service 接口
 * 
 * @author Dongxibao
 * @date 2020-07-05
 */
public interface AttachAssociationService {
    /**
     * 根据条件查询
     * @param attachAssociation
     * @return
     */
    List<AttachAssociation> selectByCondition(AttachAssociation attachAssociation);

    /**
     * 查询附件关联表
     *
     * @param id
     * @return 附件关联表
     */
    AttachAssociation getById(Long id);

    /**
     * 新增附件关联表
     *
     * @param attachAssociation 附件关联表
     * @return 结果
     */
    AttachAssociation save(AttachAssociation attachAssociation);

    /**
     * 修改附件关联表
     *
     * @param attachAssociation 附件关联表
     * @return 结果
     */
    AttachAssociation updateById(AttachAssociation attachAssociation);

    /**
     * 删除附件关联表
     *
     * @param id 附件关联表ID
     * @return 结果
     */
    boolean removeById(Long id);

    /**
     * 批量删除附件关联表
     *
     * @param idList 需要删除的数据ID集合
     * @return 结果
     */
    boolean removeByIds(List<Long> idList);
}
