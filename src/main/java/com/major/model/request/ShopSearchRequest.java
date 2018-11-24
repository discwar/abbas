package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/8/9 11:22
 * @Version 1.0
 */
@Getter
@Setter
public class ShopSearchRequest {

    @ApiModelProperty(value = "店铺类型：1-爱果小店；2-水果店；3-农场；4-采摘园；5-进口商；6-扶贫区", required = true)
    @NotNull(message = "店铺类型不能为空")
    private Integer shopType;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 地址
     */
    private String shopAddress;

    /**
     * 销量
     */
    private String salesCountStart;

    private String salesCountStop;

    /**
     * 订单额
     */
    private String totalOrderStart;

    private String totalOrderStop;

    /**
     * 商品数
     */
    private String groundNumberStart;
    private String groundNumberStop;
    /**
     * 评分
     */
    private String scoreStart;
    private String scoreStop;

    /**
     * 退单数
     */
    private String refundStart;
    private String refundStop;

    /**
     * 评论数
     */
    private String commentStart;
    private String commentStop;
    /**
     * 专克
     */
    private String specialGramStart;
    private String specialGramStop;

    /**
     * 时间
     */
    private Date createTimeStart;
    private Date createTimeStop;

    /**
     * 套票
     */
    private String TicketStart;
    private String TicketStop;
}
