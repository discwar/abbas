package com.major.common.enums;

import com.major.common.util.ErrorCodeUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>Title: API响应结果中status信息枚举类 </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/13 14:06      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public enum StatusResultEnum {

    /**
     *
     */
    SUCCESS("2000", "success", "请求成功"),
    ERROR("2001", "failure", "操作失败"),

    REQUIRE_AMAP_ERROR("1009", "请求高德地图失败：%1s", "请求高德地图失败：%1s"),

    REQUIRE_ARGUMENT("1100", "缺少参数：%1s", "缺少参数：%1s"),
    REQUIRE_ARGUMENT_VALID_FAIL("1104", "请求参数校验失败：%1s", "请求参数校验失败：%1s"),

    PAY_WAY_ERROR("3005", "支付方式错误：%s", "支付方式错误：%s"),

    NOT_LOGIN_IN("4001", "未登录", "未登录"),
    UN_AUTHORIZED("4002", "权限不足", "权限不足"),
    CHECK_CODE_ERROR("4006", "验证码错误", "验证码错误"),
    IDENTITY_AUTH_FAIL("4011", "身份认证失败：%1s", "身份认证失败：%1s"),
    MSG_NOTIFY_FAIL("4021", "消息发送失败：%1s", "消息发送失败：%1s"),
    INTERNAL_SERVER_ERROR("5000", "%1s", "内部服务器错误，请联系爱果客服人员。"),
    DB_SAVE_ERROR("1999", "数据操作失败：%1s", "数据操作失败：%1s"),
    KUAI_DI_100_ERROR("3000", "订阅快递100失败：%1s", "订阅快递100失败：%1s"),
    WX_REFUND_ORDER_FAIL("3001", "微信退款失败：%1s", "微信退款失败：%1s"),
    AL_REFUND_ORDER_FAIL("3002", "支付宝退款失败：%1s", "支付宝退款失败：%1s"),
    ORDER_REFUND_TIMEOUT("3003", "订单已超过48小时，无法申请退款！", "订单已超过48小时，无法申请退款！"),
    DADA_ORDER_QUERY_FAIL("3004", "达达订单详情查询失败：%1s", "订单详情查询失败：%1s"),
    DADA_ORDER_CANCEL_FAIL("3005", "达达订单取消失败：%1s", "达达订单取消失败：%1s"),
    DADA_ADD_ORDER_CANCEL_FAIL("3006", "达达新增订单消失败：%1s", "达达新增订单消失败：%1s"),
    KUAI_DI_100_CANCEL_ERROR("3007", "查询快递100失败：%1s", "查询快递100失败：%1s"),
    REFUND_MONEY_GREATER_PAY_MONEY("3008", "退款金额大于支付金额！", "退款金额大于支付金额！"),
    ;

    /**
     * 响应返回码
     */
    @Getter
    private String code;
    /**
     * 响应描述，面向开发者
     */
    @Setter
    private String codeMsg;
    /**
     * 响应描述，面向用户
     */
    @Setter
    private String statusMsg;

    StatusResultEnum(String code, String codeMsg, String statusMsg) {
        this.code = code;
        this.codeMsg = codeMsg;
        this.statusMsg = statusMsg;
    }

    /**
     * 根据指定的占位符参数格式化异常消息。
     *
     * @param args 占位符参数
     * @return 格式化后的异常消息
     */
    public String getCodeMsg(Object... args) {
        return ErrorCodeUtils.formatMessage(codeMsg, args);
    }

    /**
     * 根据指定的占位符参数格式化异常消息。
     *
     * @param args 占位符参数
     * @return 格式化后的异常消息
     */
    public String getStatusMsg(Object... args) {
        return ErrorCodeUtils.formatMessage(statusMsg, args);
    }

}
