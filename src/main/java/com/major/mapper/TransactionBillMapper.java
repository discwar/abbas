package com.major.mapper;

import com.major.entity.TransactionBill;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 交易账单表 Mapper 接口
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-19
 */
public interface TransactionBillMapper extends BaseMapper<TransactionBill> {


    /**
     * 钱包分页查询
     * 当userId有值时可查询单个用户的钱包情况
     * @param page
     * @param flowNo
     * @param phone
     * @param transactionType
     * @param createTimeStart
     * @param createTimeStop
     * @param orderNo
     * @param shopName
     * @param payWay
     * @param moneyStart
     * @param moneyStop
     * @return
     */
    List<Map<String, Object>> selectTransactionBillPage(Pagination page, @Param("userId") Long userId,@Param("flowNo") String flowNo,
                                                        @Param("phone") String phone,
                                                        @Param("transactionType") Integer transactionType,
                                                        @Param("createStartTime") String createTimeStart, @Param("createTimeStop") String createTimeStop,
                                                        @Param("orderNo") String orderNo, @Param("shopName") String shopName,
                                                        @Param("payWay") Integer payWay,@Param("moneyStart") String moneyStart,@Param("moneyStop") String moneyStop);

    /**
     *
     * 交易详情:针对交易类型为:线上消费,线下支付,退款,钱包储值,提现,创客储值,创客升级---通用获取当前交易详情
     * @param billId
     * @return
     */
    @Select("SELECT " +
            " tb.create_time, " +
            " tb.flow_no, tb.order_no, u.username,u.nickname, u.phone, " +
            " tb.transaction_type, tb.pay_way, " +
            " tb.money, tb.id, tb.shop_name, tb.shop_id,u.nickname,tb.user_id ,tb.maker_ranks ,tb.original_maker_ranks ,s.shop_name,so.legal_person,so.phone as shop_phone  " +
            "FROM " +
            " transaction_bill tb " +
            "LEFT JOIN `user` u ON tb.user_id = u.id " +
            "LEFT JOIN shop s ON s.id=tb.shop_id  " +
            "LEFT JOIN shop_operate so ON so.shop_id=s.id  " +
            "WHERE tb.id=#{billId}  ")
     Map<String,Object> selectTransactionBillById(@Param("billId") Long billId);


    /**
     * 交易详情:针对:邀请用户成为创客,好友消费返利,二级好友消费返利
     * @param billId
     * @return
     */
    @Select("SELECT " +
            "u.username,u.nickname,u.phone,tb.user_id,tb.transaction_type,tb.pay_way,tb.money,  " +
            "tb.rebate_ratio,tb.create_time,tb.flow_no,b.*,tb.id as bill_id  " +
            "FROM  " +
            "transaction_bill tb " +
            "LEFT JOIN `user` u ON tb.user_id=u.id " +
            "LEFT JOIN  " +
            " (SELECT " +
            " u.nickname as invitee_username,u.phone as invitee_phone ,tb.invitee_id, " +
            "  mr.ranks_name as invitee_ranks_name " +
            "FROM " +
            " transaction_bill tb " +
            "LEFT JOIN `user` u ON tb.invitee_id = u.id  " +
            "LEFT JOIN maker m ON tb.invitee_id=m.user_id  " +
            "LEFT JOIN maker_ranks mr ON mr.id=m.maker_ranks_id) b ON b.invitee_id=tb.invitee_id  " +
            "WHERE " +
            "u.`status`=1  AND tb.id=#{billId} GROUP BY tb.id  " )
    Map<String,Object> selectTransactionBillMarkById(@Param("billId") Long billId);

    /**
     * 查询总储值额和可提现额
     * 注解 pincome 是根据解冻时间判断的可以提现金额
     * @return
     */
    @Select({
            "<script>"+
            "SELECT " +
            "IFNULL(SUM(u.wallet), 0) + IFNULL(SUM(m.money), 0) +IFNULL(SUM(m.income), 0) AS total_money,  " +
                    " IFNULL(SUM(m.income), 0) + IFNULL(SUM(m.pincome), 0) AS postal_money  " +
            "FROM " +
            " `user` u " +
            "LEFT JOIN ( " +
                    "SELECT " +
                    "user_id, " +
                    "( " +
                    " CASE WHEN DATE_FORMAT(CURDATE(), '%Y-%m-%d') >= DATE_FORMAT(thaw_time, '%Y-%m-%d') THEN " +
                    " money " +
                    " END " +
                    ")as pincome,money,income " +
                    "FROM " +
                    "maker  " +
                    ")m ON u.id = m.user_id " +
            "WHERE u.`status`=1 " +
                    " <if test='userId !=null  '>" +
                    "            AND u.id=#{userId}" +
                    "  </if> "+
            "</script>"})
    Map<String,Object> selectSumTotalMoney(@Param("userId") Long userId);


    /**
     * 根据用户id获取收益明细分页列表
     * @param page
     * @param flowNo
     * @param userId
     * @param phone
     * @param transactionType
     * @param createTimeStart
     * @param createTimeStop
     * @param moneyStart
     * @param moneyStop
     * @return
     */
    List<Map<String, Object>> selectTransactionBillByUserIdPage(Pagination page, @Param("flowNo") String flowNo,@Param("userId") Long userId,
                                                        @Param("phone") String phone,
                                                        @Param("transactionType") Integer transactionType,
                                                        @Param("createStartTime") String createTimeStart, @Param("createTimeStop") String createTimeStop,
                                                       @Param("moneyStart") String moneyStart,@Param("moneyStop") String moneyStop);

}
