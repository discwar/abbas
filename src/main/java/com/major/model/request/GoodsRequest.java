package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/8/9 19:25
 * @Version 1.0
 */
@Getter
@Setter
public class GoodsRequest {

    @ApiModelProperty(value = "商品名称", required = true)
    @NotNull(message = "name不能为空")
    private String name;

    @ApiModelProperty(value = "店铺名称", required = true)
    @NotNull(message = "shopId不能为空")
    private Long shopId;

    @ApiModelProperty(value = "商品副标题", required = false)
    private String subTitle;

    @ApiModelProperty(value = "商品分类Id", required = false)
    private Long categoryId;

    @ApiModelProperty(value = "优惠之后价格", required = false)
    private BigDecimal currentPrice;

    @ApiModelProperty(value = "原价", required = false)
    private BigDecimal originalPrice;


    @ApiModelProperty(value = "降价时，每人每次限购数量", required = false)
    private Integer limitNum;


    @ApiModelProperty(value = "优惠类型：0-特惠；1-折扣；2-普通（普通商品都参与满减）；3-1元购", required = true)
    @NotNull(message = "promotionType不能为空")
    private Integer promotionType;

    @ApiModelProperty(value = "库存", required = false)
    private Long totalCount;

    @ApiModelProperty(value = "订单量", required = false)
    private Long ordersCount;


    @ApiModelProperty(value = "商品介绍图地址，多个", required = false)
    private List<String> imgUrls;


    @ApiModelProperty(value = "商品介绍", required = false)
    private String goodsDesc;


    @ApiModelProperty(value = "备注", required = false)
    private String remark;

    @ApiModelProperty(value = "针对采摘园店铺:费用内容说明", required = false)
    private String cost;

    @ApiModelProperty(value = "针对采摘园店铺:费用内容不包括说明", required = false)
    private String costNotInclude;

    @ApiModelProperty(value = "针对采摘园店铺:时间说明/行程安排", required = false)
    private String timeDescription;

    @ApiModelProperty(value = "针对采摘园店铺:使用说明/预定须知", required = false)
    private String instructions;


    @ApiModelProperty(value = "规格", required = false)
    private String specifications;

    @ApiModelProperty(value = "产地", required = false)
    private String producer;

    @ApiModelProperty(value = "下季预售:发货时间", required = false)
    private Date deliveryTime;

    @ApiModelProperty(value = "只针对1元购配送方式：0-爱果配送；1-商家配送（预留）；2-物流配送", required = false)
    private Integer deliveryType;

    @ApiModelProperty(value = "普通（店铺类型为：2-水果店；3-农场；4-采摘园；5-进口商）；1-果切果汁（店铺类型为：爱果小店）；2-礼品果盒（总部自营店）；3-当季热卖（总部自营店）；4-预售（总部自营店）；5-1元购（总部自营店）", required = true)
    @NotNull(message = "goodsType不能为空")
    private Integer goodsType;

    @ApiModelProperty(value = "针对采摘园店铺:一句话简介", required = false)
    private String profile;

    @ApiModelProperty(value = "针对采摘园店铺组团采摘分类:组团人数", required = false)
    private Integer personNum;

    @ApiModelProperty(value = "针对采摘园类型:其他说明/参团流程", required = false)
    private String  otherDesc;

    @ApiModelProperty(value = "商品封面", required = false)
    private String coverUrl;
}
