package top.dongxibao.erp.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import top.dongxibao.erp.annotation.RepeatSubmit;
import top.dongxibao.erp.exception.ServiceException;
import top.dongxibao.erp.util.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 防止重复提交拦截器
 *
 * @author Dongxibao
 * @date 2020-06-06
 */
@Slf4j
@Aspect
@Component
public class RepeatSubmitAspect {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(top.dongxibao.erp.annotation.RepeatSubmit)")
    public void RepeatSubmitPointCut() {
    }

    /**
     * 处理请求前执行
     *
     * @param joinPoint 切点
     */
    @Before(value = "RepeatSubmitPointCut()")
    public synchronized void doBefore(JoinPoint joinPoint) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request =
                (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        handleRepeatSubmit(joinPoint, request);
    }

    private void handleRepeatSubmit(final JoinPoint joinPoint, HttpServletRequest request) {
        // 获得注解
        RepeatSubmit annotationRepeatSubmit = getAnnotationRepeatSubmit(joinPoint);
        if (annotationRepeatSubmit == null) {
            return;
        } else {
            int expiredTime = annotationRepeatSubmit.expiredTime();
            if (this.isRepeatSubmit(request, expiredTime)) {
                throw new ServiceException("不允许重复提交，请稍后再试");
            }
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private RepeatSubmit getAnnotationRepeatSubmit(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(RepeatSubmit.class);
        }
        return null;
    }

    /**
     * 判断 请求方法+url和数据 是否和上一次相同，
     * 如果和上次相同，则是重复提交表单。 有效时间默认为5秒内。
     *
     * @param request
     * @param expiredTime
     * @return
     */
    private boolean isRepeatSubmit(HttpServletRequest request, int expiredTime) {
        StringBuffer data = new StringBuffer();
        String line = null;
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            while (null != (line = reader.readLine())) {
                data.append(line);
            }
        } catch (IOException e) {
            log.error("[RepeatSubmit]readerError:{};{}", e, e.getMessage());
        }
        String parameterData = data.toString().trim();
        // body参数为空，获取Parameter的数据
        if (StringUtils.isEmpty(parameterData)) {
            parameterData = JsonUtils.obj2Json(request.getParameterMap()).trim();
        }
        // 请求方法+请求地址（作为存放Redis的key值）
        String method = request.getMethod();
        String url = request.getRequestURI();
        String repeatSubmitKey = method + url;
        if (StringUtils.isEmpty(parameterData)) {
            parameterData = "";
        }
        Boolean exist = stringRedisTemplate.hasKey(repeatSubmitKey)
                && parameterData.equals(stringRedisTemplate.opsForValue().get(repeatSubmitKey) == null ? "" :
                stringRedisTemplate.opsForValue().get(repeatSubmitKey));
        log.info("[RepeatSubmit]repeatSubmitKey: {}", repeatSubmitKey);
        log.info("[RepeatSubmit]repeatSubmitKey是否存在: {}", exist);
        log.info("[RepeatSubmit]过期时间: {} 秒", expiredTime);
        if (exist) {
            return true;
        }
        stringRedisTemplate.opsForValue().set(repeatSubmitKey, parameterData, expiredTime, TimeUnit.SECONDS);
        return false;
    }
}
