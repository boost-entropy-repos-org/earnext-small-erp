package top.dongxibao.erp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

/**
 * @ClassName TestCompletableFuture
 * @description 测试异步
 * @author Dongxibao
 * @date 2020/12/5
 * @Version 1.0
 */
@Slf4j
@SpringBootTest
public class CompletableFutureTest {
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Autowired
    private ScheduledExecutorService scheduledExecutorService;
    @Test
    void test01() throws ExecutionException, InterruptedException {
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> "测试CompletableFuture");
        System.out.println(supplyAsync.get());

    }
    @Test
    void test02() throws ExecutionException, InterruptedException {
        final Semaphore semaphore = new Semaphore(5000);
        final CountDownLatch countDownLatch = new CountDownLatch(200);
        for (int i = 0; i < 200; i++) {
            final int count = i;
            threadPoolTaskExecutor.execute(() -> {
                try {
                    semaphore.acquire();
                    update(count);
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        threadPoolTaskExecutor.shutdown();
    }
    private void update(int count) {
        log.info("{}===={}",threadPoolTaskExecutor.getThreadNamePrefix(),count);
    }
    @Test
    void test03() throws ExecutionException, InterruptedException {
        scheduledExecutorService.schedule(new Runnable() {
            @Override
            public void run() {

            }
        },5,TimeUnit.MINUTES);
    }
}
