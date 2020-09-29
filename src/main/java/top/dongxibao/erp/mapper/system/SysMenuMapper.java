package top.dongxibao.erp.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.dongxibao.erp.entity.system.SysMenu;

import java.util.List;

/**
 * 菜单权限Mapper 接口
 * 
 * @author Dongxibao
 * @date 2020-06-13
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 根据条件查询
     * @param sysMenu
     * @return
     */
    List<SysMenu> selectByCondition(SysMenu sysMenu);

    /**
     * 根据用户ID查询权限
     *
     * @param id 用户ID
     * @return 权限列表
     */
    List<String> selectMenuPermsByUserId(@Param("userId") Long id);

    List<SysMenu> checkSysMenu(SysMenu entity);
}
