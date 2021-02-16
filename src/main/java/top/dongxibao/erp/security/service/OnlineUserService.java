package top.dongxibao.erp.security.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import top.dongxibao.erp.constant.SecurityConstants;
import top.dongxibao.erp.security.dto.JwtUserDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 在线用户信息
 * @author Dongxibao
 * @date 2021-01-11
 */
@Service
@Slf4j
public class OnlineUserService {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查找匹配key
     *
     * @param pattern key
     * @return /
     */
    public List<String> scan(String pattern) {
        ScanOptions options = ScanOptions.scanOptions().match(pattern).build();
        RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
        RedisConnection rc = Objects.requireNonNull(factory).getConnection();
        Cursor<byte[]> cursor = rc.scan(options);
        List<String> result = new ArrayList<>();
        while (cursor.hasNext()) {
            result.add(new String(cursor.next()));
        }
        try {
            RedisConnectionUtils.releaseConnection(rc, factory);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 查询全部数据，不分页
     * @param filter /
     * @return /
     */
    public List<JwtUserDto> getAll(String filter) {
        List<String> keys = scan(SecurityConstants.LOGIN_TOKEN_KEY + "*");
        Collections.reverse(keys);
        List<JwtUserDto> jwtUserDtos = new ArrayList<>();
        for (String key : keys) {
            JwtUserDto jwtUserDto = (JwtUserDto) redisTemplate.opsForValue().get(key);
            if (StringUtils.isNotBlank(filter)) {
                if (jwtUserDto.toString().contains(filter)) {
                    jwtUserDtos.add(jwtUserDto);
                }
            } else {
                jwtUserDtos.add(jwtUserDto);
            }
        }
        jwtUserDtos.sort((o1, o2) -> o2.getLoginTime().compareTo(o1.getLoginTime()));
        return jwtUserDtos;
    }

    /**
     * 踢出用户
     * @param key /
     */
    public void kickOut(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 检测用户是否在之前已经登录，已经登录踢下线
     * @param userName 用户名
     */
    public void checkLoginOnUser(String userName, String igoreToken) {
        List<JwtUserDto> jwtUserDtos = getAll(userName);
        if (jwtUserDtos == null || jwtUserDtos.isEmpty()) {
            return;
        }
        for (JwtUserDto jwtUserDto : jwtUserDtos) {
            if (jwtUserDto.getUsername().equals(userName)) {
                try {
                    String token = SecurityConstants.LOGIN_TOKEN_KEY + jwtUserDto.getLoginRedisKey();
                    if (StringUtils.isNotBlank(igoreToken) && !igoreToken.equals(token)) {
                        this.kickOut(token);
                    } else if (StringUtils.isBlank(igoreToken)) {
                        this.kickOut(token);
                    }
                } catch (Exception e) {
                    log.error("checkUser is error", e);
                }
            }
        }
    }

    /**
     * 根据redisKey强退用户
     * @param username /
     */
    @Async
    public void kickOutForUsername(String username) {
        List<JwtUserDto> jwtUserDtos = getAll(username);
        for (JwtUserDto jwtUserDto : jwtUserDtos) {
            String token = SecurityConstants.LOGIN_TOKEN_KEY + jwtUserDto.getLoginRedisKey();
            if (jwtUserDto.getUsername().equals(username)) {
                kickOut(token);
            }
        }
    }

    public void logout(String loginRedisKey) {
        String token = SecurityConstants.LOGIN_TOKEN_KEY + loginRedisKey;
        redisTemplate.delete(token);
    }
}
