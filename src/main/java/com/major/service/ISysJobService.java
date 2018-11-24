package com.major.service;

import com.major.entity.SysJob;
import com.major.model.request.SysJobRequest;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/18 19:36      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public interface ISysJobService extends IService<SysJob> {

    /**
     * 获取系统任务列表
     * @return
     */
    List<SysJob> getSysJobList();

    boolean addJob(SysJob sysJob);

    /**
     * 添加秒杀开始任务
     * @param params
     * @param date
     * @return
     */
    SysJob getSecKillStartJob(String params, Date date);

    /**
     * 添加秒杀结束任务
     * @param params
     * @param date
     * @return
     */
    SysJob getSecKillEndJob(String params, Date date);

    /**
     * 添加消息通知任务
     * @param params
     * @param date
     * @return
     */
    SysJob getMessageStartJob(String params, Date date);

    /**
     * 定时任务分页
     * @param page
     * @param jobName
     * @param jobGroup
     * @param methodName
     * @param status
     * @param createTimeStart
     * @param createTimeEnd
     * @return
     */
    Page<Map<String, Object>> selectSysJobPage(Page<Map<String, Object>> page, String jobName, String jobGroup, String methodName, Integer status,
                                               String createTimeStart, String createTimeEnd);

    /**
     * 添加定时任务
     * @param sysJobRequest
     * @return
     */
    boolean  addSysJob(SysJobRequest sysJobRequest);

    /**
     * 修改定时任务
     * @param sysJobRequest
     * @param jobId
     * @return
     */
    boolean updateSysJob(SysJobRequest sysJobRequest,Long jobId);

    /**
     * 获取当前
     * @param jobId
     * @return
     */
    Map<String,Object> selectSysJobById(Long jobId);

    /**
     * 立即执行任务
     * @param jobId
     * @return
     */
    boolean runJob(Long jobId);

    /**
     * 暂定任务
     * @param jobId
     * @return
     */
    boolean pauseJob(Long jobId);

    /**
     * 恢复任务
     * @param jobId
     * @return
     */
    boolean resumeJob(Long jobId);

    /**
     * 删除任务
     * @param jobId
     * @return
     */
    boolean deleteScheduleJob(Long jobId);

    /**
     * 根据查询字段获取定时任务
     * @param jobName
     * @param methodName
     * @param params
     * @return
     */
    SysJob selectSysJobByNameAndParams(String jobName,String methodName,String params);
}
