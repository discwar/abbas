package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/8/10 16:10
 * @Version 1.0
 */
@Getter
@Setter
public class ShopOperateRequest {


    @ApiModelProperty(value = "店铺状态：0-营业中；1-休息中", required = false)
    private Integer shopStatus;

    @ApiModelProperty(value = "营业开始时间", required = false)
    private String serviceStartTime;

    @ApiModelProperty(value = "营业结束时间", required = false)
    private String serviceEndTime;

    @ApiModelProperty(value = "商家电话", required = false)
    private String phone;

    @ApiModelProperty(value = "店铺公告", required = false)
    private String shopNotice;

    @ApiModelProperty(value = "满减（多个用英文逗号,隔开），比如满50减5即50-5", required = false)
    private List<String> promotionPlans;

    @ApiModelProperty(value = "运费", required = false)
    private BigDecimal postage;

    @ApiModelProperty(value = "满多少免运费", required = false)
    private BigDecimal postFree;

    @ApiModelProperty(value = "配送方式：0-爱果配送；1-商家配送", required = false)
    private Integer deliveryType;

    @ApiModelProperty(value = "起送价", required = false)
    private BigDecimal minDeliveryPrice;

    @ApiModelProperty(value = "最远配送距离（单位：km）", required = false)
    private Double farDistance;

    @ApiModelProperty(value = "店铺标签", required = false)
    private String label;

    @ApiModelProperty(value = "发货时间", required = false)
    private String deliveryTime;

    @ApiModelProperty(value = "各店铺类型下的商品是否新鲜/是否进出口认证：1-是", required = false)
    private Integer isFresh;

    @ApiModelProperty(value = "快递签收24内变质是否包退 1:是", required = false)
    private Integer isRetreating;


    @ApiModelProperty(value = "只针对水果商家:专客折扣，几折", required = false)
    private  BigDecimal specialGuestRebate;

    @ApiModelProperty(value = "总部自营-爱果小店-附近水果店:下订单时是否需要短信通知：0-否，1-是", required = false)
    private Integer isMessageNotice;

    @ApiModelProperty(value = "总部自营-爱果小店-附近水果店:接收短信通知的手机号", required = false)
    private String pushPhone;

    @ApiModelProperty(value = "总部自营-爱果小店-附近水果店: 是否自动接单：0-否；1-是", required = false)
    private Integer isAutoReceiveOrder;
}
