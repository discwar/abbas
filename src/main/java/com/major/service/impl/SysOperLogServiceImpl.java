package com.major.service.impl;

import com.major.common.enums.StatusResultEnum;
import com.major.common.exception.AgException;
import com.major.entity.SysOperLog;
import com.major.entity.SysUser;
import com.major.mapper.SysOperLogMapper;
import com.major.service.ISysOperLogService;
import com.major.service.ISysUserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
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
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements ISysOperLogService {

    @Autowired
    private ISysUserService sysUserService;

    @Override
    public void insertSysOperLog(SysOperLog sysOperLog) {
        super.insert(sysOperLog);
    }

    @Override
    public Page<Map<String, Object>> selectSysLoginLogPage(Page<Map<String, Object>> page, Long sysUserId,String searchSysUserName,
                                                           String title,String action,String channel,Integer status,String createTimeStart,String createTimeStop ) {
        Wrapper ew = new EntityWrapper();
        ew.where("status <>2");
        Map<String,Object> map=new HashMap<>();
        map.put("action",action);
        map.put("channel",channel);
        map.put("status",status);

        Map<String, Object> mapRole=sysUserService.selectRoleBySysUserId(sysUserId);
        SysUser sysUser=sysUserService.selectUserById(sysUserId);
        if(mapRole==null || mapRole.size()<=0){
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"该用户没有角色");
        }
        if(StringUtils.isNotEmpty(title)){
            ew.like("title",title);
        }
        //如果当前的账户角色为最高权限时:展示所有用户的操作的日志,也可以搜索查询，当其它角色时查询的仅为自己的用户名称
        if("root".equals(mapRole.get("role_key"))){
            map.put("login_name",searchSysUserName);
        }else{
            map.put("login_name",sysUser.getUsername());
        }
        if(null!=createTimeStart) {
            ew.ge("create_time",createTimeStart);
        }
        if(null!=createTimeStop) {
            ew.le("create_time",createTimeStop);
        }
        ew.allEq(map);
        ew.orderBy(" create_time DESC ");
        return  page.setRecords(baseMapper.selectSysOperLogPage(page,ew));
    }

    @Override
    public Map<String,Object> selectSysOperLogById(Long id){
        Map<String,Object> map=new HashMap<>();
        map.put("sys_oper_log_info",baseMapper.selectSysOperLogById(id));
        return map;
    }
}
