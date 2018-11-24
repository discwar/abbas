package com.major.service;

import com.major.entity.SysUserRole;

/**
 * Created by Administrator on 2018/8/2.
 */
public interface ISysUserRoleService {

    boolean addSysUserRole(SysUserRole sysUserRole);

    boolean deleteSysUserRole(Long userId);

    /**
     * 系统用户绑定权限
     * @param sysUserId
     * @param roleId
     */
    void sysUserBindRole(Long sysUserId,Long roleId);

}
