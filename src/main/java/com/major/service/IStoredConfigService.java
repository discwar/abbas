package com.major.service;

import com.major.entity.StoredConfig;
import com.major.model.request.StoredConfigRequest;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 储值送好礼配置表 服务类
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-11-15
 */
public interface IStoredConfigService extends IService<StoredConfig> {

    /**
     * 获取所有的储值赠送好礼配置
     * @return
     */
    List<StoredConfig> selectStoredGift();

    /**
     * 新增
     * @param storedConfigRequest
     * @param operId
     * @return
     */
    boolean addStoredConfig(StoredConfigRequest storedConfigRequest, Long operId);

    /**
     * 修改
     * @param storedConfigRequest
     * @param id
     * @return
     */
    boolean updateStoredConfig(StoredConfigRequest storedConfigRequest,Long id);

    /**
     * 删除
     * @param id
     * @param operId
     * @return
     */
    boolean deleteStoredConfig(Long id,Long operId);

    /**
     * 分页
     * @param page
     * @return
     */
    Page<Map<String, Object>> selectStoredConfigPage(Page<Map<String, Object>> page);

    /**
     * 获取当前
     * @param id
     * @return
     */
    Map<String,Object> selectStoredConfigById(Long id);
}
