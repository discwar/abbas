package com.major.common.util;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/9/11 16:02
 * @Version 1.0
 */
public  class GADUtils {
    private static final String KEY = "389880a06e3f893ea46036f030c94700";
    private static final String OUTPUT = "JSON";
    private static final String GET_LNG_LAT_URL = "http://restapi.amap.com/v3/geocode/geo";

    //获取指定地点经纬度
    public static String[] getLngLatFromOneAddr(String address){
        if(StringUtils.isEmpty(address)) {
            return null;
        }
        Map<String, String> params = new HashMap<String, String>();
        params.put("address", address);
        params.put("output", OUTPUT);
        params.put("key", KEY);

        String result = HttpUtils.sendPost(GET_LNG_LAT_URL,params);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String[] lngLatArr = new String[2];
        //拿到返回报文的status值，高德的该接口返回值有两个：0-请求失败，1-请求成功；
        int status = Integer.valueOf(jsonObject.getString("status"));
        if(status == 1) {
            JSONArray jsonArray = jsonObject.getJSONArray("geocodes");
            for(int i = 0; i < jsonArray.size(); i++) {
                JSONObject json = jsonArray.getJSONObject(i);
                String lngLat = json.getString("location");
                lngLatArr = lngLat.split(",");
            }
        } else {
            String errorMsg = jsonObject.getString("info");
        }
        return lngLatArr;
    }


    public static void main(String[] args) {
        GADUtils gadUtils=new GADUtils();
       System.out.println();
       String strings[]=gadUtils.getLngLatFromOneAddr("福州市");
      String s2=strings[0];
        String s3=strings[1];
    }
}
