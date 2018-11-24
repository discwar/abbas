package com.major.job.queue;

import com.major.common.util.RedissonUtils;
import com.alibaba.fastjson.JSONObject;
import org.redisson.api.RScoredSortedSet;

/**
 * 一组以时间为维度的有序队列，用来存放所有需要延迟的DelayJob（这里只存放DelayJob Id）
 *
 * @author LianGuoQing
 */
public class DelayBucket {

    /**
     * 添加 DelayJob 到延迟任务桶中
     * @param key
     * @param scoredSortedItem
     */
    public static void addToBucket(String key, ScoredSortedItem scoredSortedItem) {
        RScoredSortedSet<JSONObject> scoredSortedSet = RedissonUtils.getScoredSortedSet(key);
        JSONObject json = (JSONObject) JSONObject.toJSON(scoredSortedItem);
        scoredSortedSet.add(scoredSortedItem.getDelayTime(), json);
    }

    /**
     * 从延迟任务桶中获取延迟时间最小的 jodId
     * @param key
     * @return
     */
    public static ScoredSortedItem getFromBucket(String key) {
        RScoredSortedSet<JSONObject> scoredSortedSet = RedissonUtils.getScoredSortedSet(key);
        if (scoredSortedSet.size() == 0) {
            return null;
        }

        JSONObject json = scoredSortedSet.first();
        return JSONObject.parseObject(json.toJSONString(), ScoredSortedItem.class);
    }

    /**
     * 从延迟任务桶中删除 jodId
     * @param key
     * @param scoredSortedItem
     */
    public static void deleteFormBucket(String key, ScoredSortedItem scoredSortedItem) {
        RScoredSortedSet<JSONObject> scoredSortedSet = RedissonUtils.getScoredSortedSet(key);
        JSONObject json = (JSONObject) JSONObject.toJSON(scoredSortedItem);
        scoredSortedSet.remove(json);
    }

}
