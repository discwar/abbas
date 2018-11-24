package com.major.service;

import com.major.entity.ShopApply;
import com.major.model.request.ShopApplyRequest;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 店铺申请表 服务类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-24
 */
public interface IShopApplyService extends IService<ShopApply> {

    /**
     * 获取所有的店铺申请分页列表
     * @param page
     * @param createTimeStart
     * @param createTimeStop
     * @param shopType
     * @param shopAddress
     * @param shopName
     * @param telephone
     * @param status
     * @return
     */
    Page<Map<String, Object>> selectShopApplyPage(Page<Map<String, Object>> page, String createTimeStart, String createTimeStop, Integer shopType, String shopAddress,
                                                  String shopName, String telephone, Integer status);

    /**
     * 审核-通用
     * @param applyId
     * @param shopApplyRequest
     * @return
     */
    boolean toExamine(Long applyId, ShopApplyRequest shopApplyRequest );

    /**
     * 查看店铺申请详情
     * @param applyId
     * @return
     */
    Map<String,Object> selectShopApplyById( Long applyId);

}
