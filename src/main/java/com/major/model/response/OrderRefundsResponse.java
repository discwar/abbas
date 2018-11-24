package com.major.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/10/24 13:57
 * @Version 1.0
 */
@Data
public class OrderRefundsResponse {

    @TableField("id")
    @JSONField(name = "refundId")
    private Integer Id;

    @TableField("order_id")
    private Integer orderId;
    /**
     * 退款类型：0-退全部；1-退部分
     */
    @TableField("refund_type")
    private Integer refundType;
    /**
     * 退款金额
     */
    @TableField("refund_money")
    private BigDecimal refundMoney;
    /**
     * order_reason_config表ID
     */
    @TableField("refund_reason_id")
    private Long refundReasonId;
    /**
     * 问题描述
     */
    @TableField("problem_desc")
    private String problemDesc;
    /**
     * 图片描述地址
     */
    @TableField("img_urls")
    private String imgUrls;
    /**
     * 审核状态：0-待审核；1-审核通过；2-拒绝
     */
    @TableField("audit_status")
    private Integer auditStatus;
    /**
     * 拒绝理由
     */
    @TableField("refuse_reason")
    private String refuseReason;
    /**
     * 用户申请退款时间
     */
    @TableField("create_time")
    private Date createTime;

    @JSONField(name = "refundReason")
    private String reason;
}
