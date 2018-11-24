package com.major.service;

import com.major.entity.Shop;
import com.major.model.request.ShopRequest;
import com.major.model.request.ShopSearchRequest;
import com.major.model.request.UpdateShopRequest;
import com.major.model.response.ShopResponse;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 店铺信息表 服务类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-07
 */
public interface IShopService extends IService<Shop> {

    /**
     * 添加店铺通用版
     * @param shopRequest
     * @return
     */
    boolean addShop(ShopRequest shopRequest,Long operId);

    /**
     * 修改店铺通用版，暂定只修改shop店铺表
     * @param updateShopRequest
     * @param shopId
     * @return
     */
    boolean updateShop(UpdateShopRequest updateShopRequest, Long shopId, Long operId);

    /**
     * 删除店铺功能暂留
     * @param shopId
     * @return
     */
    boolean deleteShop(Long shopId,Long operId);

    /**
     * 返回店铺的基本信息--通用版-管理员使用
     * @param shopId
     * @return
     */
    Map<String,Object> selectShopInfoByShopId(Long shopId);


    /**
     * 根据店铺列型和搜索参数返回该类型下所有的店铺列表--通用版
     * @param page
     * @param shopSearchRequest
     * @param shopType
     * @return
     */
    Page<Map<String, Object>> selectShopPage(Page<Map<String, Object>> page, ShopSearchRequest shopSearchRequest,Integer shopType);

    /**
     * 根据当前用户ID和店铺类型返回当前的店铺表信息
     * @param
     * @return
     */
    Shop selectShopBySysUserId( Long sysUserId,Integer shopType);

    /**
     * 针对爱果小店添加
     * @param shopRequest
     * @return
     */
    boolean addAGShop(ShopRequest shopRequest,Long operId) throws Exception;

    /**
     * 管理员获取当前店铺信息
     * @param shopId
     * @return
     */
    ShopResponse selectShopById(Long shopId);

    /**
     * 根据用户Id返回店铺信息
     * @param sysUserId
     * @return
     */
    List<Map<String,Object>> selectShopInfoBySysUserId(Long sysUserId);

    /**
     * 可以根据店铺类型返回该类型下所有的店铺信息
     * @param shopType
     * @return
     */
    Map<String,Object> selectShopByShopType (Integer shopType);

    /**
     * 可根据店铺名称模糊搜索返回满足条件的店铺
     * @param shopName
     * @return
     */
    Page<Map<String, Object>> selectShopByShopName (Page<Map<String, Object>> page,String shopName,Integer shopType);


    /**
     * 店铺二维码
     * @param shopId
     * @return
     */
    Map<String, Object> getShopQRCode(Long shopId);

    /**
     * 查找所有状态正常的店铺Id集合
     * @return
     */
    List<Map<String, Object>> getStatusEnableShopIdList();

    /**
     * 更新店铺的店铺评分
     * @param
     * @param idList
     * @return
     */
    StringBuffer updateShopScore(List<Map<String, Object>> idList);

    /**
     * 今天往前三十天的店铺订单销量
     * @return
     */
    List<Map<String, Object>> getShopMonthOrderCount();

    /**
     * 更新店铺的月销量
     * @param shopMonthOrderCount
     * @return
     */
    StringBuffer updateMonthOrderCount(List<Map<String, Object>> shopMonthOrderCount);

    /**
     * 根据当前用户id返回银行信息
     * @param shopType
     * @param sysUserId
     * @return
     */
    Map<String,Object> selectShopByBankInfo(Long shopId,Integer shopType);

    /**
     * 查看店铺的可提现金额和冻结金额
     * 如果有关联爱果小店，只取水果店的shopId
     * @param shopId
     * @return
     */
    Map<String,Object> selectShopByCarryMoneyInfo(Long shopId);

    /**
     * 商家提现管理分页
     * @param page
     * @param shopId
     * @return
     */
    Page<Map<String, Object>> selectWithdrawalPageById (Page<Map<String, Object>> page,Long shopId);


}
