package com.major.service.impl;

import com.major.common.constant.Constants;
import com.major.common.constant.RedisConstants;
import com.major.entity.AgSay;
import com.major.mapper.AgSayMapper;
import com.major.model.request.AgSayRequest;
import com.major.service.IAgSayService;
import com.major.service.RedisService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class AgSayServiceImpl extends ServiceImpl<AgSayMapper, AgSay> implements IAgSayService {

    /**
     * 爱果有话说最新数量
     */
    public static final Integer AG_SAY_LAST_NUM = 5;

    @Autowired
    private RedisService redisService;

    @Override
    public boolean addAgSay(AgSayRequest agSayRequest) {
        AgSay agSay = new AgSay();
        BeanUtils.copyProperties(agSayRequest, agSay);
        agSay.setStatus(Constants.STATUS_NORMAL);
        boolean insert  = super.insert(agSay);
        if (insert){
            //redis 中更新爱果有话说的列表
            String agSayRedisKey = RedisConstants.AG_SAY_TOP;
            redisService.rPushForever(agSayRedisKey,getAgSayRedisMap(agSay.getId(),agSay.getTitle()));
            // 保持列表只有5位
            redisService.trim(RedisConstants.AG_SAY_TOP, 0L, AG_SAY_LAST_NUM - 1);
        }
        return insert;
    }

    private Map<String,Object> getAgSayRedisMap(Long id,String title){
        Map<String,Object> agSayMap = new HashMap<>(2);
        agSayMap.put("id",id.intValue());
        agSayMap.put("title",title);
        return agSayMap;
    }

    private List<Map<String, Object>> getAgSaysList(String limit) {
        Wrapper<Map<String, Object>> query = new EntityWrapper<>();
        query.where("status = {0}", Constants.STATUS_ENABLE)
                .orderBy("create_time", false)
                .last(limit);
        return baseMapper.selectAgSayBO(query);
    }

    private void updateAgSayDate(){
        String limit = "LIMIT " + AG_SAY_LAST_NUM;
        List<Map<String, Object>> agSaysList = this.getAgSaysList(limit);

        String redisKey = RedisConstants.AG_SAY_TOP;

        // 把前5个最新发布的爱果有话说列表添加到列表中
        redisService.lPushAll(redisKey, agSaysList);

        // 保持列表只有5位
        redisService.trim(RedisConstants.AG_SAY_TOP, 0L, AG_SAY_LAST_NUM - 1);
    }

    @Override
    public boolean updateAgSay(AgSayRequest agSayRequest, Long id) {
        AgSay agSay = new AgSay();
        BeanUtils.copyProperties(agSayRequest, agSay);
        agSay.setId(id);
        boolean flag = super.updateById(agSay);
        if (flag){
            updateAgSayDate();
        }
        return flag;
    }

    @Override
    public boolean deleteAgSay( Long agsayid) {
        AgSay agSay = new AgSay();
        agSay.setId(agsayid);
        agSay.setStatus(Constants.STATUS_DELETE);
        boolean flag = agSay.updateById();
        if (flag){
            updateAgSayDate();
        }
        return flag;
    }

    @Override
    public Page<Map<String, Object>> selectAgSay(Page<Map<String, Object>> page,String title,String createTimeStart,String createTimeStop,Integer status) {
        Wrapper ew = new EntityWrapper();
        ew.where("status<>{0}",Constants.STATUS_DELETE);
        Map<String,Object> map=new HashMap<>();
        map.put("status",status);
        ew.allEq(map);
        if(StringUtils.isNotEmpty(title)){
            ew.like("title",title);
        }
        if(StringUtils.isNotEmpty(createTimeStart)) {
            ew.ge("create_time",createTimeStart);
        }
        if(StringUtils.isNotEmpty(createTimeStop)) {
            ew.le("create_time",createTimeStop);
        }
        ew.orderBy(" create_time DESC ,status ");
        return page.setRecords(baseMapper.selectAgSayPage(page,ew));
    }

    @Override
    public AgSay selectAgSayById(Long Id) {
        return  baseMapper.selectAgSayById(Id);
    }

    @Override
    public  boolean shelvesAgSay(Long agSaysId,Integer status){
        AgSay agSay = new AgSay();
        agSay.setId(agSaysId);
        agSay.setStatus(status);
        boolean flag = agSay.updateById();
        if (flag){
            updateAgSayDate();
        }
        return flag;
    }

}
