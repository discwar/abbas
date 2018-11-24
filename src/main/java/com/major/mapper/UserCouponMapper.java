package com.major.mapper;

import com.major.entity.UserCoupon;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户优惠卷表 Mapper 接口
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-08-01
 */
public interface UserCouponMapper extends BaseMapper<UserCoupon> {

    /**
     * 通过查询条件获取用户优惠劵信息列表
     * @param ew
     * @return
     */
    @Select({"<script> " +
            "SELECT uc.id, uc.user_id, uc.status, c.coupon_desc, uc.coupon_source, uc.create_time, uc.use_time " +
            "FROM user_coupon uc " +
            "LEFT JOIN coupon c on c.id = uc.coupon_id " +
            "<where> ${ew.sqlSegment} </where>" +
            "</script>"})
    List<UserCoupon> selectUserCouponList(@Param("ew") Wrapper ew);

    /**
     * 为了限制一个用户只有拥有一张未使用的优惠券
     * 如果返回的长度大于零，则不能派发
     * @param userId
     * @param couponId
     * @return
     */
    @Select("SELECT uc.id, uc.user_id, uc.status, uc.coupon_source " +
            " FROM user_coupon uc  WHERE uc.user_id=#{userId} AND uc.coupon_id=#{couponId} AND uc.status=0 ")
    List<UserCoupon> selectUserCouponListByUserId(@Param("userId") Long userId,@Param("couponId") Long couponId);


    /**
     * 根据用户Id查询用户拥有的优惠券的分页情况----此处查的是未使用的优惠券
     * @param page
     * @param userId
     * @return
     */
    @Select("SELECT " +
            "  uc.user_id,c.id,c.kind,s.shop_name,c.apply_range,c.coupon_name, " +
            " c.coupon_type,c.discount_desc,c.limit_condition,c.deadline,uc.coupon_source " +
            "FROM  " +
            "user_coupon uc  " +
            "LEFT JOIN `user` u ON uc.user_id=u.id " +
            "LEFT JOIN coupon c ON c.id=uc.coupon_id  " +
            "LEFT JOIN shop s ON s.id=c.shop_id  " +
            "WHERE  " +
            "uc.`status`=0  " +
            "AND uc.user_id=#{userId}  " +
            "GROUP BY uc.id  ORDER BY uc.create_time DESC  ")
    List<Map<String,Object>> selectUserCouponNotUserByUserId(Pagination page, @Param("userId") Long userId);

    /**
     * 查询用户的优惠券明细分页
     * @param page
     * @param userId
     * @return
     */
   @Select({
                "<script> "+
                   "SELECT " +
                        " uc.id AS user_coupon_id,c.coupon_name,c.coupon_type,c.kind,c.apply_range,c.discount_desc,c.limit_condition,uc.deadline, " +
                        " uc.create_time,uc.use_time,uc.coupon_source,uc.`status`,   uc.coupon_id,  " +
                        " (CASE WHEN c.kind=0 THEN  c.coupon_name ELSE s.shop_name END ) AS 'owner',  " +
                        "   (CASE WHEN uc.`status`=1 THEN uc.remark    ELSE c.discount_desc  END   )as remark, " +
                        " uc.user_id " +
                        "FROM " +
                        " user_coupon uc  " +
                        "LEFT JOIN `user` u ON uc.user_id = u.id  " +
                        "LEFT JOIN coupon c ON c.id = uc.coupon_id   " +
                        "LEFT JOIN shop s ON s.id = c.shop_id  "+
                   "WHERE " +
                   "  uc.user_id=#{userId}  " +
                        " <if test='discountDesc !=null  '>" +
                        "            AND c.discount_desc like concat('%',#{discountDesc},'%')" +
                        "  </if> "+
                        " <if test='status !=null  '>" +
                        "            AND uc.status=#{status}" +
                        "  </if> "+
                        " <if test='createTimeStart !=null  '>" +
                        "            AND uc.create_time  <![CDATA[ >= ]]>#{createTimeStart}  " +
                        "  </if> "+
                        " <if test='createTimeStop !=null  '>" +
                        "            AND uc.create_time <![CDATA[ <= ]]>#{createTimeStop}  " +
                        "  </if> "+
                   "GROUP BY uc.id  ORDER BY uc.create_time DESC  " +
                 "</script>"}  )
    List<Map<String,Object>> selectUserCouponInfoByUserId(Pagination page, @Param("userId") Long userId,@Param("discountDesc") String discountDesc,@Param("status") Integer status,
                                                          @Param("createTimeStart") String createTimeStart,@Param("createTimeStop") String createTimeStop);



}
