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
import java.util.Date;

/**
 * <p>
 * 消息信息表
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-10-12
 */
@Getter
@Setter
@ToString
public class Message extends Model<Message> {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    /**
     * 消息标题
     */
    private String title;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 是否已读：0-否；1-是
     */
    private Integer isRead;
    /**
     * message_type表ID
     */
    private Long messageTypeId;

    /**
     * 消息通知时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
