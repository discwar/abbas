package com.major.model.request;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
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
public class OrderCommentRequest {

    @ApiModelProperty(value = " 商家回复", required = true)
    @NotNull(message = "shopReply不能为空")
    private String shopReply;

}
