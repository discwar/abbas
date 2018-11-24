package com.major.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.major.common.constant.Constants;
import com.major.common.constant.TaskConstants;
import com.major.common.util.DateUtils;
import com.major.common.util.ScheduleUtils;
import com.major.entity.SysJob;
import com.major.mapper.SysJobMapper;
import com.major.model.request.SysJobRequest;
import com.major.service.ISysJobService;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/18 19:37      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Service
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJob> implements ISysJobService {

    @Autowired
    private Scheduler scheduler;

    /**
     * 项目启动时，初始化定时器
     */
    //@PostConstruct
    public void init() {
        List<SysJob> jobList = this.getSysJobList();

        for (SysJob job : jobList) {
            this.checkJob(job);
        }
    }

    @Override
    public List<SysJob> getSysJobList() {
        Wrapper<SysJob> ew = new EntityWrapper<>();
        ew.and("status= {0}", Constants.STATUS_NORMAL);
        return super.selectList(ew);
    }

    @Override
    public boolean addJob(SysJob sysJob) {
        boolean result = super.insert(sysJob);
        if (result) {
            ScheduleUtils.createScheduleJob(scheduler, sysJob);
        }
        return true;
    }

    @Override
    public SysJob getSecKillStartJob(String params, Date date) {
        SysJob sysJob = new SysJob();
        sysJob.setJobName(TaskConstants.ACTIVITY_JOB_NAME);
        sysJob.setJobGroup(TaskConstants.JOB_GROUP_SEC_KILL_START);
        sysJob.setMethodName(TaskConstants.METHOD_SEC_KILL_START);
        sysJob.setStatus(Constants.STATUS_NORMAL);
        sysJob.setParams(params);
        sysJob.setCronExpression(DateUtils.getCronExpression(date, -5));
        return sysJob;
    }

    @Override
    public SysJob getSecKillEndJob(String params, Date date) {
        SysJob sysJob = new SysJob();
        sysJob.setJobName(TaskConstants.ACTIVITY_JOB_NAME);
        sysJob.setJobGroup(TaskConstants.JOB_GROUP_SEC_KILL_END);
        sysJob.setMethodName(TaskConstants.METHOD_SEC_KILL_END);
        sysJob.setStatus(Constants.STATUS_NORMAL);
        sysJob.setParams(params);
        sysJob.setCronExpression(DateUtils.getCronExpression(date, 0));
        return sysJob;
    }

    @Override
    public SysJob getMessageStartJob(String params, Date date) {
        SysJob sysJob = new SysJob();
        sysJob.setJobName(TaskConstants.MESSAGE_JOB_NAME);
        sysJob.setJobGroup(TaskConstants.JOB_GROUP_MESSAGE);
        sysJob.setMethodName(TaskConstants.METHOD_MESSAGE_START);
        sysJob.setStatus(Constants.STATUS_NORMAL);
        sysJob.setParams(params);
        sysJob.setCronExpression(DateUtils.getCronExpression(date, -5));
        return sysJob;
    }

    @Override
    public Page<Map<String, Object>> selectSysJobPage(Page<Map<String, Object>> page, String jobName, String jobGroup, String methodName,Integer status,
                                                                    String createTimeStart, String createTimeEnd) {
        Wrapper ew = new EntityWrapper();
        ew.where("status <>0 ");
        Map<String,Object> map=new HashMap<>(4);
        map.put("job_name",jobName);
        map.put("job_group",jobGroup);
        map.put("method_name",methodName);
        map.put("status",status);
        ew.allEq(map);
        if(StringUtils.isNotEmpty(createTimeStart)) {
            ew.ge("create_time",createTimeStart);
        }
        if(StringUtils.isNotEmpty(createTimeEnd)) {
            ew.le("create_time",createTimeEnd);
        }
        ew.orderBy("create_time DESC ");
        return page.setRecords(baseMapper.selectSysJobPage(page,ew));
    }

    @Override
    public boolean  addSysJob(SysJobRequest sysJobRequest) {
        SysJob sysJob = new SysJob();
        BeanUtils.copyProperties(sysJobRequest, sysJob);
        sysJob.setCronExpression(sysJobRequest.getQuartzTime());
        sysJob.setStatus(Constants.STATUS_NORMAL);
        return this.addJob(sysJob);
    }

    @Override
    public boolean updateSysJob(SysJobRequest sysJobRequest,Long jobId){
        SysJob sysJob = new SysJob();
        sysJob.setId(jobId);
        BeanUtils.copyProperties(sysJobRequest, sysJob);
        sysJob.setCronExpression(sysJobRequest.getQuartzTime());
        sysJob.setStatus(Constants.STATUS_NORMAL);
        super.updateById(sysJob);
        this.checkJob(sysJob);
        return true;
    }

    @Override
    public Map<String,Object> selectSysJobById(Long jobId){
        Map<String,Object> map=new HashMap<>(1);
        map.put("sys_job_info",baseMapper.selectSysJobById(jobId));
        return map;
    }

    @Override
    public boolean runJob(Long jobId) {
        SysJob sysJob=selectById(jobId);
        if(null==sysJob) {
            return true ;
        }
        sysJob.setStatus(Constants.STATUS_NORMAL);
        sysJob.setCronExpression(DateUtils.getCronExpression(new Date(), 5));
        ScheduleUtils.createScheduleJob(scheduler, sysJob);
        this.checkJob(sysJob);
        return  true;
    }

    @Override
    public boolean pauseJob(Long jobId) {
        SysJob sysJob=selectById(jobId);
        if(null==sysJob) {
            return true ;
        }
        sysJob.setStatus(Constants.STATUS_PAUSE);
        super.updateById(sysJob);
        ScheduleUtils.pauseJob(scheduler,jobId);
        return true;
    }

    @Override
    public boolean resumeJob(Long jobId) {
        SysJob sysJob=selectById(jobId);
        if(null==sysJob) {
            return true ;
        }
        sysJob.setStatus(Constants.STATUS_NORMAL);
        super.updateById(sysJob);
        ScheduleUtils.resumeJob(scheduler,jobId);
        return true;
    }

    @Override
    public boolean deleteScheduleJob(Long jobId) {
        SysJob sysJob=selectById(jobId);
        if(null==sysJob) {
            return true ;
        }
        super.deleteById(jobId);
        ScheduleUtils.deleteScheduleJob(scheduler,jobId);
        return true;
    }

    @Override
    public  SysJob selectSysJobByNameAndParams(String jobName,String methodName,String params){
        return baseMapper.selectSysJobByNameAndParams(jobName,methodName,params);
    }

    private void checkJob(SysJob job){
        CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, job.getId());
        // 如果不存在，则创建
        if (null == cronTrigger  ) {
            ScheduleUtils.createScheduleJob(scheduler, job);
        } else {
            ScheduleUtils.updateScheduleJob(scheduler, job);
        }

    }
}
