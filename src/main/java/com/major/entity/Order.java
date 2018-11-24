package com.major.entity;

import com.baomidou.mybatisplus.annotations.TableLogic;
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
 * <p>Create Time: 2018/8/3 10:13      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Getter
@Setter
@ToString
@TableName("`order`")
public class Order extends SuperEntity<Order> {

    private static final long serialVersionUID = 1L;

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
     * 订单类型：0-普通订单；1-秒杀订单；2-团购订单
     */
    private Integer orderType;
    /**
     * 订单状态类型：0-爱果店铺&附近水果店；1-采摘园套票；2-采摘园出团；3-采摘园直销&农场&进口水果&扶贫订单；4-拼团；5-砍价
     */
    private Integer orderStatusType;
    /**
     * 爱果店铺&附近水果店（外卖订单）
     * 订单状态：1-等待付款；2-支付成功，等待商家接单；3-商品准备中；4-商品配送中，请耐心等待；
     * 5-已送达，订单完成；6-订单已取消；7-订单支付超时，已取消；8-退款申请中；9-已售后退款；10-退款失败；11-商家拒绝接单
     *
     * 采摘园套票
     * 订单状态：1-已出票，待使用；2-套票已使用；3-已退票
     *
     * 采摘园出团
     * 订单状态：1-参团成功，待出游；2-已退团；3-出团时间已顺延；4-已出团
     *
     * 采摘园直销&农场&进口水果&扶贫订单（物流快递）
     * 订单状态：1-等待付款；2-支付成功，商品准备中；3-商家已发货；4-商品运输中，请耐心等待；
     * 5-订单已完成；6-订单已取消；7-订单支付超时，已取消；8-退款申请中；9-已售后退款；10-退款失败
     *
     * 拼团（外卖订单）
     * 订单状态：1-团购中；2-拼团未成功，已退款；3-已退出拼团；4-拼团成功，商品准备中；
     * 5-商品配送中，请耐心等待；6-订单已完成；7-退款申请中；8-已售后退款；9-退款失败
     *
     * 砍价（外卖订单）
     * 订单状态：1-发起砍价中；2-未支付，砍价已过期；3-支付成功，商品准备中；4-商品配送中，请耐心等待；
     * 5-已送达，订单完成；6-退款申请中；7-已售后退款；8-退款失败
     */
    private Integer orderStatus;
    /**
     * 订单状态描述
     */
    private String orderStatusDesc;
    /**
     * 商品数量
     */
    private Integer goodsCount;
    /**
     * group_buying_info表ID
     */
    private Long groupBuyingId;
    /**
     * 组团采摘表Id
     */
    private Long groupPickingId;
    /**
     * 采摘园套票使用码
     */
    private String ticketNumber;
    /**
     * 套票已核销数量
     */
    private Integer ticketUseNum;
    /**
     * 运费
     */
    private BigDecimal postage;
    /**
     * 满多少免运费
     */
    private BigDecimal postFree;
    /**
     * 满减额
     */
    private BigDecimal promotionMoney;
    /**
     * 用户优惠劵ID
     */
    private Long userCouponId;
    /**
     * 优惠劵使用情况
     */
    private String couponInfo;
    /**
     * 商品总价格，未进行任何折扣的总价格
     */
    private BigDecimal goodsAmount;
    /**
     * 订单总金额
     */
    private BigDecimal totalAmount;
    /**
     * 实际支付总金额
     */
    private BigDecimal realTotalAmount;
    /**
     * 支付来源：1-钱包；2-支付宝；3-微信
     */
    private Integer payFrom;
    /**
     * 订单所得积分
     */
    private Integer orderScore;
    /**
     * 是否支付：0-未支付；1-已支付
     */
    private Integer isPay;
    /**
     * 是否订单已完结：0-未完结；1-已完结
     */
    private Integer isClosed;
    /**
     * 是否退款：0-否；1-是
     */
    private Integer isRefund;
    /**
     * 是否评论：0-否；1-是
     */
    private Integer isComment;
    /**
     * 是否返利：0-否；1-是
     */
    private Integer isRebate;
    /**
     * 是否申请取消订单：0-否；1-是
     */
    private Integer isApplyCancel;
    /**
     * 申请取消订单状态：0-待审批；1-审批通过；2-审批不通过
     */
    private Integer applyCancelStatus;
    /**
     * 申请取消订单审批不通过理由
     */
    private String applyCancelReply;
    /**
     * 返利金额（一级、二级邀请人所得）
     */
    private BigDecimal rebateAmount;
    /**
     * 订单取消、订单退款原因（order_reason_config表ID）
     */
    private Long reasonId;
    /**
     * 商家拒绝接单原因
     */
    private String rejectReason;
    /**
     * 配送方式：爱果配送、商家配送、物流配送
     */
    private String deliveryType;
    /**
     * express表ID，快递公司ID
     */
    private Long expressId;
    /**
     * 快递编号
     */
    private String expressNo;
    /**
     * 发货时间
     */
    private Date deliveryTime;
    /**
     * 收货人
     */
    private String receiveName;
    /**
     * 收货人手机
     */
    private String receivePhone;
    /**
     * 收货完整地址
     */
    private String receiveAddress;
    /**
     * 收货经度
     */
    private Double longitude;
    /**
     * 收货维度
     */
    private Double latitude;
    /**
     * 收货时间
     */
    private Date receiveTime;
    /**
     * 支付时间
     */
    private Date payTime;
    /**
     * 订单已完成时间
     */
    private Date orderTime;
    /**
     * 订单备注
     */
    private String orderRemark;
    /**
     * 订单有效标志：1-正常；2-禁用；0-删除
     */
    @TableLogic
    private Integer status;

    /**
     * 佣金
     */
    private BigDecimal commission;

    /**
     * 结算表ID
     */
    private Long settlementId;

    /**
     * 达达运费
     */
    private BigDecimal dadaFee;

}
