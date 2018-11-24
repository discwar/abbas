package com.major.listener;

import com.major.job.DelayBucketTimer;
import com.major.job.queue.DelayQueue;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.concurrent.*;

/**
 * 监听器，创建延迟队列池
 * @author LianGuoQing
 */
@Configuration
public class DelayQueueListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        ExecutorService executorService = Executors.newFixedThreadPool((int)DelayQueue.DELAY_BUCKET_NUM);
        for (int i = 0; i < DelayQueue.DELAY_BUCKET_NUM; i++) {
            executorService.execute(new DelayBucketTimer(DelayQueue.DELAY_BUCKET_KEY_PREFIX+i));
        }
        executorService.shutdown();
    }

}
