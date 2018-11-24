package com.major.mapper;

import com.major.entity.LogSms;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-10-11
 */
public interface LogSmsMapper extends BaseMapper<LogSms> {

    @Select({
            "<script>"+
                    "select ls.id,ls.user_id,u.nickname,ls.phone, ls.code,ls.return_code,ls.sms_type,ls.create_time  from log_sms ls  " +
                    " left join user u ON u.id=ls.user_id " +
                    " <where> ${ew.sqlSegment} </where> " +
                    "</script>"})
    List<Map<String, Object>> selectLogSmsPage(Pagination page, @Param("ew") Wrapper ew);

}
