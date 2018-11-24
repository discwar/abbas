package com.major.job;

import com.major.common.util.DateUtils;
import com.major.job.queue.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 实时扫描延迟任务桶中的任务，放到准备队列中
 *
 * @author LianGuoQing
 */
@Slf4j
public class DelayBucketTimer implements Runnable {

    @Getter
    @Setter
    private String delayBucketKey;

    public DelayBucketTimer(String delayBucketKey) {
        this.delayBucketKey = delayBucketKey;
    }

    @Override
    public void run() {
        while (true) {
            try {
                ScoredSortedItem item = DelayBucket.getFromBucket(this.delayBucketKey);

                // 没有任务
                if (item == null) {
                    sleep();
                    continue;
                }

                // 延迟时间没到
                if (item.getDelayTime() > DateUtils.getCurrentTimestamp()) {
                    sleep();
                    continue;
                }

                DelayQueueJob delayQueueJod = DelayQueueJobPool.getDelayQueueJod(item.getDelayQueueJodId());

                //延迟任务元数据不存在
                if (delayQueueJod == null) {
                    DelayBucket.deleteFormBucket(this.delayBucketKey,item);
                    continue;
                }

                // 再次确认延时时间是否到了
                if (delayQueueJod.getDelayTime() > DateUtils.getCurrentTimestamp()) {
                    // 删除旧的
                    DelayBucket.deleteFormBucket(this.delayBucketKey, item);
                    // 重新计算延迟时间
                    DelayBucket.addToBucket(this.delayBucketKey, new ScoredSortedItem(delayQueueJod.getId(), delayQueueJod.getDelayTime()));
                } else {
                    // 时间到了，自动放到准备队列中
                    ReadyQueue.pushToReadyQueue(delayQueueJod.getTopic(), delayQueueJod.getId());
                    // 删除任务桶中的任务
                    DelayBucket.deleteFormBucket(this.delayBucketKey, item);
                }

            }catch (Exception e) {
                log.error("扫描delayBucket出错：",e);
            }
        }
    }

    private void sleep(){
        try {
            TimeUnit.SECONDS.sleep(1L);
        }catch (InterruptedException e){
            log.error("",e);
        }
    }

}
