package com.major.mapper;


import com.major.entity.ShoppingCart;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/13 10:24      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {


    @Select("select id,user_id,shop_id,goods_id,status from shopping_cart where goods_id=#{goodsId} and status=#{status}")
    List<ShoppingCart> selectShoppingCartByGoods(@Param("goodsId") Long goodsId,@Param("status") Integer status);

    /**
     * 根据id返回
     * @param id
     * @return
     */
    @Select("select id,user_id,shop_id,goods_id,status from shopping_cart where id=#{id}")
    ShoppingCart selectShoppingCartById(@Param("id") Long id);

    @Update("update shopping_cart set status=#{status},update_time=now()  where id=#{id}")
    int updateStatusById(@Param("id") Long id,@Param("status") Integer status);
}
