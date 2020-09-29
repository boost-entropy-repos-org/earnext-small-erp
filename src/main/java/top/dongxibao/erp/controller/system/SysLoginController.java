package top.dongxibao.erp.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.dongxibao.erp.common.Result;
import top.dongxibao.erp.constant.Constants;
import top.dongxibao.erp.entity.system.SysUser;
import top.dongxibao.erp.security.JWTUser;
import top.dongxibao.erp.security.LoginBody;
import top.dongxibao.erp.security.service.SysLoginService;
import top.dongxibao.erp.security.service.SysPermissionService;
import top.dongxibao.erp.security.service.TokenUtils;
import top.dongxibao.erp.service.system.SysMenuService;
import top.dongxibao.erp.util.ServletUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 登录验证
 *
 * @author Dongxibao
 * @date 2020-06-21
 */
@RestController
public class SysLoginController {
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private SysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private TokenUtils tokenUtils;

    /**
     * 登录方法
     *
     * @param loginBody
     * @return 结果
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginBody loginBody) {
        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        Map<String, String> map = new HashMap<>();
        map.put(Constants.TOKEN, token);
        return new Result(map);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public Result getInfo() {
        JWTUser JWTUser = tokenUtils.getLoginUser(ServletUtils.getRequest());
        SysUser user = JWTUser.getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        Map<String, Object> resultMap = new HashMap<>(6);
        resultMap.put("user", user);
        resultMap.put("roles", roles);
        resultMap.put("permissions", permissions);
        return new Result(resultMap);
    }
}
