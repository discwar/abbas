package com.major.mapper;


import com.major.entity.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Mouxiaoshi
 * @since 2018-07-16
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询用户的注册时间
     * @param startTime
     * @return
     */
  @Select("select id ,create_time,login_time,client_id  FROM user WHERE  status=1 AND  DATE_FORMAT(create_time,'%Y-%m-%d')=#{startTime}  ")
   List<User> selectUserSearchCreateTime(@Param("startTime") String startTime);

    /**
     * 查询在某段时间内超过多少消费的用户
     * @param realTotalAmount
     * @param startTime
     * @param stopTime
     * @return
     */
   List<Map<String,Object>> selectUserOrderByExceedAmount(@Param("realTotalAmount") String realTotalAmount,
                                                   @Param("startTime") String startTime,@Param("stopTime") String stopTime);

    /**
     * 查询在某段时间内未超过多少消费的用户
     * @param realTotalAmount
     * @param startTime
     * @param stopTime
     * @return
     */
    List<Map<String,Object>> selectUserOrderByNotExceedAmount(@Param("realTotalAmount") String realTotalAmount,
                                                           @Param("startTime") String startTime,@Param("stopTime") String stopTime);

    /**
     * 返回24内未活跃的用户
     * @return
     */
  @Select("SELECT login_time, username,id ,client_id FROM `user` where login_time < NOW() - interval 1 DAY  and status=1 ")
  List<User> selectUserByLoginTime();

    /**
     * 查询所有用户
     * @return
     */
    @Select("SELECT login_time, username,client_id ,id  FROM `user` where status=1 ")
    List<User> selectAllUser();

    /**
     * 用户管理分页列表
     * sql注解:第一个嵌套-得要拼接之后的用户绑定第三方名字，第二个-得到最近消费时间，第三个-得要消费总金额和消费次数，第四个-得要邀请人数
     * @param page
     * @return
     */
    List<Map<String,Object>> selectUserPage(Pagination page, @Param("ew") Wrapper ew);

    /**
     * 用户个人信息查看
     * sql注解:第一个嵌套拼接绑定信息; 第二个得到本月消费额，第三个得要本月订单数，第四个得要总的订单数
     *         第五个得到初次储值时间,第六个得到总的消费金额
     * @param userId
     * @return
     */
    @Select("SELECT " +
            "u.id,u.username,u.phone, " +
            "u.nickname,c.source,u.create_time,s.shop_name,sg.create_time as guest_time, " +
            "u.total_score,u.wallet,mr.ranks_name, " +
            "tb.month_total_money,ttb.total_money, " +
            "od.month_order_num,o.order_num  ,tbb.first_bill_time ,m.id as maker_id,m.create_time as maker_create_time " +
            "FROM " +
            "`user` u " +
            "LEFT JOIN( " +
                       "SELECT " +
                        " GROUP_CONCAT(source) as source,user_id  " +
                        " FROM " +
                        " account_bind  " +
                        " GROUP BY user_id"+
                        ")c  ON c.user_id=u.id  " +
            "LEFT JOIN special_guest  sg ON sg.user_id=u.id  " +
            "LEFT JOIN  shop  s ON  sg.shop_id=s.id  " +
            "LEFT JOIN maker m ON m.user_id=u.id " +
            "LEFT JOIN maker_ranks mr ON m.maker_ranks_id=mr.id " +
            "LEFT JOIN (  " +
                        " SELECT " +
                        " SUM(money) as month_total_money,user_id  " +
                        " FROM transaction_bill WHERE  transaction_type in(1,2) AND mark=1  " +
                        "  AND date_format( create_time, '%Y-%m') = date_format(now(), '%Y-%m') " +
                        "  GROUP BY user_id  " +
            " ) tb  ON tb.user_id=u.id  " +
            "LEFT JOIN (  " +
                        "  SELECT  COUNT(id) as month_order_num,user_id  " +
                        " FROM `order` WHERE date_format( create_time, '%Y-%m') = date_format(now(), '%Y-%m') and  is_pay=1 AND is_closed=1 AND is_refund=0 AND order_status_type <> 6 " +
                        " GROUP BY user_id " +
                        " )od ON od.user_id=u.id " +
            "LEFT JOIN (  " +
                        "  SELECT  COUNT(id) as order_num,user_id  " +
                        " FROM `order`  where   is_pay=1 AND is_closed=1 AND is_refund=0  AND order_status_type <> 6   GROUP BY user_id  " +
                        "  )o ON o.user_id=u.id " +
            "LEFT JOIN (  " +
            "              SELECT  " +
            "               MIN(create_time)as first_bill_time, user_id  " +
            "             FROM transaction_bill WHERE  transaction_type=3   " +
            "             )tbb ON tbb.user_id=u.id  " +
            " LEFT JOIN (  " +
            "         SELECT SUM(money) as total_money,user_id FROM  transaction_bill  WHERE mark=1 GROUP BY user_id  " +
            "         ) ttb ON ttb.user_id = u.id   " +
            "WHERE " +
            "u.id=#{userId}  GROUP BY  u.id ")
    Map<String,Object> selectUserInfoByUserId(@Param("userId") Long userId);


    /**
     * 更新用户的储值钱包
     * @param userId
     * @param makerStorePay
     * @param walletPay
     * @param makerIncomePay
     * @return
     */
    @Update("<script>" +
            " UPDATE `user` u left join maker m on m.user_id = u.id " +
            " set u.wallet = u.wallet + #{walletPay},m.money = m.money + #{makerStorePay},m.income = m.income + #{makerIncomePay} " +
            " where u.id = #{id} " +
            "</script>")
    int refundToWallet(@Param("id") Long userId, @Param("makerStorePay") BigDecimal makerStorePay,
                       @Param("walletPay") BigDecimal walletPay, @Param("makerIncomePay") BigDecimal makerIncomePay);

    /**
     * 用户的邀请者
     * @param userId
     * @return
     */
    @Select("SELECT parent_user_id FROM maker_invitation where user_id = #{id} limit 0,1")
    Long userSuperiorInviter(@Param("id") Long userId);

}
