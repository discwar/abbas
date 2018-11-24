package com.major.entity;

import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 店铺评分记录表
 * </p>
 *
 * @author XuQuanMing
 * @since 2018-10-17
 */
@TableName("log_shop_score")
@Getter
@Setter
public class LogShopScore extends Model<LogShopScore> {

    private static final long serialVersionUID = 1L;

    @TableId(value="id", type= IdType.AUTO)
    private Long id;
    /**
     * 店铺id
     */
    @TableField("shop_id")
    private Long shopId;
    /**
     * 评分
     */
    @TableField("score")
    private Double score;
    /**
     * 创建时间
     */

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    @TableField("month_order_count")
    private  Long monthOrderCount;

    @TableField("status")
    private Integer status;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
