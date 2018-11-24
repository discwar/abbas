package com.major.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 创客月度收益表
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-08-17
 */
@Getter
@Setter
@ToString
public class MakerIncomeMonth extends Model<MakerIncomeMonth> {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Long userId;
    /**
     * maker表ID
     */
    private Long makerId;
    /**
     * 创客月收益
     */
    private BigDecimal income;
    /**
     * 收益日期
     */
    private Date incomeTime;
    /**
     * 统计时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
