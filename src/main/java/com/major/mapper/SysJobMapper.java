package com.major.mapper;

import com.major.entity.SysJob;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/18 19:35      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public interface SysJobMapper extends BaseMapper<SysJob> {


    @Select({
            "<script>"+
                    "select id,job_name,job_group,method_name,params,cron_expression,status,create_time from sys_job <where> ${ew.sqlSegment} </where> " +
                    "</script>"})
    List<Map<String, Object>> selectSysJobPage(Pagination page, @Param("ew") Wrapper ew);

    /**
     * 获取当前信息
     * @param jobId
     * @return
     */
    @Select("select id,job_name,job_group,method_name,params,cron_expression,status,create_time from sys_job where id=#{jobId}")
    Map<String, Object> selectSysJobById(@Param("jobId") Long jobId);


    /**
     * 根据查询字段获取定时任务
     * @param jobName
     * @param methodName
     * @param params
     * @return
     */
    @Select("select id,job_name,job_group,method_name,params,cron_expression,status,create_time,job_type   " +
            "from sys_job where job_name=#{jobName} and method_name=#{methodName} and  params=#{params}  limit 0,1 ")
    SysJob selectSysJobByNameAndParams(@Param("jobName") String jobName,@Param("methodName") String methodName,@Param("params") String params);

    /**
     * 根据jobName推出是否有数据
     * @param jobName
     * @return
     */
    @Select("select sched_name,job_name  from qrtz_job_details where job_name=#{jobName} ")
    List<Map<String, Object>> selectQrtzJobDetailsByJobName(@Param("jobName") String jobName);


}
