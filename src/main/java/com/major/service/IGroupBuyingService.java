package com.major.service;

import com.major.entity.GroupBuying;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 团购方案表 服务类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-31
 */
public interface IGroupBuyingService extends IService<GroupBuying> {

    /**
     * 添加
     * @param groupBuyingList
     * @return
     */
    boolean addGroupBuyings(List<GroupBuying> groupBuyingList);


}
