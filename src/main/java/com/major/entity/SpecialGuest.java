package com.major.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 专客表
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-09-03
 */
@TableName("special_guest")
public class SpecialGuest extends Model<SpecialGuest> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 绑定的店铺id
     */
    @TableField("shop_id")
    private Integer shopId;
    /**
     * 绑定店铺名称
     */
    @TableField("shop_name")
    private String shopName;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Integer userId;
    /**
     * 绑定时间
     */
    @TableField("create_time")
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "SpecialGuest{" +
        ", id=" + id +
        ", shopId=" + shopId +
        ", shopName=" + shopName +
        ", userId=" + userId +
        ", createTime=" + createTime +
        "}";
    }
}
