package com.major.service.impl;

import com.major.entity.SysUserRole;
import com.major.mapper.SysUserRoleMapper;
import com.major.service.ISysUserRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/2.
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole>
        implements ISysUserRoleService {

    @Override
    public boolean addSysUserRole(SysUserRole sysUserRole){
        return   super.insert(sysUserRole);
    }

    @Override
    public boolean deleteSysUserRole(Long userId){
        Map<String, Object> map = new HashMap<>(1);
        map.put("sys_user_id", userId);
        return   super.deleteByMap(map);
    }

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public void sysUserBindRole(Long sysUserId, Long roleId) {
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setSysUserId(sysUserId.intValue());
        sysUserRole.setRoleId(roleId.intValue());
        sysUserRole.insert();
    }
}
