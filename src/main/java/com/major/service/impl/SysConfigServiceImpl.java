package com.major.service.impl;

import com.major.common.constant.Constants;
import com.major.common.constant.RedisConstants;
import com.major.common.enums.StatusResultEnum;
import com.major.common.exception.AgException;
import com.major.common.util.CommonUtils;
import com.major.config.OSSConfig;
import com.major.entity.SysConfig;
import com.major.mapper.SysConfigMapper;
import com.major.model.request.SysConfigRequest;
import com.major.service.ISysConfigService;
import com.major.service.RedisService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private OSSConfig ossConfig;

    @Override
    public  boolean addSysConfig(SysConfigRequest sysConfigRequest) {
        this.check(sysConfigRequest);
        SysConfig sysConfig = new SysConfig();
        BeanUtils.copyProperties(sysConfigRequest, sysConfig);
        boolean insert = super.insert(sysConfig);
        if (insert){
            resetRedisSysConfig(sysConfig,RedisConstants.SYS_CONFIG_PREFIX + sysConfigRequest.getOsType());
        }
        return insert;
     }

    @Override
    public boolean updateSysConfig(SysConfigRequest sysConfigRequest,  Long  sysConfigId) {
        this.check(sysConfigRequest);
        SysConfig sysConfig = new SysConfig();
        BeanUtils.copyProperties(sysConfigRequest, sysConfig);
        sysConfig.setId(sysConfigId);
        boolean flag = super.updateById(sysConfig);
        if (flag){
            //删除之前的redis数据
            redisService.removePattern(RedisConstants.SYS_CONFIG_PREFIX + sysConfigRequest.getOsType());
            resetRedisSysConfig(sysConfig,RedisConstants.SYS_CONFIG_PREFIX + sysConfigRequest.getOsType());
        }
        return flag;
    }
    @Override
    public SysConfig selectSysConfigByOsType(Integer osType) {
        String key = RedisConstants.SYS_CONFIG_PREFIX + osType;
        if (!redisService.exists(key)){
            SysConfig sysConfig = baseMapper.selectSysConfigByOsType(osType);
            resetRedisSysConfig(sysConfig,key);
            return sysConfig;
        }
        return redisService.getRedisSysConfigMap(key, SysConfig.class);
    }

    /**
     * 保存sys配置到redis
     * @param sysConfig
     * @param key
     */
    private void resetRedisSysConfig(SysConfig sysConfig,String key){
        sysConfig.setId(null);
        sysConfig.setOsType(null);
        sysConfig.setCreateTime(null);
        sysConfig.setUpdateTime(null);
        redisService.pullAllForever(key, CommonUtils.object2Map(sysConfig));
    }

    @Override
    public Map<String, Object> getOssConfig() {
        Map<String, Object> resultMap = new HashMap<>(1);
        resultMap.put("oss_config", ossConfig);
        return resultMap;
    }

    private void check(SysConfigRequest sysConfigRequest){
        if(null==sysConfigRequest.getOsType()){
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR, "系统类型不能为空");
        }
        if(Constants.OS_TYPE_IOS.equals(sysConfigRequest.getOsType()) || Constants.OS_TYPE_ANDROID.equals(sysConfigRequest.getOsType()) ){
            if(StringUtils.isEmpty(sysConfigRequest.getAppVersion())){
                throw new AgException(StatusResultEnum.DB_SAVE_ERROR, "APP版本号不能为空");
            }
            if(StringUtils.isEmpty(sysConfigRequest.getAddrVersion())){
                throw new AgException(StatusResultEnum.DB_SAVE_ERROR, "地址库版本号不能为空");
            }
            if(null==sysConfigRequest.getVersionCode()){
                throw new AgException(StatusResultEnum.DB_SAVE_ERROR, "versionCode不能为空");
            }
            if(null==sysConfigRequest.getForceUpdate()){
                throw new AgException(StatusResultEnum.DB_SAVE_ERROR, "请选择是否强制更新");
            }
        }

        if(StringUtils.isEmpty(sysConfigRequest.getRule())){
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR, "密钥截取规则不能为空");
        }
    }

}
