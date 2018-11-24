package com.major.common.util;

import org.apache.shiro.crypto.hash.Md5Hash;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>Title: MD5工具类 </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/12 14:50      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public class Md5Utils {

    public static String stringToMD5(String plainText) {
        byte[] secretBytes = null;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("md5");
            secretBytes =  messageDigest.digest(plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        }

        String md5code = new BigInteger(1, secretBytes).toString(16);
        StringBuffer result = new StringBuffer(md5code);
        int len = 32 - md5code.length();

        for (int i = 0; i < len; i++) {
            result.append("0");
        }

        return result.toString();
    }

    public static  String encryptPassword(String password, String salt, int hashIterations) {
        return new Md5Hash(password, salt, hashIterations).toHex();
    }

    public static void main(String[] args) {
//        String agAppSecret = Md5Utils.stringToMD5("agAppSecret");
//
//        System.out.println(agAppSecret);
//
//        // 取第6个到第11个字符
//        System.out.println(agAppSecret.substring(5, 11));


//        String agAppSecret = Md5Utils.stringToMD5("appid=wxd930ea5d5a258f4f&body=test&device_info=1000&mch_id=10000100&nonce_str=ibuaiVcKdpRxkhJA&key=192006250b4c09247ec02edce69f6a2d");
//        System.out.println(agAppSecret);

        String username = "18050430300";
        String salt = "55c152";
        String credentialsSalt = username + salt;

        //生成密码案例
        System.out.println(Md5Utils.encryptPassword(Md5Utils.stringToMD5("aiguo666"), credentialsSalt, 2));

    }

}
