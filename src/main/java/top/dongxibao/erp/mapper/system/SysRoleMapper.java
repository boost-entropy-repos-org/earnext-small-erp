package top.dongxibao.erp.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.dongxibao.erp.entity.system.SysRole;

import java.util.List;

/**
 * 角色信息Mapper 接口
 * 
 * @author Dongxibao
 * @date 2020-06-13
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 根据条件查询
     * @param sysRole
     * @return
     */
    List<SysRole> selectByCondition(SysRole sysRole);

    /**
     * 根据用户ID查询角色
     *
     * @param id 用户ID
     * @return 角色列表
     */
    List<SysRole> selectRolePermissionByUserId(@Param("userId") Long id);
}
