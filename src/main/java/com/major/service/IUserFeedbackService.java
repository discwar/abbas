package com.major.service;

import com.major.entity.UserFeedback;
import com.major.model.request.UserFeedbackRequest;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户反馈表 服务类
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-08-17
 */
public interface IUserFeedbackService extends IService<UserFeedback> {

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
    Page<Map<String, Object>> selectUserFeedbackPage(Page<Map<String, Object>> page,String content, String createTimeStart, String createTimeStop,
                                                     String phone, Integer status, Integer isPictures);

    /**
     * 答复反馈
     * @param userFeedbackRequest
     * @param feedBackId
     * @return
     */
    boolean replyFeedback(UserFeedbackRequest userFeedbackRequest,Long feedBackId);

    /**
     * 获取反馈详情
     * @param feedBackId
     * @return
     */
    Map<String, Object> selectUserFeedbackById( Long feedBackId);
}
