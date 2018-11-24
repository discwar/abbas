package com.major.model.request;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/8/9 19:27
 * @Version 1.0
 */
@Getter
@Setter
public class GoodsCategoryRequest {

    @ApiModelProperty(value = "店铺ID", required = false)
    private Long shopId;

    @ApiModelProperty(value = "分类名称", required = true)
    @NotNull(message = "分类名称不能为空")
    private String name;

    @ApiModelProperty(value = "排序号", required = true)
    @NotNull(message = "排序不能为空")
    private Integer categorySort;

    @ApiModelProperty(value = "类别描述", required = false)
    private String categoryDesc;


    @ApiModelProperty(value = "针对进出口店铺-所属洲id", required = false)
    private Long continentId;


    @ApiModelProperty(value = "分类图片地址", required = false)
    private String imgUrls;


    @ApiModelProperty(value = "店铺类型:3-农场；4-采摘园；5-进口商；", required = false)
    private Integer shopType;


    @ApiModelProperty(value = "1-普通（店铺类型为：2-水果店；3-农场；4-采摘园；5-进口商,6-爱果小店）；2-礼品果盒；3-当季热卖；4-下季预售；5-1元购", required = false)
    private Integer goodsType;


}
