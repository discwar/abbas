package com.major.job.queue;

import com.major.common.util.DateUtils;

/**
 * 延迟消息队列
 *
 * @author LianGuoQing
 */
public class DelayQueue {

    /**
     * redis中延迟任务桶前缀
     */
    public static final String DELAY_BUCKET_KEY_PREFIX = "delay_bucket_";
    /**
     * 延迟任务桶中的数量
     */
    public static final long DELAY_BUCKET_NUM = 10L;

    /**
     * 获取delayBucket key 分开多个，有利于提高效率
     * @param delayQueueJodId
     * @return
     */
    private static String getDelayBucketKey(long delayQueueJodId) {
        return DELAY_BUCKET_KEY_PREFIX + Math.floorMod(delayQueueJodId, DELAY_BUCKET_NUM);
    }

    /**
     * 添加延迟任务到延迟队列
     * @param delayQueueJob
     */
    public static void push(DelayQueueJob delayQueueJob) {
        DelayQueueJobPool.addDelayQueueJod(delayQueueJob);
        ScoredSortedItem item = new ScoredSortedItem(delayQueueJob.getId(), delayQueueJob.getDelayTime());
        DelayBucket.addToBucket(getDelayBucketKey(delayQueueJob.getId()), item);
    }

    /**
     * 获取准备好的延迟任务
     * @param topic
     * @return
     */
    public static DelayQueueJob pop(String topic) {
        Long delayQueueJodId = ReadyQueue.pollFormReadyQueue(topic);
        if (delayQueueJodId == null) {
            return null;
        } else {
            DelayQueueJob delayQueueJod = DelayQueueJobPool.getDelayQueueJod(delayQueueJodId);
            if (delayQueueJod == null) {
                return null;
            } else {
                long delayTime = delayQueueJod.getDelayTime();
                // 获取消费超时时间，重新放到延迟任务桶中
                long reDelayTime = DateUtils.getCurrentTimestamp() + delayQueueJod.getTtrTime()*1000L;
                delayQueueJod.setDelayTime(reDelayTime);
                DelayQueueJobPool.addDelayQueueJod(delayQueueJod);
                ScoredSortedItem item = new ScoredSortedItem(delayQueueJod.getId(), reDelayTime);
                DelayBucket.addToBucket(getDelayBucketKey(delayQueueJod.getId()), item);
                // 返回的时候设置成原来的超时时间
                delayQueueJod.setDelayTime(delayTime);
                return delayQueueJod;
            }
        }
    }

    /**
     * 删除延迟队列任务
     * @param delayQueueJodId
     */
    public static void delete(long delayQueueJodId) {
        DelayQueueJobPool.deleteDelayQueueJod(delayQueueJodId);
    }

    /**
     * 清除延迟队列job池的队列
     * @param delayQueueJodId
     */
    public static void finish(long delayQueueJodId) {
        DelayQueueJob delayQueueJod = DelayQueueJobPool.getDelayQueueJod(delayQueueJodId);
        if (delayQueueJod == null) {
            return;
        }

        DelayQueueJobPool.deleteDelayQueueJod(delayQueueJodId);
        ScoredSortedItem item = new ScoredSortedItem(delayQueueJod.getId(), delayQueueJod.getDelayTime());
        DelayBucket.deleteFormBucket(getDelayBucketKey(delayQueueJod.getId()), item);
    }

    /**
     * 查询 delay queue job
     * @param delayQueueJodId
     * @return
     */
    public static DelayQueueJob get(long delayQueueJodId) {
        return DelayQueueJobPool.getDelayQueueJod(delayQueueJodId);
    }

}
