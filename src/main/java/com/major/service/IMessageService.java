package com.major.service;


import com.major.entity.Message;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 消息信息表 服务类
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-10-12
 */
public interface IMessageService extends IService<Message> {

    /**
     * 批量添加
     * @param messageList
     * @return
     */
    boolean addMessageList(List<Message> messageList);

    /**
     * 单条添加
     * @param message
     * @return
     */
    boolean addMessage(Message message);

    /**
     * 同时添加个推和添加消息表
     * @param userId
     * @param title
     * @param content
     * @return
     */
    void addGeTu( Long userId, String title, String content,Long messageTypeId);
}
