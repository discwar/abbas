package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/11/22 18:15
 * @Version 1.0
 */
@Getter
@Setter
public class ApplayWithdrawalRequest {

    @ApiModelProperty(value = "提现金额", required = true)
    @NotNull(message = "提现金额不能为空")
    private BigDecimal withdrawalMoney;

}
