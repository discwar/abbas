package com.major.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-06
 */
@Getter
@Setter
@TableName("coupon_send_log")
public class CouPonSendLog extends SuperEntity<CouPonSendLog> {

    private static final long serialVersionUID = 1L;

    /**
     * 优惠券Id
     */
    @TableField("coupon_id")
    private Integer couponId;
    /**
     * 发送群组Id
     */
    @TableField("user_groups_id")
    private Integer userGroupsId;

    /**
     * 发送人数
     */
    @TableField("send_number")
    private String sendNumber;
    /**
     * 创建者ID
     */
    private Integer creatorId;
    /**
     * 状态：1-正常；0-删除
     */
    @TableLogic
    private Integer status;
    /**
     * 创建时间
     */
    @TableField(value="create_time", fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;



}
