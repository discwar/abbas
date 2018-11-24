package com.major.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 店铺运营表
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-07
 */
@Getter
@Setter
@TableName("shop_operate")
public class ShopOperate extends SuperEntity<ShopOperate> {

    private static final long serialVersionUID = 1L;

    @TableField("shop_id")
    private Long shopId;
    /**
     * 店铺状态：0-营业中；2-休息中
     */
    @TableField("shop_status")
    private Integer shopStatus;
    /**
     * 营业开始时间
     */
    @TableField("service_start_time")
    private Date serviceStartTime;
    /**
     * 营业结束时间
     */
    @TableField("service_end_time")
    private Date serviceEndTime;

    /**
     * 法人
     */
    private String legalPerson;
    /**
     * 商家电话
     */
    private String phone;
    /**
     * 店铺公告
     */
    @TableField("shop_notice")
    private String shopNotice;
    /**
     * 满减（多个用英文逗号,隔开），比如满50减5即50-5
     */
    @TableField("promotion_plans")
    private String promotionPlans;

    /**
     * 运费
     */
    private BigDecimal postage;
    /**
     * 满多少免运费
     */
    @TableField("post_free")
    private BigDecimal postFree;
    /**
     * 配送方式：0-爱果配送；1-商家配送
     */
    @TableField("delivery_type")
    private Integer deliveryType;
    /**
     * 起送价
     */
    @TableField("min_delivery_price")
    private BigDecimal minDeliveryPrice;
    /**
     * 最远配送距离（单位：km）
     */
    @TableField("far_distance")
    private Double farDistance;

    /**
     * 发货时间
     */
    private String deliveryTime;

    /**
     * 各店铺类型下的商品是否新鲜、是否进口认证:1-是;
     */
    private Integer isFresh;

    /**
     * 快递签收24内变质是否包退 1:是
     */
    private Integer isRetreating;


    /**
     * 总体评分
     */
    private Double score;

    /**
     * 店铺标签
     */
   private String label;

    /**
     * 专客折扣，几折
     */
   private  BigDecimal specialGuestRebate;

    /**
     * 二维码地址
     */
   private String  qrCodeUrl;

    /**
     * 状态：0-删除;1-正常
     */
    private Integer status;

    /**
     * 总部自营-爱果小店-附近水果店
     * 下订单时是否需要短信通知：0-否，1-是
     */
    private Integer isMessageNotice;

    /**
     * 总部自营-爱果小店-附近水果店
     * 接收短信通知的手机号
     */
    private String pushPhone;

    /**
     * 附近水果和爱果店铺订单预计送达时间（分钟）
     */
    private String estimatedExpendTime;

    /**
     * 总部自营-爱果小店-附近水果店
     * 是否自动接单：0-否；1-是
     */
    private Integer isAutoReceiveOrder;
}
