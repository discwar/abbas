package com.major.service;

import com.major.common.enums.StatusResultEnum;
import com.major.common.exception.AgException;
import com.major.common.util.HttpUtils;
import com.major.model.NeighPositionBO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/17 18:07      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Service
public class AMapService {

    @Value("${amap.key}")
    private String key;

    @Value("${amap.host}")
    private String host;

    @Value("${amap.default-distance}")
    private Long defaultDistance;

    private static final String STATUS_FAILURE = "0";

    /**
     * 判断是否在默认距离范围内
     * @param location
     * @param endLngLat
     * @return
     */
    public boolean isDefaultDistanceScope(String location, String endLngLat) {
        Long distance = this.getDistance(location, endLngLat);
        return distance <= defaultDistance;
    }

    /**
     * 获取两地址之间的距离
     * @param startAddress 起始地址
     * @param endAddress 终止地址
     * @return
     */
    public Long getDistanceByAddress(String startAddress, String endAddress) {
        String startLonLat = getLngLat(startAddress);
        String endLonLat = getLngLat(endAddress);
        return this.getDistance(startLonLat, endLonLat);
    }

    /**
     * 地理编码：将详细的结构化地址转换为高德经纬度坐标。
     * 参考API：https://lbs.amap.com/api/webservice/guide/api/georegeo
     * @param address 详细的结构化地址
     * @return 返回高德经纬度坐标
     */
    private String getLngLat(String address) throws AgException {
        String uri = host + "/v3/geocode/geo?key={key}&address={address}";
        Map<String, String> uriVariables = new HashMap<>(2);
        uriVariables.put("key", key);
        uriVariables.put("address", address);

        String responseResult = HttpUtils.getResponseResult(uri, uriVariables);

        JSONObject responseResultJson = JSONObject.parseObject(responseResult);
        String status = responseResultJson.getString("status");
        if (StringUtils.equals(STATUS_FAILURE, status)) {
            throw new AgException(StatusResultEnum.REQUIRE_AMAP_ERROR, responseResultJson.getString("info"));
        }

        JSONArray geocodesJSONArray = responseResultJson.getJSONArray("geocodes");
        JSONObject geocodeJSONObject = JSONObject.parseObject(geocodesJSONArray.getString(0));

        return geocodeJSONObject.get("location").toString();
    }

    /**
     * 通过经纬度测量出两点之间的直线距离，单位米
     * 参考API：https://lbs.amap.com/api/webservice/guide/api/direction#distance
     * @param startLngLat 起点经纬度
     * @param endLngLat 终点经纬度
     * @return
     */
    public Long getDistance(String startLngLat, String endLngLat) throws AgException {
        String uri = host + "/v3/distance?key={key}&origins={origins}&destination={destination}&type=0";
        Map<String, String> uriVariables = new HashMap<>(3);
        uriVariables.put("key", key);
        uriVariables.put("origins", startLngLat);
        uriVariables.put("destination", endLngLat);

        String responseResult = HttpUtils.getResponseResult(uri, uriVariables);

        JSONObject responseResultJson = JSONObject.parseObject(responseResult);
        String status = responseResultJson.getString("status");
        if (StringUtils.equals(STATUS_FAILURE, status)) {
            throw new AgException(StatusResultEnum.REQUIRE_AMAP_ERROR, responseResultJson.getString("info"));
        }

        JSONArray resultsJSONArray = responseResultJson.getJSONArray("results");
        JSONObject resultJSONObject = JSONObject.parseObject(resultsJSONArray.getString(0));

        return Long.parseLong(resultJSONObject.get("distance").toString());
    }

    public String getProvinceCity(String longitude, String latitude) throws AgException {
        Map provinceCityMap = this.getProvinceCityMap(longitude, latitude);
        String province = (String) provinceCityMap.get("province");
        String city = (String) provinceCityMap.get("city");

        if(StringUtils.isBlank(city)) {
            city = "市辖区";
        }

        return province + city;
    }

    public Map getProvinceCityMap(String longitude, String latitude) throws AgException {
        String uri = host + "/v3/geocode/regeo?key={key}&location={location}";
        Map<String, String> uriVariables = new HashMap<>(2);
        String location = longitude + "," + latitude;
        uriVariables.put("key", key);
        uriVariables.put("location", location);

        String responseResult = HttpUtils.getResponseResult(uri, uriVariables);

        JSONObject responseResultJson = JSONObject.parseObject(responseResult);
        String status = responseResultJson.getString("status");
        if (StringUtils.equals(STATUS_FAILURE, status)) {
            throw new AgException(StatusResultEnum.REQUIRE_AMAP_ERROR, responseResultJson.getString("info"));
        }

        JSONObject regeocodeJson = responseResultJson.getJSONObject("regeocode");
        JSONObject addressComponentJson = regeocodeJson.getJSONObject("addressComponent");
        String province = addressComponentJson.getString("province");
        String city = addressComponentJson.getString("city");


        Map<String,Object> result = new HashMap<>(2);
        result.put("province",province);
        result.put("city",city);
        return result;
    }



    public static void main(String[] args) {
        double longitude = Double.valueOf("119.342350");
        double latitude = Double.valueOf("26.052943");
        Long defaultDistance = 450L;
        double dis = defaultDistance/(double) 1000;
        System.out.println(dis);
//        findNeighPosition(longitude, latitude);
    }

    /**
     * 计算查询点的经纬度范围，查询范围按默认配置500米内
     * @param longitude 经度
     * @param latitude 纬度
     * @return
     */
    public NeighPositionBO findNeighPosition(double longitude, double latitude) {
        // 地球半径千米
        double r = 6371;
        //默认0.5千米距离
        double dis = defaultDistance/(double) 1000;

        double dLng = 2*Math.asin(Math.sin(dis/(2*r))/Math.cos(latitude*Math.PI/180));
        //角度转为弧度
        dLng = dLng*180/Math.PI;
        double minLng = longitude -dLng;
        double maxLng = longitude + dLng;

        double dLat = dis/r;
        dLat = dLat*180/Math.PI;
        double minLat = latitude-dLat;
        double maxLat = latitude+dLat;

        NeighPositionBO neighPositionBO = new NeighPositionBO();
        neighPositionBO.setMinLongitude(minLng);
        neighPositionBO.setMaxLongitude(maxLng);
        neighPositionBO.setMinLatitude(minLat);
        neighPositionBO.setMaxLatitude(maxLat);

        return neighPositionBO;
    }

}
