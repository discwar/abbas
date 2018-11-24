package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/11/16 14:39
 * @Version 1.0
 */
@Getter
@Setter
public class StoredConfigRequest {

    @ApiModelProperty(value = "名称", required = true)
    @NotNull(message = "名称不能为空")
    private String name;


    @ApiModelProperty(value = "金额阀值", required = true)
    @NotNull(message = "金额阀值不能为空")
    private BigDecimal threshold;

    @ApiModelProperty(value = "储值图片地址", required = true)
    @NotNull(message = "储值图片地址不能为空")
    private String imgUrl;

    @ApiModelProperty(value = "优惠券id和数量", required = true)
    @NotNull(message = "请选择优惠券")
    private List<CouponNumberRequest> couponNumberRequestList;

}
