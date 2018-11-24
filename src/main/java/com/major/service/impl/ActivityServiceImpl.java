package com.major.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.major.common.constant.Constants;
import com.major.common.constant.RedisConstants;
import com.major.common.constant.TaskConstants;
import com.major.common.enums.ActivityTypeEnum;
import com.major.common.enums.ShopTypeEnum;
import com.major.common.enums.StatusResultEnum;
import com.major.common.exception.AgException;
import com.major.common.util.DateUtils;
import com.major.entity.Activity;
import com.major.entity.GroupBuying;
import com.major.entity.SysJob;
import com.major.mapper.ActivityMapper;
import com.major.model.request.ActivityRequest;
import com.major.model.request.SysJobRequest;
import com.major.model.request.UpdateActivityRequest;
import com.major.model.response.GoodsResponse;
import com.major.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/18 19:37      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Service
@Slf4j
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private IGroupBuyingService groupBuyingService;

    @Autowired
    private ISysJobService sysJobService;

    @Autowired
    private IGoodsService goodsService;

    /**
     * 校验秒杀活动，并加入到redis中
     * @param activity
     */
    private boolean checkSecKillActivity(Activity activity) {
        if (activity.getSecKillLimitNum() == null) {
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR, "每人秒杀限制数量不可为空");
        }
        //校验活动时间段只能在三天的范围
        if(DateUtils.THREE_DAY_MINUTE < DateUtils.dateMethod(activity.getStartTime(),activity.getEndTime())){
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR, "秒杀的时间范围只能在三天之内");
        }
        super.insert(activity);
        // 活动的结束时间戳减去当前时间戳，即失效时间，如果大于零则添加redis缓存
        long expireTime = DateUtils.differentSeconds(DateUtils.getNowDate(), activity.getEndTime());
        if (expireTime > 0) {
            addActivityToRedis(activity,expireTime);
        }

        return true;
    }

    private void addActivityToRedis(Activity activity,Long expireTime){
        Long goodsId = activity.getGoodsId();
        String params = String.valueOf(activity.getId());
            // 放到任务中（待执行）
            SysJob secKillStartJob = sysJobService.getSecKillStartJob(params, activity.getStartTime());
            secKillStartJob.setJobType(Constants.SYS_JOB_TYPE_TEMPORARY);
            sysJobService.addJob(secKillStartJob);
            SysJob secKillEndJob = sysJobService.getSecKillEndJob(params, activity.getEndTime());
            secKillEndJob.setJobType(Constants.SYS_JOB_TYPE_TEMPORARY);
            sysJobService.addJob(secKillEndJob);

        // 将秒杀库存放入到redis列表中，并设置失效时间到活动结束
        this.addStockToRedis(activity.getTotalCount().intValue(), goodsId, expireTime);

        // 将活动信息添加到redis哈希中，并设置失效时间到活动结束
        this.addActivityToRedis(goodsId, activity, expireTime,null);
    }

    @Override
    public boolean addActivity(ActivityRequest activityRequest) {
        if(null==activityRequest.getGoodsId()) {
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR, "请选择商品");
        }
        // 一个店铺下一个商品只能添加一个活动
        List<Activity> activityList = baseMapper.selectActivityByGoodsIdAndShopId(activityRequest.getGoodsId(), activityRequest.getShopId());
        if (activityList != null && activityList.size() > 0) {
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR, "该商品已经添加过活动了");
        }
        //现价不能大于原价
        if(activityRequest.getSecKillPrice() !=null && activityRequest.getOriginalPrice()!=null){
            if(activityRequest.getSecKillPrice().compareTo(activityRequest.getOriginalPrice())>=0){
                throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"秒杀价不能大于等于原价");
            }
        }
        Activity activity = new Activity();
        BeanUtils.copyProperties(activityRequest, activity);
        activity.setStatus(Constants.STATUS_NORMAL);

        //如果是秒杀类型
        if (ActivityTypeEnum.SEC_KILL.getValue().equals(activityRequest.getActivityType())) {
            return this.checkSecKillActivity(activity);
        } else if(ActivityTypeEnum.GROUP_BUYING.getValue().equals(activityRequest.getActivityType())) {
            //如果是团购类型
            if(activityRequest.getGroupBuying()==null) {
                throw new AgException(StatusResultEnum.DB_SAVE_ERROR, "请选择团购价格");
            }
            super.insert(activity);
            //添加团购方案表
            List<GroupBuying> groupBuyingList = new ArrayList<>();
            JSONArray obj =JSONArray.parseArray(activityRequest.getGroupBuying());
            for (int i=0 ;i<obj.size();i++){
                String s=obj.getString(i);
                String[] a=s.split("-");
                GroupBuying groupBuying=new GroupBuying();
                groupBuying.setNumberLimit(Integer.valueOf(a[0]));
                BigDecimal bigDecimal=new BigDecimal(a[1]);
                groupBuying.setPrice(bigDecimal);
                groupBuying.setActivityId(activity.getId().intValue());
                groupBuyingList.add(groupBuying);
            }

            return groupBuyingService.addGroupBuyings(groupBuyingList);
        }

        return super.insert(activity);
    }

    @Override
    public boolean updateActivity(UpdateActivityRequest updateActivityRequest, Long activity_Id) {
        //现价不能大于原价
        if(updateActivityRequest.getSecKillPrice() !=null && updateActivityRequest.getOriginalPrice()!=null){
            if(updateActivityRequest.getSecKillPrice().compareTo(updateActivityRequest.getOriginalPrice())>0){
                throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"秒杀价不能大于原价");
            }
        }
        Activity activity = super.selectById(activity_Id);
        //修改活动的校验，活动的商品是否为普通商品类型
        GoodsResponse goodsResponse=goodsService.selectGoodsById(activity.getGoodsId());
        if(!Constants.ORDINARY.equals(goodsResponse.getPromotionType())){
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"活动商品只能为普通");
        }
        BeanUtils.copyProperties(updateActivityRequest, activity);

        // 针对秒杀类型
        if (ActivityTypeEnum.SEC_KILL.getValue().equals(activity.getActivityType())) {
            // 活动的结束时间戳减去当前时间戳，即失效时间，如果大于零则添加redis缓存
            long expireTime = DateUtils.differentSeconds(DateUtils.getNowDate(), activity.getEndTime());
            this.updateActivityJob(activity,expireTime);
        }
        return super.updateById(activity);
    }

    @Override
    public boolean deleteActivity(Long activity_Id) {
        Activity activity =selectById(activity_Id);
        Integer type=baseMapper.selectActivityType(activity.getId());
        if(Constants.ACTIVITY_TYPE_ONGOING.equals(type) ){
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"亲，等活动做完在删除吧！");
        }
        activity.setStatus(Constants.STATUS_DELETE);
        activity.setUpdateTime(new Date());
        boolean flag = activity.updateById();
        if (flag){
            deleteRedisActivityInfo(activity.getGoodsId());
        }
        //等待中时删除任务
        if(Constants.ACTIVITY_TYPE_WAIT.equals(type)){
            SysJob secKillStartJob=sysJobService.selectSysJobByNameAndParams(TaskConstants.ACTIVITY_JOB_NAME,TaskConstants.METHOD_SEC_KILL_START,activity.getId().toString());
            SysJob secKillEndJob=sysJobService.selectSysJobByNameAndParams(TaskConstants.ACTIVITY_JOB_NAME,TaskConstants.METHOD_SEC_KILL_END,activity.getId().toString());
            sysJobService.deleteScheduleJob(secKillStartJob.getId());
            sysJobService.deleteScheduleJob(secKillEndJob.getId());
        }
        return flag;
    }

    @Override
    public Map<String,Object> selectActivityById(Long activityId) {
        return  baseMapper.selectActivityInfoById(activityId);
    }

    @Override
    public Page<Map<String, Object>> selectActivityPage(Page<Map<String, Object>> page, String goodsName, Integer activityStatus, Integer activityType) {
        return page.setRecords(baseMapper.selectActivityPage(page, ShopTypeEnum.AIGUO_SHOP.getValue(),null,goodsName,activityStatus,activityType));
    }

    @Override
    public Page<Map<String, Object>> selectActivityByShopIdPage(Page<Map<String, Object>> page, Long sysUserId,String goodsName,Integer activityStatus, Integer activityType){
        return page.setRecords(baseMapper.selectActivityPage(page,null,sysUserId,goodsName,activityStatus,activityType));
    }

    /**
     * 将秒杀库存放入到redis列表中，并设置失效时间到活动结束
     * @param totalCount 活动库存数量
     * @param goodsId 商品ID
     * @param expireTime 失效时间，单位秒
     */
    private void addStockToRedis(Integer totalCount, Long goodsId, long expireTime) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < totalCount; i ++) {
            list.add("1");
        }
        redisService.lPushAll(RedisConstants.SEC_KILL_STOCK_PREFIX + goodsId, list, expireTime);
    }

    /**
     * 将活动信息添加到redis哈希中，并设置失效时间到活动结束（延迟1分钟）
     * @param goodsId
     * @param activity
     * @param expireTime
     */
    private void addActivityToRedis(Long goodsId, Activity activity, long expireTime,Integer type) {
        Map<String, Object> activityMap = new HashMap<>(7);
        activityMap.put("activityId", activity.getId());
        activityMap.put("startTime", activity.getStartTime());
        //当活动状态为进行中时不更新开始时间
        boolean b=redisService.exists(RedisConstants.SEC_KILL_ACTIVITY_PREFIX + goodsId,"startTime");
        if(b && Constants.ACTIVITY_TYPE_ONGOING.equals(type)){
            activityMap.remove("startTime");
        }
        activityMap.put("endTime", activity.getEndTime());
        activityMap.put("status", activity.getStatus());
        activityMap.put("secKillPrice", activity.getSecKillPrice());
        activityMap.put("originalPrice", activity.getOriginalPrice());
        activityMap.put("secKillLimitNum", activity.getSecKillLimitNum());
        redisService.pullAll(RedisConstants.SEC_KILL_ACTIVITY_PREFIX + goodsId, activityMap, expireTime + 60L);
    }

    @Override
    public  boolean shelvesActivity( Long activityId,Integer type){
        Activity activity=selectById(activityId);
        if(null==activity){
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"找不到该活动");
        }
        activity.setStatus(type);
        activity.setId(activityId);
        boolean flag = activity.updateById();
        if (flag){
            if (Constants.STATUS_ENABLE.equals(type)){
                long expireTime = DateUtils.differentSeconds(DateUtils.getNowDate(), activity.getEndTime());
                if (expireTime > 0) {
                    shelvesActivityJob(activity,expireTime,type);
                }
            }else {
                shelvesActivityJob(activity,null,type);
            }
        }
        return flag;
    }
    public Activity getActivityByGoodsId(Long goodsId) {
        Wrapper<Activity> ew = new EntityWrapper<>();
        ew.where("goods_id = {0}", goodsId)
                .and("status = {0}", Constants.STATUS_ENABLE);
        return super.selectOne(ew);
    }

    @Override
    public boolean cancelActivityStock(Long goodsId, Long goodsNum) {
        Activity activity = this.getActivityByGoodsId(goodsId);
        Long totalCount = activity.getTotalCount() + goodsNum;
        Long ordersCount = ObjectUtils.defaultIfNull(activity.getOrdersCount(),0L) - goodsNum;
        if (totalCount < 0L) {
            totalCount = 0L;
        }
        if (ordersCount < 0L) {
            ordersCount = 0L;
        }
        activity.setTotalCount(totalCount);
        activity.setOrdersCount(ordersCount);
        boolean flag = this.updateById(activity);
        if (flag){
            goodsService.changeRedisGoodsStock(goodsId,goodsNum.intValue(),Constants.ACTIVITY_GOODS,Constants.GOODS_NUM_ADD);
        }
        return flag;
    }

    @Override
    public void deleteRedisActivityInfo(Long goodsId){
        String secKillStockKey = RedisConstants.SEC_KILL_STOCK_PREFIX + goodsId;
        String activityRedisKeyName =  RedisConstants.SEC_KILL_ACTIVITY_PREFIX+goodsId;
        redisService.remove(secKillStockKey);
        redisService.remove(activityRedisKeyName);
    }

    private void  shelvesActivityJob(Activity activity,Long expireTime,Integer status){
        //上架：1.进行中：活动开始任务立即执行，活动结束任务更新（变更为正常状态）
        //      2.等待中：开启任务
        //      3.已结束：不变任务
        //下架：1.进行中：不变任务
        //      2.等待中：暂定任务
        //      3.已结束：不变任务
        Integer type=baseMapper.selectActivityType(activity.getId());
        SysJob secKillStartJob=sysJobService.selectSysJobByNameAndParams(TaskConstants.ACTIVITY_JOB_NAME,TaskConstants.METHOD_SEC_KILL_START,activity.getId().toString());
        SysJob secKillEndJob=sysJobService.selectSysJobByNameAndParams(TaskConstants.ACTIVITY_JOB_NAME,TaskConstants.METHOD_SEC_KILL_END,activity.getId().toString());
        if(null==secKillStartJob || null==secKillEndJob ){
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR, "请重新创建定时任务");
        }
        //上架
        if(Constants.STATUS_ENABLE.equals(status)){
             //进行中
            if(Constants.ACTIVITY_TYPE_ONGOING.equals(type)){
                sysJobService.runJob(secKillStartJob.getId());

                SysJobRequest secKillEndJobRequest=new SysJobRequest();
                BeanUtils.copyProperties(secKillEndJob, secKillEndJobRequest);
                secKillEndJobRequest.setQuartzTime(DateUtils.getCronExpression(activity.getEndTime(), 0));
                sysJobService.updateSysJob(secKillEndJobRequest,secKillEndJob.getId());
            }
            //等待中
            if(Constants.ACTIVITY_TYPE_WAIT.equals(type)){
                this.updateSysJob(secKillStartJob,secKillEndJob,activity.getStartTime(),activity.getEndTime());
            }
            // 移除redis缓存并更新
            deleteRedisActivityInfo(activity.getGoodsId());
            // 将秒杀库存放入到redis列表中，并设置失效时间到活动结束
            this.addStockToRedis(activity.getTotalCount().intValue(), activity.getGoodsId(), expireTime);
            // 将活动信息添加到redis哈希中，并设置失效时间到活动结束
            this.addActivityToRedis(activity.getGoodsId(), activity, expireTime,type);
        }
        //下架
        if(Constants.STATUS_DOWN.equals(status)) {
            deleteRedisActivityInfo(activity.getGoodsId());
            //等待中
            if(Constants.ACTIVITY_TYPE_WAIT.equals(type) ){
                sysJobService.pauseJob(secKillStartJob.getId());
                sysJobService.pauseJob(secKillEndJob.getId());
            }
        }
    }
    /**
     * 更新任务，并且设置成正常状态
     * @param secKillStartJob
     * @param secKillEndJob
     * @param startTime
     * @param endTime
     */
   private void updateSysJob(SysJob secKillStartJob, SysJob secKillEndJob,Date startTime,Date endTime){
       //更新秒杀开始定时任务
       SysJobRequest secKillStartJobRequest=new SysJobRequest();
       BeanUtils.copyProperties(secKillStartJob, secKillStartJobRequest);
       secKillStartJobRequest.setQuartzTime(DateUtils.getCronExpression(startTime, -5));
       sysJobService.updateSysJob(secKillStartJobRequest,secKillStartJob.getId());

       //更新秒杀结束定时任务
       SysJobRequest secKillEndJobRequest=new SysJobRequest();
       BeanUtils.copyProperties(secKillEndJob, secKillEndJobRequest);
       secKillEndJobRequest.setQuartzTime(DateUtils.getCronExpression(endTime, 0));
       sysJobService.updateSysJob(secKillEndJobRequest,secKillEndJob.getId());
   }

    private void updateActivityJob(Activity activity,Long expireTime){
        Integer type=baseMapper.selectActivityType(activity.getId());
        SysJob secKillStartJob=sysJobService.selectSysJobByNameAndParams(TaskConstants.ACTIVITY_JOB_NAME,TaskConstants.METHOD_SEC_KILL_START,activity.getId().toString());
        SysJob secKillEndJob=sysJobService.selectSysJobByNameAndParams(TaskConstants.ACTIVITY_JOB_NAME,TaskConstants.METHOD_SEC_KILL_END,activity.getId().toString());
        if(null==secKillStartJob || null==secKillEndJob ){
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR, "请重新创建定时任务");
        }
        //上架状态
        if(Constants.STATUS_NORMAL.equals(activity.getStatus())){
            //已完成：开启任务（暂停状态变更成正常状态）
            //进行中：更新任务（任务状态不变），会出现活动秒杀价前后不一致问题，缓存:更新redis数据，但活动开始时间不更新
            //等待中：更新任务（任务状态不变）
            this.updateSysJob(secKillStartJob,secKillEndJob,activity.getStartTime(),activity.getEndTime());
            if (expireTime > 0) {
                // 将秒杀库存放入到redis列表中，并设置失效时间到活动结束
                this.addStockToRedis(activity.getTotalCount().intValue(), activity.getGoodsId(), expireTime);
                // 将活动信息添加到redis哈希中，并设置失效时间到活动结束
                this.addActivityToRedis(activity.getGoodsId(), activity, expireTime,type);
            }
        }
        //下架状态
        if(Constants.STATUS_DOWN.equals(activity.getStatus())){
            //已完成：不做任何任务处理
            //进行中：不做任何任务处理
            //等待中：暂停任务
            if(Constants.ACTIVITY_TYPE_WAIT.equals(type) ){
                sysJobService.pauseJob(secKillStartJob.getId());
                sysJobService.pauseJob(secKillEndJob.getId());
            }
        }
    }


}
