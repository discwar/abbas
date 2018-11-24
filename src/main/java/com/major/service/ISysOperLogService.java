package com.major.service;

import com.major.entity.SysOperLog;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/27 19:59      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public interface ISysOperLogService extends IService<SysOperLog> {

    void insertSysOperLog(SysOperLog sysOperLog);

    /**
     * 操作日志分页
     * @param page
     * @param sysUserId
     * @param searchSysUserName
     * @param title
     * @param action
     * @param channel
     * @param status
     * @return
     */
    Page<Map<String, Object>> selectSysLoginLogPage(Page<Map<String, Object>> page, Long sysUserId,
                                                    String searchSysUserName, String title, String action, String channel, Integer status ,String createTimeStart,String createTimeStop);

    /**
     * 获取单条操作记录
     * @param id
     * @return
     */
    Map<String,Object> selectSysOperLogById(Long id);
}
