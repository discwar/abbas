package com.major.common.constant;

import lombok.Getter;

/**
 * 用户常量信息
 * @author LianGuoQing
 */
public interface UserConstants {

    /**
     * 用户名长度限制
     */
    Integer USERNAME_MIN_LENGTH = 2;
    Integer USERNAME_MAX_LENGTH = 20;
    
    /**
     * 密码长度限制
     */
    Integer PASSWORD_MIN_LENGTH = 5;
    Integer PASSWORD_MAX_LENGTH = 40;

    /**
     * 正常状态
     */
    Integer NORMAL = 0;

    /**
     * 异常状态
     */
    Integer EXCEPTION = 1;

    @Getter
    enum ScoreEnum {
        /**
         * 积分规则
         */
        EVALUATE_ORDER(5, "评价订单"),
        SHARE_APP(5, "分享App"),
        READ_TIPS(2, "阅读小贴士"),
        MAKER(100, "成为&升级创客"),
        INVITE_REGISTER(30, "邀请好友注册"),
        INVITE_MAKER(50, "邀请好友成为创客");

        private Integer value;
        private String desc;

        ScoreEnum(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }
    }

    @Getter
    enum ScoreSourceEnum {
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

        ScoreSourceEnum(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }
    }

}
