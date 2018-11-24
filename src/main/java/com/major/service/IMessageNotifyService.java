package com.major.service;

import com.major.entity.MessageNotify;
import com.major.model.request.MessageNotifyRequest;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 消息通知表 服务类
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-08-14
 */
public interface IMessageNotifyService extends IService<MessageNotify> {



    /**
     * 通用的保存消息
     * @param messageNotifyRequest
     * @return
     */
    String addMessageNotify(MessageNotifyRequest messageNotifyRequest);

    /**
     * 获取当前消息表
     * @param messageId
     * @return
     */
    MessageNotify selectMessageNotifyById(Long messageId);

    /**
     * 删除
     * @param messageId
     * @return
     */
    boolean deleteMessageNotify(Long messageId);

    /**
     * 分页
     * @param page
     * @param title
     * @param status
     * @param sendTimeStart
     * @param sendTimeStop
     * @param createTimeStart
     * @param createTimeStop
     * @return
     */
    Page<Map<String, Object>> selectMessageNotifyPage(Page<Map<String, Object>> page, String title, Integer status,
                                                      String sendTimeStart, String sendTimeStop, String createTimeStart, String createTimeStop);

    /**
     * 针对每天定时器使用，查询每天预约发送的消息
     * @return
     */
    List<MessageNotify> selectMessageNotifyByJob();



    /**
     * 修改预约发送消息的状态为已发送
     * @param messageId
     * @return
     */
    boolean updateMessageNotify(Long messageId);

    /**
     * 消息发送方法
     * @param messageNotifyRequest
     * @param messageId
     * @param messageType
     * @return
     */
    String SendMessageNow(MessageNotifyRequest messageNotifyRequest,Long messageId,Integer messageType);

}
