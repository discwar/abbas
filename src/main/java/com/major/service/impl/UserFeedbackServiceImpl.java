package com.major.service.impl;

import com.major.common.constant.Constants;
import com.major.common.enums.MessageTypeEnum;
import com.major.common.enums.StatusResultEnum;
import com.major.common.exception.AgException;
import com.major.common.util.GeTuiUtils;
import com.major.config.GeTuiConfig;
import com.major.entity.Message;
import com.major.entity.User;
import com.major.entity.UserFeedback;
import com.major.mapper.UserFeedbackMapper;
import com.major.model.request.UserFeedbackRequest;
import com.major.service.IMessageService;
import com.major.service.IUserFeedbackService;
import com.major.service.IUserService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户反馈表 服务实现类
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-08-17
 */
@Service
public class UserFeedbackServiceImpl extends ServiceImpl<UserFeedbackMapper, UserFeedback> implements IUserFeedbackService {

    @Autowired
    private GeTuiConfig geTuiConfig;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IUserService userService;
     @Override
      public   Page<Map<String, Object>> selectUserFeedbackPage(Page<Map<String, Object>> page,String content, String createTimeStart, String createTimeStop,
                                                                String phone, Integer status, Integer isPictures){
          return page.setRecords(baseMapper.selectUserFeedbackPage(page,content,createTimeStart,createTimeStop,phone,
                  status,isPictures));
      }

    @Override
    public boolean replyFeedback(UserFeedbackRequest userFeedbackRequest, Long feedBackId){
        UserFeedback userFeedback=selectById(feedBackId);
       if(userFeedbackRequest.getReplyContent().length()>Constants.DB_FIELD_512) {
           throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"答复内容过长");
       }
        try {
            User user=userService.selectUserById(userFeedback.getUserId());
            geTuiConfig.setClientId(user.getClientId());
            geTuiConfig.setTitle(MessageTypeEnum.AI_OFFICIAL_REPLY.getDesc());
            geTuiConfig.setText(userFeedbackRequest.getReplyContent());
            //透传消息格式
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title",MessageTypeEnum.AI_OFFICIAL_REPLY.getDesc());
            jsonObject.put("content","您的反馈:"+userFeedbackRequest.getReplyContent());
            geTuiConfig.setTransmissionContent(jsonObject.toString());
            if(StringUtils.isNotEmpty(user.getClientId())){
                GeTuiUtils.pushToSingle(geTuiConfig,null,null);
            }
            //添加消息表
            Message message=new Message();
            message.setUserId(Long.valueOf(userFeedback.getUserId().toString()));
            message.setTitle(userFeedbackRequest.getReplyContent());
            message.setContent("您的反馈:"+userFeedback.getContent());
            message.setMessageTypeId(MessageTypeEnum.AI_OFFICIAL_REPLY.getId());
            messageService.addMessage(message);

        }catch (Exception e){
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"个推发送失败");
        }

        BeanUtils.copyProperties(userFeedbackRequest, userFeedback);
        //已答复
        userFeedback.setStatus(Constants.STATUS_NORMAL);
        return super.updateById(userFeedback);
    }

    @Override
    public Map<String, Object> selectUserFeedbackById( Long feedBackId){
        Map<String, Object>  map=new HashMap<>(1);
        map.put("user_feedback_info",baseMapper.selectUserFeedbackById(feedBackId));
        return map;
    }

}
