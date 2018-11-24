package com.major.controller;

import com.major.common.constant.VersionConstants;
import com.major.common.enums.StatusResultEnum;
import com.major.common.util.SnowflakeIdUtil;
import com.major.model.response.BaseResponse;
import com.major.model.response.ResultResponse;
import com.major.job.queue.DelayQueue;
import com.major.job.queue.DelayQueueJob;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

/**
 * 延迟队列模块
 * @author LianGuoQing
 */
@Api(description="延迟队列模块")
@RestController
@RequestMapping(value = VersionConstants.V1 + "/queue")
public class DelayQueueController {

    private static SnowflakeIdUtil idUtil = new SnowflakeIdUtil(1,1);

    @ApiOperation("添加延迟任务")
    @PostMapping("/push")
    public BaseResponse push(@ApiParam(name = "topic", value = "任务类型", required = true) @RequestParam("topic") String topic,
                             @ApiParam(name = "delayTime", value = "延迟任务执行时间（13位时间时间戳）", required = true) @RequestParam("delayTime") Long delayTime,
                             @ApiParam(name = "ttrTime", value = "延迟任务执行超时时间（单位：秒）", required = true) @RequestParam("ttrTime") Long ttrTime,
                             @ApiParam(name = "message", value = "消息内容", required = true) @RequestParam("message") String message) {
        DelayQueueJob delayQueueJob = new DelayQueueJob();
        delayQueueJob.setTopic(topic);
        delayQueueJob.setDelayTime(delayTime);
        delayQueueJob.setMessage(message);
        delayQueueJob.setTtrTime(ttrTime);
        delayQueueJob.setId(idUtil.nextId());
        DelayQueue.push(delayQueueJob);
        return ResultResponse.success(StatusResultEnum.SUCCESS);
    }

    @ApiOperation("轮询队列获取任务")
    @GetMapping("/pop/{topic}")
    public BaseResponse pop(@PathVariable("topic") String topic) {
        DelayQueueJob delayQueueJob = DelayQueue.pop(topic);
        return ResultResponse.success(StatusResultEnum.SUCCESS, delayQueueJob);
    }

    @ApiOperation("完成任务")
    @PostMapping("/finish")
    public BaseResponse finish(@ApiParam(name = "id", value = "任务id", required = true) @RequestParam("id") Long id) {
        DelayQueue.finish(id);
        return ResultResponse.success(StatusResultEnum.SUCCESS);
    }
}
