package com.major.service.impl;


import com.major.common.constant.Constants;
import com.major.common.enums.MoneySourceEnum;
import com.major.common.enums.StatusResultEnum;
import com.major.common.exception.AgException;
import com.major.entity.LogPay;
import com.major.entity.User;
import com.major.mapper.UserMapper;
import com.major.model.request.UserSearchRequest;
import com.major.service.ILogPayService;
import com.major.service.IUserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Mouxiaoshi
 * @since 2018-07-16
 */

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private ILogPayService logPayService;

    @Autowired
    public UserServiceImpl(ILogPayService logPayService) {
        this.logPayService = logPayService;
    }

    /**
     * 用户积分操作类型 0-减，1-加
     */
    public static  final Integer TYPE_REDUCE_SCORE=0;
    public   static final Integer TYPE_ADD_SCORE=1;


    @Override
    public List<User> selectUserSearchCreateTime(String startTime) {
        return baseMapper.selectUserSearchCreateTime(startTime);
    }

    @Override
    public List<Map<String, Object>> selectUserOrderByExceedAmount(String realTotalAmount, String startTime, String stopTime) {
        return baseMapper.selectUserOrderByExceedAmount(realTotalAmount, startTime, stopTime);
    }

    @Override
    public List<Map<String, Object>> selectUserOrderByNotExceedAmount(String realTotalAmount, String startTime, String stopTime) {
        return baseMapper.selectUserOrderByNotExceedAmount(realTotalAmount, startTime, stopTime);
    }

    @Override
    public List<User> selectUserByLoginTime() {
        return baseMapper.selectUserByLoginTime();
    }

    @Override
    public List<User> selectAllUser() {
        return baseMapper.selectAllUser();
    }


    @Override
    public Page<Map<String, Object>> selectUserPage(Page<Map<String, Object>> page, UserSearchRequest userSearchRequest) {
        Wrapper ew = new EntityWrapper();
        ew.where("u.`status`={0}", Constants.STATUS_ENABLE);
        //自动判空，当有值时sql含义表示 =
        Map<String,Object> map=new HashMap<>(2);
        map.put("ab.source",userSearchRequest.getSource());
        map.put("mr.ranks_name",userSearchRequest.getRanksName());
        ew.allEq(map);
        if(StringUtils.isNotEmpty(userSearchRequest.getPhone())){
            ew.like("u.phone",userSearchRequest.getPhone());
        }
        if(StringUtils.isNotEmpty(userSearchRequest.getNickname())){
            ew.like("u.nickname",userSearchRequest.getNickname());
        }
        if(null!=userSearchRequest.getCreateTimeStart()) {
            //大于
            ew.ge("u.create_time",userSearchRequest.getCreateTimeStart());
        }
        if(null!=userSearchRequest.getCreateTimeStop()) {
            //小于
            ew.le("u.create_time",userSearchRequest.getCreateTimeStop());
        }
        if(StringUtils.isNotEmpty(userSearchRequest.getLoginTimeStart())) {
            ew.ge("u.login_time",userSearchRequest.getLoginTimeStart());
        }
        if(StringUtils.isNotEmpty(userSearchRequest.getLoginTimeStop())) {
            ew.le("u.login_time",userSearchRequest.getLoginTimeStop());
        }
        if(StringUtils.isNotEmpty(userSearchRequest.getBillTimeStart())) {
            ew.ge("ts.last_bill_time",userSearchRequest.getBillTimeStart());
        }
        if(StringUtils.isNotEmpty(userSearchRequest.getBillTimeStop())) {
            ew.le("ts.last_bill_time",userSearchRequest.getBillTimeStop());
        }
        if(StringUtils.isNotEmpty(userSearchRequest.getTotalMoneyStart())) {
            ew.ge("tb.total_money",userSearchRequest.getTotalMoneyStart());
        }
        if(StringUtils.isNotEmpty(userSearchRequest.getTotalMoneyStop())) {
            ew.le("tb.total_money",userSearchRequest.getTotalMoneyStop());
        }
        if(StringUtils.isNotEmpty(userSearchRequest.getTotalScoreStart())) {
            ew.ge("u.total_score",userSearchRequest.getTotalScoreStart());
        }
        if(StringUtils.isNotEmpty(userSearchRequest.getTotalScoreStop())) {
            ew.le("u.total_score",userSearchRequest.getTotalScoreStop());
        }
        if(null!=userSearchRequest.getIsMaker() && userSearchRequest.getIsMaker()==0){
            ew.isNull("m.id");
        }
        if(null!=userSearchRequest.getIsMaker() && userSearchRequest.getIsMaker()==1){
            ew.isNotNull("m.id");
        }
        if(StringUtils.isNotEmpty(userSearchRequest.getWalletStart())) {
            ew.ge("u.wallet",userSearchRequest.getWalletStart());
        }
        if(StringUtils.isNotEmpty(userSearchRequest.getWalletStop())) {
            ew.le("u.wallet",userSearchRequest.getWalletStop());
        }
        if(StringUtils.isNotEmpty(userSearchRequest.getInvitationNumStart())) {
            ew.ge("mi.invitation_num",userSearchRequest.getInvitationNumStart());
        }
        if(StringUtils.isNotEmpty(userSearchRequest.getInvitationNumStop())) {
            ew.le("mi.invitation_num",userSearchRequest.getInvitationNumStop());
        }
        if(StringUtils.isNotEmpty(userSearchRequest.getBillNumStart())) {
            ew.ge("tb.bill_num",userSearchRequest.getBillNumStart());
        }
        if(StringUtils.isNotEmpty(userSearchRequest.getBillNumStop())) {
            ew.le("tb.bill_num",userSearchRequest.getBillNumStop());
        }
        ew.groupBy("u.id");
        ew.orderBy(" u.create_time DESC ");
        return page.setRecords(baseMapper.selectUserPage(page,ew));
    }

    @Override
    public Map<String, Object> selectUserInfoByUserId(Long userId) {
        return baseMapper.selectUserInfoByUserId(userId);
    }

    @Override
    public boolean refundToWallet(LogPay logPay) {
        //获取订单记录的用户id以及消费金额
        BigDecimal money = logPay.getMoney();
        Long userId = logPay.getUserId();

        //获取订单记录的用户id以及消费金额
        BigDecimal makerStorePay = new BigDecimal("0");
        BigDecimal walletPay ;
        BigDecimal makerIncomePay = new BigDecimal("0");

        String moneySourceString = logPay.getMoneySource();
        if (StringUtils.isNotBlank(moneySourceString)){
            //计算各支付来源的支付记录
            Map<String, BigDecimal> moneySource = this.treatRefundMoney(moneySourceString,money);
            makerStorePay = moneySource.get("maker_store_pay");
            walletPay =  moneySource.get("wallet_pay");
            makerIncomePay = moneySource.get("maker_income_pay");
        }else{
            walletPay =money;
        }


        //更新用户储值钱包
        int affectedResult= baseMapper.refundToWallet(userId,makerStorePay,walletPay,makerIncomePay);
        if (affectedResult == 0) {
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR, "退款到钱包失败");
        }
        return true;
    }

    private Map<String, BigDecimal> treatRefundMoney(String moneySourceString,BigDecimal refundMoney) {
        Map<String,BigDecimal> result = new HashMap<>(3);

        Map<Integer, BigDecimal> moneySourceMap = logPayService.moneySource2Map(moneySourceString);
        BigDecimal makerStorePay = moneySourceMap.get(MoneySourceEnum.MAKER_STORED.getValue());
        BigDecimal walletPay = moneySourceMap.get(MoneySourceEnum.WALLET.getValue());
        BigDecimal makerIncomePay = moneySourceMap.get(MoneySourceEnum.MAKER_INCOME.getValue());
        BigDecimal totalPay = makerStorePay.add(walletPay).add(makerIncomePay);
        if (totalPay.compareTo(refundMoney) == 1){
            BigDecimal zero = new BigDecimal("0");
            BigDecimal cost = totalPay.subtract(refundMoney);
            //创客储值
            if (makerStorePay.compareTo(zero) == 1) {
                //创客储值支付的金额大于0
                if (makerStorePay.compareTo(cost) > -1) {
                    //创客储值支付的金额大于扣除费用
                    makerStorePay = makerStorePay.subtract(cost);
                    cost = zero;
                } else {
                    //创客储值支付的金额小于扣除费用
                    cost = cost.subtract(makerStorePay);
                    makerStorePay = zero;
                }
            }

            //钱包支付
            if (walletPay.compareTo(zero) == 1 && cost.compareTo(zero) == 1) {
                //钱包支付的金额大于0
                if (walletPay.compareTo(cost) > -1) {
                    //钱包支付的金额大于扣除费用
                    walletPay = walletPay.subtract(cost);
                    cost = zero;
                } else {
                    //钱包支付的金额小于扣除费用
                    cost = cost.subtract(walletPay);
                    walletPay = zero;
                }
            }

            //创客收益支付
            if (makerIncomePay.compareTo(zero) == 1 && cost.compareTo(zero) == 1) {
                //创客收益支付的金额大于0
                makerIncomePay = makerIncomePay.subtract(cost);
            }
        }

        result.put("maker_store_pay",makerStorePay);
        result.put("wallet_pay",walletPay);
        result.put("maker_income_pay",makerIncomePay);
        return result;
    }

    @Override
    public boolean updateUserScore(Long userId, Integer addScore,Integer type) {
        User user = super.selectById(userId);
        return this.updateUserScore(user, addScore,type);
    }

    @Override
    public boolean updateUserScore(User user, Integer addScore,Integer type) {
        Integer score;
        Integer totalScore;
        if(TYPE_ADD_SCORE.equals(type)){
            score=  ObjectUtils.defaultIfNull(user.getScore(), Integer.valueOf(0)) + addScore;
            totalScore=ObjectUtils.defaultIfNull(user.getTotalScore(), Integer.valueOf(0)) + addScore;
        }else {
            score=  ObjectUtils.defaultIfNull(user.getScore(), Integer.valueOf(0)) - addScore;
            totalScore=ObjectUtils.defaultIfNull(user.getTotalScore(), Integer.valueOf(0)) - addScore;
            if(score<=0){
                score=0;
            }
            if(totalScore<=0){
                totalScore=0;
            }
        }
        user.setScore(score);
        user.setTotalScore(totalScore);
        return super.updateById(user);
    }

    @Override
    public User selectUserById(Long userId){
        return selectById(userId);
    }
}
