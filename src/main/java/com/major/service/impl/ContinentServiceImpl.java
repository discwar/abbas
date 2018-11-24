package com.major.service.impl;

import com.major.entity.Continent;
import com.major.mapper.ContinentMapper;
import com.major.service.IContinentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 大洲表 服务实现类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-10-24
 */
@Service
public class ContinentServiceImpl extends ServiceImpl<ContinentMapper, Continent> implements IContinentService {

    @Override
    public Map<String,Object> selectContinent(){
        Map<String,Object> map=new HashMap<>(1);
        map.put("continent_info",baseMapper.selectContinent());
        return map;
    }
}
