package com.major.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: XuQuanMing
 * @Date: 2018/7/13 13:42
 * @Description:
 */
@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${shiro.redis.autoOutTime}")
    private long autoOutTime;

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key,autoOutTime*60*60*24,TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Long increment(String key, long value) {
        ValueOperations operations = redisTemplate.opsForValue();
        return operations.increment(key, value);
    }

    public Long increment(String key, long value, Long expireTime) {
        ValueOperations operations = redisTemplate.opsForValue();
        Long count = operations.increment(key, value);
        redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
        return count;
    }

    /**
     * 写入缓存设置时效时间
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 获取缓存剩余时间
     *
     * @param key
     * @return
     */
    public long getLiveTime(final String key){
        if (exists(key)) {
            return redisTemplate.getExpire(key, TimeUnit.SECONDS);
        }
        return 0L;
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 判断缓存中Map是否有的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key,final String hashKey){
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        ValueOperations operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    /**
     * 哈希 添加
     *
     * @param key
     * @param hashKey
     * @param value
     */
    public void hmSet(String key, Object hashKey, Object value) {
        HashOperations hash = redisTemplate.opsForHash();
        redisTemplate.expire(key,autoOutTime*60*60*24, TimeUnit.SECONDS);
        hash.put(key, hashKey, value);
    }

    /**
     * hash添加集合
     */
    public void pullAll(String key, Map<String, Object> map) {
        HashOperations hash = redisTemplate.opsForHash();
        hash.putAll(key, map);
        redisTemplate.expire(key,autoOutTime*60*60*24, TimeUnit.SECONDS);
    }

    public void pullAllForever(String key, Map<String, Object> map) {
        HashOperations hash = redisTemplate.opsForHash();
        hash.putAll(key,map);
    }

    /**
     * hash添加集合
     */
    public void pullAll(String key, Map<String, Object> map, long expireTime) {
        HashOperations hash = redisTemplate.opsForHash();
        hash.putAll(key, map);
        redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 哈希获取数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    public Object hmGet(String key, Object hashKey) {
        HashOperations hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    /**
     * 列表添加
     *
     * @param k
     * @param v
     */
    public void lPush(String k, Object v) {
        ListOperations list = redisTemplate.opsForList();
        list.leftPush(k, v);
        redisTemplate.expire(k,autoOutTime*60*60*24, TimeUnit.SECONDS);
    }

    /**
     * 右添加
     * @param k
     * @param v
     */
    public void rPushForever(String k, Object v) {
        ListOperations list = redisTemplate.opsForList();
        list.rightPush(k, v);
    }


    /**
     * 添加多个列表
     * @param k
     * @param var2
     * @param expireTime
     */
    public void lPushAll(String k, Collection var2, long expireTime) {
        ListOperations list = redisTemplate.opsForList();
        list.leftPushAll(k, var2);
        redisTemplate.expire(k, expireTime, TimeUnit.SECONDS);
    }

    /**
     * 添加多个列表
     * @param k
     * @param var2
     */
    public void lPushAll(String k, Collection var2) {
        ListOperations list = redisTemplate.opsForList();
        list.leftPushAll(k, var2);
    }

    /**
     * 列表头部获取一个数据，并移除
     * @param k
     * @return
     */
    public String lPop(String k) {
        ListOperations list = redisTemplate.opsForList();
        return (String) list.leftPop(k);
    }

    /**
     * 截取集合元素长度（从左到右开始），保留长度内的数据
     * @param k
     * @param start
     * @param end
     */
    public void trim(String k, long start, long end) {
        ListOperations list = redisTemplate.opsForList();
        list.trim(k, start, end);
    }

    /**
     * 列表获取
     *
     * @param k
     * @param l
     * @param l1
     * @return
     */
    public List lRange(String k, long l, long l1) {
        ListOperations list = redisTemplate.opsForList();
        return list.range(k, l, l1);
    }

    /**
     * 集合添加
     *
     * @param key
     * @param value
     */
    public void add(String key, Object value) {
        SetOperations set = redisTemplate.opsForSet();
        redisTemplate.expire(key,autoOutTime*60*60*24, TimeUnit.SECONDS);
        set.add(key, value);
    }
    /**
     * 集合获取
     *
     * @param key
     * @return
     */
    public Set setMembers(String key) {
        SetOperations set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     * @param key
     * @param value
     * @param score
     */
    public void zAdd(String key, Object value, double score) {
        ZSetOperations zSet = redisTemplate.opsForZSet();
        zSet.add(key, value, score);
        redisTemplate.expire(key,autoOutTime*60*60*24, TimeUnit.SECONDS);
    }

    public void zAdd(String key, Object value, double score, long timeout) {
        ZSetOperations zSet = redisTemplate.opsForZSet();
        zSet.add(key, value, score);
        redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 有序集合获取
     * @param key
     * @param score
     * @param score1
     * @return
     */
    public Set rangeByScore(String key, double score, double score1) {
        ZSetOperations zSet = redisTemplate.opsForZSet();
        return zSet.rangeByScore(key, score, score1);
    }
    /**
     * 通过key获取哈希Map
     * @param key
     * @return
     */
    public Map<String, Object> hmEntries(String key) {
        HashOperations hash = redisTemplate.opsForHash();
        return hash.entries(key);
    }

    /**
     * 从redis中获取SYS配置
     * @param key
     * @param cls
     * @param <T>
     * @return
     */
    public <T> T getRedisSysConfigMap(String key,Class<T> cls) {
        Map<String, Object> redisMap = this.hmEntries(key);
        return JSONObject.parseObject(JSONObject.toJSONString(redisMap), cls);
    }

} 