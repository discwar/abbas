package com.major.service;

import com.major.entity.TipsCategory;
import com.major.model.request.TipsCategoryRequset;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/18 19:36      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public interface ITipsCategoryService extends IService<TipsCategory> {

    /**
     * 获取小贴士分类列表
     * @return
     */
    Map<String, Object> getTipsCategoryList();

    /**
     * 添加小贴士分类
     *
     * @return
     */
    boolean saveTipsCategory(TipsCategoryRequset tipsCategoryRequset);

    /**
     * 修改小贴士分类
     *
     * @param id
     * @return
     */
    boolean putTipsCategory(TipsCategoryRequset tipsCategoryRequset,Long id);

    /**
     * 删除小贴士分类
     * @param id
     * @return
     */
    boolean deleteTipsCategory(Long id);

    /**
     * 获取小贴士分类分页列表
     * @param page
     * @return
     */
    Page<Map<String, Object>> selectTipsCategoryPage(Page<Map<String, Object>> page);

    /**
     * 小贴士分类上架-下架
     * @param id
     * @param status
     * @return
     */
    boolean shelvesTipsCategory(Long id,Integer status);

}
