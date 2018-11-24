package com.major.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户关联银行卡表
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-10-24
 */
@TableName("user_bank_card")
@Getter
@Setter
@ToString
public class UserBankCard extends Model<UserBankCard> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 所属对象类型：0-用户；1-商家
     */
    @TableField("target_type")
    private Integer targetType;
    /**
     * 所属对象ID；如果是用户，则对应user表ID
     */
    @TableField("target_id")
    private Integer targetId;
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
     * 银行名称
     */
    @TableField("bank_name")
    private String bankName;
    /**
     * banks表ID
     */
    @TableField("banks_id")
    private Integer banksId;
    /**
     * 银行卡类型：0-储蓄卡
     */
    @TableField("card_type")
    private Integer cardType;
    /**
     * 状态：1-正常；2-禁用；0-删除
     */
    private Integer status;
    @TableField("create_time")
    private Date createTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
