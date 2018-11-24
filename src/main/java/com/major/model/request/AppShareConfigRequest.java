package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/10/11 10:00
 * @Version 1.0
 */
@Getter
@Setter
@ToString
public class AppShareConfigRequest {

    @ApiModelProperty(value = "APP分享标题", required = true)
    @NotNull(message = "APP分享标题不能为空")
    private String shareTitle;

    @ApiModelProperty(value = "APP分享描述", required = true)
    @NotNull(message = "APP分享描述不能为空")
    private String shareDesc;

    @ApiModelProperty(value = "APP分享图片地址", required = true)
    @NotNull(message = "APP分享图片地址不能为空")
    private String shareImageUrl;

    @ApiModelProperty(value = "APP分享链接", required = true)
    @NotNull(message = "APP分享链接不能为空")
    private String shareUrl;

    @ApiModelProperty(value = "APP分享类型：0-邀请注册；1：下载App", required = true)
    @NotNull(message = "APP分享类型不能为空")
    private Integer shareType;

}
