package com.major.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>
 * 用户反馈表
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-08-17
 */
@Getter
@Setter
@ToString
public class UserFeedback extends SuperEntity<UserFeedback> {

    private static final long serialVersionUID = 1L;

    private Long userId;
    /**
     * 用户反馈内容
     */
    private String content;
    /**
     * 反馈图片地址，多个用英文逗号隔开
     */
    @TableField("pictures_paths")
    private String picturesPaths;
    /**
     * 状态：0-未回复；1-已回复
     */
    private Integer status;
    /**
     * 答复内容
     */
    @TableField("reply_content")
    private String replyContent;

}
