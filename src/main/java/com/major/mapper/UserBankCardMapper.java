package com.major.mapper;

import com.major.entity.UserBankCard;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户关联银行卡表 Mapper 接口
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-10-24
 */
public interface UserBankCardMapper extends BaseMapper<UserBankCard> {


    @Select({
            "<script>"+
                    "select id,target_type,target_id,cardholder,card_number,bank_name,banks_id,card_type,status,create_time from user_bank_card <where> ${ew.sqlSegment} </where> " +
                    "</script>"})
    List<Map<String,Object>> selectUserBankCardPageByUserId(Pagination page,  @Param("ew") Wrapper ew);

}
