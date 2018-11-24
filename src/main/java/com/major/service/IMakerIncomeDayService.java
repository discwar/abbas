package com.major.service;

import com.major.entity.MakerIncomeDay;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 创客日收益表 服务类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-19
 */
public interface IMakerIncomeDayService extends IService<MakerIncomeDay> {

    /**
     * 统计昨天创客收益
     * @return
     */
    List<MakerIncomeDay> statisticsMakerIncomeLastDay();
}
