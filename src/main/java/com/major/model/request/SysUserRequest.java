package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 2018/8/1.
 */
@Getter
@Setter
public class SysUserRequest {

    @ApiModelProperty(value = "用户名", required = true)
    @NotNull(message = "username不能为空")
    private String username;
    /** 原密码 */
    @ApiModelProperty(value = "密码", required = true)
    @NotNull(message = "password不能为空")
    private String password;
    /**新密码*/

    private String newpassword;
    private String remark;

    private Long  roleId;
}
