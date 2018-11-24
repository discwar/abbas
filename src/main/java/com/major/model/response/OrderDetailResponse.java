package com.major.model.response;

import lombok.Data;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/10/31 16:28
 * @Version 1.0
 */
@Data
public class OrderDetailResponse {

    private Long goodsId;

    private Integer deliveryType;

    private Long orderId;


}
