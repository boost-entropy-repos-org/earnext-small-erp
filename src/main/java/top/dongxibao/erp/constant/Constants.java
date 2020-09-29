package top.dongxibao.erp.constant;

/**
 * 通用常量信息
 *
 * @author Dongxibao
 * @date 2020-06-21
 */
public class Constants {

    /**
     * 通用成功标识
     */
    public static final Integer SUCCESS = 0;

    /**
     * 通用失败标识
     */
    public static final Integer FAIL = 1;

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT = "Logout";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL = "Error";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 登录用户 redis key ,放到jwt中负载的value
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 验证码有效期（分钟）
     */
    public static final Integer CAPTCHA_EXPIRATION = 2;

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 令牌前缀, 获取jwt负载中value(用户信息Redis Key)
     */
    public static final String LOGIN_USER_KEY = "jwt_user_key";

}
