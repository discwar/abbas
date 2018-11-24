package com.major.common.util;

/**
 * <p>Title: 对称加密算法RC4工具类 </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/6/28 18:25      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public class Rc4Utils {

    /**
     * 解密
     * @param data 需要解密的数据
     * @param key 密钥
     * @return
     */
    public static String decryptRC4(byte[] data, String key) {
        if (data == null || key == null) {
            return null;
        }
        return asString(RC4Base(data, key));
    }

    public static String decryptRC4(String data, String key) {
        if (data == null || key == null) {
            return null;
        }
        return new String(RC4Base(HexString2Bytes(data), key));
    }

    /**
     * 加密
     * @param data 需要加密的数据
     * @param key 密钥
     * @return
     */
    public static byte[] encryptRC4Byte(String data, String key) {
        if (data == null || key == null) {
            return null;
        }
        byte b_data[] = data.getBytes();
        return RC4Base(b_data, key);
    }

    public static String encryptRC4String(String data, String key) {
        if (data == null || key == null) {
            return null;
        }
        return toHexString(asString(encryptRC4Byte(data, key)));
    }

    private static String asString(byte[] buf) {
        StringBuffer stringBuffer = new StringBuffer(buf.length);
        for (int i = 0; i < buf.length; i++) {
            stringBuffer.append((char) buf[i]);
        }
        return stringBuffer.toString();
    }

    private static byte[] initKey(String aKey) {
        byte[] bKey = aKey.getBytes();
        byte[] state = new byte[256];

        for (int i = 0; i < 256; i++) {
            state[i] = (byte) i;
        }

        int index1 = 0;
        int index2 = 0;
        if (bKey == null || bKey.length == 0) {
            return null;
        }

        for (int i = 0; i < 256; i++) {
            index2 = ((bKey[index1] & 0xff) + (state[i] & 0xff) + index2) & 0xff;
            byte tmp = state[i];
            state[i] = state[index2];
            state[index2] = tmp;
            index1 = (index1 + 1) % bKey.length;
        }

        return state;
    }

    private static String toHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch & 0xFF);
            if (s4.length() == 1) {
                s4 = '0' + s4;
            }
            str = str + s4;
        }

        return str;
    }

    private static byte[] HexString2Bytes(String src) {
        int size = src.length();
        byte[] ret = new byte[size / 2];
        byte[] tmp = src.getBytes();
        for (int i = 0; i < size / 2; i++) {
            ret[i] = uniteBytes(tmp[i * 2], tmp[i * 2 + 1]);
        }
        return ret;
    }

    private static byte uniteBytes(byte src0, byte src1) {
        char _b0 = (char) Byte.decode("0x" + new String(new byte[] { src0 })).byteValue();
        _b0 = (char) (_b0 << 4);
        char _b1 = (char) Byte.decode("0x" + new String(new byte[] { src1 })).byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }

    private static byte[] RC4Base(byte[] input, String mKkey) {
        int x = 0;
        int y = 0;
        byte key[] = initKey(mKkey);
        int xorIndex;
        byte[] result = new byte[input.length];

        for (int i = 0; i < input.length; i++) {
            x = (x + 1) & 0xff;
            y = ((key[x] & 0xff) + y) & 0xff;
            byte tmp = key[x];
            key[x] = key[y];
            key[y] = tmp;
            xorIndex = ((key[x] & 0xff) + (key[y] & 0xff)) & 0xff;
            result[i] = (byte) (input[i] ^ key[xorIndex]);
        }
        return result;
    }

    public static void main(String[] args) {
        String key = "055803e4-5cc4-4509-88e4-16208d3c58a7";
        String data = String.format("%10d", 123L);
        System.out.println(data);

        // 加密的数据按10位进行补位，不足则前面补空格
        String value = Rc4Utils.encryptRC4String(data, key).toUpperCase();
        System.out.println(value);

        // 解密后数据要去掉空格
        String decryptData = Rc4Utils.decryptRC4(value.toLowerCase(), key).trim();
        System.out.println(Long.valueOf(decryptData));
    }

}
