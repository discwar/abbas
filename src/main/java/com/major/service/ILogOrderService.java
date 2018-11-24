package com.major.service;

import com.major.entity.LogOrder;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单日志记录表 服务类
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-08-20
 */
public interface ILogOrderService extends IService<LogOrder> {

    /**
     * 根据订单id查找订单日志记录
     * @param orderId
     * @return
     */
    List<Map<String,Object>> selectLogOrderByOrderId(Long orderId);

}
