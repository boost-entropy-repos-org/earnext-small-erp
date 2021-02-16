package top.dongxibao.erp.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.dongxibao.erp.constant.SecurityConstants;
import top.dongxibao.erp.entity.system.SysUser;
import top.dongxibao.erp.exception.ServiceException;
import top.dongxibao.erp.security.dto.JwtUserDto;
import top.dongxibao.erp.service.system.SysUserService;

/**
 * 用户验证处理
 *
 * @author Dongxibao
 * @date 2021-01-11
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userService.selectUserByUserName(username);
        if (user == null) {
            log.info("登录用户：{} 不存在.", username);
            throw new ServiceException("登录用户：" + username + " 不存在");
        } else if (SecurityConstants.USER_DISABLE.equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", username);
            throw new ServiceException("对不起，您的账号：" + username + " 已停用");
        }
        UserDetails loginUser = createLoginUser(user);
        return loginUser;
    }

    public UserDetails createLoginUser(SysUser user) {
        return new JwtUserDto(user, permissionService.getMenuPermission(user));
    }
}
