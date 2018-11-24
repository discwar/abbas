package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SysConfigRequest {

    @ApiModelProperty(value = "APP版本号", required = false)
    private String appVersion;

    @ApiModelProperty(value = "是否强制更新：0-否；1-是", required = false)
    private Integer forceUpdate ;


    @ApiModelProperty(value = "APP版本描述", required = false)
    private String versionDesc;

    @ApiModelProperty(value = "系统类型：0-iOS；1-Android；2-H5", required = false)
    private Integer osType;


    @ApiModelProperty(value = "地址库版本号", required = false)
    private  String addrVersion;


    @ApiModelProperty(value = "密钥截取规则，比如3-5表示第3个字符到第5个字符", required = false)
    private String rule;

    @ApiModelProperty(value = "version_code", required = false)
    private Integer versionCode;
}
