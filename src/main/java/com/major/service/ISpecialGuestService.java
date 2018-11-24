package com.major.service;

import com.major.entity.SpecialGuest;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 专客表 服务类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-09-03
 */
public interface ISpecialGuestService extends IService<SpecialGuest> {


    /**
     * 水果商家查看专客列表
     * @param page
     * @param sysUserId
     * @param createTimeStart
     * @param createTimeStop
     * @param consumptionNumStart
     * @param consumptionNumStop
     * @param consumptionMoneyStart
     * @param consumptionMoneyStop
     * @return
     */
    Page<Map<String, Object>> selectSpecialGuestBySysUserId(Page<Map<String, Object>> page, Long sysUserId, String phone,String createTimeStart, String createTimeStop,
                                                            Integer consumptionNumStart, Integer consumptionNumStop, Integer consumptionMoneyStart, Integer consumptionMoneyStop);

    /**
     * 管理员-商家管理-附近水果店-商家信息-专客管理
     * @param page
     * @param shopId
     * @param phone
     * @param createTimeStart
     * @param createTimeStop
     * @param consumptionNumStart
     * @param consumptionNumStop
     * @param consumptionMoneyStart
     * @param consumptionMoneyStop
     * @return
     */
    Page<Map<String, Object>> selectSpecialGuestByShopId(Page<Map<String, Object>> page, Long shopId, String phone,String createTimeStart, String createTimeStop,
                                                            Integer consumptionNumStart, Integer consumptionNumStop, Integer consumptionMoneyStart, Integer consumptionMoneyStop);

}
