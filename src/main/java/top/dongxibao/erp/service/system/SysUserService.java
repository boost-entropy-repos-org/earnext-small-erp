package top.dongxibao.erp.service.system;

import com.baomidou.mybatisplus.extension.service.IService;
import top.dongxibao.erp.entity.system.SysUser;
import top.dongxibao.erp.entity.system.SysUser;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author Dongxibao
 * @date 2020-06-14
 */
public interface SysUserService extends IService<SysUser> {

    @Override
    SysUser getById(Serializable id);

    /**
     * 根据条件查询
     * @param sysUser
     * @return
     */
    List<SysUser> selectByCondition(SysUser sysUser);

    SysUser insert(SysUser entity);

    SysUser update(SysUser entity);

    @Override
    boolean removeById(Serializable id);

    /**
     * 根据登陆名查询user
     * @param username
     * @return
     */
    SysUser selectUserByUserName(String username);

    /**
     * 根据username修改密码
     * @param username
     * @param password
     * @return
     */
    int resetUserPwd(String username, String password);
}
