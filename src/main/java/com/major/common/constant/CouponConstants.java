package com.major.common.constant;

import lombok.Getter;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/9/30 18:23
 * @Version 1.0
 */
public interface CouponConstants {

    /**
     * 全部适用范围
     */
    String CouponApplyRangeAll="1,2,3,4,5,6,7,8,9,10";

    @Getter
    enum CouponApplyRangeEnum{
        /**
         * 适用范围
         */
        APPLY_RANGE_AG(1,"爱果果品店"),
        APPLY_RANGE_FJ(2,"附近水果店"),
        APPLY_RANGE_NC(3,"农场"),
        APPLY_RANGE_CZ(4,"采摘园"),
        APPLY_RANGE_JK(5,"进出口商"),
        APPLY_RANGE_FP(6,"扶贫"),
        APPLY_RANGE_LP(7,"爱果礼品果盒"),
        APPLY_RANGE_DJ(8,"爱果当季热卖"),
        APPLY_RANGE_YS(9,"爱果预售"),
        APPLY_RANGE_ONE(10,"一元购");
        private Integer value;
        private String desc;

        CouponApplyRangeEnum(Integer value, String desc) {
            this.value = value;
            this.desc = desc;
        }
    }
}
