package com.major.mapper;

import com.major.entity.SysJobLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: 定时任务调度日志表 Mapper 接口 </p>
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
public interface SysJobLogMapper extends BaseMapper<SysJobLog> {


    @Select({
            "<script>"+
                    "select id,job_name,method_name,job_message,is_exception,exception_info,create_time from sys_job_log <where> ${ew.sqlSegment} </where> " +
                    "</script>"})
    List<Map<String, Object>> selectSysJobLogPage(Pagination page, @Param("ew") Wrapper ew);

}
