package com.major.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-17
 */
@Getter
@Setter
@TableName("order_comment")
public class OrderComment extends Model<OrderComment> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 订单id
     */
    @TableField("order_id")
    private Long orderId;
    /**
     * 评论用户昵称
     */
    @TableField("user_name")
    private String userName;
    /**
     * 评论
     */
    private String comment;
    /**
     * 综合评分
     */
    @TableField("overall_score")
    private Integer overallScore;
    /**
     * 口感评分
     */
    @TableField("taste_score")
    private Integer tasteScore;
    /**
     * 包装评分
     */
    @TableField("package_score")
    private Integer packageScore;
    /**
     * 配送服务评分
     */
    @TableField("transporter_score")
    private Integer transporterScore;
    /**
     * 图片数组
     */
    @TableField("img_urls")
    private String imgUrls;
    /**
     * 商家回复
     */
    @TableField("shop_reply")
    private String shopReply;
    /**
     * 回复时间
     */
    @TableField("reply_time")
    private Date replyTime;
    /**
     * 评论时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 是否有效： 0：无效 1：有效
     */
    private Integer status;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
