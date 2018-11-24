package com.major.service.impl;

import com.major.entity.LogSms;
import com.major.mapper.LogSmsMapper;
import com.major.service.ILogSmsService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-10-11
 */
@Service
public class LogSmsServiceImpl extends ServiceImpl<LogSmsMapper, LogSms> implements ILogSmsService {

    @Override
    public Page<Map<String, Object>> selectLogSmsPage(Page<Map<String, Object>> page, Integer smsType, String createTimeStart, String createTimeStop ) {
        Wrapper ew = new EntityWrapper();
        ew.where("1=1");
        Map<String,Object> map=new HashMap<>();
        map.put("ls.sms_type",smsType);
        if(null!=createTimeStart) {
            ew.ge("ls.create_time",createTimeStart);
        }
        if(null!=createTimeStop) {
            ew.le("ls.create_time",createTimeStop);
        }
        ew.allEq(map);
        ew.orderBy(" ls.create_time DESC ");
        return  page.setRecords(baseMapper.selectLogSmsPage(page,ew));
    }
}
