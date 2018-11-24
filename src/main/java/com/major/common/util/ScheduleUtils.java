package com.major.common.util;

import com.major.common.constant.Constants;
import com.major.common.constant.TaskConstants;
import com.major.entity.SysJob;
import com.major.job.ScheduleJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

/**
 * 定时任务工具类
 *
 * @author LianGuoQing
 */
@Slf4j
public class ScheduleUtils {

    public final static String JOB_NAME_PREFIX = "TASK_";

    /**
     * 获取触发器key
     */
    public static TriggerKey getTriggerKey(Long jobId) {
        return TriggerKey.triggerKey(JOB_NAME_PREFIX + jobId);
    }

    /**
     * 获取job任务Key
     */
    public static JobKey getJobKey(Long jobId) {
        return JobKey.jobKey(JOB_NAME_PREFIX + jobId);
    }

    /**
     * 获取表达式触发器
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, Long jobId) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 创建定时任务
     */
    public static void createScheduleJob(Scheduler scheduler, SysJob job) {
        try {
            // 构建job信息
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJob.class).withIdentity(getJobKey(job.getId())).build();

            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

            // 按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(job.getId())).withSchedule(scheduleBuilder).build();

            // 放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(TaskConstants.JOB_PARAM_KEY, job);

            scheduler.scheduleJob(jobDetail, trigger);

            // 暂停任务
            if (Constants.STATUS_PAUSE.equals(job.getStatus())) {
                pauseJob(scheduler, job.getId());
            }
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 更新定时任务
     */
    public static void updateScheduleJob(Scheduler scheduler, SysJob job) {
        try {
            TriggerKey triggerKey = getTriggerKey(job.getId());

            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());

            CronTrigger trigger = getCronTrigger(scheduler, job.getId());

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 参数
            trigger.getJobDataMap().put(TaskConstants.JOB_PARAM_KEY, job);

            scheduler.rescheduleJob(triggerKey, trigger);

            // 暂停任务
            if (Constants.STATUS_PAUSE.equals(job.getStatus())) {
                pauseJob(scheduler, job.getId());
            }
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 立即执行任务
     */
    public static void run(Scheduler scheduler, SysJob job) {
        try {
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(TaskConstants.JOB_PARAM_KEY, job);
            scheduler.triggerJob(getJobKey(job.getId()), dataMap);
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 暂停任务
     */
    public static void pauseJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.pauseJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 恢复任务
     */
    public static void resumeJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.resumeJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 删除定时任务
     */
    public static void deleteScheduleJob(Scheduler scheduler, Long jobId) {
        try {
            scheduler.deleteJob(getJobKey(jobId));
        } catch (SchedulerException e) {
            log.error(e.getMessage());
        }
    }

}
