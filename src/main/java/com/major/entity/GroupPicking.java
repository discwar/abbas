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
 * 组团采摘表
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-09-05
 */
@Getter
@Setter
@ToString
@TableName("group_picking")
public class GroupPicking extends Model<GroupPicking> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 联系人
     */
    private String contacts;
    /**
     * 联系人手机号
     */
    @TableField("contacts_phone")
    private String contactsPhone;
    /**
     * 出团时间
     */
    @TableField("out_time")
    private Date outTime;
    /**
     * 原出团时间
     */
    @TableField("original_out_time")
    private Date originalOutTime;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }


}
