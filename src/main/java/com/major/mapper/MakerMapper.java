package com.major.mapper;

import com.major.entity.Maker;
import com.major.model.NewMaker;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 创客表 Mapper 接口
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-19
 */
public interface MakerMapper extends BaseMapper<Maker> {


    /**
     * 语句注解:第一个嵌套的是统计上个月的收益;第二个嵌套是查询七天的收益总和，第三嵌套是查询邀请人数，第四个是查询邀请的创客人数
     * @param page
     * @return
     */
    List<Map<String,Object>> selectMakerPage(Pagination page, @Param("ew") Wrapper ew);

    /**
     * 根据用户Id查询创客详情
     * 其中第一个嵌套查询用户的第一次创客储值时间，第二是查询上一次升级时间
     * @param userId
     * @return
     */
    @Select("SELECT " +
            "u.username,u.phone,u.nickname,m.user_id,SUM(m.money+m.income)+SUM(u.wallet) AS total_money, " +
            "mr.ranks_name,tb.first_bill_time,ts.last_bill_time " +
            "FROM maker m  " +
            "LEFT JOIN `user` u ON m.user_id=u.id  " +
            "LEFT JOIN maker_ranks mr ON m.maker_ranks_id=mr.id  " +
            "LEFT JOIN (  " +
            "  SELECT " +
            "   MIN(create_time)as first_bill_time, user_id " +
            "  FROM transaction_bill WHERE  transaction_type=3  GROUP BY user_id " +
            ")tb ON tb.user_id=m.user_id " +
            "LEFT JOIN ( " +
            "  SELECT " +
            "  create_time as last_bill_time ,user_id " +
            "  FROM transaction_bill WHERE create_time in(SELECT MAX(create_time) FROM transaction_bill where transaction_type = 4 GROUP BY user_id  )  " +
            ")ts ON ts.user_id=m.user_id " +
            "WHERE u.id=#{userId}")
    Map<String,Object> selectMakerUserInfoByUserId(@Param("userId") Long userId);

    /**
     * 查询创客详情中的可提现金额，上月收益和七日收益，昨日收益
     * @param makerId
     * @return
     */
    @Select("SELECT " +
            "m.id,mim.last_moth_income,mid.seven_day_income,mis.yesterday_income, " +
            "( " +
            " CASE WHEN DATE_FORMAT(CURDATE(), '%Y-%m-%d') >= DATE_FORMAT(m.thaw_time, '%Y-%m-%d') THEN  " +
            "  SUM(m.money+m.income) " +
            " ELSE m.income " +
            " END  " +
            ")as postal_money " +
            "FROM maker m  " +
            "LEFT JOIN ( " +
            "                SELECT " +
            "                SUM(income)as last_moth_income,maker_id " +
            "                FROM " +
            "                maker_income_month " +
            "                WHERE " +
            "                PERIOD_DIFF( date_format( now( ) , '%Y%m' ) , date_format( income_time, '%Y%m' ) ) =1  GROUP BY maker_id " +
            "                )mim ON m.id = mim.maker_id " +
            "LEFT JOIN (  " +
            "               SELECT " +
            "                SUM(income)AS seven_day_income,maker_id " +
            "                FROM " +
            "                maker_income_day " +
            "                WHERE  " +
            "          DATE_SUB(CURDATE(), INTERVAL 7 DAY)  <= date(income_time) GROUP BY maker_id  " +
            "                ) mid ON mid.maker_id = m.id " +
            "LEFT JOIN ( " +
            "               SELECT " +
            "                SUM(income)AS yesterday_income,maker_id " +
            "                FROM " +
            "                maker_income_day " +
            "                WHERE   " +
            "          DATE_SUB(CURDATE(), INTERVAL 1 DAY)  = date(income_time)   GROUP BY maker_id   " +
            "                ) mis ON mis.maker_id = m.id " +
            "WHERE m.id=#{makerId}")
    Map<String,Object> selectMakerIncomeById(@Param("makerId") Long makerId);


