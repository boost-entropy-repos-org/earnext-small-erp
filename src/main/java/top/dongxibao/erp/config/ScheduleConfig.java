package top.dongxibao.erp.config;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @ClassName ScheduleAtConfig
 * @description 多线程执行定时任务
 * @author Dongxibao
 * @date 2020/4/3
 * @Version 1.0
 */
@Configuration
public class ScheduleConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        // 设定一个长度20的定时任务线程池
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(20,
                new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(true).build());
        scheduledTaskRegistrar.setScheduler(executorService);
    }
}
