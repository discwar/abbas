package com.major.mapper;

import com.major.entity.WebMessage;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * web端消息表 Mapper 接口
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-10-17
 */
public interface WebMessageMapper extends BaseMapper<WebMessage> {

    /**
     * 列表
     * @param isRead
     * @return
     */
    @Select("select id ,shop_id,order_id,message_type,content,is_read,create_time from web_message where is_read=#{isRead} and shop_id=#{shopId} order by create_time desc ")
    List<Map<String, Object>> selectWebMessagePage(@Param("shopId") Long shopId,@Param("isRead") Integer isRead);

}
