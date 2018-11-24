package com.major.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * <p>
 * 用户支付日志记录表
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-10-11
 */
@TableName("log_pay")
@Getter
@Setter
@ToString
public class LogPay extends SuperEntity<LogPay> {


    @TableField("user_id")
    private Long userId;
    /**
     * 支付方式：1-钱包；2-支付宝；3-微信；4-银行卡
     */
    @TableField("pay_way")
    private Integer payWay;
    /**
     * 账单类型：1-订单支付；2-钱包储值；3-创客储值；4-邀请新用户成为创客；5-好友消费返利；6-提现
     */
    @TableField("bill_type")
    private Integer billType;
    /**
     * 交易类型：0-钱包储值；1-线上消费；2-线下支付；3-创客储值；4-创客升级；5-邀请用户成为创客；6-好友消费返利；7-二级好友消费返利；8-提现；9-退款
     */
    @TableField("transaction_type")
    private Integer transactionType;
    /**
     * 支付流水号
     */
    @TableField("flow_no")
    private String flowNo;
    /**
     * 订单号（订单支付），其他情况值为流水号
     */
    @TableField("order_no")
    private String orderNo;
    /**
     * 金额
     */
    private BigDecimal money;
    /**
     * 外部订单号，比如支付宝交易订单号
     */
    @TableField("outer_trade_no")
    private String outerTradeNo;
    /**
     * 支付状态：-1支付失败；0-待支付；1-支付成功
     */
    private Integer status;
    /**
     * 原始数据
     */
    @TableField("original_data")
    private String originalData;
    /**
     * 第三方返回的原始数据
     */
    @TableField("outer_original_data")
    private String outerOriginalData;
    /**
     * 钱包支付时，金钱来源：0-10,1-20 （0-创客储值金；1-钱包储值金；2-创客收益金）
     */
    private String moneySource;
}
