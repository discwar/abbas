package com.major.service.impl;


import com.major.common.constant.ImgLogConstants;
import com.major.entity.StoredConfig;
import com.major.mapper.StoredConfigMapper;
import com.major.model.request.CouponNumberRequest;
import com.major.model.request.StoredConfigRequest;
import com.major.model.response.CouponByStored;
import com.major.service.ICouponService;
import com.major.service.IImgLogService;
import com.major.service.IStoredConfigService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 储值送好礼配置表 服务实现类
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-11-15
 */
@Service
public class StoredConfigServiceImpl extends ServiceImpl<StoredConfigMapper, StoredConfig> implements IStoredConfigService {

    @Autowired
    private IImgLogService imgLogService;

    @Autowired
    private ICouponService couponService;

    @Override
    public List<StoredConfig> selectStoredGift() {
        return baseMapper.selectList(null);
    }

    @Override
    public boolean addStoredConfig(StoredConfigRequest storedConfigRequest, Long operId){
        StoredConfig storedConfig=new StoredConfig();
        BeanUtils.copyProperties(storedConfigRequest, storedConfig);
        StringBuffer  storedGive=new StringBuffer();
        List<String> ids=new ArrayList<>();
        for(CouponNumberRequest couponNumberRequest :storedConfigRequest.getCouponNumberRequestList()){
            storedGive.append(couponNumberRequest.getNumber()+"张"+couponNumberRequest.getDiscountDesc()+"优惠券");
            for(int i=0;i<couponNumberRequest.getNumber();i++){
                ids.add(couponNumberRequest.getCouponId());
            }
        }
        storedConfig.setCouponIds(String.join(",",ids));
        storedConfig.setStoredGive(storedGive.toString());
        super.insert(storedConfig);
        return imgLogService.addImgLog(storedConfigRequest.getImgUrl(), ImgLogConstants.FROM_TABLE_STORED_CONFIG,storedConfig.getId(),ImgLogConstants.FROM_FIELD_IMGURL,operId);
    }

    @Override
    public boolean updateStoredConfig(StoredConfigRequest storedConfigRequest,Long id){
        StoredConfig storedConfig=selectById(id);
        BeanUtils.copyProperties(storedConfigRequest, storedConfig);
        StringBuffer  storedGive=new StringBuffer();
        List<String> ids=new ArrayList<>();
        for(CouponNumberRequest couponNumberRequest :storedConfigRequest.getCouponNumberRequestList()){
            storedGive.append(couponNumberRequest.getNumber()+"张"+couponNumberRequest.getDiscountDesc()+"优惠券");
            for(int i=0;i<couponNumberRequest.getNumber();i++){
                ids.add(couponNumberRequest.getCouponId());
            }
        }
        storedConfig.setCouponIds(String.join(",",ids));
        storedConfig.setStoredGive(storedGive.toString());
        super.updateById(storedConfig);
        return  imgLogService.updateImgLogsByTable(storedConfig.getImgUrl(),ImgLogConstants.FROM_TABLE_STORED_CONFIG,id,storedConfigRequest.getImgUrl(),ImgLogConstants.FROM_FIELD_IMGURL);
    }

    @Override
    public boolean deleteStoredConfig(Long id,Long operId){
        StoredConfig storedConfig=selectById(id);
        super.deleteById(storedConfig);
        return  imgLogService.deleteImgLogsByTable(storedConfig.getImgUrl(),ImgLogConstants.FROM_TABLE_STORED_CONFIG,id,operId,ImgLogConstants.FROM_FIELD_IMGURL);
    }

    @Override
    public Map<String,Object> selectStoredConfigById(Long id){
        StoredConfig storedConfig=selectById(id);
        Map<String,Object> map=new HashMap<>(2);
        List<CouponByStored> list=couponService.selectCouponByStoredConfig();
        List<Map<String,Object>> reCouponList=new ArrayList<>();
        for(CouponByStored coupon:list){
           int num=pinrtCount(storedConfig.getCouponIds(),coupon.getId().toString());
            Map<String,Object> m=new HashMap<>(5);
            m.put("couponId",coupon.getId());
            m.put("discountDesc",coupon.getDiscountDesc());
            m.put("couponName",coupon.getCouponName());
            m.put("couponType",coupon.getCouponType());
            m.put("num",num);
            reCouponList.add(m);
        }
        map.put("coupon_info",reCouponList);
        Set set = new HashSet();
        String [] ids=storedConfig.getCouponIds().split(",");
        for(String s :ids){
            set.add(s);
        }
        storedConfig.setCouponIds(set.toString());
        map.put("stored_config",storedConfig);
        return map;
    }

    @Override
    public Page<Map<String, Object>> selectStoredConfigPage(Page<Map<String, Object>> page){
        return page.setRecords(baseMapper.selectStoredConfigPage(page));
    }


    public static int pinrtCount(String string, String subString) {
        int index = 0;
        int count = 0;
        while ((index = string.indexOf(subString, index)) != -1) {
            //在循环控制的条件中将获得的索引值赋给index,不等于-1是因为.在JDK中规定了indexOf查找不到子字符串时就返回-1.在我们这里也就是跳出循环的意思
            //得到索引后,从本位置开始进行下一次循环,所以字符串的索引加一
            index++;
            //计数器统计出现的次数
            count++;
        }
        return count;
    }

    public String removeRepeatChar(String s) {
        if (s == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        int i = 0;
        int len = s.length();
        while (i < len) {
            char c = s.charAt(i);
            sb.append(c);
            i++;
            //这个是如果这两个值相等，就让i+1取下一个元素
            while (i < len && s.charAt(i) == c) {
                i++;
            }
        }
        return sb.toString();
    }

}
