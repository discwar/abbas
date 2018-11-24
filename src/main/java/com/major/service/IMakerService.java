package com.major.service;

import com.major.entity.Maker;
import com.major.entity.UserCoupon;
import com.major.model.NewMaker;
import com.major.model.bo.RebateOrderBO;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 创客表 服务类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-19
 */
public interface IMakerService extends IService<Maker> {

    /**
     * 创客分页查询
     * @param page
     * @param phone
     * @param ranksId
     * @param createTimeStart
     * @param createTimeStop
     * @param inviteNumberStart
     * @param inviteNumberStop
     * @param totalIncomeStart
     * @param totalIncomeStop
     * @param sevenDayIncomeStart
     * @param sevenDayIncomeStop
     * @param incomeStart
     * @param incomeStop
     * @param makerNumberStart
     * @param makerNumberStop
     * @return
     */
    Page<Map<String, Object>> selectMakerPage(Page<Map<String, Object>> page, String phone, Integer ranksId, String createTimeStart, String createTimeStop,
                                              String inviteNumberStart, String inviteNumberStop, String totalIncomeStart, String totalIncomeStop,
                                              String sevenDayIncomeStart, String sevenDayIncomeStop, String incomeStart, String incomeStop, String makerNumberStart,
                                              String makerNumberStop);

    /**
     * 创客详情
     * @param userId
     * @param makerId
     * @return
     */
    Map<String,Object> selectMakerById(Long userId,Long makerId);

    /**
     * 根据当前用户获取创客邀请情况分页显示
     * @param page
     * @param userId
     * @param phone
     * @param isMaker
     * @param createTimeStart
     * @param createTimeStop
     * @return
     */
    Page<Map<String, Object>> selectMakerInvitationByUserId(Page<Map<String, Object>> page, Long userId,String phone,  Integer isMaker,  String createTimeStart, String createTimeStop);

    /**
     * 找昨天成为创客的用户列表
     * @return
     */
    List<NewMaker> selectBeMakerYesterday();

    /**
     * 通过被邀请人ID查找邀请人创客信息
     * @param userId
     * @return
     */
    Map<String, Object> selectMakerByChildUserId(Long userId);

    /**
     * 成功邀请用户成为创客的创客
     * @param beMakerYesterday
     * @return
     */
    List<Map<String, Object>> selectSuccessInviteMaker(List<NewMaker> beMakerYesterday);

    /**
     * 获取所有符合抽成的创客集合，并发放奖励
     * @param orders
     * @return
     */
    Set<Long> getOrdersRewardMakers(List<RebateOrderBO> orders);

    /**
     * 根据用户id查创客信息
     * @param userId
     * @return
     */
    Maker selectMakerByUserId(Long userId);

    /**
     * 查找昨天储值且满足条件的创客
     * @return
     */
    List<Map<String, Object>> getStoredValueMakerYesterday();

    /**
     * 满足条件的创客优惠券发放
     * @param makerList
     * @param  ids
     */
    List<UserCoupon> issueCoupons(List<Map<String, Object>> makerList, Set<Long> ids);

    /**
     * 更新创客收益
     * @param maker
     * @param addIncome
     * @return
     */
    boolean updateMakerIncome(Maker maker, BigDecimal addIncome);

    /**
     * 通过用户ID获取其创客信息
     * @param userId
     * @return
     */
    Map<String, Object> getMakerByUserId(Long userId);

}
