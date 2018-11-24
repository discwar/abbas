package com.major.mapper;

import com.major.entity.LogUserScore;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户积分记录表 Mapper 接口
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-08-17
 */
public interface LogUserScoreMapper extends BaseMapper<LogUserScore> {


    @Select({
            "<script>"+
                    "select id,user_id,score,source,mark,remark,content,create_time  from log_user_score <where> ${ew.sqlSegment} </where> " +
                    "</script>"})
    List<Map<String, Object>> selectLogUserScorePageByUserId(Pagination page, @Param("ew") Wrapper ew);

    /**
     * 获取用户的当前积分数和历史积分数
     * @param userId
     * @return
     */
    @Select("SELECT SUM(los.score) as total_score_now  ,u.total_score,u.id FROM `user` u  " +
            "LEFT JOIN log_user_score los ON los.user_id=u.id  WHERE u.id=#{userId}")
    Map<String ,Object> selectLogUserScoreInfoByUserId(@Param("userId") Long userId);

}
