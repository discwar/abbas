package com.major.mapper;

import com.major.entity.OrderRefunds;
import com.major.model.response.OrderRefundsResponse;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 订单退款表 Mapper 接口
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-28
 */
public interface OrderRefundsMapper extends BaseMapper<OrderRefunds> {


    /**
     * 获取退款详情
     * @param refundsId
     * @return
     */
    @Select("SELECT os.id,os.order_id,os.refund_type,os.refund_money,os.problem_desc,os.img_urls,os.audit_status,os.create_time,orc.reason,os.refund_reason_id,os.refuse_reason " +
            "  FROM order_refunds os  " +
            "LEFT JOIN order_reason_config orc ON os.refund_reason_id=orc.id WHERE os.id=#{refundsId}")
    OrderRefundsResponse selectOrderRefundsById(@Param("refundsId") Long refundsId);


}
