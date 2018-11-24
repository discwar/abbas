package com.major.common.enums;

import lombok.Getter;

/**
 * 帐号状态
 */
@Getter
public enum SysUserStatusEnum {

    /**
     * 帐号状态
     */
    OK(1, "正常"),
    DISABLE(2, "禁用"),
    DELETED(0, "删除");

    private Integer code;
    private String desc;

    SysUserStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
