package com.major.mapper;

import com.major.entity.Activity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/18 19:35      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public interface ActivityMapper extends BaseMapper<Activity> {

    /**
     * 根据店铺id和商品id查询活动表，已限制同一个店铺和同一个商品只能添加一个活动
     * @param goodsId
     * @param shopId
     * @return
     */
    @Select("SELECT gs.shop_id,a.goods_id,a.original_price FROM activity a " +
            "left join goods gs  on  gs.id=a.goods_id " +
            "WHERE a.status<>0 AND gs.shop_id=#{shopId} AND a.goods_id=#{goodsId}   ")
    List<Activity> selectActivityByGoodsIdAndShopId(@Param("goodsId") Long goodsId,@Param("shopId") Long shopId);

    /**
     * 通用版获取活动分页列表
     * @param page
     * @param sysUserId
     * @param goodsName
     * @param activityStatus
     * @param activityType
     * @return
     */
    List<Map<String, Object>> selectActivityPage(Pagination page,@Param("shopType") Integer shopType,@Param("sysUserId") Long sysUserId,
                                               @Param("goodsName") String goodsName,
                                                 @Param("activityStatus") Integer activityStatus,
                                                 @Param("activityType") Integer activityType);

    @Select("SELECT " +
            "a.id as activity_id,g.`name` as goods_name,gc.`name` as category_name,s.shop_name,a.original_price, " +
            "a.sec_kill_price,a.group_buying,a.activity_desc,a.start_time,a.end_time,a.total_count,g.total_count as goods_total_count, " +
            "a.orders_count,a.sec_kill_limit_num,a.activity_type,g.current_price  " +
            "FROM activity a   " +
            "LEFT JOIN goods  g ON g.id=a.goods_id  " +
            "LEFT JOIN shop s ON s.id=g.shop_id  " +
            "LEFT JOIN goods_category gc ON g.category_id=gc.id  " +
            "WHERE " +
            "a.id=#{activityId}")
    Map<String,Object> selectActivityInfoById(@Param("activityId") Long activityId );

    /**
     * 查看当前活动的状态
     * 0-已结束，1-进行中，2-等待中
     * @param activityId
     * @return
     */
    @Select(" select  (   CASE   WHEN NOW() > a.start_time  " +
            "                AND NOW()   <   a.end_time THEN '1'  " +
            "                WHEN NOW() > a.end_time THEN   '0' " +
            "                WHEN NOW() <   a.start_time THEN '2'  " +
            "                END " +
            "                ) AS activity_state from  activity a  where a.id=#{activityId}")
    Integer selectActivityType(@Param("activityId") Long activityId );
}
