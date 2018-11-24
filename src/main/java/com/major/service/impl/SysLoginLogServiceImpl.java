package com.major.service.impl;

import com.major.entity.SysLoginLog;
import com.major.entity.SysUser;
import com.major.mapper.SysLoginLogMapper;
import com.major.service.ISysLoginLogService;
import com.major.service.ISysUserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/27 20:00      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Service("sysLoginLogServiceImpl")
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements ISysLoginLogService {

    @Autowired
    private ISysUserService sysUserService;

    @Override
    public void insertSysLoginLog(SysLoginLog sysLoginLog) {
        super.insert(sysLoginLog);
    }

    @Override
    public Page<Map<String, Object>> selectSysLoginLogPage(Page<Map<String, Object>> page,Long sysUserId, Integer status,String ipAddress,String createTimeStart,String createTimeStop) {
        SysUser sysUser=sysUserService.selectUserById(sysUserId);
        Wrapper ew = new EntityWrapper();
        ew.where("status <>2");
        Map<String,Object> map=new HashMap<>();
        map.put("status",status);
        map.put("ip_address",ipAddress);
        map.put("login_name",sysUser.getUsername());
        if(null!=createTimeStart) {
            ew.ge("login_time",createTimeStart);
        }
        if(null!=createTimeStop) {
            ew.le("login_time",createTimeStop);
        }
        ew.allEq(map);
        ew.orderBy(" login_time DESC ");
        return  page.setRecords(baseMapper.selectSysLoginLogPageBySysUserId(page,ew));
    }


}
