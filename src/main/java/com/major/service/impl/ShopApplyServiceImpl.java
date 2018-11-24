package com.major.service.impl;

import com.major.common.constant.Constants;
import com.major.common.enums.StatusResultEnum;
import com.major.common.exception.AgException;
import com.major.entity.ShopApply;
import com.major.mapper.ShopApplyMapper;
import com.major.model.request.ShopApplyRequest;
import com.major.service.IShopApplyService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import jodd.util.StringUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 店铺申请表 服务实现类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-24
 */
@Service
public class ShopApplyServiceImpl extends ServiceImpl<ShopApplyMapper, ShopApply> implements IShopApplyService {

   @Override
   public  Page<Map<String, Object>> selectShopApplyPage(Page<Map<String, Object>> page, String createTimeStart, String createTimeStop, Integer shopType, String shopAddress,
                                                                String shopName, String telephone, Integer status) {
          String adre=null;
       if(StringUtil.isNotEmpty(shopAddress)){
           String address[]=shopAddress.split(",");
           StringBuffer adr=new StringBuffer();
           adr.append(address[0]);
           adr.append(address[1]);
           adr.append(address[2]);
           adre= adr.toString();
       }
       return page.setRecords(baseMapper.selectShopApplyPage(page,createTimeStart,createTimeStop,shopType,adre,shopName,telephone,status));
    }

    @Override
    public boolean toExamine(Long applyId, ShopApplyRequest shopApplyRequest ){
       if(shopApplyRequest.getRemark()!=null && shopApplyRequest.getRemark().length()> Constants.DB_FIELD_255) {
           throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"备注信息过长");
       }
        ShopApply shopApply=new ShopApply();
        shopApply.setId(applyId);
        shopApply.setStatus(shopApplyRequest.getStatus());
        shopApply.setRemark(shopApplyRequest.getRemark());
        return super.updateById(shopApply);
    }

    @Override
    public  Map<String,Object> selectShopApplyById( Long applyId) {
        Map<String,Object> map=new HashMap<>(1);
        map.put("shop_apply_info",baseMapper.selectShopApplyById(applyId));
        return map;
    }

}
