package com.major.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.major.common.constant.Constants;
import com.major.common.enums.MessageTypeEnum;
import com.major.common.enums.StatusResultEnum;
import com.major.common.exception.AgException;
import com.major.common.util.GeTuiUtils;
import com.major.config.GeTuiConfig;
import com.major.entity.*;
import com.major.mapper.MessageNotifyMapper;
import com.major.model.request.MessageNotifyRequest;
import com.major.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 消息通知表 服务实现类
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-08-14
 */
@Service
@Slf4j
public class MessageNotifyServiceImpl extends ServiceImpl<MessageNotifyMapper, MessageNotify> implements IMessageNotifyService {

    @Autowired
    private GeTuiConfig geTuiConfig;

    @Autowired
    private IUserGroupService userGroupService;

    @Autowired
    private IUserGroupFieldService userGroupFieldService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private ISysJobService sysJobService;


    @Override
    public MessageNotify selectMessageNotifyById(Long messageId){
       return super.selectById(messageId);
    }

    @Override
    public boolean deleteMessageNotify(Long messageId) {
        MessageNotify message=new MessageNotify();
        message.setId(messageId);
        return message.deleteById();
    }

    @Override
    public Page<Map<String, Object>> selectMessageNotifyPage(Page<Map<String, Object>> page, String title, Integer status,
                                                             String sendTimeStart, String sendTimeStop,String createTimeStart, String createTimeStop) {
        return  page.setRecords(baseMapper.selectMessageNotifyPage(page,title,status,sendTimeStart,sendTimeStop,createTimeStart,createTimeStop));
    }
    @Override
    public String addMessageNotify(MessageNotifyRequest messageNotifyRequest) {
        if(Constants.MSG_TYPE_WAIT.equals(messageNotifyRequest.getIsSendNow()) && messageNotifyRequest.getOrderSendTime()==null){
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"请填写预约时间");
        }
        if(messageNotifyRequest.getTitle().length()>Constants.DB_FIELD_255) {
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"通知标题过长");
        }
        if(messageNotifyRequest.getContent().length()>Constants.DB_FIELD_255) {
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"通知内容过长");
        }
        //先添加消息表
        MessageNotify message=new MessageNotify();
        BeanUtils.copyProperties(messageNotifyRequest, message);
        //默认为发送成功
        message.setMsgStatus(Constants.MSG_SENT);
        message.setStatus(Constants.STATUS_NORMAL);
        //表示预约发送
        if(Constants.MSG_TYPE_WAIT.equals(messageNotifyRequest.getIsSendNow()) ) {
            //表示预定中
            message.setMsgStatus(Constants.MSG_YD);
            super.insert(message);
            // 放到任务中（待执行）
            String params = String.valueOf(message.getId());
            SysJob messageStartJob = sysJobService.getMessageStartJob(params, messageNotifyRequest.getOrderSendTime());
            messageStartJob.setJobType(Constants.SYS_JOB_TYPE_TEMPORARY);
            sysJobService.addJob(messageStartJob);
            return this.SendMessageNow(messageNotifyRequest, message.getId(),Constants.MSG_TYPE_WAIT);
        }
        message.setSendTime(new Date());
        super.insert(message);
        //表示立即发送
      return this.SendMessageNow(messageNotifyRequest, message.getId(),Constants.MSG_TYPE_NOW);
    }

    @Override
    public  List<MessageNotify> selectMessageNotifyByJob() {
        return baseMapper.selectMessageNotifyByJob();
   }
    @Override
   public boolean updateMessageNotify(Long messageId){
       MessageNotify message=new MessageNotify();
       message.setId(messageId);
       message.setSendTime(new Date());
       message.setMsgStatus(Constants.MSG_SENT);
        return super.updateById(message);
   }

    /**
     * 立即发送、预约发送、定时器任务发送公用方法
     * 通用于预约发送和立即发送，当为预约发送时，展示时间不能为空
     * @param messageNotifyRequest
     * @param messageId
     * @return
     */
    @Override
    public String SendMessageNow(MessageNotifyRequest messageNotifyRequest,Long messageId,Integer messageType){
        //1.查看用户组表信息
        UserGroup userGroup=userGroupService.selectUserGroupById(messageNotifyRequest.getUserGroupId());
        if(userGroup==null ) {
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"该群组不存在");
        }
        //2.用户群组关联表信息
        List<Map<String,String>> userGroupList=userGroupFieldService.selectUserGroupFieldByGroupId(userGroup.getId());
        if(userGroupList==null && userGroupList.size()<=0) {
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"该群组信息不存在");
        }
        String userSize=null;
        try{
            switch (userGroup.getSearchType()) {
                //0-xx刚注册的用户
                case  Constants.SEARCH_TYPE_ZHU_CE :
                    String fieldValue=null;
                    for(int i=0;i<userGroupList.size();i++) {
                        fieldValue=userGroupList.get(0).get("field_value");
                    }
                    List<User> userList=userService.selectUserSearchCreateTime(fieldValue);
                    userSize= this.sendMessageLogTime(userList,messageNotifyRequest,messageType);
                 break;

                //1-xx消费超过x元的用户
                case   Constants.SEARCH_TYPE_XIAO_FEI:
                    userSize= this.sendMessage(userGroupList,messageNotifyRequest,Constants.SEARCH_TYPE_XIAO_FEI,messageType);
                break;

                // 2-xx消费未超过x元的用户
                case   Constants.SEARCH_TYPE_WEI_XIAO_FEI:
                    userSize=  this.sendMessage(userGroupList,messageNotifyRequest,Constants.SEARCH_TYPE_WEI_XIAO_FEI,messageType);
                break;

                //3-24内未活跃的用户--此条件不需要获取筛选条件直接定义
                case   Constants.SEARCH_TYPE_WEI_HUO_YUE:
                    List<User> userList2=userService.selectUserByLoginTime();
                    userSize= this.sendMessageLogTime(userList2,messageNotifyRequest,messageType);
                break;

                //所有用户--此条件不需要获取筛选条件直接定义
                case   Constants.SEARCH_TYPE_ALL:
                      List<User> userList3=userService.selectAllUser();
                    userSize=   this.sendMessageLogTime(userList3,messageNotifyRequest,messageType);
                break;

                default:
                    break;
            }
        }catch (Exception e){
            //有异常修改消息状态为失败
            MessageNotify messageNew=new MessageNotify();
            messageNew.setId(messageId);
            messageNew.setMsgStatus(Constants.MSG_FAIL);
            super.updateById(messageNew);
            throw new AgException(StatusResultEnum.MSG_NOTIFY_FAIL, e.getMessage());
        }
        return userSize;
    }

    /**
     * 立即发送、预约发送、定时器任务发送公用方法
     * 针对分组类型为0和3/4的的通用发送方法
     * @param userList
     * @param messageNotifyRequest
     * @param messageType
     * @return
     * @throws Exception
     */
    public String  sendMessageLogTime( List<User> userList,MessageNotifyRequest messageNotifyRequest,Integer messageType)throws Exception {
        if(userList==null || userList.size()<=0) {
            return Constants.MSG_SUCCESS_SIZE+userList.size()+"位用户";
        }
        List<Message> messageList=new ArrayList<>();
        for(User user :userList) {
            geTuiConfig.setClientId(user.getClientId());
            geTuiConfig.setTitle(messageNotifyRequest.getTitle());
            geTuiConfig.setText(messageNotifyRequest.getContent());
            //透传消息格式
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title",messageNotifyRequest.getTitle());
            jsonObject.put("content",messageNotifyRequest.getContent());
            geTuiConfig.setTransmissionContent(jsonObject.toString());
            //预约发送的时候不添加个推消息,个推id为空时不发
            if(!Constants.MSG_TYPE_WAIT.equals(messageType)){
                if(StringUtils.isNotEmpty(geTuiConfig.getClientId())){
                    GeTuiUtils.pushToSingle(geTuiConfig,null,null);
                }
            }
                //添加消息表
                Message message=new Message();
                message.setUserId(Long.valueOf(user.getId().toString()));
                message.setTitle(messageNotifyRequest.getTitle());
                message.setContent(messageNotifyRequest.getContent());
                message.setMessageTypeId(MessageTypeEnum.SYSTEM_NOTIFY.getValue().longValue());
                messageList.add(message);
            }
        //只有当立即发送和定时器任务发送时 添加消息表
        if(Constants.MSG_TYPE_NOW.equals(messageType) || Constants.MSG_TYPE_JOB.equals(messageType)){
            messageService.addMessageList(messageList);
        }

        return Constants.MSG_SUCCESS_SIZE+userList.size()+"位用户";
    }

    /**
     * 立即发送、预约发送、定时器任务发送公用方法
     * 针对分组类型为:1-xx消费超过x元的用户;2-xx消费未超过x元的用户的发送方法
     * @param userGroupList
     * @param messageNotifyRequest
     * @param type
     * @param messageType
     * @return
     * @throws Exception
     */
    public String sendMessage( List<Map<String,String>> userGroupList,MessageNotifyRequest messageNotifyRequest,Integer type,Integer messageType)throws Exception {
        String realTotalAmount=null;
        String startTime=null;
        String stopTime=null;
        //一个分组只有一条对应的数据
        for(int i=0;i<userGroupList.size();i++) {
            //field_config表字段
            //未超过消费金额
            if(Constants.SEARCH_TYPE_WEI_XIAO_FEI==type && userGroupList.get(i).get("field_code").equals("not_exceed_amount")){
                realTotalAmount=userGroupList.get(i).get("field_value");
            }
            //超过消费金额
            if(Constants.SEARCH_TYPE_XIAO_FEI==type && userGroupList.get(i).get("field_code").equals("exceed_amount")){
                realTotalAmount=userGroupList.get(i).get("field_value");
            }
            if(userGroupList.get(i).get("field_code").equals("start_time")){
                startTime=userGroupList.get(i).get("field_value");
            }
            if(userGroupList.get(i).get("field_code").equals("stop_time")){
                stopTime=userGroupList.get(i).get("field_value");
            }
        }
        //根据上部分组条件查询用户，得到userId集合
        List<Map<String,Object>> userList=null;
        if(Constants.SEARCH_TYPE_XIAO_FEI==type) {
            userList=  userService.selectUserOrderByExceedAmount(realTotalAmount,startTime,stopTime);
        }else{
            userList= userService.selectUserOrderByNotExceedAmount(realTotalAmount,startTime,stopTime);
        }

        if(userList==null || userList.size()<=0){
            return Constants.MSG_SUCCESS_SIZE+"0位用户";
        }
        //根据筛选出来的用户组id，发送消息
        List<Message> messageList=new ArrayList<>();
        for(int i=0;i<userList.size();i++) {
            Message message=new Message();
            geTuiConfig.setClientId((String)userList.get(i).get("client_id"));
            geTuiConfig.setTitle(messageNotifyRequest.getTitle());
            geTuiConfig.setText(messageNotifyRequest.getContent());
            //透传消息格式
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title",messageNotifyRequest.getTitle());
            jsonObject.put("content",messageNotifyRequest.getContent());
            geTuiConfig.setTransmissionContent(jsonObject.toString());
            //预约发送的时候不添加个推消息,个推id为空时不发
            if(!Constants.MSG_TYPE_WAIT.equals(messageType)){
                if(StringUtils.isNotEmpty(geTuiConfig.getClientId())){
                    GeTuiUtils.pushToSingle(geTuiConfig,null,null);
                }
            }
            //添加消息表
            message.setUserId(Long.valueOf(userList.get(i).get("user_id").toString()));
            message.setTitle(messageNotifyRequest.getTitle());
            message.setContent(messageNotifyRequest.getContent());
            message.setMessageTypeId(MessageTypeEnum.SYSTEM_NOTIFY.getValue().longValue());
            messageList.add(message);
        }
        //只有当立即发送和定时器任务发送时 添加消息表
        if(Constants.MSG_TYPE_NOW.equals(messageType) || Constants.MSG_TYPE_JOB.equals(messageType)){
            messageService.addMessageList(messageList);
        }
        return Constants.MSG_SUCCESS_SIZE+userList.size()+"位用户";
    }


}
