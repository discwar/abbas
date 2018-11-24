package com.major.service.impl;

import com.major.common.constant.Constants;
import com.major.common.enums.GoodsTypeEnum;
import com.major.common.enums.ShopTypeEnum;
import com.major.common.enums.StatusResultEnum;
import com.major.common.exception.AgException;
import com.major.entity.Goods;
import com.major.entity.GoodsCategory;
import com.major.mapper.GoodsCategoryMapper;
import com.major.model.request.GoodsCategoryRequest;
import com.major.service.IGoodsCategoryService;
import com.major.service.IGoodsService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品分类表 服务实现类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-09
 */
@Service
public class GoodsCategoryServiceImpl extends ServiceImpl<GoodsCategoryMapper, GoodsCategory> implements IGoodsCategoryService {

    @Autowired
    private IGoodsService goodsService;

    @Override
    public boolean addGoodsCategory(GoodsCategoryRequest goodsCategoryRequest) {
        //同一家店铺只能有一个分类名称
        if(goodsCategoryRequest.getShopId()!=null){
            List<Map<String, Object>> list=baseMapper.selectGoodsCategoryByShopIdAndName(goodsCategoryRequest.getShopId().longValue(),goodsCategoryRequest.getName());
            if(list!=null && list.size()>0){
                throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"存在同名的商品分类名称");
            }
        }
        //同一家店只能有6个上架分类
        if(null!=goodsCategoryRequest.getShopId()){
            Wrapper ew = new EntityWrapper();
            ew.where("shop_id={0}",goodsCategoryRequest.getShopId());
            ew.and("status={0}",Constants.STATUS_NORMAL);
            List<Map<String, Object>> categoryList=baseMapper.selectGoodsCategoryByShopId(ew);
            if(null!=categoryList && categoryList.size()>5){
                throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"一家店铺只能有六个上架分类");
            }
        }
        //当店铺类型为:爱果小店、水果店.、扶贫区，可不填shopId,其它条件必填
        if(goodsCategoryRequest.getShopType()!=null ){
            if( goodsCategoryRequest.getShopType().equals( ShopTypeEnum.AIGUO_SHOP.getValue()) || goodsCategoryRequest.getShopType().equals(ShopTypeEnum.NEAR_FRUIT.getValue())
                    || goodsCategoryRequest.getShopType().equals( ShopTypeEnum.POVERTY_ALLEVIATION.getValue())) {
                if(goodsCategoryRequest.getShopId()==null) {
                    throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"请选择店铺");
                }
            }
        }

       if(ShopTypeEnum.IMPORT_FRUIT.getValue().equals(goodsCategoryRequest.getShopType())){
           if(null==goodsCategoryRequest.getContinentId()) {
               throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"请选择大洲");
           }
       }
        if(goodsCategoryRequest.getImgUrls()!=null && goodsCategoryRequest.getImgUrls().length()>Constants.DB_FIELD_512 ) {
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"分类图片地址过长");
        }
        GoodsCategory goodsCategory=new GoodsCategory();
        BeanUtils.copyProperties(goodsCategoryRequest, goodsCategory);
        //goods_type:2-礼品果盒（总部自营店）,shop_id:-2；3-当季热卖（总部自营店）,shop_id:- 3；4-预售（总部自营店）,shop_id:-4；5-1元购（总部自营店）,shop_id:-5
        if(null!=goodsCategoryRequest.getGoodsType()){
            GoodsTypeEnum goodsTypeEnum=GoodsTypeEnum.getGoodsTypeEnum(goodsCategoryRequest.getGoodsType());
            switch (goodsTypeEnum){
                //礼品果盒
                case GIFT_BOX:
                    goodsCategory.setShopId(GoodsTypeEnum.GIFT_BOX.getValue().longValue());
                    break;
                // 当季热卖
                case SEASON_HOT:
                    goodsCategory.setShopId(GoodsTypeEnum.SEASON_HOT.getValue().longValue());
                    break;
                // 下季预售
                case PRE_SALE:
                    goodsCategory.setShopId(GoodsTypeEnum.PRE_SALE.getValue().longValue());
                    break;
                // 一元购
                case ONE_DOLLAR_BUY:
                    goodsCategory.setShopId(GoodsTypeEnum.ONE_DOLLAR_BUY.getValue().longValue());
                    //一元购只能有一个分类
                    Wrapper ew = new EntityWrapper();
                    ew.where("shop_id={0}",goodsCategory.getShopId());
                    ew.and("status<>{0}",Constants.STATUS_DELETE);
                    List<Map<String, Object>> list=baseMapper.selectGoodsCategoryByShopId(ew);
                    if(null!=list && list.size()>=1){
                        throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"一元购只能添加一个分类");
                    }
                    break;
                default:
                    break;
            }
        }
        goodsCategory.setStatus(Constants.STATUS_NORMAL);
        return super.insert(goodsCategory);
    }

    @Override
    public boolean updateGoodsCategory(GoodsCategoryRequest goodsCategoryRequest,Long category_id) {
        GoodsCategory goodsCategory=selectById(category_id);
       if(goodsCategoryRequest.getImgUrls()!=null && goodsCategoryRequest.getImgUrls().length()>Constants.DB_FIELD_512 ) {
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"分类图片地址过长");
        }
        //只有原数据库中的名称和现传的分类名称不一致时，校验
        if(!goodsCategory.getName().equals(goodsCategoryRequest.getName())){
            //同一家店铺只能有一个分类名称
            if(goodsCategoryRequest.getShopId()!=null){
                List<Map<String, Object>> list=baseMapper.selectGoodsCategoryByShopIdAndName(goodsCategoryRequest.getShopId().longValue(),goodsCategoryRequest.getName());
                if(list!=null && list.size()>0){
                    throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"存在同名的商品分类名称");
                }
            }
        }
        BeanUtils.copyProperties(goodsCategoryRequest, goodsCategory);
        goodsCategory.setId(category_id);
        if(GoodsTypeEnum.GIFT_BOX.getValue().equals(goodsCategoryRequest.getGoodsType()) || GoodsTypeEnum.SEASON_HOT.getValue().equals(goodsCategoryRequest.getGoodsType()) ||
                GoodsTypeEnum.PRE_SALE.getValue().equals(goodsCategoryRequest.getGoodsType())|| GoodsTypeEnum.ONE_DOLLAR_BUY.getValue().equals(goodsCategoryRequest.getGoodsType())){
            goodsCategory.setShopId(null);
        }
        return super.updateById(goodsCategory);
    }



    @Override
    public boolean deleteGoodsCategory( Long category_id) {
        //分类删除时，置空关联此分类的商品中的分类id，同时也把购物车中的商品状态改为禁用
        List<Goods> list=baseMapper.selectGoodsByCategoryId(category_id);
        if(list!=null && list.size()>0){
            goodsService.removeGoodsCategoryId(list);
        }
        GoodsCategory goodsCategory=new GoodsCategory();
        goodsCategory.setStatus(Constants.STATUS_DELETE);
        goodsCategory.setId(category_id);
      return   goodsCategory.updateById();
    }

    @Override
    public GoodsCategory selectGoodsCategoryById( Long id) {
     return baseMapper.selectGoodsCategoryById(id);
    }

    @Override
    public List<Goods> selectGoodsByCategoryId(Long categoryId){
        return baseMapper.selectGoodsByCategoryId(categoryId);
    }


    @Override
    public Page<Map<String, Object>> selectGoodsCategoryPage(Page<Map<String, Object>> page,Long shopId,Long continentId,Integer shop_type) {
        return page.setRecords(baseMapper.selectGoodsCategoryPage(page,shopId,continentId,shop_type));
    }
    @Override
    public Map<String, Object> selectGoodsCategoryListByShopId(Long shopId) {
        Map<String, Object> map=new HashMap<>(1);
        map.put("goods_category_list",baseMapper.selectGoodsCategoryList(shopId,null,null));
        return map;
    }

    @Override
    public Map<String, Object> selectGoodsCategoryListByShopType(Integer shopType,Long continentId) {
        Map<String, Object> map=new HashMap<>();
        if(null!=continentId){
            map.put("goods_category_list",baseMapper.selectGoodsCategoryListByJCK(shopType,continentId));
            return map;
        }
        map.put("goods_category_list",baseMapper.selectGoodsCategoryList(null,shopType,continentId));
        return map;
    }

    @Override
    public  boolean shelvesGoodsCategory( Long categoryId,Integer categoryType){
        //1-为上架;2-为下架
        GoodsCategory goodsCategory=selectById(categoryId);
        goodsCategory.setStatus(categoryType);
        boolean flag = goodsCategory.updateById();
        if (flag ) {
            List<Goods> goodsList = baseMapper.selectGoodsByCategoryId(categoryId);
            if (null != goodsList && goodsList.size() >0){
                if (Constants.STATUS_ENABLE.equals(categoryType)) {
                    //同一家店只能有6个上架分类
                    if(null!=goodsCategory.getShopId()){
                        Wrapper ew = new EntityWrapper();
                        ew.where("shop_id={0}",goodsCategory.getShopId());
                        ew.and("status={0}",Constants.STATUS_NORMAL);
                        List<Map<String, Object>> categoryList=baseMapper.selectGoodsCategoryByShopId(ew);
                        if(null!=categoryList && categoryList.size()>5){
                            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"一家店铺只能有六个上架分类");
                        }
                    }
                    for (Goods goods : goodsList){
                        goodsService.addRedisGoodsStock(goods.getId());
                    }
                }else {
                    for (Goods goods : goodsList) {
                        goodsService.deleteRedisGoodsInfo(goods.getId());
                    }
                }
            }
        }
        return flag;
    }


}
