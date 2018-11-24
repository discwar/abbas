package com.major.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.major.common.constant.Constants;
import com.major.common.constant.DynamicConfigConstants;
import com.major.common.constant.ImgLogConstants;
import com.major.common.constant.RedisConstants;
import com.major.common.enums.GoodsTypeEnum;
import com.major.common.enums.PromotionTypeEnum;
import com.major.common.enums.ShopTypeEnum;
import com.major.common.enums.StatusResultEnum;
import com.major.common.exception.AgException;
import com.major.entity.Goods;
import com.major.entity.GoodsCategory;
import com.major.mapper.GoodsMapper;
import com.major.model.request.GoodsRequest;
import com.major.model.response.GoodsResponse;
import com.major.model.response.ShopResponse;
import com.major.service.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/18 19:37      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {

    @Autowired
    private IImgLogService imgLogService;

    @Autowired
    private IActivityService activityService;

    @Autowired
    private IShopService shopService;

    @Autowired
    private IShoppingCartService shoppingCartService;

    @Autowired
    private IGoodsCategoryService goodsCategoryService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private IDynamicConfigService dynamicConfigService;

    @Override
    public  boolean addGoods(GoodsRequest goodsRequest,Long operId){
        Goods goods=new Goods();
        //当为折扣、特惠时
        if(Constants.PREFERENCE.equals(goodsRequest.getPromotionType()) || Constants.DISCOUNT.equals(goodsRequest.getPromotionType())){
            if(goodsRequest.getCurrentPrice() !=null && goodsRequest.getOriginalPrice()!=null){
                if(goodsRequest.getCurrentPrice().compareTo(goodsRequest.getOriginalPrice())>=0){
                    throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"现价不能大于等于原价");
                }
            }
        }
        if(GoodsTypeEnum.ONE_DOLLAR_BUY.getValue().equals(goodsRequest.getGoodsType())){
            if(null==goodsRequest.getDeliveryType()){
                throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"配送方式不能为空");
            }
        }
        BeanUtils.copyProperties(goodsRequest, goods);
        //当为普通类型时，原价等于现价
        if (Constants.ORDINARY.equals(goodsRequest.getPromotionType())) {
            goods.setOriginalPrice(goodsRequest.getCurrentPrice());
        }
        if(null==goods.getOriginalPrice()){
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"原价不能为空");
        }
        GoodsCategory goodsCategory = goodsCategoryService.selectGoodsCategoryById(goodsRequest.getCategoryId());
        if (null != goodsCategory) {
            //当为采摘园店铺，并且分类是采摘套票时，商品的封面为：
            if (Constants.PLUCKING_TICKET.equals(goodsCategory.getName())
                    && ShopTypeEnum.PLUCKING_GARDEN.getValue().equals(goodsCategory.getShopType())) {
                String ticketOrderUrl = dynamicConfigService.getDynamicDesc(DynamicConfigConstants.DynamicTypeEnum.ORDER.getValue(), DynamicConfigConstants.ORDER_TICKET_IMG);
                goods.setCoverUrl(ticketOrderUrl);
            }
        }
        if (StringUtils.isEmpty(goods.getCoverUrl())) {
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"商品封面不能为空");
        }

        //goods_type:2-礼品果盒（总部自营店）,shop_id:-2；3-当季热卖（总部自营店）,shop_id:- 3；4-预售（总部自营店）,shop_id:-4；5-1元购（总部自营店）,shop_id:-5
        GoodsTypeEnum goodsTypeEnum=GoodsTypeEnum.getGoodsTypeEnum(goodsRequest.getGoodsType());
        switch (goodsTypeEnum){
            //礼品果盒
            case GIFT_BOX:
                goods.setShopId(GoodsTypeEnum.GIFT_BOX.getValue().longValue());
                break;
             // 当季热卖
            case SEASON_HOT:
                goods.setShopId(GoodsTypeEnum.SEASON_HOT.getValue().longValue());
                break;
            // 下季预售
            case PRE_SALE:
                goods.setShopId(GoodsTypeEnum.PRE_SALE.getValue().longValue());
                if(null==goodsRequest.getDeliveryTime()){
                    throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"发货时间不能为空");
                }
                break;
            // 一元购
            case ONE_DOLLAR_BUY:
                goods.setShopId(GoodsTypeEnum.ONE_DOLLAR_BUY.getValue().longValue());
                String currentPrice="1";
                goods.setCurrentPrice(new BigDecimal(currentPrice));
                break;
             default:
                break;
        }
        goods.setStatus(Constants.STATUS_NORMAL);
        if(goodsRequest.getImgUrls() !=null && goodsRequest.getImgUrls().size() >0 ) {
            goods.setImgUrls(String.join(",",goodsRequest.getImgUrls()));
        }
        boolean insert = super.insert(goods);
        if (insert){
            addRedisGoodsStock(goods.getId());
        }
        imgLogService.addImgLog(goods.getImgUrls(),ImgLogConstants.FROM_ABLE_GOODS,goods.getId(),ImgLogConstants.FROM_FIELD_IMGURLS,operId);
        imgLogService.addImgLog(goodsRequest.getCoverUrl(), ImgLogConstants.FROM_ABLE_GOODS,goods.getId(),ImgLogConstants.FROM_FIELD_COVER_URL,operId);
        return true;
    }

    @Override
    public boolean updateGoods(GoodsRequest goodsRequest,Long goods_id,Long operId) {
        Goods goods=super.selectById(goods_id);
        if(GoodsTypeEnum.ONE_DOLLAR_BUY.getValue().equals(goodsRequest.getGoodsType())){
            if(null==goodsRequest.getDeliveryType()){
                throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"配送方式不能为空");
            }
        }
        //当为折扣、特惠时
        if(Constants.PREFERENCE.equals(goodsRequest.getPromotionType()) || Constants.DISCOUNT.equals(goodsRequest.getPromotionType())){
            if(goodsRequest.getCurrentPrice() !=null && goodsRequest.getOriginalPrice()!=null){
                if(goodsRequest.getCurrentPrice().compareTo(goodsRequest.getOriginalPrice())>=0){
                    throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"现价不能大于等于原价");
                }
            }
        }
        //当商品原数据中的分类id为空时，表示重新分配商品分类，此时把购物车之前禁用的商品状态设置为可用
        if(null==goods.getCategoryId() && null!=goodsRequest.getCategoryId()){
            shoppingCartService.updateShoppingCartByGoodsId(goods_id,Constants.STATUS_DISABLE,Constants.STATUS_NORMAL);
        }
        if(goodsRequest.getImgUrls() !=null && goodsRequest.getImgUrls().size() >0 ) {
           //如果有图片地址时，先删除原来的oss上的图片和删除数据库中数据再添加goods 在批量添加图片记录表
            imgLogService.updateImgLogsByTable(goods.getImgUrls(),ImgLogConstants.FROM_ABLE_GOODS,goods_id,String.join(",",goodsRequest.getImgUrls()),ImgLogConstants.FROM_FIELD_IMGURLS);
            goods.setImgUrls(String.join(",",goodsRequest.getImgUrls()));
        }
        if(StringUtils.isNotEmpty(goodsRequest.getCoverUrl())){
            imgLogService.updateImgLogsByTable(goods.getCoverUrl(),ImgLogConstants.FROM_ABLE_GOODS,goods_id,String.join(",",goodsRequest.getCoverUrl()),ImgLogConstants.FROM_FIELD_COVER_URL);
        }
        BeanUtils.copyProperties(goodsRequest, goods);
        //当为普通类型时，原价等于现价
        if (Constants.ORDINARY.equals(goodsRequest.getPromotionType())) {
            goods.setOriginalPrice(goodsRequest.getCurrentPrice());
        }
        if(null==goods.getOriginalPrice()){
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"原价不能为空");
        }
        GoodsCategory goodsCategory = goodsCategoryService.selectGoodsCategoryById(goodsRequest.getCategoryId());
        if (null != goodsCategory) {
            //当为采摘园店铺，并且分类是采摘套票时，商品的封面为：
            if (!Constants.PLUCKING_TICKET.equals(goodsCategory.getName())
                    && ! ShopTypeEnum.PLUCKING_GARDEN.getValue().equals(goodsCategory.getShopType())) {
                if(StringUtils.isEmpty(goods.getCoverUrl())){
                    throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"商品封面不能为空");
                }
            }
        }

        //当为采摘园店铺类型时，费用不包括说明为空时:置空
        ShopResponse shopResponse=shopService.selectShopById(goods.getShopId());
        if(ShopTypeEnum.PLUCKING_GARDEN.getValue().equals(shopResponse.getShopType()) && "".equalsIgnoreCase(goodsRequest.getCostNotInclude())){
              baseMapper.removeCostNotInclude(goods_id);
        }

        if(GoodsTypeEnum.GIFT_BOX.getValue().equals(goodsRequest.getGoodsType()) || GoodsTypeEnum.SEASON_HOT.getValue().equals(goodsRequest.getGoodsType()) ||
                GoodsTypeEnum.PRE_SALE.getValue().equals(goodsRequest.getGoodsType())|| GoodsTypeEnum.ONE_DOLLAR_BUY.getValue().equals(goodsRequest.getGoodsType())){
               goods.setShopId(null);
        }
        boolean flag = super.updateById(goods);
        if (flag && Constants.STATUS_ENABLE.equals(goods.getStatus()) ){
            addRedisGoodsStock(goods.getId());
        }
        return flag;
    }

    @Override
    public boolean deleteGoods( Long goods_id,Long operId){
        Goods goods=super.selectById(goods_id);
        //修改时，先查询该条记录是否有图片地址，有的话先批量删除oss上的图片，在删除数据库表img_log中对应的数据
        if(goods.getImgUrls()!=null) {
            //删除oss上的图片和删除数据库中数据
            imgLogService.deleteImgLogsByTable(goods.getImgUrls(),ImgLogConstants.FROM_ABLE_GOODS,goods_id,operId,ImgLogConstants.FROM_FIELD_IMGURLS);
        }
        if(StringUtils.isNotEmpty(goods.getCoverUrl())){
            imgLogService.deleteImgLogsByTable(goods.getImgUrls(),ImgLogConstants.FROM_ABLE_GOODS,goods_id,operId,ImgLogConstants.FROM_FIELD_COVER_URL);
        }
        goods.setStatus(Constants.STATUS_DELETE);
        boolean flag = goods.updateById();
        if (flag){
         deleteRedisGoodsInfo(goods_id);
        }
        return flag;
    }

    @Override
    public GoodsResponse selectGoodsById(Long goods_id) {
        return baseMapper.selectGoodsById(goods_id);
    }

     @Override
     public  Page<Map<String, Object>> selectShopGoodsByShopIdPage(Page<Map<String, Object>> page, Long shopId,Long categoryId, String goodsName, String orderNumberStart, String orderNumberStop,
                                                          String currentPriceStart, String currentPriceStop, Integer status, String totalCountStart, String totalCountStop,
                                                          String deadlineStartTime, String deadlineStopTime,Integer promotionType) {
         Wrapper ew = new EntityWrapper();
         ew.where(" s.`status`={0}",Constants.STATUS_ENABLE);
         //如果搜索字段中状态字段为空时，默认不显示删除的商品状态
         if(null==status){
             ew.and("gs.`status`<>{0}",Constants.STATUS_DELETE);
         }
         ew.and("s.id={0}",shopId);
         Map<String,Object> map=new HashMap<>();
         map.put("gs.`status`",status);
         map.put("gc.id",categoryId);
         map.put("gs.promotion_type",promotionType);
         ew.allEq(map);
         if(StringUtils.isNotEmpty(goodsName)){
             ew.like(" gs.`name`",goodsName);
         }
         if(StringUtils.isNotEmpty(orderNumberStart)) {
             //大于
             ew.ge("gs.orders_count",orderNumberStart);
         }
         if(StringUtils.isNotEmpty(orderNumberStop)) {
             //小于
             ew.le("gs.orders_count",orderNumberStop);
         }
         if(StringUtils.isNotEmpty(currentPriceStart)) {
             ew.ge("gs.current_price",currentPriceStart);
         }
         if(StringUtils.isNotEmpty(currentPriceStop)) {
             ew.le("gs.current_price",currentPriceStop);
         }
         if(StringUtils.isNotEmpty(totalCountStart)) {
             ew.ge("gs.total_count",totalCountStart);
         }
         if(StringUtils.isNotEmpty(totalCountStop)) {
             ew.le("gs.total_count",totalCountStop);
         }
         if(StringUtils.isNotEmpty(deadlineStartTime)) {
             ew.ge("gs.create_time",deadlineStartTime);
         }
         if(StringUtils.isNotEmpty(deadlineStopTime)) {
             ew.le("gs.create_time",deadlineStopTime);
         }
         ew.groupBy("gs.id ");
         ew.orderBy("gs.create_time DESC");
         return  page.setRecords(baseMapper.selectGoodsByShopId(page,ew));
     }

    @Override
    public  Page<Map<String, Object>> selectGoodsList(Page<Map<String, Object>> page,String goodsName) {
        return page.setRecords(baseMapper.selectAGGoodsList(page,goodsName));
    }

    @Override
    public   Page<Map<String, Object>> selectGoodsListBySysUserId(Page<Map<String, Object>> page, Long sysUserId,String goodsName) {
        return page.setRecords(baseMapper.selectGoodsList(page,sysUserId,goodsName));
    }

    @Override
    public   Page<Map<String, Object>> selectGoodsListByGoodsName(Page<Map<String, Object>> page,String goodsName,Integer shopType) {
        return page.setRecords(baseMapper.selectGoodsListByGoodsName(page,goodsName,shopType));
    }

    @Override
    public  Page<Map<String, Object>> selectShopGoodsBySysUserIdPage(Page<Map<String, Object>> page,  Long sysUserId,Integer shopType,String goodsName,Integer categoryId,Long continentId,
                                                            String currentPriceStart,String currentPriceStop,Integer status, String totalCountStart,String totalCountStop,
                                                            String deadlineStartTime,String deadlineStopTime,String orderNumberStart,String orderNumberStop,Integer promotionType) {
        Wrapper ew = new EntityWrapper();
        ew.where(" s.`status`={0}",Constants.STATUS_ENABLE);
        //如果搜索字段中状态字段为空时，默认不显示删除的商品状态
        if(null==status) {
            ew.and("gs.`status`<>{0}", Constants.STATUS_DELETE);
        }
        ew.and("s.sys_user_id={0}",sysUserId);
        //自动判空，当有值时sql含义表示 =
        Map<String,Object> map=new HashMap<>();
        map.put("s.shop_type",shopType);
        map.put("gc.id",categoryId);
        map.put("gc.continent_id",continentId);
        map.put("gs.`status`",status);
        map.put("gs.promotion_type",promotionType);
        ew.allEq(map);
        if(StringUtils.isNotEmpty(goodsName)){
            ew.like(" gs.`name`",goodsName);
        }
        if(StringUtils.isNotEmpty(currentPriceStart)) {
            ew.ge("gs.current_price",currentPriceStart);
        }
        if(StringUtils.isNotEmpty(currentPriceStop)) {
            ew.le("gs.current_price",currentPriceStop);
        }
        if(StringUtils.isNotEmpty(totalCountStart)) {
            ew.ge("gs.total_count",totalCountStart);
        }
        if(StringUtils.isNotEmpty(totalCountStop)) {
            ew.le("gs.total_count",totalCountStop);
        }
        if(StringUtils.isNotEmpty(orderNumberStart)) {
            ew.ge("gs.orders_count",orderNumberStart);
        }
        if(StringUtils.isNotEmpty(orderNumberStop)) {
            ew.le("gs.orders_count",orderNumberStop);
        }
        if(StringUtils.isNotEmpty(deadlineStartTime)) {
            ew.ge("gs.create_time",deadlineStartTime);
        }
        if(StringUtils.isNotEmpty(deadlineStopTime)) {
            ew.le("gs.create_time",deadlineStopTime);
        }
        ew.groupBy("gs.id ");
        ew.orderBy("gs.`status`,gs.create_time DESC");

        return  page.setRecords(baseMapper.selectShopGoodsBySysUserIdPage(page,ew));
    }

    @Override
    public Page<Map<String, Object>> selectAGShopGoodsPage(Page<Map<String, Object>> page,Integer shopType,String goodsName,Integer categoryId,
                                                    String currentPriceStart,String currentPriceStop,
                                                    String orderNumberStart,String orderNumberStop,String createTimeStart,String createTimeStop,Integer promotionType,Integer status) {
        Wrapper ew = new EntityWrapper();
        ew.where("s.shop_type={0}",shopType);
        //如果搜索字段中状态字段为空时，默认不显示删除的商品状态
        if(null==status) {
            ew.and("gs.`status`<>{0}", Constants.STATUS_DELETE);
        }
        //自动判空，当有值时sql含义表示 =
        Map<String,Object> map=new HashMap<>();
        map.put("gc.id",categoryId);
        map.put("gs.promotion_type",promotionType);
        map.put("gs.`status`",status);
        ew.allEq(map);
        if(StringUtils.isNotEmpty(goodsName)){
            ew.like(" gs.`name`",goodsName);
        }
        if(StringUtils.isNotEmpty(currentPriceStart)) {
            ew.ge("gs.current_price",currentPriceStart);
        }
        if(StringUtils.isNotEmpty(currentPriceStop)) {
            ew.le("gs.current_price",currentPriceStop);
        }
        if(StringUtils.isNotEmpty(orderNumberStart)) {
            ew.ge("gs.orders_count",orderNumberStart);
        }
        if(StringUtils.isNotEmpty(orderNumberStop)) {
            ew.le("gs.orders_count",orderNumberStop);
        }
        if(StringUtils.isNotEmpty(createTimeStart)) {
            ew.ge("gs.create_time",createTimeStart);
        }
        if(StringUtils.isNotEmpty(createTimeStop)) {
            ew.le("gs.create_time",createTimeStop);
        }
        ew.groupBy("gs.id ");
        ew.orderBy("gs.`status`,gs.create_time DESC");
        return  page.setRecords(baseMapper.selectZYShopGoodsPage(page,ew));
    }

    @Override
    public boolean shelvesGoods( Long goodsId,Integer goodsType) {
        //1-为上架;2-为下架
        Goods goods=new Goods();
        goods.setStatus(goodsType);
        goods.setId(goodsId);
        boolean flag = goods.updateById();
        if (flag){
            if (Constants.STATUS_ENABLE.equals(goodsType)){
                addRedisGoodsStock(goodsId);
            }else{
                deleteRedisGoodsInfo(goodsId);
            }
        }
        return flag;
    }

    @Override
    public boolean removeGoodsCategoryId(List<Goods> goodsList){
        for (Goods goods : goodsList){
            //把符合购物车中的商品状态设置成禁用
            shoppingCartService.updateShoppingCartByGoodsId(goods.getId(),Constants.STATUS_NORMAL,Constants.STATUS_DISABLE);
            baseMapper.removeGoodsCategoryId(goods.getId());
            deleteRedisGoodsInfo(goods.getId());
        }
        return true;
    }


    @Override
    public boolean cancelGoodsStock(Long shopId, Long goodsId, Long goodsNum, Integer goodsMark, Integer activityNum) {
        // 活动商品更新库存量及订单量
        if (PromotionTypeEnum.SEC_KILL.getValue().equals(goodsMark)
                || PromotionTypeEnum.GROUP_BUYING.getValue().equals(goodsMark)
                || PromotionTypeEnum.BARGAIN.getValue().equals(goodsMark)) {
            activityService.cancelActivityStock(goodsId, activityNum.longValue());
        }
        //针对支付超时，加回商品库存，减去商品订单量
        Goods goods = super.selectById(goodsId);
        if(null==goods){
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"找不到该商品，更新商品库存、订单量失败");
        }
        Long totalCount = ObjectUtils.defaultIfNull(goods.getTotalCount(), 0L) + goodsNum;
        Long ordersCount = ObjectUtils.defaultIfNull(goods.getOrdersCount(), 0L) - goodsNum;
        if (totalCount < 0L) {
            totalCount = 0L;
        }
        if (ordersCount < 0L) {
            ordersCount = 0L;
        }
        goods.setTotalCount(totalCount);
        goods.setOrdersCount(ordersCount);
        boolean flag = this.updateById(goods);
        if (flag){
            changeRedisGoodsStock(goodsId,goodsNum.intValue(),Constants.ORDINARY_GOODS,Constants.GOODS_NUM_ADD);
        }
        return flag;
    }

    @Override
    public void changeRedisGoodsStock(Long goodsId, Integer goodNum,Integer goodsType,Integer updateType) {
        //如果是活动，则更新redis活动库存
        if (Constants.ACTIVITY_GOODS.equals(goodsType)){
            String activityRedisKeyName  = RedisConstants.SEC_KILL_ACTIVITY_PREFIX+goodsId;
            String activityStockRedisKey = RedisConstants.SEC_KILL_STOCK_PREFIX + goodsId;
            if (redisService.exists(activityRedisKeyName)){
                if (Constants.GOODS_NUM_ADD.equals(updateType) ){
                    //加入数量
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < goodNum; i ++) {
                        list.add("1");
                    }
                    redisService.lPushAll(activityStockRedisKey,list);
                }else{
                    //拿走数量
                    for (int i = 0; i < goodNum; i++) {
                        redisService.lPop(activityStockRedisKey);
                    }
                }
            }
        }else{
            //统一更新redis商品库存
            String redisKeyName   = RedisConstants.GOODS_STOCK_PREFIX + goodsId;
            if(redisService.exists(redisKeyName)) {
                Integer goodsRedisCount = (Integer) redisService.hmGet(redisKeyName,Constants.GOODS_TOTAL_COUNT);
                Integer totalCount;
                if (Constants.GOODS_NUM_ADD.equals(updateType) ){
                    totalCount = ObjectUtils.defaultIfNull(goodsRedisCount, 0) + goodNum;
                }else {
                    totalCount = ObjectUtils.defaultIfNull(goodsRedisCount, 0) - goodNum;
                    if (totalCount <0){
                        totalCount = 0;
                    }
                }
                redisService.hmSet(redisKeyName, Constants.GOODS_TOTAL_COUNT, totalCount);
            }
        }
    }

    @Override
    public void addRedisGoodsStock(Long goodsId) {
        String redisKeyName = RedisConstants.GOODS_STOCK_PREFIX + goodsId;
        Map<String, Object> goodsStatus = this.getGoodsStatus(goodsId);
        if ( null != goodsStatus){
            redisService.pullAllForever(redisKeyName, goodsStatus);
        }
    }

    @Override
    public Map<String, Object> getGoodsStatus(Long goodId) {
        EntityWrapper ew = new EntityWrapper();
        ew.where("s.status = {0}",Constants.STATUS_ENABLE)
                .and("g.id = {0}",goodId)
                .and("g.status = {0}",Constants.STATUS_ENABLE)
                .and("gc.status = {0}",Constants.STATUS_ENABLE)
                .last("limit 0,1");
        return  baseMapper.getGoodsStatus(ew);
    }

    @Override
    public  List<Goods> selectGoodsInfoByShopId(Long shopId){
        return baseMapper.selectGoodsInfoByShopId(shopId);
    }

    @Override
    public void deleteRedisGoodsInfo(Long goodsId) {
        String redisKeyName   = RedisConstants.GOODS_STOCK_PREFIX + goodsId;
        redisService.remove(redisKeyName);
    }
}
