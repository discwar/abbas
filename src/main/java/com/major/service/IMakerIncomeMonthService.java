package com.major.service;

import com.major.entity.MakerIncomeMonth;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 创客月度收益表 服务类
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-08-17
 */
public interface IMakerIncomeMonthService extends IService<MakerIncomeMonth> {

    /**
     * 统计上个月创客收益
     * @return
     */
    List<MakerIncomeMonth> statisticsMakerIncomeLastMonth();

    /**
     * 将上个月创客收益放入redis中
     * @param lastYearMonth
     * @param makerIncomeMonthList
     */
    void pushMakerIncomeMonthToRedis(String lastYearMonth, List<MakerIncomeMonth> makerIncomeMonthList);

}
