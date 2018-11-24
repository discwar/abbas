package com.major.service;

import com.major.common.constant.UserConstants;
import com.major.entity.LogUserScore;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户积分记录表 服务类
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-08-17
 */
public interface ILogUserScoreService extends IService<LogUserScore> {

    /**
     * 新增用户积分记录
     * @param userId
     * @param score
     * @param scoreSourceEnum
     * @param content
     * @return
     */
    boolean addLogUserScore(Long userId, Integer score, UserConstants.ScoreSourceEnum scoreSourceEnum, String content,Integer mark);

    /**
     * 用户积分明细分页
     * @param page
     * @param userId
     * @param content
     * @param source
     * @param createTimeStart
     * @param createTimeEnd
     * @param scoreStart
     * @param scoreEnd
     * @return
     */
    Page<Map<String, Object>> selectLogUserScorePageByUserId(Page<Map<String, Object>> page, Long userId, String content, Integer source,
                                                             String createTimeStart, String createTimeEnd, Integer scoreStart, Integer scoreEnd);

    /**
     * 获取用户的当前积分数和历史积分数
     * @param userId
     * @return
     */
    Map<String ,Object> selectLogUserScoreInfoByUserId(Long userId );
}
