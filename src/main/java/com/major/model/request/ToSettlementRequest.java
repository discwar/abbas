package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/11/21 14:58
 * @Version 1.0
 */
@Getter
@Setter
public class ToSettlementRequest {

    @ApiModelProperty(value = "选择结算的orderId", required = true)
    @NotNull(message = "请选择结算订单")
    private List<Long> orderIds;

}
