package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/9/4 17:09
 * @Version 1.0
 */
@Getter
@Setter
public class SendLogRequest {

    @ApiModelProperty(value = "优惠券Id", required = true)
    @NotNull(message = "优惠券Id不能为空")
     private Long couponId;

    @ApiModelProperty(value = "发送群组Id", required = true)
    @NotNull(message = "发送群组Id不能为空")
     private  Long groupId;
}
