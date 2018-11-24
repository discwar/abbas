package com.major.service.impl;


import com.major.entity.ShoppingCart;
import com.major.mapper.ShoppingCartMapper;
import com.major.service.IShoppingCartService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>Title: 区域表数据服务层接口实现类 </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/13 10:37      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements IShoppingCartService {

    @Override
    public boolean updateShoppingCartByGoodsId(Long goodsId,Integer status,Integer updateStatus){
        List<ShoppingCart> shoppingCartList=baseMapper.selectShoppingCartByGoods(goodsId, status);
        if(shoppingCartList==null && shoppingCartList.size()<0){
            return true;
        }
        for(ShoppingCart shoppingCart :shoppingCartList){
          this.updateStatus(shoppingCart.getId(),updateStatus);
        }
        return true;
    }

    public int updateStatus(Long id,Integer status){
        return baseMapper.updateStatusById(id,status);
    }
}
