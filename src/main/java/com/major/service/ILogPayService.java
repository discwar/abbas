package com.major.service;

import com.major.entity.LogPay;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 用户支付日志记录表 服务类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-10-11
 */
public interface ILogPayService extends IService<LogPay> {

    /**
     * 支付日志分页
     * @param page
     * @param payWay
     * @param transactionType
     * @param flowNo
     * @param orderNo
     * @param status
     * @param createTimeStart
     * @param createTimeStop
     * @param moneyStart
     * @param moneyStop
     * @return
     */
    Page<Map<String, Object>> selectLogPayPage(Page<Map<String, Object>> page, Integer payWay,Integer billType, Integer transactionType, String flowNo, String orderNo, Integer status,
                                               String createTimeStart, String createTimeStop, String moneyStart, String moneyStop );

    /**
     * 支付记录 moneySource转Map
     * @param moneySource
     * @return
     */
    Map<Integer, BigDecimal> moneySource2Map(String moneySource);
}
