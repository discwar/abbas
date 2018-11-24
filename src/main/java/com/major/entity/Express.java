package com.major.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 快递表
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-29
 */
@Getter
@Setter
@ToString
public class Express extends Model<Express> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 公司名称
     */
    private String name;
    /**
     * 快递公司代码
     */
    private String com;
    /**
     * 有效标志：1-正常；2-禁用；0-删除
     */
    private Integer status;
    @TableField("create_time")
    private Date createTime;




    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Express{" +
        ", id=" + id +
        ", name=" + name +
        ", com=" + com +
        ", status=" + status +
        ", createTime=" + createTime +
        "}";
    }
}
