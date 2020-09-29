package top.dongxibao.erp.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.dongxibao.erp.entity.system.SysRoleMenu;

import java.util.List;

/**
 * 角色和菜单关联Mapper 接口
 * 
 * @author Dongxibao
 * @date 2020-06-13
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
    /**
     * 根据条件查询
     * @param sysRoleMenu
     * @return
     */
    List<SysRoleMenu> selectByCondition(SysRoleMenu sysRoleMenu);
}
