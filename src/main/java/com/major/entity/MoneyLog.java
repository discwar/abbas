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
 * 商家资金流水表
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-11-22
 */
@Getter
@Setter
@TableName("money_log")
public class MoneyLog extends Model<MoneyLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 店铺用户Id
     * 考虑到有关联爱果小店的订单，所以保存时存入用户id
     */
    @TableField("sys_user_id")
    private Long sysUserId;

    /**
     * 流水来源：0-订单结算；1-提现申请；
     */
    private Integer source;
    /**
     * 流水标识：1-支出；2-收入
     */
    private Integer mark;
    /**
     * 金额
     */
    private BigDecimal money;
    /**
     * 资金流水备注
     */
    private String remarks;
    /**
     * 创建时间
     */
    @TableField(value="create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
