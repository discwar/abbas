package com.major.service.impl;

import com.major.common.constant.Constants;
import com.major.common.constant.DynamicConfigConstants;
import com.major.common.constant.RedisConstants;
import com.major.entity.DynamicConfig;
import com.major.mapper.DynamicConfigMapper;
import com.major.service.IDynamicConfigService;
import com.major.service.RedisService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 动态配置表 服务实现类
 * </p>
 *
 * @author XuQuanMing
 * @since 2018-11-16
 */
@Service
public class DynamicConfigServiceImpl extends ServiceImpl<DynamicConfigMapper, DynamicConfig> implements IDynamicConfigService {

    @Autowired
    private RedisService redisService;

    @Override
    public Map<String, Object> getDynamicConfigMap(Integer dynamicType, String keyword) {
        String dynamicConfigKey = RedisConstants.DYNAMIC_CONFIG + dynamicType;

        if (redisService.exists(dynamicConfigKey)) {
            Map<String, Object> dynamicConfigMap = (Map<String, Object>) redisService.hmGet(dynamicConfigKey, keyword);
            if (dynamicConfigMap != null) {
                return dynamicConfigMap;
            }
        }

        Map<String, Object> dbDynamicConfigMap = this.findDynamicConfig(dynamicType, keyword);
        if (dbDynamicConfigMap != null) {
            redisService.pullAllForever(dynamicConfigKey, dbDynamicConfigMap);
        }

        return dbDynamicConfigMap;
    }

    @Override
    public String getDynamicDesc(Integer dynamicType, String keyword) {
        Map<String, Object> dynamicConfigMap = this.getDynamicConfigMap(dynamicType, keyword);
        return (String) dynamicConfigMap.get(DynamicConfigConstants.DYNAMIC_DESC);
    }

    private Map<String, Object> findDynamicConfig(Integer dynamicType, String keyword) {
        Wrapper<DynamicConfig> ew = new EntityWrapper<>();
        ew.setSqlSelect("dynamic_title, dynamic_desc");
        ew.where("status = {0}", Constants.STATUS_ENABLE)
            .and("dynamic_type = {0}", dynamicType)
            .and("keyword = {0}", keyword)
            .last("LIMIT 1");
        return super.selectMap(ew);
    }

}
