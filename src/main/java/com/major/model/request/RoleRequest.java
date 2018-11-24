package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Administrator on 2018/8/1.
 */
@Getter
@Setter

public class RoleRequest {
    /** 角色名 */
    @ApiModelProperty(value = "角色名", required = true)
    @NotNull(message = "roleName不能为空")
    private String roleName;

    /** 角色权限 */
    @ApiModelProperty(value = "角色权限", required = true)
    @NotEmpty(message = "roleKey不能为空")
    private String roleKey;

    /**菜单列表*/
    @ApiModelProperty(value = "菜单列表", required = true)
    @NotEmpty(message = "menuId不能为空")
    private List<String> menuId;
}
