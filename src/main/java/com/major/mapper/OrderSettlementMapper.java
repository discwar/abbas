package com.major.mapper;

import com.major.entity.OrderSettlement;
import com.major.entity.Shop;
import com.major.model.response.OrderBySettlementResponse;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单结算表 Mapper 接口
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-11-20
 */
public interface OrderSettlementMapper extends BaseMapper<OrderSettlement> {


    /**
     * 商家获取结算信息分页
     * @param page
     * @param ew
     * @return
     */
    @Select({
            "<script>"+
                    "SELECT os.id as settlement_id  ,os.settlement_type,os.settlement_money,os.commission,os.back_money,os.`status`,os.settlement_time,os.remarks,os.create_time  " +
                    " FROM order_settlement os " +
                    " LEFT JOIN `order` o ON o.settlement_id=os.id " +
                    " LEFT JOIN shop s ON s.id=o.shop_id  <where> ${ew.sqlSegment} </where> " +
                    "</script>"})
    List<Map<String,Object>> selectOrderSettlementInfoPageBySysUserId(Pagination page, @Param("ew") Wrapper ew);

    /**
     * 获取单条结算详情中的关联订单信息
     * @param settlementId
     * @return
     */
    @Select("SELECT s.shop_name, o.order_no,o.create_time,o.goods_amount,o.postage,o.post_free,o.dada_fee,o.promotion_money,  " +
            "uc.coupon_source,( CASE WHEN o.user_coupon_id IS NULL THEN 0  ELSE  o.coupon_info END )as coupon_info,o.total_amount,o.real_total_amount,  " +
            "o.commission " +
            "FROM `order` o   " +
            "LEFT JOIN user_coupon uc ON o.user_coupon_id=uc.id " +
            "LEFT JOIN shop s ON s.id=o.shop_id  " +
            "WHERE o.settlement_id=#{settlementId}")
    List<Map<String,Object>> selectOrderBySettlementId(@Param("settlementId") Long settlementId);

    /**
     * 根据当前用户获取结算订单
     * 包涵已结算和未结算
     * @param page
     * @param ew
     * @return
     */
    @Select({
            "<script>"+
                    "SELECT " +
                    " o.id as order_id,o.order_no,lp.pay_way,o.create_time,o.goods_amount, " +
                    " o.postage,o.post_free,o.dada_fee,o.promotion_money,uc.coupon_source, " +
                    " ( CASE WHEN o.user_coupon_id IS NULL THEN 0 ELSE o.coupon_info END ) AS coupon_info, " +
                    " o.total_amount,o.real_total_amount,o.commission,os.`status`,o.settlement_id,os.settlement_time,os.create_time ,os.settlement_type " +
                    "FROM order_settlement os  " +
                    "LEFT JOIN `order` o ON  os.id=o.settlement_id " +
                    "LEFT JOIN user_coupon uc ON o.user_coupon_id = uc.id " +
                    "LEFT JOIN shop s ON s.id = o.shop_id " +
                    "left join log_pay lp ON o.log_pay_id=lp.id  "+
                    " <where> ${ew.sqlSegment} </where> " +
                    "</script>"})
    List<Map<String,Object>> selectOrderSettlementPageBySysUserId(Pagination page, @Param("ew") Wrapper ew);

    /**
     * 获取当前订单详情
     * @param oderId
     * @return
     */
    @Select(
            "SELECT o.id,o.order_no,o.create_time,o.goods_amount, (CASE WHEN o.postage IS NULL THEN '0' ELSE o.postage END ) AS postage, " +
                    "o.post_free,(CASE WHEN o.dada_fee IS NULL THEN '0' ELSE o.dada_fee END ) AS dada_fee,o.promotion_money,   " +
                    "  uc.coupon_source,( CASE WHEN o.user_coupon_id IS NULL THEN 0  ELSE  o.coupon_info END )as coupon_info,o.total_amount,o.real_total_amount,  " +
                    "  (CASE WHEN o.commission IS NULL THEN '0' ELSE o.commission END ) AS commission ,o.is_refund,os.refund_type ,os.refund_money " +
                    "FROM `order` o     " +
                    "LEFT JOIN user_coupon uc ON o.user_coupon_id=uc.id  " +
                    "LEFT JOIN order_refunds os ON o.id=os.order_id   " +
                    " WHERE o.id=#{oderId} ")
    OrderBySettlementResponse selectOrderInfoById( @Param("oderId") Long oderId);

    /**
     * 获取结算详情中的店铺信息
     * 如果此查询有两条记录时，表示有关联爱果小店的订单，显示页面时可以加备注
     * @param settlementId
     * @return
     */
    @Select("SELECT " +
            "s.shop_name,b.bank_name,s.card_number,s.cardholder " +
            "FROM `order` o  " +
            "LEFT JOIN shop s ON s.id =o.shop_id  " +
            "LEFT JOIN banks b ON s.banks_id=b.id  " +
            "WHERE  " +
            " o.settlement_id =#{settlementId} GROUP BY s.id  ")
    List<Map<String,Object>> selectShoInfoBySettlementId(@Param("settlementId") Long settlementId);


    /**
     * 财务获取结算信息分页
     * @param page
     * @param ew
     * @return
     */
    @Select({
            "<script>"+
                    " SELECT  " +
                    " s.shop_name, " +
                    " os.id as settlement_id ,os.settlement_type,os.settlement_money,os.commission,os.back_money,os.`status`,os.settlement_time,os.remarks,os.create_time  " +
                    " FROM order_settlement os  " +
                    " LEFT JOIN `order` o ON o.settlement_id=os.id  " +
                    " LEFT JOIN shop s ON s.id=o.shop_id"+
                    "<where> ${ew.sqlSegment} </where> " +
                    "</script>"})
    List<Map<String,Object>> selectOrderSettlementInfoPageByFinancial(Pagination page, @Param("ew") Wrapper ew);

    /**
     * 根据结算单号获取店铺的sysUserId
     * @param settlementId
     * @return
     */
     @Select(" SELECT  " +
             " s.shop_name,s.sys_user_id,s.shop_type,s.id ,s.is_relation_type  " +
             " FROM  `order` o  " +
             " LEFT JOIN shop s ON s.id=o.shop_id " +
             " WHERE s.`status`=1 AND o.settlement_id =#{settlementId} ORDER BY s.id ")
     List<Shop> selectShoInfoOneBySettlementId(@Param("settlementId") Long settlementId);

}
