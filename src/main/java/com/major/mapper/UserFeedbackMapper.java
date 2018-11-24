package com.major.mapper;

import com.major.entity.UserFeedback;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户反馈表 Mapper 接口
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-08-17
 */
public interface UserFeedbackMapper extends BaseMapper<UserFeedback> {


    /**
     * 获取反馈分页列表
     * @param page
     * @param createTimeStart
     * @param createTimeStop
     * @param phone
     * @param status
     * @param isPictures
     * @return
     */
    List<Map<String, Object>> selectUserFeedbackPage(Pagination page, @Param("content") String content,
                                                     @Param("createTimeStart") String createTimeStart, @Param("createTimeStop")  String createTimeStop,
                                                     @Param("phone") String phone, @Param("status") Integer status, @Param("isPictures") Integer isPictures);

    /**
     * 获取反馈详情
     * @param feedBackId
     * @return
     */
    @Select("SELECT  " +
            " u.username,u.phone,u.nickname, uf.content, " +
            " uf.`status`,uf.reply_content,uf.pictures_paths, " +
            " uf.user_id,uf.id AS feedback_id,uf.create_time  " +
            "FROM " +
            " user_feedback uf  " +
            "LEFT JOIN `user` u ON u.id = uf.user_id  " +
            "WHERE uf.id=#{feedBackId} ")
    Map<String, Object> selectUserFeedbackById(@Param("feedBackId") Long feedBackId);

}
