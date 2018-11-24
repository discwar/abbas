package com.major.mapper;

import com.major.entity.MoneyLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商家资金流水表 Mapper 接口
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-11-22
 */
public interface MoneyLogMapper extends BaseMapper<MoneyLog> {

    /**
     * 查询资金流水
     * @param page
     * @param ew
     * @return
     */
    @Select({
            "<script>"+
                   "SELECT id,sys_user_id,source,mark,money,remarks,create_time FROM money_log ml "+
                    " <where> ${ew.sqlSegment} </where> " +
                    "</script>"})
    List<Map<String,Object>> selectMoneyLogPageBySysUserId(Pagination page, @Param("ew") Wrapper ew);


}
