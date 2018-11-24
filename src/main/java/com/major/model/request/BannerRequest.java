package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BannerRequest {

    @ApiModelProperty(value = "名称", required = true)
    private String name;

    @ApiModelProperty(value = "排序", required = false)
    private Long bannerSort;

    @ApiModelProperty(value = "banner类型：1-首页；2-附件水果；3-农场；4-采摘园；5-进口水果；6-扶贫", required = true)
    private Integer bannerType;

    @ApiModelProperty(value = "图片地址", required = false)
    private String imgUrl;


    @ApiModelProperty(value = "跳转类型:0-不跳转 1-商品；2-店铺；3-文章；4-链接", required = true)
    private Integer skipType;



    @ApiModelProperty(value = "跳转内容：商品及店铺保存其ID，文章及链接保存其地址", required = false)
    private String skipContent;

    @ApiModelProperty(value = "适用范围：0-全国通用；1-多地市；2-单地市（附近水果仅适用于单地市）", required = false)
    private Integer scope;


    @ApiModelProperty(value = "保存多地市, 地市ID集合（逗号隔开）", required = false)
    private List<String> cityIds;

    @ApiModelProperty(value = "城市名称中文:当单地市时为单个，多地市为多个", required = false)
    private List<String> cityNames;

}
