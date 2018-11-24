package com.major.model.response;

import com.major.entity.Shop;
import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author xuquanming
 * @date 2018/9/19 11:28
 */
@Data
public class ShopResponse extends Shop {
    @ApiModelProperty(value = "店铺标签")
    private String label;

    @JSONField(name = "login_name")
    @ApiModelProperty(value = "登陆名")
    private String username;

    private String legalPerson;

    @JSONField(name = "phone")
    private String phone;

    private Long shopOperateId;

    private String bankName;
}
