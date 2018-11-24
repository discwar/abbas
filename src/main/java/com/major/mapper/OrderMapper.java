package com.major.mapper;

import com.major.entity.LogPay;
import com.major.entity.Order;
import com.major.entity.OrderDetail;
import com.major.model.bo.RebateOrderBO;
import com.major.model.response.OrderDetailResponse;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-08-03
 */
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 根据当前用户返回订单详情
     * @param page
     * @param ew
     * @return
     */
    List<Map<String,Object>> selectOrderByUserIdPage(Pagination page,@Param("ew") Wrapper ew);


    /**
     * 商品订单查看，只包涵店铺类型为1-爱果小店；2-水果店；3-农场；4-采摘园；5-进口商；6-扶贫区
     * @param page
     * @param ew
     * @return
     */
    List<Map<String,Object>> selectSPOrderPage(Pagination page,@Param("ew") Wrapper ew);

    /**
     * 管理员查看订单详情可通用于商品订单和爱果订单中的所有店铺类型
     *
     * @param orderId
     * @return
     */
    @Select(" SELECT " +
            " u.username,u.phone,u.nickname,o.user_id, s.shop_name,so.legal_person,so.phone AS shop_phone, so.shop_id,o.delivery_type, " +
            " s.shop_type,o.order_status_type,o.order_type,o.order_status,o.order_no, " +
            " tb.flow_no,tb.pay_way,o.real_total_amount, " +
            "  o.promotion_money,uc.coupon_source,  " +
            "( CASE WHEN o.user_coupon_id IS NULL THEN 0 " +
            "   ELSE  o.coupon_info " +
            "   END " +
            "   )as coupon_info, o.postage,o.receive_address , o.receive_name , " +
            "  o.receive_phone ,e.`name` AS express_name, " +
            " o.express_no,o.order_remark,o.reject_reason,orc.reason, " +
            " os.id AS refunds_id,os.audit_status,o.is_apply_cancel,o.apply_cancel_status,o.apply_cancel_reply,o.express_id ,o.post_free " +
            "FROM `order` o " +
            "LEFT JOIN `user` u ON o.user_id=u.id " +
            "LEFT JOIN shop s ON o.shop_id=s.id " +
            "LEFT JOIN shop_operate so ON s.id=so.shop_id " +
            "LEFT JOIN user_coupon uc  ON o.user_coupon_id=uc.id " +
            "LEFT JOIN transaction_bill tb ON tb.order_no=o.order_no " +
            "LEFT JOIN order_reason_config  orc ON orc.id=o.reason_id  " +
            "LEFT JOIN express e ON o.express_id=e.id   " +
            "LEFT JOIN order_refunds os ON os.order_id=o.id  " +
            "WHERE " +
            "o.id=#{orderId} GROUP BY o.id ")
    Map<String,Object> selectOrderByOrderId(@Param("orderId") Long orderId);

    /**
     * 根据订单id查询订单商品情况
     * @param orderId
     * @return
     */
    @Select("SELECT " +
            " od.goods_name,od.current_total_price, " +
            "(CASE WHEN od.promotion_type=0 THEN '特惠' " +
            "WHEN od.promotion_type=1 THEN '折扣' " +
            "WHEN od.promotion_type=2 THEN '普通' " +
            "WHEN od.promotion_type=3 THEN '1元购'  " +
            "WHEN od.promotion_type=4 THEN '秒杀' " +
            "WHEN od.promotion_type=5 THEN '团购' " +
            "WHEN od.promotion_type=6 THEN '砍价' " +
            "WHEN od.promotion_type=6 THEN '今日特惠' " +
            "END " +
            " )as goods_mark,gs.current_price,  gs.original_price, od.goods_num,od.current_total_price as total_price ,od.goods_cover_url as img_urls  " +
            "FROM " +
            " `order` o  " +
            "LEFT JOIN order_detail od ON o.id = od.order_id  " +
            "LEFT JOIN goods gs ON od.goods_id=gs.id  " +
            "WHERE  " +
            " o.id=#{orderId}  GROUP BY od.id ")
    List<Map<String,Object>> selectOrderDetailByOrderId(@Param("orderId") Long orderId);

    /**
     * 总部自营获取爱果小店查看订单
     * @param page
     * @param ew
     * @return
     */
    List<Map<String,Object>> selectZYOrderPage(Pagination page,@Param("ew") Wrapper ew);

    /**
     * 水果商家/采摘园/农场/进出口查看订单列表-通用版
     * 排除爱果小店订单
     * @param page
     * @param ew
     * @return
     */
    List<Map<String,Object>> selectFruitsOrderPage(Pagination page,@Param("ew") Wrapper ew);

    /**
     * 当前商家查看关联的爱果小店的订单
     * 同爱果小店商家一起使用
     * @param page
     * @param ew
     * @return
     */
    List<Map<String,Object>> selectFruitsAGOrderPage(Pagination page,@Param("ew") Wrapper ew);

    /**
     * 管理员-商家管理-**店铺-商家信息-商家订单
     * @param page
     * @param shopId
     * @param goodsName
     * @param orderNo
     * @param phone
     * @param orderTimeStart
     * @param orderTimeStop
     * @param orderStatusType
     * @param orderStatus
     * @param realTotalAmountStart
     * @param realTotalAmountStop
     * @param isActivity
     * @return
     */
    List<Map<String,Object>> selectFruitsOrderPageByShopId(Pagination page,@Param("shopId") Long shopId,@Param("goodsName") String goodsName,@Param("orderNo") String orderNo,@Param("phone") String phone,
                                                   @Param("orderTimeStart") String orderTimeStart, @Param("orderTimeStop") String orderTimeStop, @Param("orderStatusType") Integer orderStatusType,
                                                   @Param("orderStatus") Integer orderStatus, @Param("realTotalAmountStart") String realTotalAmountStart,
                                                   @Param("realTotalAmountStop") String realTotalAmountStop, @Param("isActivity") Integer isActivity);

    /**
     * 组团订单分页列表
     * @param page
     * @param shopName
     * @param phone
     * @param orderTimeStart
     * @param orderTimeStop
     * @param departTimeStart
     * @param departTimeStop
     * @param orderNo
     * @param realTotalAmountStart
     * @param realTotalAmountStop
     * @param goodsName
     * @param pickingStatus
     * @return
     */
    List<Map<String,Object>> selectZTOrderPage(Pagination page,@Param("shopName") String shopName,@Param("phone") String phone,
                                               @Param("orderTimeStart") String orderTimeStart, @Param("orderTimeStop") String orderTimeStop,
                                               @Param("departTimeStart") String departTimeStart, @Param("departTimeStop") String departTimeStop,
                                               @Param("orderNo") String orderNo, @Param("realTotalAmountStart") String realTotalAmountStart, @Param("realTotalAmountStop") String realTotalAmountStop,
                                               @Param("goodsName") String goodsName,@Param("pickingStatus") Integer pickingStatus);

    /**
     * 组团订单类型查看详情
     * 注解:
     * @param orderId
     * @return
     */
    @Select(  "SELECT   " +
            "              u.username,u.phone,u.nickname,o.user_id, s.shop_name, so.phone AS shop_phone, so.shop_id,   " +
            "              o.order_no,tb.flow_no,tb.pay_way,g.person_num,gp.out_time,gp.original_out_time,   " +
            "               od.goods_name,od.goods_num,o.goods_amount, o.real_total_amount,    " +
            "              o.goods_amount - o.real_total_amount AS discount_amount, " +
            "              o.coupon_info,o.promotion_money,o.order_remark, o.cancel_reason, os.id AS refunds_id,os.audit_status    n" +
            "             FROM   " +
            "              `order` o   " +
            "             LEFT JOIN group_picking gp  ON o.group_picking_id=gp.id " +
            "             LEFT JOIN `user` u ON o.user_id = u.id     " +
            "             LEFT JOIN  order_detail od ON od.order_id=o.id " +
            "             LEFT JOIN goods g ON od.goods_id=g.id    " +
            "             LEFT JOIN shop s ON o.shop_id = s.id    " +
            "             LEFT JOIN shop_operate so ON s.id = so.shop_id    " +
            "             LEFT JOIN transaction_bill tb ON tb.order_no = o.order_no    " +
            "             LEFT JOIN order_refunds os ON os.order_id = o.id   "+
            "WHERE  o.id =#{orderId}  GROUP BY o.id "  )
    Map<String,Object> selectZTOrderByOrderId(@Param("orderId") Long orderId);

    /**
     * 根据套票码返回订单数据，按照时间倒排序，只取第一条数据
     * @param ticketNumber
     * @return
     */
    @Select("SELECT  " +
            "o.id as order_id,u.username,u.nickname,u.phone,o.order_status_desc ,od.goods_name,od.goods_num ,o.ticket_use_num   " +
            "FROM " +
            "`order` o  " +
            "LEFT JOIN `user` u ON u.id=o.user_id  " +
            "LEFT JOIN  order_detail od ON od.order_id=o.id  " +
            "left join  shop s ON  s.id = o.shop_id  " +
            "WHERE " +
            "o.ticket_number=#{ticketNumber} and s.id=#{shopId} AND o.is_pay=1  " +
            "ORDER BY o.create_time DESC  LIMIT 0,1  ")
    Map<String,Object> selectOrderByTicketNumber(@Param("shopId") Long shopId,@Param("ticketNumber") String ticketNumber);

    /**
     * 获取待支付的支付日志记录
     * @param ew
     * @return
     */
    @Select("<script>" +
            "SELECT * from log_pay" +
            "<where>" +
            " ${ew.sqlSegment}" +
            "</where>" +
            "</script>")
    LogPay getLogPay(@Param("ew") Wrapper<LogPay> ew);

    /**
     * 七日前的一天的的订单
     * @param ew
     * @return
     */
    @Select("<script>" +
            "SELECT o.id, o.order_no, o.shop_id, o.user_id, o.log_pay_id, o.real_total_amount,o.order_time,lp.money_source   " +
            " FROM `order` o " +
            " JOIN  log_pay lp on o.log_pay_id = lp.id " +
            " left join order_refunds orr on orr.order_id = o.id " +
            "<where>" +
            " ${ew.sqlSegment}" +
            "</where>" +
            "</script>")
    List<RebateOrderBO> getBeforeSevenDaysOrders(@Param("ew") Wrapper ew);

    /**
     * 只适用于查询：同城、物流店铺类型的待接单订单数量
     * @param shopId
     * @return
     */
    @Select("SELECT count(id) as wait_number ,o.shop_id  from `order`  o where o.is_pay=1 AND o.order_status=2 AND o.is_refund=0 AND o.is_closed=0 AND o.shop_id=#{shopId } ")
    Map<String, Object> selectOrderWaitReceiptByShopId(@Param("shopId") Long shopId);

    /**
     * 根据快递单号查找订单数据
     * @param ExpressNo
     * @return
     */
    @Select("select id ,order_no ,express_no,order_type,order_status_type,order_status,order_status_desc from `order` where express_no=#{ExpressNo} and status=1  LIMIT 0,1  ")
    Order selectOrderByExpressNo(@Param("ExpressNo") String ExpressNo);

    /**
     * 根据订单id返回
     * @param orderId
     * @return
     */
    @Select("select id,order_id,goods_id,goods_num,money_off,promotion_type,activity_num from order_detail where order_id=#{orderId}")
    List<OrderDetail> selectOrderDetailByOrderID(@Param("orderId") Long orderId);

    @Select("select o.id,s.shop_name  " +
            "from `order`  o  " +
            "left join shop s ON s.id=o.shop_id  " +
            " where o.id=#{orderId} ")
    Map<String,Object> selectShopNameByOrderId(@Param("orderId") Long orderId);

    /**
     * 根据订单编号返回数据
     * @param orderNo
     * @return
     */
    @Select("select id,order_no,shop_id,user_id,log_pay_id,order_type,order_status_type,order_status,order_status_desc,status  from `order` where order_no=#{orderNo} and status=1  LIMIT 0,1  ")
    Order selectOrderByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 查询店铺首页订单相关信息的总览
     * 此处查询为接单数据只适用于：同城、物流店铺类型的订单
     * @param shopId
     * @return
     */
    @Select(" SELECT  " +
            "COUNT(IF(o.is_pay=1 AND o.order_status=2 AND o.is_refund=0 AND o.is_closed=0, TRUE, NULL)) AS wait_number, " +
            "COUNT(IF( o.is_apply_cancel=1 AND o.apply_cancel_status=0 , TRUE, NULL)) AS cancel_number, " +
            "COUNT(ou.order_id) AS reminder_number, " +
            "COUNT(IF(o.is_pay=1  AND o.is_refund=1 , TRUE, NULL)) AS refund_number,r.day_number,r.day_total_amount " +
            "FROM shop s  " +
            "LEFT JOIN `order` o ON s.id=o.shop_id " +
            "LEFT JOIN order_urge ou ON o.id=ou.order_id " +
            "LEFT JOIN (  " +
            "SELECT shop_id, COUNT(id) AS day_number,SUM(real_total_amount) AS day_total_amount FROM `order`   " +
            "WHERE to_days(create_time) = to_days(now()) AND is_pay=1 AND is_refund=0  AND is_closed=1  " +
            ")r ON r.shop_id=s.id  " +
            "WHERE   s.`status`=1 AND  s.id=#{shopId} ")
    Map<String,Object> selectShopOrderInfo(@Param("shopId") Long shopId);

    /**
     * 针对一元购订单查询配送方式
     * @param orderId
     * @return
     */
    @Select("SELECT  " +
            "od.order_id,od.goods_id,gs.delivery_type  " +
            "FROM `order` o  " +
            "LEFT JOIN order_detail od ON o.id=od.order_id  " +
            "LEFT JOIN goods gs ON gs.id=od.goods_id " +
            "WHERE od.order_id=#{orderId} ")
    OrderDetailResponse selectOneOrderDetailByOrderId(@Param("orderId") Long orderId);

    /**
     * 查询一笔订单的详情
     * @param orderId
     * @return
     */
    @Select("select id,order_id,goods_id,goods_num,money_off,promotion_type from order_detail where order_id=#{orderId} LIMIT 0,1 ")
    OrderDetail selectOrderDetailOneByOrderID(@Param("orderId") Long orderId);

    /**
     * 根据当前用户返回所有未申请结算订单分页列表
     * 包涵已关联爱果小店的订单
     * 返还金额=实付金额+平台优惠券金额-达达运费
     * @param page
     * @param ew
     * @return
     */
    @Select("<script>" +
            "SELECT o.id,o.order_no,o.create_time,o.goods_amount,o.postage,o.post_free,o.dada_fee,o.promotion_money,   " +
            "  uc.coupon_source,( CASE WHEN o.user_coupon_id IS NULL THEN 0  ELSE  o.coupon_info END )as coupon_info,o.total_amount,o.real_total_amount,  " +
            "  o.commission  ,(CASE WHEN DATEDIFF(NOW(),order_time)>3 THEN '1' ELSE '0' END) as is_withdrawal,o.is_refund,os.refund_type ,os.refund_money  " +
            "FROM `order` o     " +
            "LEFT JOIN user_coupon uc ON o.user_coupon_id=uc.id  " +
            "LEFT JOIN  shop s ON s.id=o.shop_id  " +
            "LEFT JOIN order_refunds os ON o.id=os.order_id  " +
            "<where>" +
            " ${ew.sqlSegment}" +
            "</where>" +
            "</script>")
    List<Map<String,Object>> selectNoSettlementOrderPageBySysUserId(Pagination page,@Param("ew") Wrapper ew);

    /**
     *根据当前用户返回所有未申请结算订单不分页
     * 当商家选择全部申请结算使用
     * @param ew
     * @return
     */
    @Select("<script>" +
            "SELECT o.id,o.order_no ,o.settlement_id " +
            "FROM `order` o     " +
            "LEFT JOIN user_coupon uc ON o.user_coupon_id=uc.id  " +
            "LEFT JOIN  shop s ON s.id=o.shop_id  " +
            "LEFT JOIN order_refunds os ON o.id=os.order_id  " +
            "<where>" +
            " ${ew.sqlSegment}" +
            "</where>" +
            "</script>")
    List<Order> selectNoSettlementOrderBySysUserId(@Param("ew") Wrapper ew);

}
