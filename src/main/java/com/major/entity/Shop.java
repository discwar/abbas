package com.major.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * <p>
 * 店铺信息表
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-07
 */
@Getter
@Setter
public class Shop extends SuperEntity<Shop> {

    @TableField("sys_user_id")
    private Integer sysUserId;
    /**
     * 店铺名称
     */
    @TableField("shop_name")
    private String shopName;
    /**
     * 店铺类型：0-水果外卖；1-水果采摘园；2-地方水果农场；3-世界水果外贸站
     1-爱果小店；2-水果店；3-农场；4-采摘园；5-进口商；6-扶贫区
     */
    @TableField("shop_type")
    private Integer shopType;

    @TableField("province")
    private String province;

    @TableField("city")
    private String city;

    @TableField("district")
    private String district;
    /**
     * 店铺详细地址
     */
    @TableField("shop_address")
    private String shopAddress;
    /**
     * 经度
     */
    private BigDecimal longitude;
    /**
     * 纬度
     */
    private BigDecimal latitude;
    /**
     * 店铺简介
     */
    @TableField("shop_desc")
    private String shopDesc;
    /**
     * 店铺视频
     */
    @TableField("shop_video")
    private String shopVideo;
    /**
     * 店铺图标地址
     */
    @TableField("shop_logo")
    private String shopLogo;
    /**
     * 店铺图片地址，多个
     */
    @TableField("img_urls")
    private String imgUrls;
    /**
     * 资质图片地址，多个
     */
    @TableField("license_img_urls")
    private String licenseImgUrls;

    /**
     * 店铺封面地址
     */
    private String coverUrl;

    /**
     * 统一社会信用代码
     */
    @TableField("credit_code")
    private String creditCode;
    /**
     * 食品许可证号
     */
    @TableField("license_code")
    private String licenseCode;
    /**
     * 是否优选商家：0-否；1-是
     */
    @TableField("is_quality_shop")
    private Integer isQualityShop;

    /**
     * 状态：0-删除;1-正常
     */
    @TableLogic
    private Integer status;

    /**
     * 针对农场店铺类型:是否新农场 0-不是 ;1 -是
     */
    private Integer isNewFarm;

    /**
     * 针对农场店铺类型:新农场排序
     */
    private Integer shopSort;

    /**
     * 达达门店编号，针对附近商家
     */
    private  String shopNo;

    /**
     * 是否关联过爱果小店:0-否；1-是；
     */
    private Integer isRelationType;

    /**
     *爱果小店编号
     */
    private String agNumber;

    /**
     *爱果小店关联的水果商家的shop_id
     */
    private Long relationId;

    /**
     * 持卡人
     */
    private String cardholder;
    /**
     * 卡号
     */
    @TableField("card_number")
    private String cardNumber;

    /**
     * banks表ID
     */
    @TableField("banks_id")
    private Integer banksId;

}
