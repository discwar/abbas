package com.major.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>Title: banner图管理表实体类 </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/13 9:44      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Getter
@Setter
@ToString
@TableName("banner")
public class Banner extends SuperEntity<Banner> {

    private String name;

    private Long bannerSort;

    /**
     * banner类型：1-首页；2-附件水果；3-采摘园；4-农场；5-进口水果；6-扶贫
     */
    private Integer bannerType;

    /**
     * 图片地址
     */
    private String imgUrl;

    /**
     *
     * 跳转类型:0-不跳转 1-商品；2-店铺；3-文章；4-链接
     */
    private Integer skipType;


    /**
     * 当跳转类型为商品和店铺时显示该字段
     * 店铺类型：1-爱果小店；2-水果店；3-农场；4-采摘园；5-进口商；6-扶贫区；7-礼品果盒；8-当季热卖；9-下季预售；10-一元购；（当跳转类型为商品和店铺时显示该字段）
     */
    private Integer shopType;

    /**
     * 跳转内容：商品及店铺保存其ID，文章及链接保存其地址
     */
    private String skipContent;

    /**
     * banner状态：0-删除；1-上架；2-下架
     */
    private Integer status;

    /**
     * 适用范围：0-全国通用；1-多地市；2-单地市
     *
     * 附近水果仅适用于单地市
     */
    private Integer scope;

    /**
     * 保存多地市
     *
     * 地市ID集合（逗号隔开）
     */
    private String cityIds;

    /**
     * 经度（单地市）
     */
    private Double longitude;

    /**
     * 维度（单地市）
     */
    private Double latitude;

    private String cityNames;

}
