package com.major.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * web端消息表
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-10-17
 */
@Getter
@Setter
@ToString
@TableName("web_message")
public class WebMessage extends Model<WebMessage> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 店铺id
     */
    @TableField("shop_id")
    private Long shopId;
    /**
     * 订单Id
     */
    @TableField("order_id")
    private Long orderId;
    /**
     * 0-商家接单通知；1-用户申请取消订单通知；2-用户申请订单退款通知；3-催单通知；4-订单配送异常通知；5-提现申请通知；
     */
    @TableField("message_type")
    private Integer messageType;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 是否已读：0-否；1-是
     */
    @TableField("is_read")
    private Integer isRead;

    @TableField(value="create_time", fill = FieldFill.INSERT)
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
