package com.major.common.util;

import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;

import java.util.Map;


public class JSONUtil {

    public static String toJson(Object object) {
        return object == null ? "" : JSON.toJSONString(object);
    }

    public static <T> T fromJson(String json, Class<T> tClass){
        return JSON.parseObject(json, tClass);
    }

    /**
     * map转化成json
     * @param map
     * @return
     */
    public static String mapToJson(Map<String,Object> map){
       JSONObject json = JSONObject.fromObject(map);
       return json.toString();
    }
}
