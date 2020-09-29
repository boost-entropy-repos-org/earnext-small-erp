package top.dongxibao.erp.mapper.system;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import top.dongxibao.erp.entity.system.SysUser;

import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author Dongxibao
 * @date 2020-06-14
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 根据条件查询
     * @param sysUser
     * @return
     */
    List<SysUser> selectByCondition(SysUser sysUser);

    /**
     * 修改密码
     * @param username
     * @param password
     * @return
     */
    int updateByUsername(@Param("username") String username, @Param("password") String password);

    /**
     * 校验用户
     * @param entity
     * @return
     */
    List<SysUser> checkSysUser(SysUser entity);
}
