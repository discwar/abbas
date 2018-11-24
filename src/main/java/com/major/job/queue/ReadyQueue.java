package com.major.job.queue;

import com.major.common.util.RedissonUtils;
import org.redisson.api.RBlockingQueue;

/**
 * 存放可以消费的job
 *
 * @author LianGuoQing
 */
public class ReadyQueue {

    /**
     * 添加jodId到准备队列
     * @param topic
     * @param delayQueueJodId
     */
    public static void pushToReadyQueue(String topic, long delayQueueJodId) {
        RBlockingQueue<Long> rBlockingQueue = RedissonUtils.getBlockingQueue(topic);
        rBlockingQueue.offer(delayQueueJodId);
    }

    /**
     * 从准备队列中获取jodId
     * @param topic
     * @return
     */
    public static Long pollFormReadyQueue(String topic) {
        RBlockingQueue<Long> rBlockingQueue = RedissonUtils.getBlockingQueue(topic);
        return rBlockingQueue.poll();
    }

}
