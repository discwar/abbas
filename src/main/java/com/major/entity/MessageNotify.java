package com.major.entity;

import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 消息通知表
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-08-14
 */
@Getter
@Setter
@ToString
@TableName("message_notify")
public class MessageNotify extends Model<MessageNotify> {

    private static final long serialVersionUID = 1L;

    @TableId(value="id", type= IdType.AUTO)
    private Long id;
    /**
     * 消息标题
     */
    private String title;
    /**
     * 消息内容
     */
    private String content;
    /**
     * user_group表ID
     */
    @TableField("user_group_id")
    private Long userGroupId;
    /**
     * 预约发送时间
     */
    @TableField("order_send_time")
    private Date orderSendTime;
    /**
     * 实际发送时间
     */
    @TableField("send_time")
    private Date sendTime;
    /**
     * 消息状态：1-已发送；2-预定中;3-发送失败
     */
    @TableField("msg_status")
    private Integer msgStatus;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
