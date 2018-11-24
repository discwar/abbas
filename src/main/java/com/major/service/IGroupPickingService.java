package com.major.service;

import com.major.entity.GroupPicking;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 组团采摘表 服务类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-31
 */
public interface IGroupPickingService extends IService<GroupPicking> {

    /**
     * 组团采购分页列表
     * @param page
     * @param goodsName
     * @param shopName
     * @return
     */
    Page<Map<String, Object>> selectGroupPickingPage(Page<Map<String, Object>>  page, String goodsName, String shopName );


}
