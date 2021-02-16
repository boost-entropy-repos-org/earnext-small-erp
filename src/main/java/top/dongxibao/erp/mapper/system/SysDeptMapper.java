package top.dongxibao.erp.mapper.system;

import org.apache.ibatis.annotations.Param;
import top.dongxibao.erp.entity.system.SysDept;

import java.util.List;

/**
 * 部门表
 * 
 * @author Dongxibao
 * @date 2021-01-12
 */
public interface SysDeptMapper {

    /**
     * 查询部门表
     *
     * @param id 部门表ID
     * @return 部门表
     */
    SysDept getById(Long id);

    /**
     * 查询部门表列表
     *
     * @param sysDept 部门表
     * @return 部门表集合
     */
    List<SysDept> selectByCondition(SysDept sysDept);

    /**
     * 新增部门表
     *
     * @param sysDept 部门表
     * @return 结果
     */
    int insert(SysDept sysDept);

    /**
     * 修改部门表
     *
     * @param sysDept 部门表
     * @return 结果
     */
    int update(SysDept sysDept);

    /**
     * 删除部门表
     *
     * @param id 部门表ID
     * @return 结果
     */
    int deleteById(Long id);

    /**
     * 批量删除部门表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    int deleteBatchIds(@Param("ids") List<Long> ids);

    /**
     * 根据角色id查询
     * @param roleId
     * @return
     */
    List<Long> selectDeptListByRoleId(@Param("roleId") Long roleId);
}
