package com.major.mapper;

import com.major.entity.MakerIncomeDay;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 创客日收益表 Mapper 接口
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-19
 */
public interface MakerIncomeDayMapper extends BaseMapper<MakerIncomeDay> {

    /**
     * 统计昨天创客收益
     * @param ew
     * @return
     */
    @Select({"<script> " +
            "SELECT m.id AS maker_id, IFNULL(SUM(tb.money), 0) AS income, DATE_FORMAT(tb.create_time, '%Y-%m-%d') AS income_time " +
            "FROM transaction_bill tb " +
            "LEFT JOIN user u ON tb.user_id = u.id " +
            "LEFT JOIN maker m ON u.id = m.user_id " +
            "<where> ${ew.sqlSegment} </where>" +
            "</script>"})
    List<MakerIncomeDay> selectMakerIncomeLastMonth(@Param("ew") Wrapper ew);
}
