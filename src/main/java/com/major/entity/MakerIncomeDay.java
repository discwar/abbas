package com.major.entity;

import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 创客日收益表
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-19
 */
@TableName("maker_income_day")
public class MakerIncomeDay extends Model<MakerIncomeDay> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * maker表ID
     */
    @TableField("maker_id")
    private Integer makerId;
    /**
     * 日收益
     */
    private BigDecimal income;

    @TableField("income_time")
    private Date incomeTime;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMakerId() {
        return makerId;
    }

    public void setMakerId(Integer makerId) {
        this.makerId = makerId;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public Date getIncomeTime() {
        return incomeTime;
    }

    public void setIncomeTime(Date incomeTime) {
        this.incomeTime = incomeTime;
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
        return "MakerIncomeDay{" +
        ", id=" + id +
        ", makerId=" + makerId +
        ", income=" + income +
        ", incomeTime=" + incomeTime +
        ", createTime=" + createTime +
        "}";
    }
}
