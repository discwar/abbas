package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/11/20 14:36
 * @Version 1.0
 */
@Getter
@Setter
public class MakerRanksRightsRequest {

    @ApiModelProperty(value = "maker_ranks_id", required = true)
    @NotNull(message = "请选择创客等级")
    private Integer makerRanksId;

    @ApiModelProperty(value = "maker_ranks_id", required = true)
    @NotNull(message = "请选择专属权益")
    private Integer makerRightsId;

    @ApiModelProperty(value = "创客专属权益描述", required = true)
    @NotNull(message = "创客专属权益描述不能为空")
    private String rightsDesc;

    @ApiModelProperty(value = "返现比例", required = true)
    @NotNull(message = "返现比例不能为空")
    private BigDecimal rebateRatio;
}
