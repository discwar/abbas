package com.major.service;

import com.major.entity.SysConfig;
import com.major.model.request.SysConfigRequest;

import java.util.Map;

public interface ISysConfigService {

    /**
     * 新增配置信息
     * @param sysConfig
     * @return
     */
    boolean addSysConfig(SysConfigRequest sysConfig);

    /**
     * 修改配置信息
     * @param sysConfig
     * @param id
     * @return
     */
    boolean updateSysConfig(SysConfigRequest sysConfig,  Long id);


    /**
     * 根据手机系统类型获取数据
     * @param osType
     * @return
     */
    SysConfig selectSysConfigByOsType(Integer osType);

    /**
     * 获取OSS配置信息
     * @return
     */
    Map<String, Object> getOssConfig();
}
