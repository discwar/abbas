package com.major.service.impl;

import com.major.common.constant.RedisConstants;
import com.major.common.enums.StatusResultEnum;
import com.major.common.exception.AgException;
import com.major.common.util.CommonUtils;
import com.major.entity.AppShareConfig;
import com.major.mapper.AppShareConfigMapper;
import com.major.model.bo.AppShareConfigBO;
import com.major.model.request.AppShareConfigRequest;
import com.major.service.IAppShareConfigService;
import com.major.service.RedisService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * APP分享配置表 服务实现类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-10-11
 */
@Service
public class AppShareConfigServiceImpl extends ServiceImpl<AppShareConfigMapper, AppShareConfig> implements IAppShareConfigService {

    @Autowired
    private RedisService redisService;

    @Override
    public Page<Map<String, Object>> selectAppShareConfigPage(Page<Map<String, Object>> page,Integer shareType){
        return page.setRecords(baseMapper.selectAppShareConfigPage(page,shareType));
    }

    @Override
    public boolean addAppShareConfig(AppShareConfigRequest appShareConfigRequest){
        //一种类型只能有一条数据
        List<Map<String,Object> > list=baseMapper.selectAppShareConfigByShareType(appShareConfigRequest.getShareType());
        if(null!=list && list.size()>0){
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR, "一种类型只能有一条数据");
        }
        AppShareConfig appShareConfig=new AppShareConfig();
        BeanUtils.copyProperties(appShareConfigRequest, appShareConfig);
        this.putAppShareConfig2Redis(appShareConfig);
        return super.insert(appShareConfig);
    }

    @Override
    public boolean updateAppShareConfig(AppShareConfigRequest appShareConfigRequest,Long id){
        AppShareConfig appShareConfig=selectById(id);
        BeanUtils.copyProperties(appShareConfigRequest, appShareConfig);
        this.putAppShareConfig2Redis(appShareConfig);
        return super.updateById(appShareConfig);
    }

    @Override
    public boolean deleteAppShareConfig(Long id){
        return super.deleteById(id);
    }

    @Override
    public Map<String,Object> selectAppShareConfigById(Long id){
        Map<String,Object> map=new HashMap<>();
        map.put("app_share_config_info",baseMapper.selectAppShareConfigById(id));
        return map;
    }

    /**
     * 将App分享配置按类型存放到redis中
     * @param appShareConfig
     * @return
     */
    private void putAppShareConfig2Redis(AppShareConfig appShareConfig) {
        AppShareConfigBO appShareConfigBO = new AppShareConfigBO();
        BeanUtils.copyProperties(appShareConfig, appShareConfigBO);
        String key = RedisConstants.APP_SHARE_CONFIG_PREFIX + appShareConfig.getShareType();
        redisService.pullAllForever(key, CommonUtils.object2Map(appShareConfigBO));
    }

}
