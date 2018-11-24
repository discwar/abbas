package com.major.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 商品分类表
 * </p>
 *
 * @author XuQuanMing
 * @since 2018-07-24
 */
@Getter
@Setter
@TableName("goods_category")
public class GoodsCategory extends SuperEntity<GoodsCategory> {

    private static final long serialVersionUID = 1L;

    @TableField("shop_id")
    private Long shopId;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 排序号
     */
    @TableField("category_sort")
    private Integer categorySort;
    /**
     * 类别描述
     */
    @TableField("category_desc")
    private String categoryDesc;

    /**
     * 分类状态：0-删除；1-上架；2-下架
     */

    private Integer status;

    /**
     * 分类类型：4：采摘套票 5：果园直销 6：组团采摘
     */
    private Integer categoryType;

    /**
     * 针对进出口店铺-所属洲id
     */
    private Long continentId;

    /**
     * 店铺类型:0-总部自营；1-爱果小店；2-水果店；3-农场；4-采摘园；5-进口商；6-扶贫区
     */

    private Integer shopType;

    /**
     * 分类图片地址
     */
    private String imgUrls;

}
