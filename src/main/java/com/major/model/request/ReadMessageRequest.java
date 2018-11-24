package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/10/17 16:32
 * @Version 1.0
 */
@Getter
@Setter
public class ReadMessageRequest {

    @ApiModelProperty(value = "消息id", required = true)
    @NotNull(message = "请选择消息")
     private  List<Long> listId ;
}
