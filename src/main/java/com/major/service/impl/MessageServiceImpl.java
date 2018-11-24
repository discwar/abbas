package com.major.service.impl;


import com.major.common.enums.StatusResultEnum;
import com.major.common.exception.AgException;
import com.major.common.util.GeTuiUtils;
import com.major.config.GeTuiConfig;
import com.major.entity.Message;
import com.major.entity.User;
import com.major.mapper.MessageMapper;
import com.major.service.IMessageService;
import com.major.service.IUserService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 消息信息表 服务实现类
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-10-12
 */
@Service
@Slf4j
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

    @Autowired
    private IUserService userService;
    @Autowired
    private GeTuiConfig geTuiConfig;

    @Override
    public  boolean addMessageList(List<Message> messageList) {
        return super.insertBatch(messageList);
    }

    @Override
    public boolean addMessage(Message message){
        return super.insert(message);
    }

    /**
     * 针对发送个推和添加消息表
     * @param userId
     * @param title
     * @param content
     */
    @Override
    public void addGeTu( Long userId, String title, String content,Long messageTypeId){
        User user=userService.selectUserById(userId);
        if(null==user){
            throw new AgException(StatusResultEnum.MSG_NOTIFY_FAIL,"找不到该用户");
        }
        Message message = new Message();
        message.setUserId(userId);
        message.setTitle(title);
        message.setContent(content);
        message.setMessageTypeId(messageTypeId);
        super.insert(message);

        // 只有个推cid不为空的时候发送推送
        if (StringUtils.isNotEmpty(user.getClientId())) {
            geTuiConfig.setClientId(user.getClientId());
            geTuiConfig.setTitle(title);
            geTuiConfig.setText(content);

            //透传消息格式
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", title);
            jsonObject.put("content", content);
            geTuiConfig.setTransmissionContent(jsonObject.toString());

            try {
                GeTuiUtils.pushToSingle(geTuiConfig, null, null);
            } catch (Exception e) {
                log.error("个推消息发送失败，userId:" + userId, e);
            }
        }
    }


}
