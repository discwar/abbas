package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/11/22 14:59
 * @Version 1.0
 */
@Getter
@Setter
public class ToSettlementRequest {

    @ApiModelProperty(value = "结算备注", required = false)
    private String remarks;
}
