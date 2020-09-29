package top.dongxibao.erp.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import top.dongxibao.erp.entity.system.SysMenu;
import top.dongxibao.erp.entity.system.SysMenu;

import java.util.List;
import java.util.Set;

/**
 * 菜单权限Service 接口
 * 
 * @author Dongxibao
 * @date 2020-06-13
 */
public interface SysMenuService extends IService<SysMenu> {
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
    Set<String> selectMenuPermsByUserId(Long id);

    @Override
    boolean save(SysMenu entity);

    @Override
    boolean updateById(SysMenu entity);
}
