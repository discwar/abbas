package com.major.service;


import com.major.entity.Goods;
import com.major.model.request.GoodsRequest;
import com.major.model.response.GoodsResponse;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
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
public interface IGoodsService extends IService<Goods> {

    /**
     * 通用版添加商品
     * 添加商品
     * @param goodsRequest
     * @return
     */
    boolean addGoods(GoodsRequest goodsRequest,Long operId);

    boolean updateGoods(GoodsRequest goodsRequest,Long goods_id,Long operId);

    boolean deleteGoods( Long goods_id,Long operId);

    GoodsResponse selectGoodsById(Long goods_id);
    /**
     * 获取当前商家下商品信息列表--通用
     * @param page
     * @param shopId
     * @param goodsName
     * @param orderNumberStart
     * @param orderNumberStop
     * @param currentPriceStart
     * @param currentPriceStop
     * @param status
     * @param totalCountStart
     * @param totalCountStop
     * @param deadlineStartTime
     * @param deadlineStopTime
     * @return
     */
    Page<Map<String, Object>> selectShopGoodsByShopIdPage(Page<Map<String, Object>> page,  Long shopId,Long categoryId,String goodsName,String orderNumberStart, String orderNumberStop,
                                                          String currentPriceStart,String currentPriceStop,Integer status, String totalCountStart,String totalCountStop,
                                                          String deadlineStartTime,String deadlineStopTime,Integer promotionType);

    /**
     * 总部--针对选择活动商品查询,只筛选没有关联水果店的爱果小店的普通商品
     * @param page
     * @param goodsName
     * @return
     */
    Page<Map<String, Object>> selectGoodsList(Page<Map<String, Object>> page,String goodsName);

    /**
     * 针对水果商家选择活动商品查询
     * @param page
     * @param sysUserId
     * @param goodsName
     * @return
     */
    Page<Map<String, Object>> selectGoodsListBySysUserId(Page<Map<String, Object>> page, Long sysUserId,String goodsName);

    /**
     * 可以根据商品名称模糊查询返回满足条件的所有商品
     * @param page
     * @param goodsName
     * @param shopType
     * @return
     */
    Page<Map<String, Object>> selectGoodsListByGoodsName(Page<Map<String, Object>> page,String goodsName,Integer shopType);

    /**
     * 根据当前用户获取商品信息列表
     * @param page
     * @param sysUserId
     * @param shopType
     * @param goodsName
     * @param categoryId
     * @param continentId
     * @param currentPriceStart
     * @param currentPriceStop
     * @param status
     * @param totalCountStart
     * @param totalCountStop
     * @param deadlineStartTime
     * @param deadlineStopTime
     * @param salesVolumeStart
     * @param salesVolumeStop
     * @param promotionType
     * @return
     */
    Page<Map<String, Object>> selectShopGoodsBySysUserIdPage(Page<Map<String, Object>> page,  Long sysUserId,Integer shopType,String goodsName,Integer categoryId, Long continentId,
                                                          String currentPriceStart,String currentPriceStop,Integer status, String totalCountStart,String totalCountStop,
                                                          String deadlineStartTime,String deadlineStopTime,String salesVolumeStart,String salesVolumeStop,Integer promotionType);

    /**
     * 总部自营-获取预售、当季热卖、礼品果盒商品信息列表
     * @param page
     * @param shopName
     * @param categoryId
     * @param currentPriceStart
     * @param currentPriceStop
     * @param salesVolumeStart
     * @param salesVolumeStop
     * @param createTimeStart
     * @param createTimeStop
     * @return
     */
    Page<Map<String, Object>> selectAGShopGoodsPage(Page<Map<String, Object>> page,Integer shopType,String shopName,Integer categoryId,
                                                             String currentPriceStart,String currentPriceStop,
                                                             String salesVolumeStart,String salesVolumeStop,String createTimeStart,String createTimeStop,Integer promotionType,Integer status);

    /**
     * 针对商品的上架或者下架
     * @param goodsId
     * @param goodsType
     * @return
     */
   boolean shelvesGoods( Long goodsId,Integer goodsType);

    /**
     * 当商品分类下架或者删除时，移除商品关联的分类id，
     * @param goodsList
     * @return
     */
    boolean removeGoodsCategoryId(List<Goods> goodsList);

    /**
     * 更新商品库存及订单量
     *
     * @param shopId
     * @param goodsId
     * @param goodsNum
     * @param goodsMark
     * @param activityNum
     * @return
     */
    boolean cancelGoodsStock(Long shopId, Long goodsId, Long goodsNum, Integer goodsMark, Integer activityNum);

    /**
     * 更新商品库存及订单量
     *
     * @param goodsId
     * @return
     */
    void addRedisGoodsStock(Long goodsId);

    /**
     * 更改redis中商品库存
     * @param goodsId
     * @param goodNum
     * @return
     */
    void changeRedisGoodsStock(Long goodsId, Integer goodNum,Integer goodsType,Integer updateType);

    /**
     * 获取redis中商品库存信息
     *
     */
    Map<String, Object> getGoodsStatus(Long goodId);

    /**
     * 查询一家店铺下所有上架的商品
     * @param shopId
     * @return
     */
    List<Goods> selectGoodsInfoByShopId(Long shopId);

    /**
     * 删除redis中商品信息
     * @param goodsId
     */
    void deleteRedisGoodsInfo(Long goodsId);

}
