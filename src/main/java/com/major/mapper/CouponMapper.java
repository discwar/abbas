package com.major.mapper;

import com.major.entity.Coupon;
import com.major.model.response.CouponByStored;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
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
public interface CouponMapper extends BaseMapper<Coupon> {

    /**
     * 优惠券分页查找
     * @param page
     * @param couponName
     * @param couponDesc
     * @param couponType
     * @param deadlineStartTime
     * @param deadlineStopTime
     * @param createStartTime
     * @param createStopTime
     * @return
     */
    List<Map<String, Object>> selectCouponList(Pagination page,
                                               @Param("couponName") String couponName,
                                               @Param("couponDesc") String couponDesc,
                                               @Param("couponType") Integer couponType,
                                               @Param("deadlineStartTime") String deadlineStartTime,
                                               @Param("deadlineStopTime") String deadlineStopTime,
                                               @Param("createStartTime") String createStartTime,
                                               @Param("createStopTime") String createStopTime,
                                               @Param("deadlineState") Integer deadlineState);

    /**
     * 根据当前Id返回当前信息
     * @param id
     * @return
     */
    @Select("SELECT * FROM coupon WHERE id=#{id}")
    Coupon selectCouponById (@Param("id") Long id );

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
    List<Map<String, Object>> selectCouponBusioness(Pagination page,
                                                    @Param("shopId") Long shopId,
                                               @Param("couponName") String couponName,
                                               @Param("couponDesc") String couponDesc,
                                               @Param("couponState") String couponState,
                                               @Param("deadlineStartTime") String deadlineStartTime,
                                               @Param("deadlineStopTime") String deadlineStopTime,
                                               @Param("createStartTime") String createStartTime,
                                               @Param("createStopTime") String createStopTime,
                                               @Param("status") Integer groundState);

    /**
     * 根据展示开始时间和展示结束时间返回数据
     * 同一时间段只能上架一张优惠券
     * @param displayStartTime
     * @param displayStopTime
     * @param shopId
     * @return
     */
    @Select("SELECT  " +
            "id,kind,display_start_time,display_stop_time " +
            "FROM " +
            "coupon WHERE ( (display_start_time >= #{displayStartTime}  AND display_start_time <=#{displayStopTime} ) " +
            "    OR (display_start_time <=  #{displayStartTime}  AND display_stop_time >= #{displayStopTime}) " +
            "    OR (display_stop_time >=  #{displayStartTime}  AND display_stop_time <=#{displayStopTime})) and shop_id=#{shopId} AND status=1  ")
    List<Coupon> selectCouponByStartAndStopTime( @Param("displayStartTime") Date displayStartTime, @Param("displayStopTime") Date displayStopTime, @Param("shopId") Integer shopId);

    /**
     * 移除限制条件
     * @param id
     * @return
     */
    @Update("update coupon set limit_condition = null,update_time=now() where id =#{id}")
    int removeCouponLimitCondition(@Param("id") Long id);


    /**
     * 用于储值送好礼配置获取储值送好礼优惠券
     *
     * @param ew
     * @return
     */
    @Select({
            "<script> "+
                    "  SELECT id,coupon_name,coupon_type,discount_desc FROM coupon  <where> ${ew.sqlSegment} </where> " +
                    "</script>"}  )
    List<CouponByStored> selectCouponByStoredConfig (@Param("ew") Wrapper ew );

}
