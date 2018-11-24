package com.major.model.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xuquanming
 * @date 2018/11/20 11:23
 */
@Getter
@Setter
@ToString
public class RebateOrderBO {
    private Long id;
    /**
     * 订单号：唯一值
     */
    private String orderNo;
    /**
     * 店铺编号
     */
    private Long shopId;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户支付日志记录ID
     */
    private Long logPayId;
    /**
     * 实际支付总金额
     */
    private BigDecimal realTotalAmount;
    /**
     * 钱包支付时，金钱来源：0-10,1-20 （0-创客储值金；1-钱包储值金；2-创客收益金）
     */
    private String moneySource;
    /**
     * 订单已完成时间
     */
    private Date orderTime;
}
