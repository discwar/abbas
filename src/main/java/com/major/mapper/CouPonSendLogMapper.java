package com.major.mapper;

import com.major.entity.CouPonSendLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-06
 */
public interface CouPonSendLogMapper extends BaseMapper<CouPonSendLog> {

    /**
     * 查询优惠券发送记录
     * @param page
     * @param couponName
     * @param couponContent
     * @param couponType
     * @param deadlineStartTime
     * @param deadlineStopTime
     * @param createStartTime
     * @param createStopTime
     * @return
     */
    List<Map<String, Object>> selectSendLogList(Pagination page,
                                               @Param("couponName") String couponName,
                                               @Param("couponContent") String couponContent,
                                               @Param("couponType") Integer couponType,
                                               @Param("deadlineStartTime") String deadlineStartTime,
                                               @Param("deadlineStopTime") String deadlineStopTime,
                                               @Param("createStartTime") String createStartTime,
                                               @Param("createStopTime") String createStopTime);

}
