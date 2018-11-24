package com.major.common.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/9/13 9:43
 * @Version 1.0
 */
@Slf4j
public class ImgUtils {

    /**
     * 根据传入的图片url转换成图片的大小
     * @param imgUrl
     * @return
     */
    public static String getImageInfoByUrl(String imgUrl){
        String size=null;
        try {
            URL url = new URL(imgUrl);
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            int length = connection.getContentLength();
            size=String.format("%.1f",length/1024.0)+"KB";
        }catch (Exception e){
            log.info("图片大小转换失败"+e.getMessage());
        }
        return size ;
    }

    public static void main(String[] args) throws IOException {

        System.out.println(getImageInfoByUrl("http://missfreshfruits.oss-cn-hangzhou.aliyuncs.com/ag/web/logo/1537948032854.jpg"));
    }
}
