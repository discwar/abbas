package com.major.mapper;

import com.major.entity.LogOrder;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单日志记录表 Mapper 接口
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-08-20
 */
public interface LogOrderMapper extends BaseMapper<LogOrder> {

    /**
     * 根据订单id查找订单日志记录
     * @param orderId
     * @return
     */
    @Select("SELECT id,order_status_desc,create_time from log_order WHERE order_id=#{orderId} ORDER BY create_time DESC,order_status DESC  ")
    List<Map<String,Object>> selectLogOrderByOrderId(@Param("orderId") Long orderId);

}
