package com.major.service.impl;

import com.major.common.constant.UserConstants;
import com.major.entity.LogUserScore;
import com.major.mapper.LogUserScoreMapper;
import com.major.service.ILogUserScoreService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户积分记录表 服务实现类
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-08-17
 */
@Service
public class LogUserScoreServiceImpl extends ServiceImpl<LogUserScoreMapper, LogUserScore> implements ILogUserScoreService {

    @Override
    public boolean addLogUserScore(Long userId, Integer score, UserConstants.ScoreSourceEnum scoreSourceEnum, String content,Integer mark) {
        LogUserScore logUserScore = new LogUserScore();
        logUserScore.setUserId(userId);
        logUserScore.setScore(score);
        logUserScore.setSource(scoreSourceEnum.getValue());
        logUserScore.setMark(mark);
        logUserScore.setRemark(scoreSourceEnum.getDesc());
        logUserScore.setContent(content);
        return logUserScore.insert();
    }

    @Override
    public Page<Map<String, Object>> selectLogUserScorePageByUserId(Page<Map<String, Object>> page, Long userId,String content,Integer source,
                                                                    String createTimeStart, String createTimeEnd, Integer scoreStart,Integer scoreEnd) {
        Wrapper ew = new EntityWrapper();
        ew.where("user_id={0}",userId);
        Map<String,Object> map=new HashMap<>();
        map.put("source",source);
        ew.allEq(map);
        if(StringUtils.isNotEmpty(content)){
            ew.like("content",content);
        }
        if(StringUtils.isNotEmpty(createTimeStart)) {
            ew.ge("create_time",createTimeStart);
        }
        if(StringUtils.isNotEmpty(createTimeEnd)) {
            ew.le("create_time",createTimeEnd);
        }
        if(null!=scoreStart) {
            ew.ge("score",scoreStart);
        }
        if(null!=scoreEnd) {
            ew.le("score",scoreEnd);
        }
        ew.orderBy(" create_time DESC ");
        return page.setRecords(baseMapper.selectLogUserScorePageByUserId(page,ew));
    }

    @Override
    public Map<String ,Object> selectLogUserScoreInfoByUserId(Long userId ) {
        Map<String,Object> map=new HashMap<>();
        map.put("log_user_score_info",baseMapper.selectLogUserScoreInfoByUserId(userId));
        return map;
    }
}
