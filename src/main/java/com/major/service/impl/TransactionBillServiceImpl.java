package com.major.service.impl;

import com.major.common.constant.Constants;
import com.major.common.constant.UserConstants;
import com.major.common.enums.BillTypeEnum;
import com.major.common.enums.PayWayEnum;
import com.major.common.enums.TransactionTypeEnum;
import com.major.common.util.CommonUtils;
import com.major.entity.LogPay;
import com.major.entity.Order;
import com.major.entity.TransactionBill;
import com.major.mapper.TransactionBillMapper;
import com.major.service.ITransactionBillService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 交易账单表 服务实现类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-19
 */
@Service
public class TransactionBillServiceImpl extends ServiceImpl<TransactionBillMapper, TransactionBill> implements ITransactionBillService {


    @Override
    public  Page<Map<String, Object>> selectTransactionBillPage(Page<Map<String, Object>> page,Long userId, String flowNo, String phone,
                                                         Integer transactionType, String createTimeStart, String createTimeStop,
                                                         String orderNo,  String shopName, Integer payWay,
                                                         String moneyStart, String moneyStop) {
        return  page.setRecords(baseMapper.selectTransactionBillPage(page,userId,flowNo,phone,transactionType,createTimeStart,
                                             createTimeStop,orderNo,shopName,payWay,moneyStart,moneyStop));
    }

    @Override
    public Map<String,Object> selectTransactionBillById( Long billId) {
        TransactionBill transactionBill=super.selectById(billId);
        //邀请用户成为创客,好友消费返利,二级好友消费返利
        if(TransactionTypeEnum.INVITE_MAKER.getValue().equals(transactionBill.getTransactionType()) ||TransactionTypeEnum.CONSUME_REBATE.getValue().equals(transactionBill.getTransactionType())
                || TransactionTypeEnum.CONSUME2_REBATE.getValue().equals(transactionBill.getTransactionType())) {
           return baseMapper.selectTransactionBillMarkById(billId);
        }
        //针对交易类型为:线上消费,线下支付,退款,钱包储值,提现,创客储值,创客升级---通用
        return baseMapper.selectTransactionBillById(billId);
    }

    @Override
    public Map<String,Object> selectSumTotalMoney(Long userId ) {
        return baseMapper.selectSumTotalMoney(userId);
    }


    @Override
    public  Page<Map<String, Object>> selectTransactionBillByUserIdPage(Page<Map<String, Object>> page, String flowNo,Long userId, String phone,
                                                                Integer transactionType, String createTimeStart, String createTimeStop,
                                                                String moneyStart, String moneyStop) {
        return  page.setRecords(baseMapper.selectTransactionBillByUserIdPage(page,flowNo,userId,phone,transactionType,createTimeStart,createTimeStop,moneyStart,moneyStop));
    }


    @Override
    public boolean addRefundTransactionBill(LogPay logPay, Order order, Integer refundType) {
        TransactionBill bill = new TransactionBill();
       if(Constants.REFUND_TYPE_BF.equals(refundType)){
           bill.setTransactionType(TransactionTypeEnum.REFUND_ALL.getValue());
       }
       if(Constants.REFUND_TYPE_ALL.equals(refundType)){
           bill.setTransactionType(TransactionTypeEnum.REFUND_PART.getValue());
       }
        bill.setUserId(logPay.getUserId());
        bill.setMoney(logPay.getMoney());
        bill.setMark(Constants.MARK_INCOME);
        bill.setPayWay(logPay.getPayWay());
        bill.setBillType(BillTypeEnum.REFUND.getValue());
        bill.setFlowNo(logPay.getFlowNo());
        bill.setOrderNo(order.getOrderNo());
        bill.setRemark(BillTypeEnum.REFUND.getDesc());
        return super.insert(bill);
    }

    @Override
    public boolean addRebateTransactionBill(Long userId, Long inviteeId, BigDecimal money, BigDecimal ratio,
                                            BillTypeEnum billTypeEnum, TransactionTypeEnum transactionTypeEnum) {
        TransactionBill bill = new TransactionBill();
        bill.setUserId(userId);
        bill.setPayWay(PayWayEnum.WALLET.getValue());
        bill.setMoney(money);
        bill.setMark(Constants.MARK_INCOME);
        bill.setBillType(billTypeEnum.getValue());
        bill.setTransactionType(transactionTypeEnum.getValue());
        bill.setFlowNo(CommonUtils.generateFlowNo(PayWayEnum.WALLET.getValue(), billTypeEnum.getValue(),
                transactionTypeEnum.getValue(),null));
        bill.setInviteeId(inviteeId);
        bill.setRebateSource(transactionTypeEnum.getDesc());
        bill.setRebateRatio(ratio);
        bill.setRemark(transactionTypeEnum.getDesc());

        if (billTypeEnum == BillTypeEnum.INVITE_MAKER) {
            bill.setScoreReward(UserConstants.ScoreEnum.INVITE_MAKER.getValue());
        }

        return bill.insert();
    }

}
