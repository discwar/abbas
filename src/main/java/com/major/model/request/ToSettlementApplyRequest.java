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
public class ToSettlementApplyRequest {

    @ApiModelProperty(value = "是否全部结算：0-否；1-是", required = true)
    @NotNull(message = "请确认是否需要全部申请结算")
    private Integer type;

    @ApiModelProperty(value = "选择申请结算的orderId", required = false)
    private List<Long> orderIds;

}
