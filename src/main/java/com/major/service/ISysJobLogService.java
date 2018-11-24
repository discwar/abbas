package com.major.service;

import com.major.entity.SysJobLog;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>Title: 定时任务调度日志表 服务类 </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/31 14:32      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public interface ISysJobLogService extends IService<SysJobLog> {

    /**
     * 调度日志
     * @param page
     * @param jobName
     * @param isException
     * @param createTimeStart
     * @param createTimeEnd
     * @return
     */
    Page<Map<String, Object>> selectSysJobLogPage(Page<Map<String, Object>> page, String jobName, Integer isException,
                                                  String createTimeStart, String createTimeEnd);
}
