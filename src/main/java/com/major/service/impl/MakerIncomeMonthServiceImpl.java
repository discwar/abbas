package com.major.service.impl;

import com.major.common.constant.Constants;
import com.major.common.constant.RedisConstants;
import com.major.common.enums.BillTypeEnum;
import com.major.common.enums.PayWayEnum;
import com.major.common.enums.TransactionTypeEnum;
import com.major.common.util.DateUtils;
import com.major.entity.MakerIncomeMonth;
import com.major.mapper.MakerIncomeMonthMapper;
import com.major.service.IMakerIncomeMonthService;
import com.major.service.IMakerService;
import com.major.service.RedisService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 创客月度收益表 服务实现类
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-08-17
 */
@Service
public class MakerIncomeMonthServiceImpl extends ServiceImpl<MakerIncomeMonthMapper, MakerIncomeMonth> implements IMakerIncomeMonthService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private IMakerService makerService;

    @Override
    public List<MakerIncomeMonth> statisticsMakerIncomeLastMonth() {
        List<Integer> billTypeList = new ArrayList<>();
        billTypeList.add(BillTypeEnum.INVITE_MAKER.getValue());
        billTypeList.add(BillTypeEnum.CONSUME_REBATE.getValue());

        List<Integer> transactionTypeList = new ArrayList<>();
        transactionTypeList.add(TransactionTypeEnum.INVITE_MAKER.getValue());
        transactionTypeList.add(TransactionTypeEnum.CONSUME_REBATE.getValue());
        transactionTypeList.add(TransactionTypeEnum.CONSUME2_REBATE.getValue());

        EntityWrapper<MakerIncomeMonth> ew = new EntityWrapper<>();
        ew.where("tb.mark={0}", Constants.MARK_INCOME)
                .and("tb.pay_way={0}", PayWayEnum.WALLET.getValue())
                .and("DATE_FORMAT(tb.create_time, '%Y-%m') = DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 MONTH), '%Y-%m')")
                .in("tb.bill_type", billTypeList)
                .in("tb.transaction_type", transactionTypeList)
                .groupBy("maker_id");

        return baseMapper.selectMakerIncomeLastMonth(ew);
    }

    @Override
    public void pushMakerIncomeMonthToRedis(String lastYearMonth, List<MakerIncomeMonth> makerIncomeMonthList) {
        String key = RedisConstants.MAKER_RANKING_LIST_PREFIX + lastYearMonth;
        // 距离下个月月初凌晨0点的秒数
        long timeout = DateUtils.getRemainSecondsOneMonth(DateUtils.getNowDate());

        for (MakerIncomeMonth makerIncomeMonth : makerIncomeMonthList) {
            Long userId = makerIncomeMonth.getUserId();
            String makerInfoKey = RedisConstants.MAKER_INFO_PREFIX + userId;
            Map<String, Object> makerInfoMap = makerService.getMakerByUserId(userId);
            redisService.pullAll(makerInfoKey, makerInfoMap, timeout);

            redisService.zAdd(key, userId, makerIncomeMonth.getIncome().doubleValue(), timeout);
        }


    }

}
