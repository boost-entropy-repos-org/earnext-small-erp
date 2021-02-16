package top.dongxibao.erp.service.system;


import top.dongxibao.erp.entity.system.SysDept;
import top.dongxibao.erp.entity.system.TreeSelect;

import java.util.List;

/**
 * 部门表
 *
 * @author Dongxibao
 * @date 2021-01-12
 */
public interface SysDeptService {

    /**
     * 查询部门表
     *
     * @param id
     * @return 部门表
     */
    SysDept getById(Long id);

    /**
     * 根据条件查询
     * @param sysDept
     * @return
     */
    List<SysDept> selectByCondition(SysDept sysDept);

    /**
     * 新增部门表
     *
     * @param sysDept 部门表
     * @return 结果
     */
    SysDept save(SysDept sysDept);

    /**
     * 修改部门表
     *
     * @param sysDept 部门表
     * @return 结果
     */
    SysDept updateById(SysDept sysDept);

    /**
     * 删除部门表
     *
     * @param id 部门表ID
     * @return 结果
     */
    boolean removeById(Long id);

    /**
     * 批量删除部门表
     *
     * @param idList 需要删除的数据ID集合
     * @return 结果
     */
    boolean removeByIds(List<Long> idList);

    List<TreeSelect> buildDeptTreeSelect(List<SysDept> depts);

    /**
     * 构建前端所需要树结构
     *
     * @param depts 部门列表
     * @return 树结构列表
     */
    List<SysDept> buildDeptTree(List<SysDept> depts);

    List<Long> selectDeptListByRoleId(Long roleId);
}

