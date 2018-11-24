package com.major.job.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: zhangzhenliang
 * @Date: 2018/11/1 15:38
 * @Version 1.0
 */
@Component
@Slf4j
public class TextTask {

    public String textJob(){
        log.info("每天一点半定时任务进来啦。。。。。。");
      return   "每天一点半定时任务进来啦。。。。。。666";
    }

    public String textJob2(){
        log.info("每天两点半定时任务进来啦。。。。。。");
        return   "每天两点半定时任务进来啦。。。。。。333";
    }

}
