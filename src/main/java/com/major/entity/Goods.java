package com.major.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

import static com.baomidou.mybatisplus.enums.FieldStrategy.IGNORED;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/18 19:32      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Getter
@Setter
@ToString
@TableName("goods")
public class Goods extends SuperEntity<Goods> {

    private String name;

    private Long shopId;

    @TableField("sub_title")
    private String subTitle;

    @TableField("category_id")
    private Long categoryId;


    @TableField("current_price")
    private BigDecimal currentPrice;

    /**
     * 设置可以更新值为NULL
     */
    private BigDecimal originalPrice;

    /**
     *优惠类型：0-特惠；1-折扣；2-普通（普通商品都参与满减）；3-1元购
     */
    @TableField("promotion_type")
    private Integer promotionType;

    /**
     * 降价时，每人每次限购数量
     */
    @TableField(value="limit_num", strategy=IGNORED)
    private Integer limitNum;

    /**
     * 库存
     */
    @TableField("total_count")
    private Long totalCount;

    /**
     * 订单量
     */
    @TableField("orders_count")
    private Long ordersCount;

    /**
     * 商品介绍图地址，多个
     */
    @TableField("img_urls")
    private String imgUrls;

    /**
     * 商品介绍
     */
    @TableField("goods_desc")
    private String goodsDesc;

    /**
     * 商品状态：0-删除；1-上架；2-下架
     * 备注，此处不要加逻辑删除注解，为了用于商品可以上架和下架操作
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;


    /**
     * 针对采摘园店铺:费用内容说明
     */
    private String cost;
    /**
     *针对采摘园店铺:费用内容不包括说明
     */
    private String costNotInclude;
    /**
     * 针对采摘园店铺：时间说明/行程安排
     */
    private String timeDescription;
    /**
     *针对采摘园店铺:使用说明/预定须知
     */
    private String instructions;


    /**
     * 规格
     */
    private String specifications;

    /**
     * 产地
     */
    private String producer;

    /**
     * 下季预售:发货时间
     */
    private Date deliveryTime;

    /**
     * 只针对1元购配送方式：0-爱果配送；1-商家配送（预留）；2-物流配送
     */
    private Integer deliveryType;

    /**
     * 针对采摘园店铺:一句话简介
     */
    private String profile;

    /**
     * 针对采摘园店铺组团采摘分类:组团人数
     */
    private Integer personNum;

    /**
     * 针对采摘园店铺：其他说明/参团流程
     */
    private String  otherDesc;

    /**
     * 商品封面
     */
    private String coverUrl;

}
