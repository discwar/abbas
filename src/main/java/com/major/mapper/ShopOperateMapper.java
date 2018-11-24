package com.major.mapper;

import com.major.entity.ShopOperate;
import com.major.model.response.ShopResponse;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Map;

/**
 * <p>
 * 店铺运营表 Mapper 接口
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-07
 */
public interface ShopOperateMapper extends BaseMapper<ShopOperate> {

    /**
     * 统计的是一个月内的订单相关评分
     * @param sysUserId
     * @param shopType
     * @return
     */
    @Select("SELECT  " +
            " s.shop_name,  AVG(b.service_order_time) AS service_order_time, " +
            " COUNT(a.id) AS order_number, " +
            "  (CASE WHEN so.score IS NULL THEN '5.0'  ELSE  so.score  END   ) AS shop_score, " +
            " (CASE WHEN b.taste_score IS NULL THEN '5.0'  ELSE   convert(b.taste_score,decimal(10,1))  END  ) AS taste_score, " +
            " (CASE WHEN b.package_score IS NULL THEN '5.0' ELSE   convert( b.package_score,decimal(10,1)) END ) AS package_score,so.*  " +
            "FROM  " +
            "shop s  " +
            "LEFT JOIN shop_operate so ON s.id=so.shop_id  " +
            "LEFT JOIN " +
            "          (SELECT " +
            "           o.real_total_amount,o.id,o.shop_id  " +
            "          FROM " +
            "            `order` o " +
                      "LEFT JOIN  order_comment oc ON o.id=oc.order_id  " +
            "          WHERE " +
            "           DATE_FORMAT(o.create_time, '%Y%m') = DATE_FORMAT(CURDATE(), '%Y%m') and  o.is_pay=1 AND o.is_closed=1 AND o.is_refund=0  AND order_status_type <> 6" +
            "        )a ON s.id = a.shop_id   " +
            "LEFT JOIN ( " +
            " SELECT  " +
            " AVG(oc.overall_score) AS shop_score, " +
            " AVG(oc.taste_score) AS taste_score, " +
            " AVG(oc.package_score) AS package_score, " +
            "    TIMESTAMPDIFF(MINUTE,r.create_time,r.order_time) AS service_order_time,r.shop_id " +
            "                    FROM " +
            "                        `order` r " +
            "                    LEFT JOIN order_comment oc ON oc.order_id = r.id " +
            "                    WHERE  " +
            "                         r.is_pay=1 AND r.is_closed=1 AND r.is_refund=0  AND order_status_type <> 6 " +
            "                    GROUP BY " +
            "                        r.shop_id  " +
            "                ) b ON s.id = b.shop_id  " +
            "WHERE s.status=1  " +
            " and s.sys_user_id=#{sysUserId} AND s.shop_type=#{shopType}  " +
            "GROUP BY s.id  ")
    Map<String,Object> selectShopOperateBySysUserId(@Param("sysUserId") Long sysUserId,@Param("shopType") Integer shopType);

