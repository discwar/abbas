package com.major.model.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/11/16 15:07
 * @Version 1.0
 */
@Getter
@Setter
public class CouponNumberRequest {

    /**
     * 优惠券
     */
    private String couponId;

    /**
     * 数量
     */
    private Integer number;
    /**
     * 优惠内容
     */
    private String discountDesc;

}
