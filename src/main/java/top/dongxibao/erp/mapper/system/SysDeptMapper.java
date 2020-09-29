package top.dongxibao.erp.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.dongxibao.erp.entity.system.SysDept;
import top.dongxibao.erp.entity.system.SysDept;

import java.util.List;

/**
 * 部门Mapper 接口
 * 
 * @author Dongxibao
 * @date 2020-06-14
 */
public interface SysDeptMapper extends BaseMapper<SysDept> {
    /**
     * 根据条件查询
     * @param sysDept
     * @return
     */
    List<SysDept> selectByCondition(SysDept sysDept);

    /**
     * 根据ID查询所有子部门
     *
     * @param deptId 部门ID
     * @return 部门列表
     */
    List<SysDept> selectChildrenDeptById(Long deptId);

    /**
     * 修改子元素关系
     *
     * @param depts 子元素
     * @return 结果
     */
    int updateDeptChildren(@Param("depts") List<SysDept> depts);
}
