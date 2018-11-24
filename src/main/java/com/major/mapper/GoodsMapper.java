package com.major.mapper;


import com.major.entity.Goods;
import com.major.model.response.GoodsResponse;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

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
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 通用版获取当前商品信息
     * @param id
     * @return
     */
    @Select("SELECT g.*,gc.* FROM goods g left join goods_category gc on g. category_id = gc.id WHERE g.id=#{id}")
    GoodsResponse selectGoodsById(@Param("id") Long id);

    /**
     * 后台管理员-获取当前商家下商品信息列表
     * @param page
     * @param ew
     * @return
     */
    List<Map<String,Object> > selectGoodsByShopId(Pagination page,@Param("ew") Wrapper ew);

    /**
     * 针对选择活动商品查询 筛选条件只能是普通商品
     * 可以显示曾经添加活动了，但是已经删除的商品
     * @param goodsName
     * @param sysUserId
     * @param goodsName
     * @return
     */
    List< Map<String, Object>> selectGoodsList(Pagination page,@Param("sysUserId") Long sysUserId,@Param("goodsName") String goodsName);

    /**
     * 根据当前用户获取商品信息列表
     * @param page
     * @param ew
     * @return
     */
    List<Map<String,Object> > selectShopGoodsBySysUserIdPage(Pagination page, @Param("ew") Wrapper ew);

    /**
     * 总部自营-获取预售、当季热卖、礼品果盒商品信息列表
     *
     * @param page
     * @return
     */
    List<Map<String,Object> > selectZYShopGoodsPage(Pagination page,@Param("ew") Wrapper ew);


    /**
     * 可根据商品名称模糊搜索
     * @param page
     * @param goodsName
     * @param shopType
     * @return
     */
    @Select({
            "<script> "+
                    "   SELECT  " +
                    "        s.shop_name,  " +
                    "        gs.id as goods_id,  " +
                    "        gs.`name` as goods_name ,s.shop_type " +
                    "        FROM shop s  " +
                    "        LEFT JOIN goods gs ON  gs.shop_id=s.id " +
                    "        WHERE  " +
                    "          gs.`status`=1  " +
                    "        <if test='goodsName!=null '>  " +
                    "            AND gs.`name`  like concat('%',#{goodsName},'%')  " +
                    "        </if>  " +
                    "        <if test='shopType!=null '>  " +
                    "            AND s.shop_type =#{shopType}  " +
                    "        </if>  " +
                    "        ORDER BY  " +
                    "        gs.create_time DESC "+
                    "</script>"}  )
    List<Map<String, Object>> selectGoodsListByGoodsName(Pagination page,@Param("goodsName") String goodsName,@Param("shopType") Integer shopType);


    /**
     * 置为空
     * @param id
     * @return
     */
    @Update("update goods set category_id = null,update_time=now() where id =#{id}")
    int removeGoodsCategoryId(@Param("id") Long id);


    /**
     * 总部添加活动，选择商品数据
     * @param page
     * @param goodsName
     * @return
     */
    @Select({
            "<script> "+
                    " SELECT " +
                    "        s.shop_name, s.id as shop_id, gs.id as goods_id, " +
                    "        gs.`name` as goods_name, gc.`name`as category_name, " +
                    "        gs.original_price, gs.current_price,gs.total_count  " +
                    "        FROM shop s " +
                    "        LEFT JOIN goods gs ON  gs.shop_id=s.id " +
                    "        LEFT JOIN goods_category gc ON gc.id=gs.category_id  " +
                    "        LEFT JOIN activity a ON gs.id=a.goods_id  " +
                    "        WHERE  " +
                    "        gs.`status`=1  AND gs.promotion_type=2  AND s.shop_type=1 " +
                    "        AND s.is_relation_type=0  AND a.goods_id IS NULL   " +
                    "        <if test='goodsName!=null '>  " +
                    "            AND gs.`name`  like concat('%',#{goodsName},'%')  " +
                    "        </if>  " +
                    "        ORDER BY  " +
                    "        gs.create_time DESC "+
                    "</script>"}  )
    List< Map<String, Object>> selectAGGoodsList(Pagination page,@Param("goodsName") String goodsName);

    /**
     * 置空费用不包括说明
     * @param id
     * @return
     */
    @Update("update goods set cost_not_include = null,update_time=now() where id =#{id}")
    int removeCostNotInclude(@Param("id") Long id);

    /**
     * 判断商品的状态
     * @param ew
     * @return
     */
    Map<String, Object> getGoodsStatus(@Param("ew") Wrapper ew);

    /**
     * 查询一家店铺下所有上架的商品
     * @param shopId
     * @return
     */
    @Select("select id,shop_id,status from goods where shop_id=#{shopId} and status=1  ")
    List<Goods> selectGoodsInfoByShopId(@Param("shopId") Long shopId);

}
