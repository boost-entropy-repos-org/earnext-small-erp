package top.dongxibao.erp.security.service;

import cn.hutool.core.util.IdUtil;
import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import top.dongxibao.erp.constant.SecurityConstants;
import top.dongxibao.erp.security.dto.JwtUserDto;
import top.dongxibao.erp.security.entity.SecurityProperties;
import top.dongxibao.erp.util.AddressUtils;
import top.dongxibao.erp.util.IpUtils;
import top.dongxibao.erp.util.ServletUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * token验证处理
 *
 * @author Dongxibao
 * @date 2021-01-11
 */
@Slf4j
@Component
public class TokenService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SecurityProperties securityProperties;

    protected static final long MILLIS_SECOND = 60000;

    // 20 * 60 * 1000L
    private static final Long MILLIS_MINUTE_TEN = 1200000L;

    /**
     * 获取用户身份信息，根据token解析
     *
     * @return 用户信息
     */
    public JwtUserDto getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            Claims claims = parseToken(token);
            // 解析对应的权限以及用户信息
            String uuid = (String) claims.get(SecurityConstants.LOGIN_USER_KEY);
            String userKey = getTokenKey(uuid);
            JwtUserDto user = (JwtUserDto) redisTemplate.opsForValue().get(userKey);
            return user;
        }
        return null;
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(JwtUserDto loginUser) {
        if (loginUser != null && StringUtils.isNotEmpty(loginUser.getLoginRedisKey())) {
            refreshToken(loginUser);
        }
    }

    /**
     * 删除用户身份信息
     */
    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            String userKey = getTokenKey(token);
            redisTemplate.delete(userKey);
        }
    }

    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    public String createToken(JwtUserDto loginUser) {
        String redisKey = IdUtil.fastSimpleUUID();
        loginUser.setLoginRedisKey(redisKey);
        // 封装登录信息
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        loginUser.setIpaddr(ip);
        try {
            loginUser.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        } catch (Exception e) {
            log.error("根据ip获取地址报错：{}; {}", e, e.getMessage());
        }
        loginUser.setBrowser(userAgent.getBrowser().getName());
        loginUser.setOs(userAgent.getOperatingSystem().getName());

        refreshToken(loginUser);
        Map<String, Object> claims = new HashMap<>();
        claims.put(SecurityConstants.LOGIN_USER_KEY, redisKey);
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(loginUser.getUsername())
                .signWith(SignatureAlgorithm.HS512, securityProperties.getSecret()).compact();
        return token;
    }

    /**
     * 验证令牌有效期，自动刷新缓存
     *
     * @param loginUser
     * @return 令牌
     */
    public void verifyToken(JwtUserDto loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshToken(loginUser);
        }
    }

    /**
     * 保存登录用户到Redis，刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(JwtUserDto loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + securityProperties.getExpireTime() * MILLIS_SECOND);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getLoginRedisKey());
        redisTemplate.opsForValue().set(userKey, loginUser, securityProperties.getExpireTime(), TimeUnit.MINUTES);
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(securityProperties.getSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    /**
     * 获取请求token
     *
     * @param request
     * @return token
     */
    private String getToken(HttpServletRequest request) {
        String token = request.getHeader(securityProperties.getHeader());
//        log.debug("请求token:{}", token);
        if (StringUtils.isNotEmpty(token) && token.startsWith(securityProperties.getTokenStartWith())) {
            token = token.replace(securityProperties.getTokenStartWith(), "");
        }
        return token;
    }

    private String getTokenKey(String uuid) {
        return SecurityConstants.LOGIN_TOKEN_KEY + uuid;
    }
}
