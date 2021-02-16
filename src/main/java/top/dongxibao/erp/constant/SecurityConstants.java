package top.dongxibao.erp.constant;

/**
 * 通用常量信息
 * @Author Dongxibao
 * @Date 2021/1/7
 */
public interface SecurityConstants {

    /**
     * 验证码 redis key
     */
    String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 登录用户 redis key
     */
    String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * jwt令牌前缀key
     */
    String LOGIN_USER_KEY = "login_user_key";
    /** 用户禁用标记(0正常,1禁用) */
    Integer USER_DISABLE = 1;
}
