package com.major.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

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
@TableName("shop_apply")
public class ShopApply extends SuperEntity<ShopApply> {


    @TableField("user_id")
    private Integer userId;
    /**
     * 店铺名称
     */
    @TableField("shop_name")
    private String shopName;
    /**
     * 0-总部自营；1-爱果小店；2-水果店；3-农场；4-采摘园；5-进口商；6-扶贫区
     */
    @TableField("shop_type")
    private Integer shopType;
    /**
     * 店铺详细地址
     */
    @TableField("shop_address")
    private String shopAddress;
    /**
     * 联系人
     */
    private String contacts;
    /**
     * 联系人电话
     */
    private String telephone;
    /**
     * 店铺门脸图
     */
    @TableField("face_img_url")
    private String faceImgUrl;
    /**
     * 店内环境图
     */
    @TableField("environment_img_url")
    private String environmentImgUrl;
    /**
     * 营业执照
     */
    @TableField("business_license")
    private String businessLicense;
    /**
     * 食品经营许可证
     */
    @TableField("food_business_license")
    private String foodBusinessLicense;

    /**
     * 状态：1-提交审核中；2-审核中；3-线上审核通过，线下审核中；4-线上审核失败；5-线下审核失败；6-审核通过
     */
    private Integer status;
    /**
     * 审核不通过备注信息
     */
    private String remark;


}
