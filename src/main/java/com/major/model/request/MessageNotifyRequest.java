package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * <p>
 * 消息通知表
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-08-14
 */
@Getter
@Setter
public class MessageNotifyRequest {


    @ApiModelProperty(value = "消息标题", required = true)
    @NotNull(message = "title不能为空")
    private String title;

    @ApiModelProperty(value = "消息内容", required = true)
    @NotNull(message = "content不能为空")
    private String content;


    @ApiModelProperty(value = "user_group表ID", required = true)
    @NotNull(message = "群组不能为空")
    private Long userGroupId;

    @ApiModelProperty(value = "预约发送时间,格式为：yyyy-MM-dd HH:mm:ss", required = false)
    private Date orderSendTime;

    @ApiModelProperty(value = "是否立即发送:0-否;1-是", required = false)
    private Integer isSendNow;

}
