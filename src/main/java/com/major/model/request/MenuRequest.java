package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/8/9 21:02
 * @Version 1.0
 */
@Getter
@Setter
public class MenuRequest {

    /** 菜单名称 */
    @ApiModelProperty(value = "菜单名称", required = true)
    @NotNull(message = "菜单名称不能为空")
    private String menuName;
    /** 父菜单ID */
    @ApiModelProperty(value = "父菜单ID", required = true)
    @NotNull(message = "parentId不能为空")
    private Long parentId;
    /** 菜单排序 */
    @ApiModelProperty(value = "菜单排序", required = true)
    @NotNull(message = "菜单排序不能为空")
    private Integer menuSort;

    /**
     * 类型：M-目录；C-菜单；F-按钮
     */
    @ApiModelProperty(value = "菜单类型", required = true)
    @NotNull(message = "菜单类型不能为空")
    private String menuType;

    /** 菜单URL */
    private String url;

    /** 权限字符串 */
    @ApiModelProperty(value = "权限字符串", required = true)
    @NotNull(message = "权限字符串不能为空")
    private String perms;

    /** 菜单图标 */
    private String icon;

    /**
     * 菜单状态：0-显示；1-隐藏
     */
    @ApiModelProperty(value = "菜单状态：0-显示；1-隐藏", required = true)
    @NotNull(message = "菜单状态不能为空")
    private String visible;

}
