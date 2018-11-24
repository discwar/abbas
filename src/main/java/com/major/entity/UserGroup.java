package com.major.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户组表
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-15
 */
@Getter
@Setter
@TableName("user_group")
public class UserGroup extends SuperEntity<UserGroup> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户组名称
     */
    @TableField("group_name")
    private String groupName;

    /**
     * 筛选状态:0-xx刚注册的用户;1-xx消费超过x元的用户;2-xx消费未超过x元的用户;3-24内未活跃的用户;4-全部用户
     */
    private Integer searchType;

    /**
     * 状态：0-删除;1-正常
     */
    @TableLogic
    private Integer status;



}
