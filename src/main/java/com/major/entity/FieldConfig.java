package com.major.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;

/**
 * <p>
 * 用户分组字段表
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-16
 */
@TableName("field_config")
public class FieldConfig extends Model<FieldConfig> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 字段说明
     */
    @TableField("field_name")
    private String fieldName;
    /**
     * 字段名
     */
    @TableField("field_code")
    private String fieldCode;
    @TableField("table_name")
    private String tableName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldCode() {
        return fieldCode;
    }

    public void setFieldCode(String fieldCode) {
        this.fieldCode = fieldCode;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "FieldConfig{" +
        ", id=" + id +
        ", fieldName=" + fieldName +
        ", fieldCode=" + fieldCode +
        ", tableName=" + tableName +
        "}";
    }
}
