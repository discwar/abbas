package com.major.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * <p>
 * 用户优惠卷表
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-08-01
 */
@Getter
@Setter
@ToString
@TableName("user_coupon")
public class UserCoupon extends SuperEntity<UserCoupon> {

    private static final long serialVersionUID = 1L;


    @TableField("user_id")
    private Integer userId;
    @TableField("coupon_id")
    private Integer couponId;
    /**
     * 状态：0-未使用；1-已使用；2-已过期
     */
    private Integer status;

    /**
     * 优惠券获取方式：0-系统发放；1-签到；2-积分兑换；3-店内领取
     */
    @TableField("coupon_source")
    private Integer couponSource;

    /**
     * 系统发放时，用户的过期时间
     */
    private Date deadline;
    /**
     * 领券时间
     */
    @TableField(value="create_time", fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 优惠券使用时间
     */
    @TableField("use_time")
    private Date useTime;



}
