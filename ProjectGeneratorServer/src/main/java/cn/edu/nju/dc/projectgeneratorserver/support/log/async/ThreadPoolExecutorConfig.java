package cn.edu.nju.dc.projectgeneratorserver.support.log.async;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author dc
 * @date 2019/10/6 14:33
 */

@Configuration
public class ThreadPoolExecutorConfig {

    @Bean("logTaskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2); // 核心线程数
        executor.setMaxPoolSize(3); // 最大线程数
        executor.setQueueCapacity(20); // 缓冲队列容量
        executor.setKeepAliveSeconds(60); // 允许线程空闲的时间
        executor.setThreadNamePrefix("logTaskExecutor-"); // 线程池名的前缀
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // 线程池对拒绝任务的处理策略
        executor.setWaitForTasksToCompleteOnShutdown(true); // 优雅停机
        executor.setAwaitTerminationSeconds(60); // 线程池中任务的等待时间
        executor.initialize();
        return executor;
    }
}
