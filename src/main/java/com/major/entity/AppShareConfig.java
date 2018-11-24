package com.major.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * APP分享配置表
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-10-11
 */
@Getter
@Setter
@ToString
@TableName("app_share_config")
public class AppShareConfig extends SuperEntity<AppShareConfig> {


    /**
     * APP分享标题
     */
    @TableField("share_title")
    private String shareTitle;
    /**
     * APP分享描述
     */
    @TableField("share_desc")
    private String shareDesc;
    /**
     * APP分享图片地址
     */
    @TableField("share_image_url")
    private String shareImageUrl;
    /**
     * APP分享链接
     */
    @TableField("share_url")
    private String shareUrl;
    /**
     * APP分享类型：0-邀请注册；
     */
    @TableField("share_type")
    private Integer shareType;

}
