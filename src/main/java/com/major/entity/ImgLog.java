package com.major.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 图片记录表
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-09-13
 */
@TableName("img_log")
@Getter
@Setter
@ToString
public class ImgLog extends Model<ImgLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 操作者ID
     */
    @TableField("oper_id")
    private Long operId;
    /**
     * 操作者类型：0-用户；1-商家；-1-系统
     */
    @TableField("oper_type")
    private Integer operType;
    /**
     * 来自哪张表
     */
    @TableField("from_table")
    private String fromTable;
    /**
     * 该图片信息所在表的ID
     */
    @TableField("from_id")
    private Integer fromId;

    /**
     * 来着表中的字段名
     */
    @TableField("from_field")
    private String fromField;

    /**
     * 图片地址
     */
    @TableField("img_url")
    private String imgUrl;
    /**
     * 图片大小
     */
    @TableField("img_size")
    private String imgSize;
    /**
     * 状态：1-正常；0-删除
     */
    private Integer status;

    @TableField(value="create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @Override
    protected Serializable pkVal() {
        return null;
    }

}
