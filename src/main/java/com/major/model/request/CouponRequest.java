package com.major.model.request;

import com.baomidou.mybatisplus.annotations.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/8/6 10:04
 * @Version 1.0
 */
@Getter
@Setter
public class CouponRequest {

    /**
     * 种类：0-爱果卷；1-店铺卷
     */
    @ApiModelProperty(value = "种类：0-爱果卷；1-店铺卷", required = true)
    @NotNull(message = "种类不能为空")
    private Integer kind;

    @ApiModelProperty(value = "shop表ID,如果是总部添加可以为空", required = false)
    private Integer shopId;

    /**
     * 优惠券名称
     */
    @ApiModelProperty(value = "优惠券名称", required = true)
    @NotNull(message = "优惠券名称不能为空")
    private String couponName;

    @ApiModelProperty(value = "优惠券类型：0-满减卷；1-折扣卷；2-运费券", required = true)
    @NotNull(message = "优惠券类型不能为空")
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


    @ApiModelProperty(value = "限制条件：0-不能同活动商品共享；1-不能同折扣商品共享；2-不能同特惠共用；3-不能同爱果优惠劵共用（此项店铺有）,此处传的是数字", required = false)
    private List<String> limitCondition;


    @ApiModelProperty(value = "适用范围：1-爱果果品店；2-爱果礼品果盒；3-爱果当季热卖；4-爱果预售；5-附近水果店；6-采摘园；7-农场；8-进出口商；9-扶贫；10-一元购,此处传的是数字", required = true)
    @NotNull(message = "applyRange不能为空")
    private List<String> applyRange;

    @ApiModelProperty(value = "限制条件：0-不能同活动商品共享；1-不能同折扣商品共享；2-不能同特惠共用；3-不能同爱果优惠劵共用（此项店铺有）,此处传的是中文意思", required = false)
    private List<String> limitConditionName;

    @ApiModelProperty(value = "适用范围：1-爱果果品店；2-爱果礼品果盒；3-爱果当季热卖；4-爱果预售；5-附近水果店；6-采摘园；7-农场；8-进出口商；9-扶贫；10-一元购,此处传的是中文意思", required = true)
    @NotNull(message = "applyRangeName不能为空")
    private List<String> applyRangeName;


    @ApiModelProperty(value = "过期时间", required = true)
    @NotNull(message = "deadline不能为空")
    private Date deadline;


    @ApiModelProperty(value = "展示开始时间", required = true)
    private Date displayStartTime;


    @ApiModelProperty(value = "展示结束时间", required = true)
    private Date displayStopTime;
}
