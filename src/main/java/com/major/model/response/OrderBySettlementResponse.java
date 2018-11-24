package com.major.model.response;

import com.major.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/11/21 15:11
 * @Version 1.0
 */
@Getter
@Setter
public class OrderBySettlementResponse extends Order {

    private String couponInfo;

    private Integer couponSource;

    /**
     * 退款类型：0-退全部；1-退部分
     */
    private Integer refundType;
    /**
     * 退款金额
     */
    private BigDecimal refundMoney;

}
