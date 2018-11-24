package com.major.common.util;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * <p>Title: token生成器 </p>
 * <p>Description: 用于生成token </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/9 10:22      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public class TokenGenerator {

    private static final TokenGenerator instance = new TokenGenerator();

    private TokenGenerator() {}

    public static TokenGenerator getInstance() {
        return instance;
    }

    /**
     * 生成Token
     * @return
     */
    public String generateToken() {
        String token = (System.currentTimeMillis() + new Random().nextInt(999999999)) + "";
        try {
            MessageDigest md = MessageDigest.getInstance("md5");
            byte[] md5 =  md.digest(token.getBytes());
            return base64Encode(md5);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static String base64Encode(byte[] var1){
        return new BASE64Encoder().encode(var1);
    }

    public static void main(String[] args) {
        String token = TokenGenerator.getInstance().generateToken();
        System.out.println(token);
    }

}
