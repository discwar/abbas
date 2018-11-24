package com.major.service;

import com.major.entity.Coupon;
import com.major.model.request.CouponRequest;
import com.major.model.response.CouponByStored;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/31 14:32      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public interface ICouponService extends IService<Coupon> {

    /**
     * 添加
     * @param couponRequest
     * @return
     */
    boolean addCoupon(CouponRequest couponRequest);

    /**
     * 修改
     * @param couponRequest
     * @param id
     * @return
     */
    boolean updateCoupon(CouponRequest couponRequest, Long id);

    /**
     * 删除
     * @param id
     * @return
     */
    boolean deleteCoupon( Long id);


    /**
     * 总部后台查看优惠券分页列表
     * @param page
     * @param couponName
     * @param couponDesc
     * @param couponType
     * @param deadlineStartTime
     * @param deadlineStopTime
     * @param createStartTime
     * @param createStopTime
     * @param status
     * @return
     */
    Page<Map<String, Object>> selectCoupon(Page<Map<String, Object>> page,String couponName,String couponDesc,Integer couponType,String deadlineStartTime,String deadlineStopTime,
                                           String createStartTime,String createStopTime,Integer status);

    /**
     * 获取当前优惠券
     * @param Id
     * @return
     */
    Coupon selectCouponById(Long Id);

    /**
     * 商家管理获取优惠券分页列表
     * @param page
     * @param couponName
     * @param couponDesc
     * @param couponState
     * @param deadlineStartTime
     * @param deadlineStopTime
     * @param createStartTime
     * @param createStopTime
     * @param groundState
     * @return
     */
    Page<Map<String, Object>> selectCouponBusioness(Page<Map<String, Object>> page,Long shopId,String couponName,String couponDesc,String couponState,String deadlineStartTime,String deadlineStopTime,
                                           String createStartTime,String createStopTime,Integer groundState);

    /**
     * 根据优惠券名和金额查找优惠券
     * @param couponName
     * @param money
     * @return
     */
    Coupon findCouponByNameAndMoney(String couponName, BigDecimal money);

    /**
     * 优惠券上架-下架
     * @param id
     * @param status
     * @return
     */
    boolean shelvesCoupon(Long id,Integer status);

    /**
     * 用于储值送好礼配置获取储值送好礼优惠券
     * @return
     */
    List<CouponByStored> selectCouponByStoredConfig();
}
