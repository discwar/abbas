package com.major.service;

import com.major.entity.CouPonSendLog;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-06
 */
public interface ICouPonSendLogService extends IService<CouPonSendLog> {

    /**
     *
     * @param page
     * @param couponName
     * @param couponRule
     * @param couponType
     * @param deadlineStartTime
     * @param deadlineStopTime
     * @param createStartTime
     * @param createStopTime
     * @return
     */
    Page<Map<String, Object>> selectSendLogPage(Page<Map<String, Object>> page, String couponName, String couponRule, Integer couponType, String deadlineStartTime, String deadlineStopTime,
    String createStartTime, String createStopTime);

    /**
     * 发送
     * @param coupon_id
     * @param group_id
     * @param usrId
     * @return
     */
    boolean sendLog( Long coupon_id,Long group_id,Long usrId);
}
