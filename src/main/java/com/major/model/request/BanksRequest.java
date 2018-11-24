package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 银行表
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-09-20
 */
@Getter
@Setter
public class BanksRequest {


    @ApiModelProperty(value = "银行名称", required = true)
    @NotNull(message = "银行名称不能为空")
    private String bankName;

    @ApiModelProperty(value = "首字母", required = false)
    private String bankKey;

    @ApiModelProperty(value = "排序号", required = false)
    private Integer bankSort;

    @ApiModelProperty(value = "银行LOGO", required = false)
    private String logoUrl;


}
