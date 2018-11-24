package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/10/11 14:30
 * @Version 1.0
 */
@Getter
@Setter
public class LogWithdrawCashRequest {

    @ApiModelProperty(value = "提现状态", required = true)
    @NotNull(message = "提现状态不能为空")
    private Integer cashStatus;

    @ApiModelProperty(value = "提现备注", required = false)
    private String cashRemarks;
}
