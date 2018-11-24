package com.major.service;


import com.major.entity.ShoppingCart;
import com.baomidou.mybatisplus.service.IService;

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
public interface IShoppingCartService extends IService<ShoppingCart> {

    /**
     * 根据商品id，和状态类型，修改
     * @param goodsId
     * @param status 当为商品分类下架时，status=1,当为商品重新修改分类时 status=2
     * @param updateStatus 当为商品分类下架时，status=2,当为商品重新修改分类时 status=1
     * @return
     */
    boolean updateShoppingCartByGoodsId(Long goodsId,Integer status,Integer updateStatus);
}
