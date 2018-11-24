package com.major.mapper;

import com.major.entity.MakerIncomeMonth;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 创客月度收益表 Mapper 接口
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-08-17
 */
public interface MakerIncomeMonthMapper extends BaseMapper<MakerIncomeMonth> {

    /**
     * 从账单交易表中统计上月创客收益情况
     * @param ew
     * @return
     */
    @Select({"<script> " +
            "SELECT m.id AS maker_id, u.id AS user_id, IFNULL(SUM(tb.money), 0) AS income, DATE_FORMAT(tb.create_time, '%Y-%m-%d') AS income_time " +
            "FROM maker m " +
            "LEFT JOIN USER u ON m.user_id = u.id " +
            "LEFT JOIN transaction_bill tb ON u.id = tb.user_id  " +
            "<where> ${ew.sqlSegment} </where>" +
            "</script>"})
    List<MakerIncomeMonth> selectMakerIncomeLastMonth(@Param("ew") Wrapper ew);
}
