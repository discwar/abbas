package com.major.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 订单结算表
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-11-20
 */
@TableName("order_settlement")
@Getter
@Setter
public class OrderSettlement extends Model<OrderSettlement> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 结算类型：0-手工结算；1-即时结算
     */
    @TableField("settlement_type")
    private Integer settlementType;
    /**
     * 结算金额
     */
    @TableField("settlement_money")
    private BigDecimal settlementMoney;
    /**
     * 结算佣金
     */
    private BigDecimal commission;
    /**
     * 应返还给商家的金额
     */
    @TableField("back_money")
    private BigDecimal backMoney;
    /**
     * 结算状态：0-未结算；1-已结算
     */
    private Integer status;
    /**
     * 结算处理时间
     */
    @TableField("settlement_time")
    private Date settlementTime;
    /**
     * 结算备注
     */
    private String remarks;
    /**
     * 申请结算时间
     */
    @TableField(value="create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }


}
