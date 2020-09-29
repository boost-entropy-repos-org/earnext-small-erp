package top.dongxibao.erp.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.dongxibao.erp.entity.system.SysRoleDept;

import java.util.List;

/**
 * 角色和部门关联Mapper 接口
 * 
 * @author Dongxibao
 * @date 2020-06-13
 */
public interface SysRoleDeptMapper extends BaseMapper<SysRoleDept> {
    /**
     * 根据条件查询
     * @param sysRoleDept
     * @return
     */
    List<SysRoleDept> selectByCondition(SysRoleDept sysRoleDept);
}
