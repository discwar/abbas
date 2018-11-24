package com.major.config;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * OSSConfig
 *
 * @author LianGuoQing
 */
@Component
@ToString
@Getter
@Setter
public class OSSConfig {

    @Value("${ali.access-key-id}")
    @JSONField(name = "access_key_id")
    private String accessKeyId;

    @Value("${ali.access-key-secret}")
    @JSONField(name = "access_key_secret")
    private String accessKeySecret;

    @Value("${ali.oss.endPoint}")
    @JSONField(name = "end_point")
    private String endPoint;

    @Value("${ali.oss.agAppFileList}")
    @JSONField(name = "ag_app_file_list")
    private String agAppFileList;

    @Value("${ali.oss.agManageFileList}")
    @JSONField(name = "ag_manage_file_list")
    private String agManageFileList;

    @Value("${ali.oss.agDefaultFileList}")
    @JSONField(name = "ag_default_file_list")
    private String agDefaultFileList;

    @Value("${ali.oss.agWebFileList}")
    @JSONField(name = "ag_web_file_list")
    private String agWebFileList;

    @Value("${ali.oss.bucketName}")
    @JSONField(name = "bucket_name")
    private String bucketName;

    @Value("${ali.oss.cdn}")
    private String cdn;
}
