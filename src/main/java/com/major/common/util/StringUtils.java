package com.major.common.util;

import com.major.common.enums.StatusResultEnum;
import com.major.common.exception.AgException;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/7/31.
 */
public class StringUtils {

    /**
     * 判断输入的字符串参数是否为空
     *
     * @return boolean 空则返回true,非空则flase
     */
    public static boolean isEmpty(String input) {
        return null == input || 0 == input.length()
                || 0 == input.replaceAll("\\s", "").length();
    }

    /**
     * 校验店铺标签长度
     * @param label
     */
    public static void checkLabelLenth(String label){
        String str[]=label.split(",");
        List<String> list= Arrays.asList(str);
        for (String s :list){
            if(s.length()>4){
                throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"店铺标签过长："+s);
            }
        }
    }
}

