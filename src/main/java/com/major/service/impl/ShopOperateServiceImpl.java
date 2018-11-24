package com.major.service.impl;

import com.major.common.constant.Constants;
import com.major.common.constant.RedisConstants;
import com.major.common.enums.GoodsTypeEnum;
import com.major.common.enums.ShopTypeEnum;
import com.major.common.enums.StatusResultEnum;
import com.major.common.exception.AgException;
import com.major.entity.Goods;
import com.major.entity.ShopOperate;
import com.major.mapper.ShopOperateMapper;
import com.major.model.request.ShopOperateRequest;
import com.major.model.response.ShopResponse;
import com.major.service.IGoodsService;
import com.major.service.IShopOperateService;
import com.major.service.IShopService;
import com.major.service.RedisService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 店铺运营表 服务实现类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-07
 */
@Service
public class ShopOperateServiceImpl extends ServiceImpl<ShopOperateMapper, ShopOperate> implements IShopOperateService {

    @Autowired
    private IShopService shopService;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisService redisService;
    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean addShopOperate(ShopOperate shopOperate) {
        return super.insert(shopOperate);
    }

    @Override
    public Map<String,Object> selectShopOperateBySysUserId(Long sysUserId,Integer shopType) {
        return baseMapper.selectShopOperateBySysUserId(sysUserId,shopType);
    }

