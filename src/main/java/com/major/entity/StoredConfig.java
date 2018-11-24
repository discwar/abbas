package com.major.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * <p>
 * 储值送好礼配置表
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-11-15
 */
@Getter
@Setter
@ToString
@TableName("stored_config")
public class StoredConfig extends SuperEntity<StoredConfig> {


    private static final long serialVersionUID = 1L;

    /**
     * 名称（后台填充）
     */
    private String name;
    /**
     * 储值赠送描述
     */
    @TableField("stored_give")
    private String storedGive;
    /**
     * 阀值，成为该等级创客需要的金额条件
     */
    private BigDecimal threshold;
    /**
     * 储值图片地址
     */
    @TableField("img_url")
    private String imgUrl;
    /**
     * coupon优惠券表ID
     */
    @TableField("coupon_ids")
    private String couponIds;
}
