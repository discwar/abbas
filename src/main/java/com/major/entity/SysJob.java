package com.major.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/18 19:32      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Getter
@Setter
@ToString
public class SysJob extends SuperEntity<SysJob> {

    private static final long serialVersionUID = 1L;

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * 任务组名
     */
    private String jobGroup;

    /**
     * 任务方法
     */
    private String methodName;

    /**
     * 方法参数
     */
    private String params;

    /**
     * cron执行表达式
     */
    private String cronExpression;

    /**
     * 状态：1-正常；2-暂停
     */
    private Integer status;

    /**
     * 任务类型：0-临时（只跑一次）；1-永久（跑多次）
     */
    private Integer jobType;



}