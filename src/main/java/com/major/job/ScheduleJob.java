package com.major.job;

import com.major.common.constant.Constants;
import com.major.common.constant.TaskConstants;
import com.major.common.util.SpringUtils;
import com.major.entity.SysJob;
import com.major.entity.SysJobLog;
import com.major.service.ISysJobLogService;
import com.major.service.ISysJobService;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.concurrent.*;

/**
 * 定时任务
 *
 * @author LianGuoQing
 */
@Slf4j
public class ScheduleJob extends QuartzJobBean {

    ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("ag-pool-%d").build();
    ExecutorService service = new ThreadPoolExecutor(5, 200,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        SysJob job = (SysJob) context.getMergedJobDataMap().get(TaskConstants.JOB_PARAM_KEY);

        SysJobLog sysJobLog = new SysJobLog();
        sysJobLog.setJobName(job.getJobGroup() + "_" + job.getJobName());
        sysJobLog.setMethodName(job.getMethodName());

        long startTime = System.currentTimeMillis();

        try {
            // 执行任务
            log.info("任务开始执行 - ID: {} 名称：{} 方法：{}", job.getId(), job.getJobName(), job.getMethodName());
            ScheduleCallable task=new ScheduleCallable(job.getJobName(), job.getMethodName(), job.getParams());
            Future<?> future = service.submit(task);

            long times = System.currentTimeMillis() - startTime;
            sysJobLog.setIsException(Integer.valueOf(Constants.SUCCESS));
            sysJobLog.setJobMessage("sys_job表ID: " + job.getId() +",任务执行情况："+future.get()+ "，总共耗时：" + times + "毫秒");

            ISysJobService sysJobService = SpringUtils.getBean(ISysJobService.class);
            if(Constants.SYS_JOB_TYPE_TEMPORARY.equals(job.getJobType())){
                job.setStatus(Constants.STATUS_PAUSE);
            }
            sysJobService.updateById(job);

            log.info("任务执行结束 - ID: {} 名称：{} 耗时：{} 毫秒", job.getId(), job.getJobName(), times);
        } catch (Exception e) {
            log.info("任务执行失败 - ID: {} 名称：{} 方法：{}", job.getId(), job.getJobName(), job.getMethodName());
            log.error("任务执行异常  - ：", e);

            long times = System.currentTimeMillis() - startTime;
            sysJobLog.setJobMessage("sys_job表ID: " + job.getId() + "，总共耗时：" + times + "毫秒");

            sysJobLog.setIsException(Integer.valueOf(Constants.FAIL));
            sysJobLog.setExceptionInfo(e.toString());
        } finally {
            ISysJobLogService sysJobLogService = SpringUtils.getBean(ISysJobLogService.class);
            sysJobLogService.insert(sysJobLog);
        }
    }

}
