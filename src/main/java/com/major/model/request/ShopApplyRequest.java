package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 店铺申请表
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-24
 */
@Getter
@Setter

public class ShopApplyRequest   {

    @ApiModelProperty(value = "状态：1-提交审核中；2-审核中；3-线上审核通过，线下审核中；4-线上审核失败；5-线下审核失败；6-审核通过", required = true)
    @NotNull(message = "status不能为空")
    private Integer status;

    @ApiModelProperty(value = "审核不通过备注信息", required = false)
    private String remark;


}
