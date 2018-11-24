package com.major.common.constant;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/9/25 11:37      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public interface TaskConstants {

    /**
     * 任务调度参数KEY
     */
    String JOB_PARAM_KEY = "JOB_PARAM_KEY";

    /**
     * 活动任务名称
     */
    String ACTIVITY_JOB_NAME = "activityTask";

    /**
     * 消息通知任务名称
     */
    String MESSAGE_JOB_NAME="messageTask";

    /**
     *消息通知
     */
    String JOB_GROUP_MESSAGE = "消息通知任务";
    String METHOD_MESSAGE_START = "messageTaskStart";

    /**
     * 秒杀活动
     */
    String JOB_GROUP_SEC_KILL_START = "秒杀活动开始任务";
    String JOB_GROUP_SEC_KILL_END = "秒杀活动结束任务";
    String METHOD_SEC_KILL_START = "secKillStart";
    String METHOD_SEC_KILL_END = "secKillEnd";

}
