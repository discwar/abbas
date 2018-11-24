package com.major.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@TableName("sys_config")
public class SysConfig extends SuperEntity<SysConfig> {

    /**
     *APP版本号
     */
    private String appVersion;
    /**
     *是否强制更新：0-否；1-是
     */
    private Integer forceUpdate ;

    /**
     *APP版本描述
     */
    private String versionDesc;

    /**
     *A系统类型：0-iOS；1-Android；2-H5
     */
    private Integer osType;

    /**
     *area地址库版本号
     */
    private  String addrVersion;

    /**
     *密钥截取规则，比如3-5表示第3个字符到第5个字符
     */
    private String rule;

    /**
     *version_code
     */
    private Integer versionCode;


}
