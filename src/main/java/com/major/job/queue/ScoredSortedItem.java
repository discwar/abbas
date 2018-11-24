package com.major.job.queue;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *  延迟队列排序
 *
 * @author LianGuoQing
 */
@Getter
@Setter
@ToString
public class ScoredSortedItem {

    /**
     * 任务的执行时间
     */
    private long delayTime;

    /**
     * 延迟任务的唯一标识
     */
    private long delayQueueJodId;

    public ScoredSortedItem(long delayQueueJodId, long delayTime) {
        this.delayQueueJodId = delayQueueJodId;
        this.delayTime = delayTime;
    }

}
