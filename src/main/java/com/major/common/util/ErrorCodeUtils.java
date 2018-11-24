package com.major.common.util;

/**
 * <p>
 * 提供异常代码验证与异常消息格式化的工具方法。
 * </p>
 * 
 * @author LianGuoQing
 */
public class ErrorCodeUtils {

    /**
     * 从指定枚举中提取出异常代码。
     * <p>
     * 枚举名称字符串将直接作为异常代码。
     * </p>
     * 
     * @param ee 枚举
     * @return 异常代码
     * @throws IllegalArgumentException 如果异常代码不符合规范定义
     */
    public static String generateCode(Enum<?> ee) throws IllegalArgumentException {
        String code = ee.name();
        return code;
    }

    /**
     * 格式化异常消息。
     * <p>
     * 该方法内部使用 {@link String#format(String, Object...)}方法进行格式化，如果格式化发生错误，将直接返回 {@code message}。
     * </p>
     * 
     * @param message 消息文本
     * @param args 消息的占位符参数值
     * @return 格式化后的消息
     */
    public static String formatMessage(String message, Object... args) {
        if (args.length == 0) {
            return message;
        }

        try {
            return String.format(message, args);
        } catch (Exception e) {
            return message;
        }
    }

}
