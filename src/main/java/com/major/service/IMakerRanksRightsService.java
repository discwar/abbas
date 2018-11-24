package com.major.service;

import com.major.entity.MakerRanksRights;
import com.major.model.request.MakerRanksRightsRequest;
import com.major.model.request.UpdateMakerRanksRightsRequest;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 创客等级与其专属权益关联表 服务类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-11-20
 */
public interface IMakerRanksRightsService extends IService<MakerRanksRights> {

    /**
     * 新增
     * @param makerRanksRightsRequest
     * @return
     */
    boolean addMakerRanksRights(MakerRanksRightsRequest makerRanksRightsRequest);

    /**
     * 修改
     * @param updateMakerRanksRightsRequest
     * @param id
     * @return
     */
    boolean updateMakerRanksRights(UpdateMakerRanksRightsRequest updateMakerRanksRightsRequest, Long id);

    /**
     * 获取
     * @param id
     * @return
     */
    Map<String,Object> selectMakerRanksRightsById(Long id);
}
