package com.major.common.enums;

import lombok.Getter;

/**
 * @author xuquanming
 * @date 2018/9/5 11:48
 */
@Getter
public enum UserScoreSourceEnum {
    /**
     * 积分来源
     */
    EXCHANGE(0, "积分兑换"),
    ONLINE_CONSUME(1, "线上订单消费"),
    UNDERLINE_PAY(2, "线下扫码支付"),
    MAKER_STORE(3, "储值成为创客"),
    MAKER_UPGRADE(4, "储值升级创客"),
    INVITE_REGISTER(5, "邀请好友注册"),
    INVITE_MAKER(6, "邀请的好友成为创客"),
    SIGN_IN(7, "签到"),
    READ_TIPS(8, "阅读小贴士"),
    EVALUATE_ORDER(9, "评价订单"),
    SHARE_APP(10, "分享App"),
    ONLINE_REFUND(11, "线上订单退款");

    private Integer value;
    private String desc;

    UserScoreSourceEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
