package com.major.service.impl;

import com.major.common.constant.Constants;
import com.major.common.enums.BillTypeEnum;
import com.major.common.enums.PayWayEnum;
import com.major.common.enums.TransactionTypeEnum;
import com.major.entity.MakerIncomeDay;
import com.major.mapper.MakerIncomeDayMapper;
import com.major.service.IMakerIncomeDayService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 创客日收益表 服务实现类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-19
 */
@Service
public class MakerIncomeDayServiceImpl extends ServiceImpl<MakerIncomeDayMapper, MakerIncomeDay> implements IMakerIncomeDayService {

    @Override
    public List<MakerIncomeDay> statisticsMakerIncomeLastDay(){
        List<Integer> billTypeList = new ArrayList<>();
        billTypeList.add(BillTypeEnum.INVITE_MAKER.getValue());
        billTypeList.add(BillTypeEnum.CONSUME_REBATE.getValue());

        List<Integer> transactionTypeList = new ArrayList<>();
        transactionTypeList.add(TransactionTypeEnum.INVITE_MAKER.getValue());
        transactionTypeList.add(TransactionTypeEnum.CONSUME_REBATE.getValue());
        transactionTypeList.add(TransactionTypeEnum.CONSUME2_REBATE.getValue());

        EntityWrapper<MakerIncomeDay> ew = new EntityWrapper<>();
        ew.where("tb.mark={0}", Constants.MARK_INCOME)
                .and("tb.pay_way={0}", PayWayEnum.WALLET.getValue())
                .and("DATE( tb.create_time ) = DATE_SUB( CURDATE( ), INTERVAL 1 DAY ) ")
                .gt("m.id", 0)
                .in("tb.bill_type", billTypeList)
                .in("tb.transaction_type", transactionTypeList)
                .groupBy("maker_id");

        return baseMapper.selectMakerIncomeLastMonth(ew);
    }
}
