package com.major.service;

import com.major.entity.SysLoginLog;
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
public interface ISysLoginLogService extends IService<SysLoginLog> {

    void insertSysLoginLog(SysLoginLog sysLoginLog);

    /**
     * 查询当前用户的登入日志分页
     * @param page
     * @param sysUserId
     * @return
     */
    Page<Map<String, Object>> selectSysLoginLogPage(Page<Map<String, Object>> page, Long sysUserId, Integer status,String ipAddress,String createTimeStart,String createTimeStop);
}
