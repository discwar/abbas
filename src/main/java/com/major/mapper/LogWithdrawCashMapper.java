package com.major.mapper;

import com.major.entity.LogWithdrawCash;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 提现记录表 Mapper 接口
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-09-20
 */
public interface LogWithdrawCashMapper extends BaseMapper<LogWithdrawCash> {


    // TODO 如果所属对象是商家需把shop表jion进来查询
    /**
     * 提现记录表
     * @param page
     * @param targetType
     * @param cashStatus
     * @return
     */
    @Select({
            "<script> "+
                    "   SELECT lw.id,lw.target_type,lw.target_id,lw.money,lw.cardholder,lw.card_number,lw.bank_name,  " +
                    "   lw.cash_status,lw.cash_remarks,lw.create_time,u.nickname  " +
                    "   FROM log_withdraw_cash lw    " +
                    "   LEFT JOIN `user` u  ON lw.target_id=u.id  " +
                    "      WHERE   1=1  " +
                    "        <if test='targetType!=null '>  " +
                    "            AND lw.target_type =#{targetType}  " +
                    "        </if>  " +
                    "        <if test='cashStatus!=null '>  " +
                    "            AND lw.cash_status =#{cashStatus}  " +
                    "        </if>  " +
                    "        <if test='nickname!=null '>  " +
                    "            AND u.nickname like concat('%',#{nickname},'%')   " +
                    "        </if>  " +
                    "        <if test='bankName!=null '>  " +
                    "            AND lw.bank_name =#{bankName}  " +
                    "        </if>  " +
                    "        ORDER BY  " +
                    "       lw.cash_status, lw.create_time DESC "+
                    "</script>"}  )
    List<Map<String, Object>> selectLogWithdrawCashPage(Pagination page,
                                               @Param("targetType") Integer targetType,
                                               @Param("cashStatus") Integer cashStatus,@Param("nickname") String nickname,@Param("bankName") String bankName);

    /**
     * 获取当前
     * @param id
     * @return
     */
    @Select(" SELECT lw.id,lw.target_type,lw.target_id,lw.money,lw.cardholder,lw.card_number,lw.bank_name, " +
            "  lw.cash_status,lw.cash_remarks,lw.create_time,u.nickname" +
            "  FROM log_withdraw_cash lw    " +
            "  LEFT JOIN `user` u  ON lw.target_id=u.id  where lw.id=#{id} ")
    Map<String, Object> selectLogWithdrawCashById(@Param("id") Long id);

}
