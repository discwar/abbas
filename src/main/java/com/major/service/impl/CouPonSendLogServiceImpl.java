package com.major.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.major.common.constant.Constants;
import com.major.common.enums.StatusResultEnum;
import com.major.common.exception.AgException;
import com.major.entity.*;
import com.major.mapper.CouPonSendLogMapper;
import com.major.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-06
 */
@Service
public class CouPonSendLogServiceImpl extends ServiceImpl<CouPonSendLogMapper, CouPonSendLog> implements ICouPonSendLogService {

    @Autowired
    private ICouponService couponService;

    @Autowired
    private IUserCouponService userCouponService;

    @Autowired
    private IUserGroupService userGroupService;

    @Autowired
    private IUserGroupFieldService userGroupFieldService;
    @Autowired
    private IUserService userService;

    @Override
    public Page<Map<String, Object>> selectSendLogPage(Page<Map<String, Object>> page, String couponName, String couponRule, Integer couponType, String deadlineStartTime, String deadlineStopTime,
                                           String createStartTime, String createStopTime) {

        return page.setRecords(baseMapper.selectSendLogList(page, couponName, couponRule,couponType,
                deadlineStartTime,deadlineStopTime,createStartTime,createStopTime));
    }
    @Override
    public boolean sendLog(Long coupon_id,Long groupId,Long user_id) {
        Coupon coupon=couponService.selectCouponById(coupon_id);
        if(coupon == null) {
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"找不到该优惠券");
        }
        //1.查看用户组表信息
        UserGroup userGroup=userGroupService.selectUserGroupById(groupId);
        if(userGroup==null ) {
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"该群组不存在");
        }
        //2.用户群组关联表信息
        List<Map<String,String>> userGroupList=userGroupFieldService.selectUserGroupFieldByGroupId(userGroup.getId());
        if(userGroupList==null && userGroupList.size()<=0) {
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"该群组信息不存在");
        }
        //满足条件的userIdList
        List<String> userIdlist=new ArrayList<>();
        //表示xx刚注册的用户
        if(Constants.SEARCH_TYPE_ZHU_CE==userGroup.getSearchType()) {
            String fieldValue=null;
            for(int i=0;i<userGroupList.size();i++) {
                fieldValue=userGroupList.get(0).get("field_value");
            }
            List<User> userList=userService.selectUserSearchCreateTime(fieldValue);
            userIdlist=this.getListUserId(userList);
        }
        //1-xx消费超过x元的用户
        if(Constants.SEARCH_TYPE_XIAO_FEI==userGroup.getSearchType()) {
            userIdlist=this.getListUserIdByType(userGroupList,Constants.SEARCH_TYPE_XIAO_FEI);
        }
        // 2-xx消费未超过x元的用户
        if(Constants.SEARCH_TYPE_WEI_XIAO_FEI==userGroup.getSearchType()) {
            userIdlist=this.getListUserIdByType(userGroupList,Constants.SEARCH_TYPE_WEI_XIAO_FEI);
        }
        //3-24内未活跃的用户--此条件不需要获取筛选条件直接定义
        if(Constants.SEARCH_TYPE_WEI_HUO_YUE==userGroup.getSearchType()){
            List<User> userList=userService.selectUserByLoginTime();
            userIdlist=this.getListUserId(userList);
        }
        //4-所有用户--此条件不需要获取筛选条件直接定义
        if (Constants.SEARCH_TYPE_ALL==userGroup.getSearchType()){
            List<User> userList=userService.selectAllUser();
            userIdlist=this.getListUserId(userList);
        }
        CouPonSendLog couPonSendLog =new CouPonSendLog();

        couPonSendLog.setCouponId(coupon_id.intValue());
        couPonSendLog.setUserGroupsId(groupId.intValue());
        couPonSendLog.setSendNumber(String.valueOf(userIdlist.size()));
        couPonSendLog.setCreatorId(user_id.intValue());
        couPonSendLog.setStatus(Constants.STATUS_NORMAL);
        super.insert(couPonSendLog);
        List<UserCoupon> userCouponList=new ArrayList<>();
        //一个用户限制只能拥有一张同种未使用的优惠券，所以在派发优惠券的时候，当用户存在和当前要派发的优惠券一样时，不派发
        for(String userId : userIdlist){
            List<UserCoupon> couponList=userCouponService.selectUserCouponListByUserId(Long.valueOf(userId),coupon_id);
            if(couponList!=null && couponList.size()>0){
                continue;
            }
            UserCoupon userCoupon=new UserCoupon();
            userCoupon.setUserId(Integer.valueOf(userId));
            //加入用户优惠券过期时间
            userCoupon.setDeadline(coupon.getDeadline());
            userCoupon.setCouponId(coupon_id.intValue());
            userCoupon.setCouponSource(Constants.COUPON_SOUTCE_SYSTEM);
            userCoupon.setStatus(Constants.COUPON_STATUS_USABLE);
            userCouponList.add(userCoupon);
        }
        if(userCouponList==null || userCouponList.size()==0){
            return true;
        }
        return   userCouponService.addUserCouponList(userCouponList);

    }


    /**
     * 针对分组类型为0和3和4的的获取用户Id集合的方法
     *
     * @param userList
     * @return
     */
    public List<String>  getListUserId(List<User> userList){
        List<String> userIdList=new ArrayList<>();
        if(userList==null || userList.size()<=0) {
            return userIdList;
        }
        for(User user :userList) {
            userIdList.add(user.getId().toString());
        }
        return userIdList;
    }

    /**
     * 针对分组类型为:1-xx消费超过x元的用户;2-xx消费未超过x元的用户 获取用户Id集合的方法
     * @param userGroupList
     * @param type
     * @return
     */
    public List<String>  getListUserIdByType( List<Map<String,String>> userGroupList,Integer type){
        List<String> userIdList=new ArrayList<>();
        String realTotalAmount=null;
        String startTime=null;
        String stopTime=null;
        //一个分组只有一条对应的数据
        for(int i=0;i<userGroupList.size();i++) {
            if(userGroupList.get(i).get("field_code").equals("not_exceed_amount")){
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
            return null;
        }
        //根据筛选出来的用户组id，发送消息
        for(int i=0;i<userList.size();i++) {
            userIdList.add(userList.get(i).get("user_id").toString());
          }
        return userIdList;
    }

}
