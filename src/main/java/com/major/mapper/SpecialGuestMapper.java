package com.major.mapper;

import com.major.entity.SpecialGuest;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 专客表 Mapper 接口
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-09-03
 */
public interface SpecialGuestMapper extends BaseMapper<SpecialGuest> {

    /**
     * 商家查看专客列表
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
    @Select({
            "<script> "+
                    "SELECT " +
                    " s.shop_name,sg.create_time,u.nickname,u.phone,u.id as user_id ,o.consumption_num,o.consumption_money, " +
                    " sg.id  " +
                    "FROM " +
                    "  special_guest sg  " +
                    "LEFT JOIN shop s  ON s.id=sg.shop_id  " +
                    " LEFT JOIN ( " +
                                "SELECT " +
                                " id,user_id,shop_id, " +
                                " COUNT(id) AS consumption_num, " +
                                " SUM(real_total_amount) AS consumption_money " +
                                " FROM " +
                                " `order`  " +
                                " WHERE " +
                                " is_pay=1 AND is_closed=1 AND is_refund=0 " +
                                " GROUP BY  id  " +
                                ")o ON o.user_id = sg.user_id AND o.shop_id=sg.shop_id " +
                    "LEFT JOIN `user` u ON u.id=sg.user_id  " +
                    "WHERE " +
                    "s.sys_user_id=#{sysUserId} " +
                    " <if test='phone !=null  '>" +
                    "            AND u.phone=#{phone}   " +
                    "  </if> "+
                    "  <if test=' createTimeStart!=null '> " +
                    "           AND sg.create_time <![CDATA[ >= ]]> #{createTimeStart} " +
                    "  </if>"+
                    "  <if test=' createTimeStop!=null '> " +
                    "           AND sg.create_time <![CDATA[ <= ]]> #{createTimeStop} " +
                    "  </if>"+

                    "  <if test='consumptionNumStart!=null '> " +
                    "           AND o.consumption_num <![CDATA[ >= ]]> #{consumptionNumStart} " +
                    "  </if>"+
                    "  <if test='consumptionNumStop!=null '> " +
                    "           AND o.consumption_num <![CDATA[ <= ]]> #{consumptionNumStop} " +
                    "  </if>"+
                    "  <if test='consumptionMoneyStart!=null '> " +
                    "           AND o.consumption_money <![CDATA[ >= ]]> #{consumptionMoneyStart} " +
                    "  </if>"+
                    "  <if test='consumptionMoneyStop!=null '> " +
                    "           AND o.consumption_money <![CDATA[ <= ]]> #{consumptionMoneyStop} " +
                    "  </if>"+
                    " GROUP BY sg.user_id   " +
                    "</script>"}  )
    List<Map<String,Object>> selectSpecialGuestBySysUserId(Pagination page, @Param("sysUserId") Long sysUserId,  @Param("phone") String phone,
                                                           @Param("createTimeStart") String createTimeStart, @Param("createTimeStop") String createTimeStop,
                                                           @Param("consumptionNumStart") Integer consumptionNumStart, @Param("consumptionNumStop") Integer consumptionNumStop,
                                                           @Param("consumptionMoneyStart") Integer consumptionMoneyStart, @Param("consumptionMoneyStop") Integer consumptionMoneyStop);

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
    @Select({
            "<script> "+
                    "SELECT " +
                    " s.shop_name,sg.create_time,u.nickname,u.phone,u.id as user_id ,o.consumption_num,o.consumption_money, " +
                    " sg.id  " +
                    "FROM " +
                    "  special_guest sg  " +
                    "LEFT JOIN shop s  ON s.id=sg.shop_id  " +
                    " LEFT JOIN ( " +
                                "SELECT " +
                                " id,user_id,shop_id, " +
                                " COUNT(id) AS consumption_num, " +
                                " SUM(real_total_amount) AS consumption_money " +
                                " FROM " +
                                " `order`  " +
                                " WHERE " +
                                " is_pay=1 AND is_closed=1 AND is_refund=0 " +
                                " GROUP BY  id  " +
                                ")o ON o.user_id = sg.user_id AND o.shop_id=sg.shop_id " +
                    "LEFT JOIN `user` u ON u.id=sg.user_id  " +
                    "WHERE " +
                    "s.id=#{shopId} " +
                    " <if test='phone !=null  '>" +
                    "            AND u.phone=#{phone}   " +
                    "  </if> "+
                    "  <if test=' createTimeStart!=null '> " +
                    "           AND sg.create_time <![CDATA[ >= ]]> #{createTimeStart} " +
                    "  </if>"+
                    "  <if test=' createTimeStop!=null '> " +
                    "           AND sg.create_time <![CDATA[ <= ]]> #{createTimeStop} " +
                    "  </if>"+

                    "  <if test='consumptionNumStart!=null '> " +
                    "           AND o.consumption_num <![CDATA[ >= ]]> #{consumptionNumStart} " +
                    "  </if>"+
                    "  <if test='consumptionNumStop!=null '> " +
                    "           AND o.consumption_num <![CDATA[ <= ]]> #{consumptionNumStop} " +
                    "  </if>"+
                    "  <if test='consumptionMoneyStart!=null '> " +
                    "           AND o.consumption_money <![CDATA[ >= ]]> #{consumptionMoneyStart} " +
                    "  </if>"+
                    "  <if test='consumptionMoneyStop!=null '> " +
                    "           AND o.consumption_money <![CDATA[ <= ]]> #{consumptionMoneyStop} " +
                    "  </if>"+
                    " GROUP BY sg.user_id   " +
                    "</script>"}  )
    List<Map<String,Object>> selectSpecialGuestByShopId(Pagination page, @Param("shopId") Long shopId,  @Param("phone") String phone,
                                                           @Param("createTimeStart") String createTimeStart, @Param("createTimeStop") String createTimeStop,
                                                           @Param("consumptionNumStart") Integer consumptionNumStart, @Param("consumptionNumStop") Integer consumptionNumStop,
                                                           @Param("consumptionMoneyStart") Integer consumptionMoneyStart, @Param("consumptionMoneyStop") Integer consumptionMoneyStop);
}
