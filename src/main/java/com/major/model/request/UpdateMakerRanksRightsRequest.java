package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/11/20 14:45
 * @Version 1.0
 */
@Getter
@Setter
public class UpdateMakerRanksRightsRequest {

    @ApiModelProperty(value = "创客专属权益描述", required = true)
    @NotNull(message = "创客专属权益描述不能为空")
    private String rightsDesc;

    @ApiModelProperty(value = "返现比例", required = true)
    @NotNull(message = "返现比例不能为空")
    private BigDecimal rebateRatio;
}
