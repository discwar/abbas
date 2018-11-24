package com.major.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>Title: 定时任务调度日志表 </p>
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
@Getter
@Setter
@ToString
@TableName("sys_job_log")
public class SysJobLog extends Model<SysJobLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 任务名称
     */
    @TableField("job_name")
    private String jobName;
    /**
     * 任务方法
     */
    @TableField("method_name")
    private String methodName;
    /**
     * 日志信息
     */
    @TableField("job_message")
    private String jobMessage;
    /**
     * 是否异常：0-否；1-是
     */
    @TableField("is_exception")
    private Integer isException;
    /**
     * 异常信息
     */
    @TableField("exception_info")
    private String exceptionInfo;
    @TableField(value="create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
