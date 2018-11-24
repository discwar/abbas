package com.major.service.impl;

import com.major.common.enums.MoneySourceEnum;
import com.major.entity.LogPay;
import com.major.mapper.LogPayMapper;
import com.major.service.ILogPayService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户支付日志记录表 服务实现类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-10-11
 */
@Service
public class LogPayServiceImpl extends ServiceImpl<LogPayMapper, LogPay> implements ILogPayService {


    public Page<Map<String, Object>> selectLogPayPage(Page<Map<String, Object>> page, Integer payWay, Integer billType,Integer transactionType,String flowNo,String orderNo,Integer status,
                                                      String createTimeStart, String createTimeStop,String moneyStart,String moneyStop ) {
        Wrapper ew = new EntityWrapper();
        ew.where("lp.status<> 3 ");
        Map<String,Object> map=new HashMap<>();
        map.put("lp.pay_way",payWay);
        map.put("lp.bill_type",billType);
        map.put("lp.transaction_type",transactionType);
        map.put("lp.flow_no",flowNo);
        map.put("lp.order_no",orderNo);
        map.put("lp.status",status);
        if(null!=createTimeStart) {
            ew.ge("lp.create_time",createTimeStart);
        }
        if(null!=createTimeStop) {
            ew.le("lp.create_time",createTimeStop);
        }
        if(null!=moneyStart) {
            ew.ge("lp.money",moneyStart);
        }
        if(null!=moneyStop) {
            ew.le("lp.money",moneyStop);
        }
        ew.allEq(map);
        ew.orderBy(" lp.create_time DESC ");
        return  page.setRecords(baseMapper.selectLogPayPage(page,ew));
    }

    @Override
    public Map<Integer, BigDecimal> moneySource2Map(String moneySource) {
        if (StringUtils.isNotBlank(moneySource)){
            Map<Integer,BigDecimal> moneySourceMap = new HashMap<>(MoneySourceEnum.values().length);
            String[] strings = StringUtils.split(moneySource,",");
            for (String  moneySourceString : strings){
                String[] split = StringUtils.split(moneySourceString, "-");
                moneySourceMap.put(Integer.valueOf(split[0]),new BigDecimal(split[1]));
            }
            return moneySourceMap;
        }
        return null;
    }
}
