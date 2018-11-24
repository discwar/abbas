package com.major.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

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
public class SysJobRequest {

    @ApiModelProperty(value = "任务名称（任务类名，首字母小写）", required = true)
    @NotNull(message = "任务名称不能为空")
    private String jobName;

    @ApiModelProperty(value = "任务组名（中文名）", required = true)
    @NotNull(message = "任务组名不能为空")
    private String jobGroup;

    @ApiModelProperty(value = "任务方法", required = true)
    @NotNull(message = "任务方法不能为空")
    private String methodName;

    @ApiModelProperty(value = "方法参数", required = false)
    private String params;

    @ApiModelProperty(value = "定时任务触发时间", required = true)
    @NotNull(message = "定时任务触发时间不能为空")
    private String quartzTime;


    @ApiModelProperty(value = "任务类型：0-临时（只跑一次）；1-永久（跑多次）", required = true)
    @NotNull(message = "任务类型不能为空")
    private Integer jobType;
}