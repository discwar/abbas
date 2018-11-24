package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 商家操作 用户申请取消订单的审批请求体
 * @Author: zhangzhenliang
 * @Date: 2018/10/16 17:29
 * @Version 1.0
 */
@Getter
@Setter
public class ApplyCancelRequest {

    @ApiModelProperty(value = "申请取消订单状态：0-待审批；1-审批通过；2-审批不通过", required = true)
    @NotNull(message = "状态不能为空")
    private Integer applyCancelStatus;

    @ApiModelProperty(value = "申请取消订单审批不通过理由", required = false)
    private String applyCancelReply;

}
