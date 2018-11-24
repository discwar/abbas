package com.major.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 团购方案表
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-31
 */
@TableName("group_buying")
public class GroupBuying extends Model<GroupBuying> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 活动Id
     */
    @TableField("activity_id")
    private Integer activityId;
    /**
     * 几人团
     */
    @TableField("number_limit")
    private Integer numberLimit;
    /**
     * 价格
     */
    private BigDecimal price;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getNumberLimit() {
        return numberLimit;
    }

    public void setNumberLimit(Integer numberLimit) {
        this.numberLimit = numberLimit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "GroupBuying{" +
        ", id=" + id +
        ", activityId=" + activityId +
        ", numberLimit=" + numberLimit +
        ", price=" + price +
        "}";
    }
}
