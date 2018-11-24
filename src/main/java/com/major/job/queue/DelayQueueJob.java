package com.major.job.queue;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 延迟任务
 *
 * @author LianGuoQing
 */
@Getter
@Setter
@ToString
public class DelayQueueJob {

    /**
     * 延迟任务的唯一标识，用于检索任务
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private long id;

    /**
     * 任务类型（具体业务类型）
     */
    private String topic;

    /**
     * 任务的执行时间，单位：秒
     */
    private long delayTime;

    /**
     * 任务的执行超时时间，单位：秒
     */
    private long ttrTime;

    /**
     * 任务具体的消息内容，用于处理具体业务逻辑用
     */
    private String message;

}
