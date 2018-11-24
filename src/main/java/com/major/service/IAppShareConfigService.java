package com.major.service;

import com.major.entity.AppShareConfig;
import com.major.model.request.AppShareConfigRequest;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * APP分享配置表 服务类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-10-11
 */
public interface IAppShareConfigService extends IService<AppShareConfig> {

    /**
     * 分页
     * @param page
     * @return
     */
    Page<Map<String, Object>> selectAppShareConfigPage(Page<Map<String, Object>> page,Integer shareType);

    /**
     * 添加
     * @param appShareConfigRequest
     * @return
     */
    boolean addAppShareConfig(AppShareConfigRequest appShareConfigRequest);

    /**
     * 修改
     * @param appShareConfigRequest
     * @param id
     * @return
     */
    boolean updateAppShareConfig(AppShareConfigRequest appShareConfigRequest,Long id);

    /**
     * 删除
     * @param id
     * @return
     */
    boolean deleteAppShareConfig(Long id);

    /**
     * 获取
     * @param id
     * @return
     */
    Map<String,Object> selectAppShareConfigById(Long id);
}
