package top.dongxibao.erp.controller.system;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.dongxibao.erp.common.Result;
import top.dongxibao.erp.constant.Constants;
import top.dongxibao.erp.util.Base64;
import top.dongxibao.erp.util.IdUtils;
import top.dongxibao.erp.util.VerifyCodeUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 验证码操作处理
 *
 * @author Dongxibao
 * @date 2020-06-21
 */
@Slf4j
@RestController
public class CaptchaController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 生成验证码
     */
    @ApiOperation("生成验证码")
    @GetMapping("/captchaImage")
    public Result getCode(HttpServletResponse response) throws IOException {
        // 生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        log.info("验证码：{}", verifyCode);
        // 唯一标识
        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

        stringRedisTemplate.opsForValue().set(verifyKey, verifyCode, Constants.CAPTCHA_EXPIRATION,
                TimeUnit.MINUTES);
        // 生成图片
        int w = 111, h = 36;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        VerifyCodeUtils.outputImage(w, h, stream, verifyCode);
        try {
            Map map = new HashMap(6);
            map.put("uuid", uuid);
            map.put("img", Base64.encode(stream.toByteArray()));
            return new Result(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(e.getMessage(), false);
        } finally {
            stream.close();
        }
    }
}
