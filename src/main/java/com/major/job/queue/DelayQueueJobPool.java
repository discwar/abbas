package com.major.job.queue;

import com.major.common.util.RedissonUtils;
import com.alibaba.fastjson.JSONObject;
import org.redisson.api.RMap;

/**
 * 延迟任务池
 *
 * @author LianGuoQing
 */
public class DelayQueueJobPool {

    /**
     * redis中延迟任务池key
     */
    private static final String DELAY_QUEUE_JOB_POOL = "delay_queue_job_pool";

    /**
     * 查询 DelayQueueJod
     * @param delayQueueJodId
     * @return
     */
    public static DelayQueueJob getDelayQueueJod(long delayQueueJodId) {
        RMap<Long, JSONObject> rMap = RedissonUtils.getMap(DELAY_QUEUE_JOB_POOL);
        JSONObject json = rMap.get(delayQueueJodId);
        if (json != null) {
            return JSONObject.parseObject(json.toJSONString(), DelayQueueJob.class);
        }

        return null;
    }

    /**
     * 添加 DelayQueueJod
     * @param delayQueueJob
     */
    public static void addDelayQueueJod(DelayQueueJob delayQueueJob) {
        RMap<Long, JSONObject> rMap = RedissonUtils.getMap(DELAY_QUEUE_JOB_POOL);
        JSONObject json = (JSONObject) JSONObject.toJSON(delayQueueJob);
        rMap.put(delayQueueJob.getId(), json);
    }

    /**
     * 删除 DelayQueueJod
     * @param delayQueueJodId
     */
    public static void deleteDelayQueueJod(long delayQueueJodId) {
        RMap<Long, JSONObject> rMap = RedissonUtils.getMap(DELAY_QUEUE_JOB_POOL);
        rMap.remove(delayQueueJodId);
    }

}
