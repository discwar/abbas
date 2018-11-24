package com.major.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/31 14:32      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Getter
@Setter
@ToString
@TableName("coupon")
public class Coupon extends SuperEntity<Coupon> {

    private static final long serialVersionUID = 1L;

    /**
     * 种类：0-爱果卷；1-店铺卷
     */
    private Integer kind;

    /**
     * shop表ID（店铺券所有者，爱果券默认为爱果）
     */
    private Integer shopId;


    /**
     * 优惠券名称
     */
    @TableField("coupon_name")
    private String couponName;
    /**
     * 优惠券类型：0-满减卷；1-折扣卷；2-免邮卷
     */
    @TableField("coupon_type")
    private Integer couponType;
    /**
     * 满多少减
     */
    @TableField("min_price")
    private BigDecimal minPrice;

    /**
     * 优惠情况：金额/折扣
     */
    @TableField("discount")
    private BigDecimal discount;
    /**
     * 优惠详情 中文含义
     */
    @TableField("discount_desc")
    private String discountDesc;
    /**
     * 限制条件：0-不能同活动商品共享；1-不能同折扣商品共享；2-不能同特惠共用；3-不能同爱果优惠劵共用（此项店铺有）\r\n\r\n（多个用英文逗号隔开）
     */
    @TableField("limit_condition")
    private String limitCondition;
    /**
     * 适用范围：1-爱果果品店；2-爱果礼品果盒；3-爱果当季热卖；4-爱果预售；5-附近水果店；6-采摘园；7-农场；8-进出口商；9-扶贫\r\n\r\n（多个用英文逗号隔开）
     */
    @TableField("apply_range")
    private String applyRange;

    /**
     * 优惠券详情\r\n\r\n满多少元减多少元\r\n\r\n适用范围：爱果果品店。。\r\n\r\n限制条件：
     */
    private String couponDesc;

    /**
     * 过期时间
     */
    private Date deadline;
    /**
     * 状态：0-删除;1-正常；2-下架; 3-未上架
     */

    private Integer status;

    /**
     * 展示开始时间
     */
    private Date displayStartTime;
    /**
     * 展示结束时间
     */
    private Date displayStopTime;
}
