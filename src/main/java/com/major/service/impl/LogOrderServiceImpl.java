package com.major.service.impl;

import com.major.entity.LogOrder;
import com.major.mapper.LogOrderMapper;
import com.major.service.ILogOrderService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单日志记录表 服务实现类
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-08-20
 */
@Service
public class LogOrderServiceImpl extends ServiceImpl<LogOrderMapper, LogOrder> implements ILogOrderService {

    @Override
   public List<Map<String,Object>> selectLogOrderByOrderId( Long orderId){
       return baseMapper.selectLogOrderByOrderId(orderId);
   }
}
