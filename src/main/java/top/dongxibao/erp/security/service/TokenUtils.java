package top.dongxibao.erp.security.service;

import eu.bitwalker.useragentutils.UserAgent;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import top.dongxibao.erp.constant.Constants;
import top.dongxibao.erp.security.JWTUser;
import top.dongxibao.erp.util.IdUtils;
import top.dongxibao.erp.util.JsonUtils;
import top.dongxibao.erp.util.ServletUtils;
import top.dongxibao.erp.util.ip.IpUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * token验证处理
 *
 * @author Dongxibao
 * @date 2020-06-21
 */
@Component
public class TokenUtils {
    // 令牌自定义标识
    @Value("${token.header}")
    private String header;

    // 令牌秘钥
    @Value("${token.secret}")
    private String secret;

    // 令牌有效期（默认30分钟）
    @Value("${token.expireTime}")
    private int expireTime;

    protected static final long MILLIS_SECOND = 1000;
    /** 分钟 */
    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;
    /** 默认不操作刷新令牌时间：20分钟 */
    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public JWTUser getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            Claims claims = parseToken(token);
            // 解析对应的权限以及用户信息
            String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
            String userKey = getTokenKey(uuid);
            String jsonUser = stringRedisTemplate.opsForValue().get(userKey);
            JWTUser user = JsonUtils.json2Obj(jsonUser, JWTUser.class);
            return user;
        }
        return null;
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(JWTUser jwtUser) {
        if (jwtUser != null && StringUtils.isNotEmpty(jwtUser.getJwtRedisKey())) {
            refreshToken(jwtUser);
        }
    }

    /**
     * 删除用户身份信息
     */
    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            String userKey = getTokenKey(token);
            stringRedisTemplate.delete(userKey);
        }
    }

    /**
     * 创建令牌
     *
     * @param jwtUser 用户信息
     * @return 令牌
     */
    public String createToken(JWTUser jwtUser) {
        String jwtRedisKey = IdUtils.fastSimpleUUID();
        jwtUser.setJwtRedisKey(jwtRedisKey);
        setUserAgent(jwtUser);
        refreshToken(jwtUser);

        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.LOGIN_USER_KEY, jwtRedisKey);
        return createToken(claims);
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param jwtUser 当前用户
     */
    public void verifyToken(JWTUser jwtUser) {
        long expireTime = jwtUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshToken(jwtUser);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param jwtUser 登录信息
     */
    public void refreshToken(JWTUser jwtUser) {
        jwtUser.setLoginTime(System.currentTimeMillis());
        jwtUser.setExpireTime(jwtUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(jwtUser.getJwtRedisKey());
        stringRedisTemplate.opsForValue().set(userKey, JsonUtils.obj2Json(jwtUser), expireTime, TimeUnit.MINUTES);
    }

    /**
     * 设置用户代理信息
     *
     * @param jwtUser 登录信息
     */
    public void setUserAgent(JWTUser jwtUser) {
        UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        jwtUser.setIpaddr(ip);
        jwtUser.setBrowser(userAgent.getBrowser().getName());
        jwtUser.setOs(userAgent.getOperatingSystem().getName());
    }

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String createToken(Map<String, Object> claims) {
        String token = Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
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
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
            token = token.replace(Constants.TOKEN_PREFIX, "");
        }
        return token;
    }

    private String getTokenKey(String uuid) {
        return Constants.LOGIN_TOKEN_KEY + uuid;
    }
}