    /**
     * 针对总部自营获取当前店铺运营信息
     * @param shopId
     * @return
     */
    @Select("SELECT  " +
            "s.shop_name,  " +
            " COUNT(a.id) AS order_number, " +
            "  (CASE WHEN b.shop_score IS NULL THEN '5.0'  ELSE   convert(b.shop_score,decimal(10,1))  END   ) AS shop_score, " +
            " (CASE WHEN b.taste_score IS NULL THEN '5.0'  ELSE   convert(b.taste_score,decimal(10,1))  END  ) AS taste_score, " +
            " (CASE WHEN b.package_score IS NULL THEN '5.0' ELSE   convert( b.package_score,decimal(10,1)) END ) AS package_score, " +
            " so.id,so.shop_id ,so.shop_status,so.service_start_time,so.service_end_time,so.legal_person,so.delivery_type,so.promotion_plans ,so.phone,  " +
            "   so.shop_notice,so.promotion_plans,so.postage,post_free,  so.delivery_time,so.is_auto_receive_order,so.min_delivery_price,so.far_distance,label,so.special_guest_rebate,  " +
            "so.push_phone,so.estimated_expend_time  ,   so.qr_code_url  " +
            "FROM  " +
            "shop s  " +
            "LEFT JOIN shop_operate so ON s.id=so.shop_id  " +
            "LEFT JOIN " +
            "          (SELECT " +
            "           o.real_total_amount,o.id,o.shop_id  " +
            "          FROM " +
            "            `order` o " +
            "LEFT JOIN  order_comment oc ON o.id=oc.order_id  " +
            "          WHERE " +
            "           DATE_FORMAT(o.create_time, '%Y%m') = DATE_FORMAT(CURDATE(), '%Y%m') " +
            "        )a ON s.id = a.shop_id   " +
            "LEFT JOIN ( " +
            " SELECT  " +
            " AVG(oc.overall_score) AS shop_score, " +
            " AVG(oc.taste_score) AS taste_score, " +
            " AVG(oc.package_score) AS package_score, r.shop_id " +
            "                    FROM " +
            "                        `order` r " +
            "                    LEFT JOIN order_comment oc ON oc.order_id = r.id " +
            "                    WHERE  " +
            "                         r.is_pay=1 AND r.is_closed=1 AND r.is_refund=0  " +
            "                    GROUP BY " +
            "                        r.shop_id  " +
            "                ) b ON s.id = b.shop_id  " +
            "WHERE s.status=1  " +
            " and s.id=#{shopId}   " +
            "GROUP BY s.id  ")
    Map<String,Object> selectShopOperateByShopId(@Param("shopId") Long shopId);


    @Select("SELECT id,shop_id ,shop_status,service_start_time,service_end_time,legal_person,delivery_type,promotion_plans ,phone,  " +
            "shop_notice,promotion_plans,postage,post_free,  delivery_time,is_auto_receive_order,min_delivery_price,far_distance,label,special_guest_rebate,push_phone,estimated_expend_time, " +
            "qr_code_url " +
            " FROM shop_operate WHERE  shop_id=#{shopId} ")
    ShopOperate selectShopOperateByShopIdOne(@Param("shopId") Long shopId);

    /**
     *置空满减信息
     * @param shopOperateId
     * @return
     */
    @Update("update shop_operate set promotion_plans = null,update_time=now() where id =#{id}")
    int removePromotionPlans(@Param("id") Long shopOperateId);

    /**
     * 置空店铺公告
     * @param shopOperateId
     * @return
     */
    @Update("update shop_operate set shop_notice = null,update_time=now() where id =#{id}")
    int removeShopNotice(@Param("id") Long shopOperateId);

    /**
     * 置空满多少免运费
     * @param shopOperateId
     * @return
     */
    @Update("update shop_operate set post_free = null,update_time=now() where id =#{id}")
    int removePostFree(@Param("id") Long shopOperateId);

    /**
     * 置空*小时内发货字段
     * @param shopOperateId
     * @return
     */
    @Update("update shop_operate set delivery_time = null,update_time=now() where id =#{id}")
    int removeDeliveryTime(@Param("id") Long shopOperateId);

    /**
     * 置空店铺标签
     * @param shopId
     * @return
     */
    @Update("update shop_operate set label = null,update_time=now() where shop_id =#{shopId}")
    int removeLabel(@Param("shopId") Long shopId);

    /**
     * 根据关联的relationId推出该条爱果小店的信息
     * @param relationId
     * @return
     */
    @Select("SELECT " +
            " s.*, " +
            " so.label,so.phone,so.legal_person, " +
            " su.username,so.id as shop_operate_id  " +
            "FROM " +
            " shop s " +
            "LEFT JOIN shop_operate so ON s.id = so.shop_id " +
            "LEFT JOIN sys_user su ON s.sys_user_id = su.id WHERE s.relation_id=#{relationId} AND s.status=1 and s.shop_type=1 ")
    ShopResponse selectShopAndShopOperateByRelationId(@Param("relationId") Long relationId);

    /**
     * 置空最远配送距离
     * @param shopOperateId
     * @return
     */
    @Update("update shop_operate set far_distance = null,update_time=now() where id =#{id}")
    int removFarDistance(@Param("id") Long shopOperateId);

}
