package com.major.service;

import com.major.entity.UserCoupon;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户优惠卷表 服务类
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-08-01
 */
public interface IUserCouponService extends IService<UserCoupon> {

    /**
     * 通过优惠券状态和过期时间获取用户优惠劵信息列表
     * @param status
     * @return
     */
    List<UserCoupon> findUserCouponList(Integer status);

    /**
     * 添加
     * @param userCoupon
     * @return
     */
    boolean addUserCoupon(UserCoupon userCoupon);

    /**
     * 添加多条
     * @param userCouponList
     * @return
     */
    boolean addUserCouponList(List<UserCoupon> userCouponList);


    /**
     * 为了限制一个用户只有拥有一张未使用的优惠券
     * @param userId
     * @param couponId
     * @return
     */
    List<UserCoupon> selectUserCouponListByUserId (Long userId, Long couponId);

    /**
     * 根据用户Id查询用户拥有的优惠券的分页情况----此处查的是未使用的优惠券
     * @param page
     * @param userId
     * @return
     */
    Page<Map<String, Object>> selectUserCouponNotUserByUserId(Page<Map<String, Object>> page, Long userId);

    /**
     * 查询用户的优惠券明细分页---此处暂留订单使用优惠券情况
     * @param page
     * @param userId
     * @return
     */
    Page<Map<String, Object>> selectUserCouponInfoByUserId(Page<Map<String, Object>> page,  Long userId,String discountDesc,  Integer status,
                                                           String createTimeStart, String createTimeStop);


    /**
     * 通过用户优惠券ID变更用户优惠券状态
     * @param userCouponId
     * @param status
     * @return
     */
    boolean updateUserCoupon(Long userCouponId, Integer status);
}
