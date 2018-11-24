package com.major.service;

import com.major.entity.Activity;
import com.major.model.request.ActivityRequest;
import com.major.model.request.UpdateActivityRequest;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/18 19:36      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public interface IActivityService extends IService<Activity> {

    /**
     * 通用-添加活动
     * @param activityRequest
     * @return
     * @throws Exception
     */
    boolean addActivity(ActivityRequest activityRequest) ;

    /**
     * 通用-修改活动
     * @param updateActivityRequest
     * @param activity_Id
     * @return
     */
    boolean updateActivity(UpdateActivityRequest updateActivityRequest, Long activity_Id);

    /**
     * 通用-删除活动
     * @param activity_Id
     * @return
     * @throws Exception
     */
    boolean deleteActivity(Long activity_Id);

    /**
     * 通用-获取当前活动信息
     * @param activity_Id
     * @return
     */
    Map<String,Object> selectActivityById(Long activity_Id);

    /**
     * 管理员使用-获取活动分页列表
     * 只筛选没有关联水果店的爱果小店的活动
     * @param page
     * @param goodsName
     * @param activityStatus
     * @param activityType
     * @return
     */
    Page<Map<String, Object>> selectActivityPage(Page<Map<String, Object>> page, String goodsName,Integer activityStatus, Integer activityType);

    /**
     * 商家使用获取-获取活动分页列表
     * @param page
     * @param sysUserId
     * @param goodsName
  * @param activityStatus
     * @param activityType
     * @return
     */
    Page<Map<String, Object>> selectActivityByShopIdPage(Page<Map<String, Object>> page, Long sysUserId,String goodsName,Integer activityStatus, Integer activityType);

    /**
     * 活动上架下架
     * @param activityId
     * @param type
     * @return
     */
    boolean shelvesActivity( Long activityId,Integer type);

    /**
     * 针对退款处理，加回活动商品库存和减回订单量
     * 更新活动商品库存及订单量
     * @param goodsId
     * @param goodsNum
     * @return
     */
    boolean cancelActivityStock(Long goodsId, Long goodsNum);

    /**
     * 删除redis中的活动信息
     * @param goodsId
     */
    void deleteRedisActivityInfo(Long goodsId);
}
