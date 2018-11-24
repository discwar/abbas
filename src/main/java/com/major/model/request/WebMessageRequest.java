package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/10/17 11:30
 * @Version 1.0
 */
@Getter
@Setter
public class WebMessageRequest {

    @ApiModelProperty(value = "订单编号", required = true)
    @NotNull(message = "订单编号不能为空")
    private String orderNo;

    @ApiModelProperty(value = "通知类型：0-商家接单通知；1-用户申请取消订单通知；2-用户申请订单退款通知；3-催单通知；", required = true)
    @NotNull(message = "通知类型不能为空")
    private Integer messageType;

}
