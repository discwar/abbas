package com.major.app;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AgxxAppServerApplicationTests {

    @Test
    public void contextLoads() {
    }

    public static void main(String[] args) {

        Map<String, Object> requestMap = new HashMap();
        requestMap.put("token", "krXzGwnq0iAUO5TK37ay+w==");
        requestMap.put("timestamp", "1531100151");
        requestMap.put("app_key", "app_key");
        requestMap.put("app_version", "1.0");
        requestMap.put("os_type", "0");
        String appSecret = "agAppSecret";

        //Key值排序
        Collection<String> keySet = requestMap.keySet();
        List<String> list = new ArrayList<>(keySet);
        Collections.sort(list);

        //拼凑签名字符串
        StringBuffer signStr = new StringBuffer();
        for(int i=0; i<list.size(); i++){
            String key = list.get(i);
            signStr.append(key + requestMap.get(key));
        }

        System.out.println(signStr.toString());

        //MD5签名
        String mySign = stringToMD5(appSecret + signStr.toString() + appSecret);
        System.out.println(mySign);

//        String mySign = stringToMD5("appSecretapp_keyapp_keybody{\"order_id\":\"20170301000001\"}formatjsonsource_id73753timestamp1488363493v1.0appSecret");

        String sign = mySign.toUpperCase();
        System.out.println(sign);
    }

    public static String stringToMD5(String plainText) {
        byte[] secretBytes = null;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("md5");
            secretBytes =  messageDigest.digest(plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        }

        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }

        return md5code;
    }

}
