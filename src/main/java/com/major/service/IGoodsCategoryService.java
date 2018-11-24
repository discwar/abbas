package com.major.service;

import com.major.entity.Goods;
import com.major.entity.GoodsCategory;
import com.major.model.request.GoodsCategoryRequest;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品分类表 服务类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-09
 */
public interface IGoodsCategoryService extends IService<GoodsCategory> {

    /**
     * 添加分类
     * @param goodsCategoryRequest
     * @return
     */
    boolean addGoodsCategory(GoodsCategoryRequest goodsCategoryRequest);

    /**
     * 修改分类
     * @param goodsCategoryRequest
     * @param category_id
     * @return
     */
    boolean updateGoodsCategory(GoodsCategoryRequest goodsCategoryRequest,Long category_id);

    /**
     * 删除分类
     * @param category_id
     * @return
     */
    boolean deleteGoodsCategory( Long category_id);

    /**
     * 获取当前分类ID信息
     * @param id
     * @return
     */
    GoodsCategory selectGoodsCategoryById( Long id);

    /**
     * 根据分类查找商品
     * @param categoryId
     * @return
     */
    List<Goods> selectGoodsByCategoryId(Long categoryId);

    /**
     * 商品分类列表
     * @param page
     * @param shopId
     * @return
     */
    Page<Map<String, Object>> selectGoodsCategoryPage(Page<Map<String, Object>> page, Long shopId,Long continentId,Integer shop_type);

    /**
     * 针对水果店、农场、采摘园添加商品时获取分类信息
     * @param shopId
     * @return
     */
    Map<String, Object> selectGoodsCategoryListByShopId(Long shopId);

    /**
     * 针对总部自营、进出口、采摘园添加商品时获取分类信息
     * @param shopType
     * @param continent
     * @return
     */
    Map<String, Object> selectGoodsCategoryListByShopType(Integer shopType,Long continent);

    /**
     * 商品分类的上架或者下架
     * @param categoryId
     * @param categoryType
     * @return
     */
    boolean shelvesGoodsCategory( Long categoryId,Integer categoryType);
}
