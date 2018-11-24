package com.major.mapper;

import com.major.entity.LogPay;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户支付日志记录表 Mapper 接口
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-10-11
 */
public interface LogPayMapper extends BaseMapper<LogPay> {

    /**
     * 支付日志分页
     * @param page
     * @param ew
     * @return
     */
    @Select({
            "<script>"+
                    "select lp.id,lp.user_id,u.nickname,lp.create_time,lp.pay_way,lp.bill_type,lp.transaction_type,lp.flow_no,lp.order_no,lp.money,  " +
                    " lp.outer_trade_no,lp.status,lp.original_data,lp.outer_original_data,lp.create_time   " +
                    "from log_pay lp  " +
                    " left join user u ON u.id=lp.user_id " +
                    " <where> ${ew.sqlSegment} </where> " +
                    "</script>"})
    List<Map<String, Object>> selectLogPayPage(Pagination page, @Param("ew") Wrapper ew);
}
