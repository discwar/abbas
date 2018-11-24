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
 * 银行表
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-09-20
 */
@Getter
@Setter
@ToString
public class Banks extends Model<Banks> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 银行名称
     */
    @TableField("bank_name")
    private String bankName;
    /**
     * 首字母
     */
    @TableField("bank_key")
    private String bankKey;
    /**
     * 排序号
     */
    @TableField("bank_sort")
    private Integer bankSort;
    /**
     * 银行LOGO
     */
    @TableField("logo_url")
    private String logoUrl;
    /**
     * 状态：1-正常；2-禁用；0-删除
     */
    private Integer status;

    @TableField("create_time")
    private Date createTime;



    @Override
    protected Serializable pkVal() {
        return this.id;
    }


}
