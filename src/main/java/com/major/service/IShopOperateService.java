package com.major.service;

import com.major.entity.ShopOperate;
import com.major.model.request.ShopOperateRequest;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 * 店铺运营表 服务类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-07
 */
public interface IShopOperateService extends IService<ShopOperate> {

    /**
     * 添加运营表
     * @param shopOperate
     * @return
     */
    boolean addShopOperate(ShopOperate shopOperate);

    /**
     * 修改店铺运营
     * @param shopOperateRequest
     * @param shopId
     * @return
     */
    boolean updateShopOperate(ShopOperateRequest shopOperateRequest,Long shopOperateId);

    /**
     * 根据当前用户返回当前店铺运营信息
     * @param
     * @return
     */
    Map<String,Object> selectShopOperateBySysUserId(Long sysUserId,Integer shopTyped);

    /**
     * 针对总部自营获取当前店铺运营信息
     * @param shopId
     * @return
     */
    Map<String,Object> selectShopOperateByShopId(Long shopId);

    /**
     * 添加二维码
     * @param oprateId
     * @param qr
     * @return
     */
    boolean updateShopOperateQR(Long oprateId,String qr);

    /**
     * 根据店铺Id删除店铺运营
     * @param shopId
     * @return
     */
    boolean deleteShopOperate(Long shopId);

    /**
     * 根据店铺id返回店铺运营表数据
     * @param shopId
     * @return
     */
    ShopOperate selectShopOperateByShopIdOne( Long shopId);

    /**
     * 根据shopId修改标签
     * @param shopId
     * @param label
     * @return
     */
    boolean updateLabelByShopId(Long shopId,String label);

    /**
     * 置空店铺标签
     * @param shopId
     * @return
     */
    int removeLabel( Long shopId);

}
