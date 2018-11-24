package com.major.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * <p>
 * 提现记录表
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-09-20
 */
@TableName("log_withdraw_cash")
@Getter
@Setter
@ToString
public class LogWithdrawCash extends SuperEntity<LogWithdrawCash> {


    /**
     * 所属对象类型：0-用户；1-商家
     */
    @TableField("target_type")
    private Integer targetType;
    /**
     * 所属对象ID；如果是用户，则对应user表ID
     */
    @TableField("target_id")
    private Integer targetId;
    /**
     * 提现金额
     */
    private BigDecimal money;
    /**
     * 持卡人
     */
    private String cardholder;
    /**
     * 卡号
     */
    @TableField("card_number")
    private String cardNumber;
    /**
     * 银行名称
     */
    @TableField("bank_name")
    private String bankName;
    /**
     * user_bank_card表ID
     */
    @TableField("user_bank_card_id")
    private Integer userBankCardId;
    /**
     * 提现状态：-1-提现失败；0-待处理；1-提现成功
     */
    @TableField("cash_status")
    private Integer cashStatus;
    /**
     * 提现备注
     */
    @TableField("cash_remarks")
    private String cashRemarks;

}
