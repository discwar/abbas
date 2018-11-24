package com.major.service;


import com.major.entity.LogPay;
import com.major.entity.User;
import com.major.model.request.UserSearchRequest;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Mouxiaoshi
 * @since 2018-07-16
 */
public interface IUserService extends IService<User> {

    /**
     * 查询多少时间注册的用户
     * @param startTime
     * @return
     */
    List<User> selectUserSearchCreateTime(String startTime);

    /**
     * 查询在某段时间内超过多少消费的用户
     * @param realTotalAmount
     * @param startTime
     * @param stopTime
     * @return
     */
    List<Map<String,Object>> selectUserOrderByExceedAmount(String realTotalAmount, String startTime, String stopTime);

    /**
     * 查询在某段时间内未超过多少消费的用户
     * @param realTotalAmount
     * @param startTime
     * @param stopTime
     * @return
     */
    List<Map<String,Object>> selectUserOrderByNotExceedAmount(String realTotalAmount,String startTime,String stopTime);

    /**
     * 返回24小时内未活跃的用户
     * @return
     */
    List<User> selectUserByLoginTime();

    /**
     * 查询所有有效用户
     * @return
     */
    List<User> selectAllUser();

    /**
     * 用户分页查询列表
     * @param page
     * @param userSearchRequest
     * @return
     */
    Page<Map<String, Object>> selectUserPage(Page<Map<String, Object>> page, UserSearchRequest userSearchRequest);

    /**
     * 得要用户基本信息
     * @param userId
     * @return
     */
    Map<String,Object> selectUserInfoByUserId(Long userId);

    /**
     * 用户钱包退款
     * @param logPay
     * @return
     */
    boolean refundToWallet(LogPay logPay);

    /**
     * 更新用户积分
     * @param userId
     * @param addScore
     * @return
     */
    boolean updateUserScore(Long userId, Integer addScore,Integer type);

    /**
     * 更新用户积分
     * @param user
     * @param addScore
     * @return
     */
    boolean updateUserScore(User user, Integer addScore,Integer type);

    /**
     * 根据当前id获取值
     * @param userId
     * @return
     */
    User selectUserById(Long userId);

}
