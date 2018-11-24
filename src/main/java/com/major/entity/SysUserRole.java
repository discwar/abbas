package com.major.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/8/2.
 */
@Getter
@Setter
@ToString
@TableName("sys_user_role")
public class SysUserRole extends Model<SysUserRole> {

    @TableField("sys_user_id")
    private Integer sysUserId;

    @TableField("role_id")
    private Integer roleId;

    @Override
    protected Serializable pkVal() {
        return null;
    }
}
