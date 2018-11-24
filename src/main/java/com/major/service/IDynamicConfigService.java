package com.major.service;

import com.major.entity.DynamicConfig;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 动态配置表 服务类
 * </p>
 *
 * @author XuQuanMing
 * @since 2018-11-16
 */
public interface IDynamicConfigService extends IService<DynamicConfig> {

    /**
     * 从缓存中获取具体配置信息，没有则从db中取
     * @param dynamicType
     * @param keyword
     * @return
     */
    Map<String, Object> getDynamicConfigMap(Integer dynamicType, String keyword);

    /**
     * 获取动态描述
     * @param dynamicType
     * @param keyword
     * @return
     */
    String getDynamicDesc(Integer dynamicType, String keyword);

}
