package com.major.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 用户分组字段关联表
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-16
 */
@Getter
@Setter
@TableName("user_group_field")
public class UserGroupField extends Model<UserGroupField> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("user_group_id")
    private Integer userGroupId;
    @TableField("field_config_id")
    private Integer fieldConfigId;
    @TableField("field_value")
    private String fieldValue;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }


}
