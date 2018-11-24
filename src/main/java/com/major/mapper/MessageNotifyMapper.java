package com.major.mapper;

import com.major.entity.MessageNotify;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 消息通知表 Mapper 接口
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-08-14
 */
public interface MessageNotifyMapper extends BaseMapper<MessageNotify> {


    List<Map<String, Object>> selectMessageNotifyPage(Pagination page, @Param("title") String title,@Param("status") Integer status,
                                                      @Param("sendTimeStart") String sendTimeStart, @Param("sendTimeStop") String sendTimeStop,
                                                      @Param("createTimeStart") String createTimeStart, @Param("createTimeStop") String createTimeStop);

    /**
     * 针对每天定时器使用，查询每天预约发送的消息
     * @return
     */
    @Select("SELECT " +
            "m.title,m.id,m.send_time, m.content,  " +
            "  m.order_send_time,m.user_group_id  " +
            "FROM  " +
            "message_notify m  " +
            "LEFT JOIN user_group  u ON m.user_group_id=u.id " +
            "WHERE " +
            "m.`status`=1  AND m.msg_status=2  " +
            "AND u.`status`=1 " +
            "and to_days(m.order_send_time) = to_days(now());")
    List<MessageNotify> selectMessageNotifyByJob();
}
