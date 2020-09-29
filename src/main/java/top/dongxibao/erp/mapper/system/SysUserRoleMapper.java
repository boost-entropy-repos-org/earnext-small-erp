package top.dongxibao.erp.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.dongxibao.erp.entity.system.SysUserRole;

import java.util.List;

/**
 * 用户和角色关联Mapper 接口
 * 
 * @author Dongxibao
 * @date 2020-06-13
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
    /**
     * 根据条件查询
     * @param sysUserRole
     * @return
     */
    List<SysUserRole> selectByCondition(SysUserRole sysUserRole);
}
