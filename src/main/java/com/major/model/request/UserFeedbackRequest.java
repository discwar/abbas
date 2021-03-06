package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 用户反馈表
 * </p>
 *
 */
@Getter
@Setter

public class UserFeedbackRequest{

    @ApiModelProperty(value = "答复内容", required = true)
    @NotNull(message = "replyContent不能为空")
    private String replyContent;

}
