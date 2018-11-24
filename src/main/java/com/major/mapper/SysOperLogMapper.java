package com.major.mapper;

import com.major.entity.SysOperLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface SysOperLogMapper extends BaseMapper<SysOperLog> {


    @Select({
            "<script>"+
                    "select id,title,action,method,channel,login_name,oper_url,oper_ip,oper_location,oper_param,status,create_time,error_msg  from sys_oper_log <where> ${ew.sqlSegment} </where> " +
                    "</script>"})
    List<Map<String, Object>> selectSysOperLogPage(Pagination page, @Param("ew") Wrapper ew);

    @Select("select id,title,action,method,channel,login_name,oper_url,oper_ip,oper_location,oper_param,status,create_time,error_msg  from sys_oper_log where id=#{id}")
    SysOperLog selectSysOperLogById(@Param("id") Long id);

}