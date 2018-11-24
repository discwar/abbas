package com.major.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/10/31 14:07
 * @Version 1.0
 */
@Getter
@Setter
public class SysUserRoleResponse {

    private Long sysUserId;

    private String roleKey;

    private String roleName;

    private Long roleId;

}
