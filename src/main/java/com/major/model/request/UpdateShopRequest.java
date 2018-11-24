package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/9/14 16:49
 * @Version 1.0
 */
@Getter
@Setter
public class UpdateShopRequest {

    @ApiModelProperty(value = "店铺名称", required = true)
    @NotBlank(message = "shopName不能为空")
    private String shopName;

    @ApiModelProperty(value = "店铺类型:0-总部自营；1-爱果小店；2-水果店；3-农场；4-采摘园；5-进口商；6-扶贫区", required = true)
    @NotNull(message = "shopType不能为空")
    private Integer shopType;

    @ApiModelProperty(value = "省份", required = false)
    private String province;

    @ApiModelProperty(value = "城市", required = true)
    @NotNull(message = "城市不能为空")
    private String city;

    @ApiModelProperty(value = "区域", required = true)
    @NotNull(message = "区域不能为空")
    private String district;

    @ApiModelProperty(value = "店铺详细地址", required = true)
    @NotNull(message = "店铺详细地址不能为空")
    private String shopAddress;

    @ApiModelProperty(value = "经度", required = true)
    @NotNull(message = "longitude不能为空")
    private BigDecimal longitude;

    @ApiModelProperty(value = "纬度", required = true)
    @NotNull(message = "latitude不能为空")
    private BigDecimal latitude;

    @ApiModelProperty(value = "店铺简介", required = false)
    private String shopDesc;

    @ApiModelProperty(value = "店铺视频", required = false)
    private String shopVideo;

    @ApiModelProperty(value = "店铺图标地址", required = true)
    @NotNull(message = "店铺图标地址不能为空")
    private String shopLogo;

    @ApiModelProperty(value = "店铺图片地址，多个", required = true)
    @NotNull(message = "店铺图片地址不能为空")
    private List<String> imgUrls;


    @ApiModelProperty(value = "资质图片地址，多", required = true)
    @NotNull(message = "资质图片不能为空")
    private List<String> licenseImgUrls;

    @ApiModelProperty(value = "店铺封面地址", required = false)
    private String coverUrl;

    @ApiModelProperty(value = "统一社会信用代码", required = false)
    private String creditCode;

    @ApiModelProperty(value = "食品许可证号", required = false)
    private String licenseCode;

    @ApiModelProperty(value = "是否优选商家：0-否；1-是", required = false)
    private Integer isQualityShop;


    @ApiModelProperty(value = "店铺标签", required = false)
    private String label;

    @ApiModelProperty(value = "针对农场店铺和进口店铺 类型:是否新农场/优质店铺 0-不是 ;1 -是", required = false)
    private Integer isNewFarm;


    @ApiModelProperty(value = "针对农场店铺类型:新农场排序", required = false)
    private Integer shopSort;

    @ApiModelProperty(value = "针对爱果小店:0-关联店铺；1-不关联", required = false)
    private Integer isRelation;

    @ApiModelProperty(value = "针对爱果小店：添加时带上关联店铺Id", required = false)
    private Long shopId;

}
