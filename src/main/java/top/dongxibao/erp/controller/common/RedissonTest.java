package top.dongxibao.erp.controller.common;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RedissonTest
 * @description Redisson测试
 * @author Dongxibao
 * @date 2021/1/2
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/redisson")
public class RedissonTest {
    @Autowired
    private RedissonClient redissonClient;
    /** 系统配置的线程池 */
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @GetMapping("/test01")
    void test01() {
        log.info("主线程... {}", Thread.currentThread().getId());
        CompletableFuture.runAsync(() -> {
            log.info("第一个线程等待加锁... {}", Thread.currentThread().getId());
            commonCode();
            log.info("第一个线程结束... {}", Thread.currentThread().getId());
        }, threadPoolTaskExecutor);
        CompletableFuture.runAsync(() -> {
            log.info("第二个线程等待加锁...{}", Thread.currentThread().getId());
            commonCode();
            log.info("第二个线程结束... {}", Thread.currentThread().getId());
        }, threadPoolTaskExecutor);
    }
    private void commonCode() {
        // 获取一把锁，只要锁的名字一样，就是同一把锁
        RLock lock = redissonClient.getLock("anyLock");
        /*加锁。阻塞式等待。默认加的锁都是30s
        1）、如果业务超长，Redisson内部提供了一个监控锁的看门狗，不断的延长锁的有效期。
        2）、加锁的业务只要运行完成，就不会给当前锁续期，突然宕机即使不手动解锁，锁默认会在30s内自动过期，不会产生死锁问题
         */
//        lock.lock();
        try {
            /*
            尝试加锁，最多等待100秒，上锁以后10秒自动解锁(如果业务执行不完也会10s自动解锁)。
            1）、在锁时间到了以后，不会自动续期。
            2）、加锁的业务只要运行完成，突然宕机即使不手动解锁，锁会在设置的10s内自动过期，不会产生死锁问题
             */
            lock.tryLock(100, 10, TimeUnit.SECONDS);
            log.info("加锁成功，执行业务... {}", Thread.currentThread().getId());
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
