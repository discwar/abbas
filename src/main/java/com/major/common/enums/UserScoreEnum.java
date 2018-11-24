package com.major.common.enums;

import lombok.Getter;

/**
 * @author xuquanming
 * @date 2018/9/5 20:40
 */
@Getter
public enum UserScoreEnum {
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

    UserScoreEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
