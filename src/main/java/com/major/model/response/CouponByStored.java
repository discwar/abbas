package com.major.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/11/20 10:17
 * @Version 1.0
 */
@Data
public class CouponByStored {

    @JSONField(name = "couponId")
    private Long id;

    private String couponName;

    private Integer couponType;

    private String discountDesc;



}
