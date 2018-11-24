package com.major.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 创客表
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-19
 */
@Data
public class Maker extends Model<Maker> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * user表ID
     */
    @TableField("user_id")
    private Long userId;
    /**
     * maker_ranks表ID
     */
    @TableField("maker_ranks_id")
    private Integer makerRanksId;
    /**
     * 创客储值金额，提现需看解冻时间
     */
    private BigDecimal money;
    /**
     * 创客收益金额
     */
    private BigDecimal income;
    /**
     * 创客累计收益金额
     */
    @TableField("total_income")
    private BigDecimal totalIncome;
    /**
     * 创客储值金解冻时间
     */
    @TableField("thaw_time")
    private Date thawTime;
    /**
     * 成为创客时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 创客升级时间
     */
    @TableField("upgrade_time")
    private Date upgradeTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
