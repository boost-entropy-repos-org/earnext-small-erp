package top.dongxibao.erp.security.entity;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Jwt参数配置
 *
 * @author Dongxibao
 * @date 2021-01-11
 */
@ConfigurationProperties(prefix = "jwt", ignoreUnknownFields = true)
@Data
@Component
public class SecurityProperties {

    /**
     * Request Headers ： Authorization
     */
    private String header;

    /**
     * 令牌前缀，最后留个空格 Bearer
     */
    private String tokenStartWith;

    /**
     * 令牌密钥
     */
    private String secret;

    /**
     * 令牌有效期（默认60分钟）
     */
    private Long expireTime;

    public String getTokenStartWith() {
        return tokenStartWith.trim() + " ";
    }
}
