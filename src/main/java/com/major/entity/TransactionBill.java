package com.major.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 交易账单表
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-19
 */
@Getter
@Setter
@TableName("transaction_bill")
public class TransactionBill extends Model<TransactionBill> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * user表ID
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 金额
     */
    private BigDecimal money;
    /**
     * 标识：1-支出；2-收入
     */
    private Integer mark;
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
     * 交易时间
     */
    @TableField(value="create_time", fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 流水单号
     */
    @TableField("flow_no")
    private String flowNo;
    /**
     * 积分奖励
     */
    @TableField("score_reward")
    private Integer scoreReward;
    @TableField("shop_id")
    private Long shopId;
    /**
     * 店铺名称
     */
    @TableField("shop_name")
    private String shopName;
    /**
     * 订单号
     */
    @TableField("order_no")
    private String orderNo;
    /**
     * 储值赠送
     */
    @TableField("stored_give")
    private String storedGive;
    /**
     * 储值送好礼表ID
     */
    @JSONField(name = "stored_config_id")
    private Long storedConfigId;
    /**
     * 原创客等级
     */
    private String originalMakerRanks;

    /**
     * 创客等级
     */
    @TableField("maker_ranks")
    private String makerRanks;
    /**
     * 可提现时间
     */
    @TableField("thaw_time")
    private Date thawTime;
    /**
     * 被邀请人用户ID（user表）
     */
    private Long inviteeId;
    /**
     * 返利来源
     */
    @TableField("rebate_source")
    private String rebateSource;
    /**
     * 返利比例
     */
    @TableField("rebate_ratio")
    private BigDecimal rebateRatio;
    /**
     * 提现银行
     */
    @TableField("bank_desc")
    private String bankDesc;

    /**
     * 备注
     */
    private String remark;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