    /**
     * 根据当前用户获取创客邀请情况分页显示
     * @param page
     * @param userId
     * @param phone
     * @param isMaker
     * @param createTimeStart
     * @param createTimeStop
     * @return
     */
    List<Map<String,Object>> selectMakerInvitationByUserId(Pagination page, @Param("userId") Long userId,@Param("phone") String phone,@Param("isMaker") Integer isMaker,
                                                           @Param("createTimeStart") String createTimeStart, @Param("createTimeStop") String createTimeStop);

    /**
     * 查找昨天成为创客的用户列表
     * @return
     */
    @Select("SELECT " +
            " m.id, " +
            " m.user_id, " +
            " m.create_time, " +
            " tb.money as store_money " +
            "FROM " +
            " maker m  " +
            " join transaction_bill tb on tb.user_id = m.user_id " +
            "WHERE " +
            "  DATE( m.create_time ) =  DATE_SUB(CURDATE(), INTERVAL 1 DAY) " +
            "  and tb.transaction_type = #{type} " +
            "  and  DATE(tb.create_time) = DATE_SUB(CURDATE(), INTERVAL 1 DAY)")
    List<NewMaker> selectBeMakerYesterday(@Param("type") Integer transactionType);

    /**
     * 通过被邀请人ID查找邀请人创客信息
     * @param userId 被邀请人ID
     * @return
     */
    @Select("SELECT " +
            " m.id, " +
            " m.user_id, " +
            " m.maker_ranks_id, " +
            " m.money, " +
            " m.income, " +
            " m.total_income, " +
            " m.thaw_time, " +
            " m.create_time, " +
            " m.upgrade_time, " +
            " mrr.rebate_ratio " +
            "FROM " +
            " maker m " +
            " JOIN maker_ranks mra ON m.maker_ranks_id = mra.id " +
            " JOIN maker_ranks_rights mrr ON mrr.maker_ranks_id = mra.id " +
            " JOIN maker_rights mri ON mri.id = mrr.maker_rights_id  " +
            "WHERE " +
            " user_id = ( SELECT parent_user_id FROM maker_invitation WHERE user_id = #{user_id} LIMIT 1 ) " +
            " AND mri.rights_key = #{key}")
    Map<String, Object> selectMakerByChildUserId(@Param("user_id") Long userId,@Param("key") String makerRightKey);

    /**
     * 查找上级邀请人信息
     *
     * @param createTime
     * @param transactionType
     * @param ew
     * @return
     */
    Map<String,Object> foundInviter(@Param("order_time") Date createTime, @Param("transaction_type") Integer transactionType, @Param("ew") Wrapper ew);

    /**
     * 用户邀请信息表
     * @param id
     * @return
     */
    @Select("select parent_user_id from maker_invitation where user_id = #{id} limit 1")
    Long getInvitationUserId(@Param("id") Long id);

    /**
     * 二级邀请人（必须为创客）
     * @param ew
     */

    Map<String,Object> foundTwoLevelInviter(@Param("ew") EntityWrapper ew);

    /**
     * 获取满足条件的创客储值优惠券发放数据
     * @param ew
     * @return
     */
    @Select("<script>" +
            "SELECT " +
            " tb.id as transaction_bill_id,m.user_id,tb.stored_config_id " +
            "FROM " +
            " transaction_bill tb " +
            " JOIN maker m ON m.user_id = tb.user_id " +
            "<where>" +
            "${ew.sqlSegment}" +
            "</where>" +
            "</script>")
    List<Map<String,Object>> getStoredValueMakerYesterday(@Param("ew") Wrapper ew);

    /**
     * 通过用户ID获取其创客信息
     * @param userId
     * @return
     */
    @Select({"<script> " +
            "SELECT CONCAT(LEFT(u.phone,3),'****',RIGHT(u.phone, 4)) AS phone, u.avatar, CONCAT(mr.ranks_name, '创客') AS ranks_name " +
            "FROM maker m " +
            "LEFT JOIN maker_ranks mr ON m.maker_ranks_id = mr.id " +
            "LEFT JOIN USER u ON m.user_id = u.id " +
            "WHERE u.id = #{user_id}" +
            "</script>"})
    Map<String, Object> selectMakerByUserId(@Param("user_id") Long userId);

}
