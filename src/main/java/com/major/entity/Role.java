package com.major.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/18 19:32      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Getter
@Setter
@ToString
@TableName("role")
public class Role extends SuperEntity<Role> {

    @TableId(value="id", type= IdType.AUTO)
    /** 角色ID */
    private Long id;
    /** 角色名 */
    private String roleName;
    /** 角色权限 */
    private String roleKey;
    /** 创建者 */
    private String creator;
    /**修改者*/
    private String modifier;
    /**状态0删除;1启用*/
    @TableLogic
    private Integer status;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
