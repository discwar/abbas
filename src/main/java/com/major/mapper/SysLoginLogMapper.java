package com.major.mapper;

import com.major.entity.SysLoginLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface SysLoginLogMapper extends BaseMapper<SysLoginLog> {

    /**
     * 查询当前用户的登入日志分页
     * @param page
     * @param ew
     * @return
     */
    @Select({
            "<script>"+
                    "select id,login_name,status,ip_address,login_location,browser,os,msg,login_time   " +
                    "  from sys_login_log <where> ${ew.sqlSegment} </where> " +
                    "</script>"})
    List<Map<String, Object>> selectSysLoginLogPageBySysUserId(Pagination page,
                                                               @Param("ew") Wrapper ew);
}