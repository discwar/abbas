package com.major.mapper;

import com.major.entity.Goods;
import com.major.entity.GoodsCategory;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品分类表 Mapper 接口
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-09
 */
public interface GoodsCategoryMapper extends BaseMapper<GoodsCategory> {

    @Select("SELECT * FROM goods_category WHERE id=#{id}")
    GoodsCategory selectGoodsCategoryById(@Param("id") Long id);

    /**
     * 商品分类分页列表
     * @param page
     * @param shopId
     * @return
     */
    List<Map<String, Object>> selectGoodsCategoryPage(Pagination page , @Param("shopId") Long shopId , @Param("continentId") Long continentId,@Param("shopType") Integer shopType);

    /**
     * 商品分类列表不分页
     * @param shopId
     * @param continentId
     * @param shopType
     * @return
     */
    List<Map<String, Object>> selectGoodsCategoryList( @Param("shopId") Long shopId ,@Param("shopType") Integer shopType,@Param("continentId") Long continentId);

    /**
     * 删除、下架商品分类时查看是否有数据
     * @param categoryId
     * @return
     */
    @Select("select id,category_id  from goods where  category_id =#{categoryId} and status <> 0   ")
    List<Goods> selectGoodsByCategoryId(@Param("categoryId") Long categoryId);

    /**
     * 同一个店铺下只能有一个分类名称
     * @param shopId
     * @param name
     * @return
     */
    @Select(" Select id , name,shop_id from  goods_category where shop_id=#{shopId} and name=#{name} and status<>0 ")
    List<Map<String, Object>> selectGoodsCategoryByShopIdAndName( @Param("shopId") Long shopId ,@Param("name") String name);

    /**
     * 进出口新增商品获取分类
     * @param shopType
     * @param continentId
     * @return
     */
    List<Map<String, Object>> selectGoodsCategoryListByJCK(@Param("shopType") Integer shopType,@Param("continentId") Long continentId);

    /**
     * 一元购只能有一个分类
     *
     * @return
     */
    @Select({
            "<script>"+
                    "select id , name,shop_id from  goods_category <where> ${ew.sqlSegment} </where> " +
                    "</script>"})
    List<Map<String, Object>> selectGoodsCategoryByShopId( @Param("ew") Wrapper ew);

}
