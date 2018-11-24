package com.major.service.impl;

import com.major.entity.SpecialGuest;
import com.major.mapper.SpecialGuestMapper;
import com.major.service.ISpecialGuestService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 专客表 服务实现类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-09-03
 */
@Service
public class SpecialGuestServiceImpl extends ServiceImpl<SpecialGuestMapper, SpecialGuest> implements ISpecialGuestService {

     @Override
    public Page<Map<String, Object>> selectSpecialGuestBySysUserId(Page<Map<String, Object>> page, Long sysUserId,String phone, String createTimeStart, String createTimeStop,
                                                                   Integer consumptionNumStart, Integer consumptionNumStop, Integer consumptionMoneyStart, Integer consumptionMoneyStop){
        return page.setRecords(baseMapper.selectSpecialGuestBySysUserId(page,sysUserId,phone,createTimeStart,createTimeStop,consumptionNumStart,consumptionNumStop,
                consumptionMoneyStart,consumptionMoneyStop));
    }

    @Override
    public Page<Map<String, Object>> selectSpecialGuestByShopId(Page<Map<String, Object>> page, Long shopId,String phone, String createTimeStart, String createTimeStop,
                                                                   Integer consumptionNumStart, Integer consumptionNumStop, Integer consumptionMoneyStart, Integer consumptionMoneyStop){
        return page.setRecords(baseMapper.selectSpecialGuestByShopId(page,shopId,phone,createTimeStart,createTimeStop,consumptionNumStart,consumptionNumStop,
                consumptionMoneyStart,consumptionMoneyStop));
    }
}
