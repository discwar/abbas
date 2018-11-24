package com.major.service;

import com.major.entity.LogSms;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-10-11
 */
public interface ILogSmsService extends IService<LogSms> {

    /**
     * 短信日志分页
     * @param page
     * @param smsType
     * @param createTimeStart
     * @param createTimeStop
     * @return
     */
    Page<Map<String, Object>> selectLogSmsPage(Page<Map<String, Object>> page, Integer smsType, String createTimeStart, String createTimeStop );

}
