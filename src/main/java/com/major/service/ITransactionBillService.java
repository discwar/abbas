package com.major.service;

import com.major.common.enums.BillTypeEnum;
import com.major.common.enums.TransactionTypeEnum;
import com.major.entity.LogPay;
import com.major.entity.Order;
import com.major.entity.TransactionBill;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 交易账单表 服务类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-19
 */
public interface ITransactionBillService extends IService<TransactionBill> {

    /**
     * 钱包分页查询列表
     * 当userId有值时可查询单个用户的钱包情况
     * @param page
     * @param flowNo
     * @param phone
     * @param transactionType
     * @param createTimeStart
     * @param createTimeStop
     * @param orderNo
     * @param shopName
     * @param payWay
     * @param moneyStart
     * @param moneyStop
     * @return
     */
    Page<Map<String, Object>> selectTransactionBillPage(Page<Map<String, Object>> page,Long userId, String flowNo, String phone,
                                                        Integer transactionType, String createTimeStart, String createTimeStop,
                                                        String orderNo, String shopName, Integer payWay,
                                                        String moneyStart, String moneyStop);

    /**
     * 获取交易详情
     * @param billId
     * @return
     */
    Map<String,Object> selectTransactionBillById( Long billId);

    /**
     * 查询总储值额和可提现额-此方法可以用于统计所有用户或者统计当前用户
     * userId-用户id
     * @return
     */
    Map<String,Object> selectSumTotalMoney(Long userId );

    /**
     * 根据用户id获取收益明细分页列表
     * @param page
     * @param flowNo
     * @param userId
     * @param phone
     * @param transactionType
     * @param createTimeStart
     * @param createTimeStop
     * @param moneyStart
     * @param moneyStop
     * @return
     */
    Page<Map<String, Object>> selectTransactionBillByUserIdPage(Page<Map<String, Object>> page, String flowNo,Long userId, String phone,
                                                                Integer transactionType, String createTimeStart, String createTimeStop,
                                                                String moneyStart, String moneyStop);

    /**
     * 新增退款的交易账单
     * @param logPay
     * @param order
     * @param refundType
     * @return
     */
    boolean addRefundTransactionBill(LogPay logPay, Order order, Integer refundType);

    /**
     * 新增邀请成为创客、好友返利交易账单
     * @param userId 邀请人ID
     * @param inviteeId 被邀请人ID
     * @param money 抽成金额
     * @param ratio 抽成比例
     * @param billTypeEnum
     * @param transactionTypeEnum
     * @return
     */
    boolean addRebateTransactionBill(Long userId, Long inviteeId, BigDecimal money, BigDecimal ratio,
                                     BillTypeEnum billTypeEnum, TransactionTypeEnum transactionTypeEnum);

}
