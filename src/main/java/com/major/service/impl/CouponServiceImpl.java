package com.major.service.impl;

import com.major.common.constant.Constants;
import com.major.common.constant.CouponConstants;
import com.major.common.enums.StatusResultEnum;
import com.major.common.exception.AgException;
import com.major.common.util.DateUtils;
import com.major.entity.Coupon;
import com.major.mapper.CouponMapper;
import com.major.model.request.CouponRequest;
import com.major.model.response.CouponByStored;
import com.major.service.ICouponService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/31 14:32      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements ICouponService {
    @Override
    public boolean addCoupon(CouponRequest couponRequest) {
        //当为店铺券的时候表示从商家优惠券管理的时候添加
       if(Constants.COUPON_KIND_BUSINESS.equals(couponRequest.getKind())) {
           if(couponRequest.getShopId()==null) {
               throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"请选择店铺");
           }
           if(couponRequest.getDisplayStartTime()==null || couponRequest.getDisplayStopTime()==null) {
               throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"请选择展示时间");
           }
           //只有商家添加优惠券的时候判断:同一时间段只能上架一张优惠券
           List<Coupon> couponList=baseMapper.selectCouponByStartAndStopTime(couponRequest.getDisplayStartTime(),couponRequest.getDisplayStopTime(),couponRequest.getShopId());
           if(couponList!=null && couponList.size()>3) {
               throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"同一时间段只能上架三张优惠券");
           }
       }
        Coupon coupon=new Coupon();
        BeanUtils.copyProperties(couponRequest, coupon);
        if(couponRequest.getLimitCondition()!=null) {
            coupon.setLimitCondition(String.join(",",couponRequest.getLimitCondition()));
        }
        coupon.setApplyRange(String.join(",",couponRequest.getApplyRange()));
        coupon.setStatus(Constants.STATUS_NORMAL);
        StringBuffer couponDesc=new StringBuffer();
        String discountDesc="";
        if(Constants.COUPON_TYPE_MJ.equals(couponRequest.getCouponType())){
            if(couponRequest.getMinPrice().compareTo(new BigDecimal("0"))==0){
                discountDesc=couponRequest.getDiscount()+"元无门槛";
            }else {
                discountDesc="满"+couponRequest.getMinPrice()+"减"+couponRequest.getDiscount()+"元";
            }
        }
        if(Constants.COUPON_TYPE_ZK.equals(couponRequest.getCouponType())){
            discountDesc="满"+couponRequest.getMinPrice()+"打"+couponRequest.getDiscount()+"折";
        }
        if(Constants.COUPON_TYPE_YF.equals(couponRequest.getCouponType())){
            discountDesc="满"+couponRequest.getMinPrice()+"减免"+couponRequest.getDiscount()+"运费";
        }
        //当适用条件为全部时，表示通用
        if(CouponConstants.CouponApplyRangeAll.equals(coupon.getApplyRange())) {
            couponDesc.append("通用于所有店铺");
        }else{
            couponDesc.append(Constants.COUPON_DESC_SY);
            couponDesc.append(String.join("、",couponRequest.getApplyRangeName()));
        }
        if(couponRequest.getLimitConditionName()!=null && couponRequest.getLimitConditionName().size()>0){
            couponDesc.append(",");
            couponDesc.append(String.join(",",couponRequest.getLimitConditionName()));
        }
        coupon.setCouponDesc(couponDesc.toString());
        coupon.setDiscountDesc(discountDesc);
        return super.insert(coupon);
    }
    @Override
    public boolean updateCoupon(CouponRequest couponRequest, Long id) {
        if(Constants.COUPON_KIND_BUSINESS.equals(couponRequest.getKind())) {
            if(couponRequest.getShopId()==null) {
                throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"请选择店铺");
            }
            if(couponRequest.getDisplayStartTime()==null || couponRequest.getDisplayStopTime()==null) {
                throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"请选择展示时间");
            }
            //只有商家添加优惠券的时候判断:同一时间段只能上架三张优惠券
            List<Coupon> couponList=baseMapper.selectCouponByStartAndStopTime(couponRequest.getDisplayStartTime(),couponRequest.getDisplayStopTime(),couponRequest.getShopId());
            if(couponList!=null && couponList.size()>3) {
                throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"同一时间段只能上架三张优惠券");
            }
        }
        Coupon coupon=new Coupon();
        BeanUtils.copyProperties(couponRequest, coupon);
        if(couponRequest.getLimitCondition()!=null) {
            coupon.setLimitCondition(String.join(",",couponRequest.getLimitCondition()));
        }
        //当限制条件为空时，先移除限制条件
        if(couponRequest.getLimitCondition()==null || couponRequest.getLimitCondition().size()<=0 ) {
           baseMapper.removeCouponLimitCondition(id);
        }
        coupon.setApplyRange(String.join(",",couponRequest.getApplyRange()));
        //优惠券使用详情
        StringBuffer couponDesc=new StringBuffer();
        String discountDesc="";
        if(Constants.COUPON_TYPE_MJ.equals(couponRequest.getCouponType())){
            if(couponRequest.getMinPrice().compareTo(new BigDecimal("0"))==0){
                discountDesc=couponRequest.getDiscount()+"元无门槛";
            }else {
                discountDesc="满"+couponRequest.getMinPrice()+"减"+couponRequest.getDiscount()+"元";
            }
        }
        if(Constants.COUPON_TYPE_ZK.equals(couponRequest.getCouponType())){
            discountDesc="满"+couponRequest.getMinPrice()+"打"+couponRequest.getDiscount()+"折";
        }
        if(Constants.COUPON_TYPE_YF.equals(couponRequest.getCouponType())){
            discountDesc="满"+couponRequest.getMinPrice()+"减免"+couponRequest.getDiscount()+"运费";
        }
        //当适用条件为全部时，表示通用
         if(CouponConstants.CouponApplyRangeAll.equals(coupon.getApplyRange())) {
             couponDesc.append("通用于所有店铺");
         }else{
             couponDesc.append(Constants.COUPON_DESC_SY);
             couponDesc.append(String.join("、",couponRequest.getApplyRangeName()));
         }
        if(couponRequest.getLimitConditionName()!=null && couponRequest.getLimitConditionName().size()>0){
            couponDesc.append(",");
            couponDesc.append(String.join(",",couponRequest.getLimitConditionName()));
        }
        coupon.setCouponDesc(couponDesc.toString());
        coupon.setDiscountDesc(discountDesc);
        coupon.setId(id);
        return super.updateById(coupon);
    }
    @Override
    public boolean deleteCoupon( Long id){
        Coupon coupon=new Coupon();
        coupon.setId(id);
        coupon.setStatus(Constants.STATUS_DELETE);
        return coupon.updateById();
    }
    @Override
    public Page<Map<String, Object>> selectCoupon(Page<Map<String, Object>> page,String couponName,String couponDesc,Integer couponType,String deadlineStartTime,String deadlineStopTime,
                                                  String createStartTime,String createStopTime,Integer deadlineState) {

        return page.setRecords(baseMapper.selectCouponList(page, couponName, couponDesc,couponType,
                deadlineStartTime,deadlineStopTime,createStartTime,createStopTime,deadlineState));
    }

    @Override
    public Coupon selectCouponById(Long Id){
        return baseMapper.selectCouponById(Id);
    }


    @Override
    public Page<Map<String, Object>> selectCouponBusioness(Page<Map<String, Object>> page,Long shopId,String couponName,String couponRule,String couponState,String deadlineStartTime,String deadlineStopTime,
                                                    String createStartTime,String createStopTime,Integer groundState) {
        return page.setRecords(baseMapper.selectCouponBusioness(page, shopId,couponName, couponRule,couponState,
                deadlineStartTime,deadlineStopTime,createStartTime,createStopTime,groundState));

    }

    @Override
    public Coupon findCouponByNameAndMoney(String couponName, BigDecimal money) {
        EntityWrapper<Coupon> ew = new EntityWrapper<>();
        ew.where("coupon_name = {0}",couponName)
                .and("discount = {0}",money)
                .and("status = {0}",Constants.STATUS_ENABLE)
                .and("deadline > {0}",DateUtils.getNowDate())
                .last("limit 1");
        return super.selectOne(ew);
    }
    @Override
    public  boolean shelvesCoupon(Long id,Integer status) {
        Coupon coupon=new Coupon();
        coupon.setId(id);
        coupon.setStatus(status);
        return super.updateById(coupon);
    }

    @Override
    public List<CouponByStored> selectCouponByStoredConfig(){
        Map<String,Object> map=new HashMap<>(1);
        Wrapper ew = new EntityWrapper();
        ew.where("coupon_name={0}",Constants.COUPON_STORED_NAME);
        return baseMapper.selectCouponByStoredConfig(ew);
    }
}
