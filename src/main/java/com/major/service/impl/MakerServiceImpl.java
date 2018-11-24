package com.major.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.major.common.constant.Constants;
import com.major.common.constant.MakerConstants;
import com.major.common.enums.BillTypeEnum;
import com.major.common.enums.MoneySourceEnum;
import com.major.common.enums.TransactionTypeEnum;
import com.major.common.util.DateUtils;
import com.major.entity.*;
import com.major.mapper.MakerMapper;
import com.major.model.NewMaker;
import com.major.model.bo.RebateOrderBO;
import com.major.service.ILogPayService;
import com.major.service.IMakerService;
import com.major.service.ITransactionBillService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 创客表 服务实现类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-19
 */
@Service
public class MakerServiceImpl extends ServiceImpl<MakerMapper, Maker> implements IMakerService {

    @Autowired
    private ITransactionBillService transactionBillService;

    @Autowired
    private ILogPayService logPayService;

    @Override
    public Page<Map<String, Object>> selectMakerPage(Page<Map<String, Object>> page, String phone,Integer ranksId, String createTimeStart, String createTimeStop,
                                                     String inviteNumberStart, String inviteNumberStop, String totalIncomeStart, String totalIncomeStop,
                                                     String sevenDayIncomeStart, String sevenDayIncomeStop, String incomeStart, String incomeStop, String makerNumberStart,
                                                     String makerNumberStop) {
        Wrapper ew = new EntityWrapper();
        ew.where("1=1");
        //自动判空，当有值时sql含义表示 =
        Map<String,Object> map=new HashMap<>();
        map.put("mr.id",ranksId);
        ew.allEq(map);
        if(StringUtils.isNotEmpty(phone)){
            ew.like("a.phone",phone);
        }
        if(StringUtils.isNotEmpty(createTimeStart)) {
            //大于
            ew.ge("m.create_time",createTimeStart);
        }
        if(StringUtils.isNotEmpty(createTimeStop)) {
            //小于
            ew.le("m.create_time",createTimeStop);
        }
        if(StringUtils.isNotEmpty(inviteNumberStart)) {
            ew.ge("a.invite_number",inviteNumberStart);
        }
        if(StringUtils.isNotEmpty(inviteNumberStop)) {
            ew.le("a.invite_number",inviteNumberStop);
        }
        if(StringUtils.isNotEmpty(totalIncomeStart)) {
            ew.ge("m.total_income",totalIncomeStart);
        }
        if(StringUtils.isNotEmpty(totalIncomeStop)) {
            ew.le("m.total_income",totalIncomeStop);
        }
        if(StringUtils.isNotEmpty(sevenDayIncomeStart)) {
            ew.ge("mid.seven_day_income",sevenDayIncomeStart);
        }
        if(StringUtils.isNotEmpty(sevenDayIncomeStop)) {
            ew.le("mid.seven_day_income",sevenDayIncomeStop);
        }
        if(StringUtils.isNotEmpty(incomeStart)) {
            ew.ge("mim.income",incomeStart);
        }
        if(StringUtils.isNotEmpty(incomeStop)) {
            ew.le("mim.income",incomeStop);
        }
        if(StringUtils.isNotEmpty(makerNumberStart)) {
            ew.ge("b.maker_number",makerNumberStart);
        }
        if(StringUtils.isNotEmpty(makerNumberStop)) {
            ew.le("b.maker_number",makerNumberStop);
        }
        ew.groupBy("m.id");
        ew.orderBy(" m.create_time DESC ");
        return page.setRecords(baseMapper.selectMakerPage(page,ew));
    }

