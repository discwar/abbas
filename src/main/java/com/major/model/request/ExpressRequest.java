package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 快递表
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-29
 */
@Getter
@Setter
public class ExpressRequest {


    @ApiModelProperty(value = "公司名称", required = true)
    @NotNull(message = "公司名称不能为空")
    private String name;

    @ApiModelProperty(value = "快递公司代码", required = true)
    @NotNull(message = "快递公司代码不能为空")
    private String com;

}
