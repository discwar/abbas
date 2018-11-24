package com.major.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 订单日志记录表
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-08-20
 */
@Getter
@Setter
@ToString
public class LogOrder extends Model<LogOrder> {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * order表ID
     */
    private Long orderId;
    /**
     * 订单状态类型：0-爱果店铺&附近水果店；1-采摘园套票；2-采摘园出团；3-采摘园直销&农场&进口水果&扶贫订单；4-拼团；5-砍价
     */
    private Integer orderStatusType;
    /**
     * 爱果店铺&附近水果店（外卖订单）
     * 订单状态：0-订单已提交；1-等待付款；2-支付成功，商品准备中；3-商品配送中，请耐心等待；4-已送达，订单完成；
     * 5-订单已取消；6-订单支付超时，已取消；7-退款申请中；8-已售后退款；9-退款失败
     *
     * 采摘园套票
     * 订单状态：0-订单已提交；1-已出票，待使用；2-套票已使用；3-已退票
     *
     * 采摘园出团
     * 订单状态：0-订单已提交；1-参团成功，待出游；2-已退团；3-出团时间已顺延；4-已出团
     *
     * 采摘园直销&农场&进口水果&扶贫订单（物流快递）
     * 订单状态：0-订单已提交；1-等待付款；2-支付成功，商品准备中；3-商家已发货；4-商品运输中，请耐心等待；
     * 5-订单已完成；6-订单已取消；7-订单支付超时，已取消；8-退款申请中；9-已售后退款；10-退款失败
     *
     * 拼团（外卖订单）
     * 订单状态：0-订单已提交；1-团购中；2-拼团未成功，已退款；3-已退出拼团；4-拼团成功，商品准备中；
     * 5-商品配送中，请耐心等待；6-订单已完成；7-退款申请中；8-已售后退款；9-退款失败
     *
     * 砍价（外卖订单）
     * 订单状态：0-订单已提交；1-发起砍价中；2-未支付，砍价已过期；3-支付成功，商品准备中；4-商品配送中，请耐心等待；
     * 5-已送达，订单完成；6-退款申请中；7-已售后退款；8-退款失败
     */
    private Integer orderStatus;
    /**
     * 订单状态描述
     */
    private String orderStatusDesc;
    /**
     * 操作者ID
     */
    private Long operId;
    /**
     * 操作者类型：0-用户；1-商家；-1-系统
     */
    private Integer operType;
    /**
     * 操作日志
     */
    private String operContent;
    /**
     * 备注
     */
    private String remark;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
