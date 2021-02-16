package top.dongxibao.erp.mapper.common;

import org.apache.ibatis.annotations.Param;
import top.dongxibao.erp.entity.common.AttachAssociation;

import java.util.List;

/**
 * 附件关联Mapper 接口
 * 
 * @author Dongxibao
 * @date 2020-07-05
 */
public interface AttachAssociationMapper {
    /**
     * 查询附件关联表
     *
     * @param id 附件关联表ID
     * @return 附件关联表
     */
    AttachAssociation getById(Long id);

    /**
     * 查询附件关联表列表
     *
     * @param attachAssociation 附件关联表
     * @return 附件关联表集合
     */
    List<AttachAssociation> selectByCondition(AttachAssociation attachAssociation);

    /**
     * 新增附件关联表
     *
     * @param attachAssociation 附件关联表
     * @return 结果
     */
    int insert(AttachAssociation attachAssociation);

    /**
     * 修改附件关联表
     *
     * @param attachAssociation 附件关联表
     * @return 结果
     */
    int update(AttachAssociation attachAssociation);

    /**
     * 删除附件关联表
     *
     * @param id 附件关联表ID
     * @return 结果
     */
    int deleteById(Long id);

    /**
     * 批量删除附件关联表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBatchIds(@Param("ids") List<Long> ids);
}