    @Override
    public Map<String, Object> selectMakerById(Long userId, Long makerId) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("maker_user_info", baseMapper.selectMakerUserInfoByUserId(userId));
        map.put("maker_income_info", baseMapper.selectMakerIncomeById(makerId));
        return map;
    }

    @Override
    public Page<Map<String, Object>> selectMakerInvitationByUserId(Page<Map<String, Object>> page, Long userId, String phone, Integer isMaker, String createTimeStart, String createTimeStop) {
        return page.setRecords(baseMapper.selectMakerInvitationByUserId(page, userId, phone, isMaker, createTimeStart, createTimeStop));
    }

    @Override
    public List<NewMaker> selectBeMakerYesterday() {
        return baseMapper.selectBeMakerYesterday(TransactionTypeEnum.MAKER_STORE.getValue());
    }

    @Override
    public Map<String, Object> selectMakerByChildUserId(Long userId) {
        return baseMapper.selectMakerByChildUserId(userId,Constants.MARKER_RIGHTS_STORE);
    }

    @Override
    public List<Map<String, Object>> selectSuccessInviteMaker(List<NewMaker> makers) {
        // 所有被邀请人的邀请人创客集合
        List<Map<String, Object>> successInviteMakers = new ArrayList<>();

        if (makers.size() > 0) {
            for (NewMaker maker : makers) {
                Map<String, Object> successInviteMaker = this.selectMakerByChildUserId(maker.getUserId());
                if (successInviteMaker == null) {
                    continue;
                }
                successInviteMaker.put("invitee_id", maker.getUserId());
                successInviteMaker.put("store_money",maker.getStoreMoney());
                successInviteMakers.add(successInviteMaker);
            }
        }

        return successInviteMakers;
    }

    @Override
    public Set<Long> getOrdersRewardMakers(List<RebateOrderBO> orders) {


        Set<Long> ids = new HashSet<>();
        for (RebateOrderBO order:orders){
            //使用创客储值金支付的订单将不参与返现
            checkOrderMoney(order);
            if (order.getRealTotalAmount().compareTo(new BigDecimal("0")) == 0){
                continue;
            }

            //消费者
            User user = new User();
            user.setId(order.getUserId());
            //找到上级以及上上级的邀请人及其可获得的抽成比例
            //一级邀请人
            Map<String,Object> oneLevelList = this.foundOneLevelInviter(user.getId(),order.getOrderTime());
            this.makerIncomeLog(oneLevelList, user, order.getRealTotalAmount(), TransactionTypeEnum.CONSUME_REBATE,ids);

            //二级邀请人(一级可以为普通)
            Map<String,Object> twoLevelList = this.foundTwoLevelInviter(user.getId(),order.getOrderTime());
            this.makerIncomeLog(twoLevelList, user, order.getRealTotalAmount(), TransactionTypeEnum.CONSUME2_REBATE,ids);

            //order设置已抽成,后期去除邮费
            if (  oneLevelList==null  && twoLevelList== null ){
                continue;
            }
            Order updateOrder = new Order();
            updateOrder.setId(order.getId());
            updateOrder.setIsRebate(Constants.IS_REBATE);
            updateOrder.updateById();
        }
        return ids;
    }

    /**
     * 使用创客储值金支付的订单将不参与返现
     * @param order
     */
    private void checkOrderMoney(RebateOrderBO order) {
        //解析各支付来源金额
        Map<Integer,BigDecimal> moneySourceMap =  logPayService.moneySource2Map(order.getMoneySource());
        if ( null !=  moneySourceMap){
            BigDecimal makerStoredPay = moneySourceMap.get(MoneySourceEnum.MAKER_STORED.getValue());
            order.setRealTotalAmount(order.getRealTotalAmount().subtract(makerStoredPay));
        }
    }

    private void makerIncomeLog(Map<String, Object> oneLevel, User user, BigDecimal realTotalAmount, TransactionTypeEnum transactionTypeEnum, Set<Long> ids) {
        if(null != oneLevel){
            Long userId = Long.valueOf((oneLevel.get("user_id").toString()));
            Maker makerCheck = this.selectMakerByUserId(userId);

            BigDecimal ratio = (BigDecimal) oneLevel.get("rebate_ratio");
            //应抽成的金额
            BigDecimal addIncome = realTotalAmount.multiply((BigDecimal) oneLevel.get("rebate_ratio")).divide(new BigDecimal(100));
            this.updateMakerIncome(makerCheck, addIncome);
            transactionBillService.addRebateTransactionBill(userId, user.getId(), addIncome, ratio, BillTypeEnum.CONSUME_REBATE, transactionTypeEnum);
            ids.add(userId);
        }
    }

    /**
     * 二级邀请人
     * @param userId
     * @param orderTime
     * @return
     */
    private Map<String,Object> foundTwoLevelInviter(Long userId, Date orderTime) {

        //一级邀请人，可不是创客
        Long oneLevelUserId = baseMapper.getInvitationUserId(userId);

        if (null != oneLevelUserId){
            //获取二级邀请人（金牌创客）
            EntityWrapper ew = new EntityWrapper();
            ew.where("u.status = {0}",Constants.STATUS_ENABLE)
                    .and("mi.user_id = {0}",oneLevelUserId)
                    .and("mra.ranks_sort = {0}", MakerConstants.MakerRankEnum.GOLD.getValue())
                    .and("mri.rights_key = {0}",MakerConstants.MakerRightEnum.CONSUME2.getKey())
                    .andNew("(m.create_time <= {0}) and ((m.upgrade_time is null) or (m.upgrade_time <=  {0}))",orderTime)
                    .last("limit 1");
            return  baseMapper.foundTwoLevelInviter(ew);
        }

        return null;
    }

    /**
     * 查找上级邀请人（创客）的信息，7天前的等级
     * @param userId 被邀请人id
     * @param orderTime
     * @return
     */
    private Map<String,Object> foundOneLevelInviter(Long userId, Date orderTime) {

        Wrapper ew = new EntityWrapper();
        ew.where("u.status = {0}",Constants.STATUS_ENABLE)
                .and("m.create_time <= {0}",orderTime)
                .and("mi.user_id = {0}",userId)
            .and("mri.rights_key = {0}",MakerConstants.MakerRightEnum.CONSUME);
        return   baseMapper.foundInviter(orderTime,TransactionTypeEnum.MAKER_UPGRADE.getValue(),ew);
    }

    @Override
    public boolean updateMakerIncome(Maker maker, BigDecimal addIncome) {
        BigDecimal income = ObjectUtils.defaultIfNull(maker.getIncome(), BigDecimal.valueOf(0)).add(addIncome) ;
        BigDecimal totalIncome = ObjectUtils.defaultIfNull(maker.getTotalIncome(), BigDecimal.valueOf(0)).add(addIncome) ;
        maker.setIncome(income);
        maker.setTotalIncome(totalIncome);
        return super.updateById(maker);
    }

    @Override
    public Map<String, Object> getMakerByUserId(Long userId) {
        return baseMapper.selectMakerByUserId(userId);
    }

    @Override
    public Maker selectMakerByUserId(Long userId) {
        EntityWrapper<Maker> ew = new EntityWrapper<>();
        ew.and("user_id = {0}",userId);
         return  super.selectOne(ew);
    }

    @Override
    public List<Map<String, Object>> getStoredValueMakerYesterday() {
        Wrapper ew = new EntityWrapper();
        ew.and("tb.bill_type = {0}",BillTypeEnum.WALLET_STORE.getValue())
                .and("tb.transaction_type = {0}",TransactionTypeEnum.WALLET_STORE.getValue())
                .and("DATE(tb.create_time) = DATE_SUB(CURDATE(), INTERVAL 1 DAY)")
                .and("m.create_time < tb.create_time");
        return baseMapper.getStoredValueMakerYesterday(ew);
    }

    @Override
    public List<UserCoupon> issueCoupons(List<Map<String, Object>> makerList, Set<Long> ids) {
        List<UserCoupon> userCouponList = new ArrayList<>();

        //根据储值送好礼表配置来赠送优惠券
        for (Map<String,Object> info:makerList) {
            Integer storedConfigId = (Integer) info.get("stored_config_id");
            Integer transactionBillId = (Integer) info.get("transaction_bill_id");
            Long userId = ((Integer) info.get("user_id")).longValue();
            if ( null != storedConfigId){
                ids.add(userId);

                StoredConfig storedConfig = new StoredConfig();
                storedConfig.setId(storedConfigId.longValue());
                storedConfig = storedConfig.selectById();

                //账单更新储值赠送字段
                TransactionBill bill = new TransactionBill();
                bill.setId(transactionBillId.longValue());
                bill.setStoredGive(storedConfig.getStoredGive());
                bill.updateById();

                //优惠券Id
                List<String> couponIds = Arrays.asList (storedConfig.getCouponIds().split(","));

                for (String couponId : couponIds){
                    UserCoupon userCoupon = new UserCoupon();
                    userCoupon.setCouponSource(Constants.COUPON_SOUTCE_SYSTEM);
                    userCoupon.setDeadline(DateUtils.getFutureDate(Constants.MAKER_STORAGE_COUPON_PAST));
                    userCoupon.setCreateTime(DateUtils.getNowDate());
                    userCoupon.setUserId(userId.intValue());
                    userCoupon.setCouponId(Integer.valueOf(couponId));
                    userCouponList.add(userCoupon);
                }
            }
        }
        return userCouponList;
    }
}