    @Override
    public Map<String,Object> selectShopOperateByShopId(Long shopId) {
        return baseMapper.selectShopOperateByShopId(shopId);
    }
    @Override
    public boolean updateShopOperate(ShopOperateRequest shopOperateRequest, Long shopOperateId) {
        if(null!=shopOperateRequest.getIsMessageNotice() && Constants.YES.equals(shopOperateRequest.getIsMessageNotice()) && shopOperateRequest.getPushPhone()==null){
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"接收短信通知的手机号不能为空");
        }
        ShopOperate shopOperate=selectById(shopOperateId);
        ShopResponse shopResponse=shopService.selectShopById(shopOperate.getShopId());
        //当为爱果小店和附近水果店时
        if(ShopTypeEnum.AIGUO_SHOP.getValue().equals(shopResponse.getShopType()) || ShopTypeEnum.NEAR_FRUIT.getValue().equals(shopResponse.getShopType())){
            //起送价不能为空
            if(null==shopOperateRequest.getMinDeliveryPrice()){
                throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"起送价不能为空");
            }
            if(null==shopOperateRequest.getSpecialGuestRebate()){
                throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"专客折扣不能为空");
            }
            int specialGuestRebate=shopOperateRequest.getSpecialGuestRebate().intValue();
            if(specialGuestRebate<0 || specialGuestRebate>10){
                throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"专客折扣只能在0-10之间");
            }
        }else {
            //其它店铺类型统一为0
            BigDecimal bigDecimal=new BigDecimal(0);
            shopOperateRequest.setMinDeliveryPrice(bigDecimal);
        }
        //当shopId为1时，修改所有总部自营的店铺信息
        if(shopOperateId.equals(Constants.SHOP_ZY_ID)){
            this.update(shopOperateRequest,GoodsTypeEnum.GIFT_BOX.getValue().longValue());
            this.update(shopOperateRequest,GoodsTypeEnum.SEASON_HOT.getValue().longValue());
            this.update(shopOperateRequest,GoodsTypeEnum.PRE_SALE.getValue().longValue());
            this.update(shopOperateRequest,GoodsTypeEnum.ONE_DOLLAR_BUY.getValue().longValue());
        }
        //当修改店铺有关联爱果小店时，同样也把其修改
        if(Constants.SHOP_IS_RELATION_TYPE_TRUE.equals(shopResponse.getIsRelationType())) {
            ShopResponse shopAndShopOperate=baseMapper.selectShopAndShopOperateByRelationId(shopOperate.getShopId());
            if(null!=shopAndShopOperate){
                this.update(shopOperateRequest,shopAndShopOperate.getShopOperateId());
            }
        }
       return this.update(shopOperateRequest,shopOperateId);
    }

    @Override
    public boolean updateShopOperateQR(Long oprateId,String qr){
        ShopOperate shopOperate=new ShopOperate();
        shopOperate.setId(oprateId);
        shopOperate.setQrCodeUrl(qr);
        return super.updateById(shopOperate);
    }

    @Override
    public boolean deleteShopOperate(Long shopId) {
        ShopOperate shopOperate=baseMapper.selectShopOperateByShopIdOne(shopId);
        shopOperate.setStatus(Constants.STATUS_DELETE);
        return shopOperate.updateById();
    }

    @Override
    public ShopOperate selectShopOperateByShopIdOne( Long shopId) {
        return baseMapper.selectShopOperateByShopIdOne(shopId);
    }

    public boolean update(ShopOperateRequest shopOperateRequest, Long shopOperateId){
        ShopOperate shopOperate=selectById(shopOperateId);
        ShopResponse shopResponse=shopService.selectShopById(shopOperate.getShopId());
        //当为爱果小店和附近水果店类型时，配送方式必填
        if(ShopTypeEnum.AIGUO_SHOP.getValue().equals(shopResponse.getShopType()) || ShopTypeEnum.NEAR_FRUIT.getValue().equals(shopResponse.getShopType()) ){
            if(null==shopOperateRequest.getDeliveryType()){
                throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"配送方式不能为空");
            }
            //当如果为爱果配送时，置空最远配送距离
            if(Constants.DELIVERY_TYPE_AG.equals(shopOperateRequest.getDeliveryType())){
                this.baseMapper.removFarDistance(shopOperateId);
            }
        }
        BeanUtils.copyProperties(shopOperateRequest, shopOperate);
        //一元购店铺的配送方式可以为空，因为下订单时时根据每个商品的配送方式配送
        if(ShopTypeEnum.ONE_DOLLAR_BUY.getValue().equals(shopResponse.getShopType())){
            shopOperate.setDeliveryType(null);
        }
        if(shopOperateRequest.getPromotionPlans() !=null && shopOperateRequest.getPromotionPlans().size() >0 ) {
            shopOperate.setPromotionPlans(String.join(",",shopOperateRequest.getPromotionPlans()));
        }else {
            //当满减信息传值为空时置空数据库满减字段
            this.baseMapper.removePromotionPlans(shopOperateId);
        }
        //置空店铺公告
        if(StringUtils.isEmpty(shopOperateRequest.getShopNotice())){
            this.baseMapper.removeShopNotice(shopOperateId);
        }
        //置空满多少免运费
        if(null==shopOperateRequest.getPostFree()){
            this.baseMapper.removePostFree(shopOperateId);
        }
        //置空*小时内发货字段
        if(StringUtils.isEmpty(shopOperateRequest.getDeliveryTime())){
            this.baseMapper.removeDeliveryTime(shopOperateId);
        }
        Date serviceStartTime=null;
        Date serviceEndTime=null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            if(shopOperateRequest.getServiceStartTime()!=null) {
                serviceStartTime=sdf.parse(shopOperateRequest.getServiceStartTime());
            }
            if(shopOperateRequest.getServiceEndTime()!=null) {
                serviceEndTime=sdf.parse(shopOperateRequest.getServiceEndTime());
            }
        }catch (Exception e){
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"日期格式错误");
        }
        shopOperate.setServiceStartTime(serviceStartTime);
        shopOperate.setServiceEndTime(serviceEndTime);
        shopOperate.setId(shopOperateId);
        //当修改店铺不为一元购时，则更新Redis其底下所有商品的配送方式
        if(!GoodsTypeEnum.ONE_DOLLAR_BUY.getValue().equals(shopOperateId) && null!=shopOperate.getDeliveryType()){
            this.updateRedisGoods(shopOperate.getShopId(),shopOperate.getDeliveryType());
        }
     return   super.updateById(shopOperate);
    }

    @Override
    public boolean updateLabelByShopId(Long shopId,String label){
        ShopOperate shopOperate=baseMapper.selectShopOperateByShopIdOne(shopId);
        shopOperate.setLabel(label);
        return updateById(shopOperate);
    }

    @Override
    public int removeLabel( Long shopId){
        return baseMapper.removeLabel(shopId);
    }

   public void updateRedisGoods(Long shopId,Integer deliveryType){
       List<Goods> goodsList=goodsService.selectGoodsInfoByShopId(shopId);
       if(null!=goodsList && goodsList.size()>0){
           for (Goods goods : goodsList){
               String redisKeyName = RedisConstants.GOODS_STOCK_PREFIX + goods.getId();
               if(redisService.exists(redisKeyName)){
                   redisService.hmSet(redisKeyName,Constants.DELIVERY_TYPE,deliveryType);
               }
           }
       }
   }
}
