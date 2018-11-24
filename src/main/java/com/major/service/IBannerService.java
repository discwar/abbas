package com.major.service;


import com.major.entity.Banner;
import com.major.model.request.BannerRequest;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>Title: 区域表数据服务层接口 </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/13 10:35      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public interface IBannerService extends IService<Banner> {


    /**
     * 添加Banner
     * @param bannerRequest
     * @return
     */
    boolean addBanner(BannerRequest bannerRequest,Long operId);

    /**
     * 修改Banner
     * @param bannerRequest
     * @param id
     * @return
     */
    boolean updateBanner(BannerRequest bannerRequest, Long id,Long operId);

    /**
     * 删除Banner
     * @param userId
     * @return
     */
    boolean deleteBanner( Long userId,Long operId);

    /**
     * 上架/下架
     * @param id
     * @return
     */
    boolean updateBannerDown( Long id,Integer type);

    /**
     * 获取Banner分页列表
     * @param page
     * @return
     */
    Page<Map<String, Object>> selectBannerPageByBannerType(Page<Map<String, Object>> page,Integer bannerType);

    /**
     * 获取当前Banner
     * @param Id
     * @return
     */
    Banner selectBannerById(Long Id);

    /**
     * 获取详情
     * @param bannerId
     * @return
     */
    Map<String ,Object> selectBannerInfoById(Long bannerId);

    /**
     * 点击新增Banner校验该类型上架数量是否超过5个
     * @param type
     * @return
     */
    boolean checkBannerDown(Integer type);
}
