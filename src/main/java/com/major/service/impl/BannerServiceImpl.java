package com.major.service.impl;


import com.major.common.constant.Constants;
import com.major.common.constant.ImgLogConstants;
import com.major.common.enums.StatusResultEnum;
import com.major.common.exception.AgException;
import com.major.common.util.GADUtils;
import com.major.entity.Banner;
import com.major.mapper.BannerMapper;
import com.major.model.request.BannerRequest;
import com.major.model.response.ShopResponse;
import com.major.service.IBannerService;
import com.major.service.IImgLogService;
import com.major.service.IShopService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: banner服务层接口实现类 </p>
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
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements IBannerService {

    @Autowired
    private IImgLogService imgLogService;
    @Autowired
    private IShopService shopService;

    private Map<String, Object> getBannerMap(Integer type, String imgUrl, String skipContent) {
        Map<String, Object> bannerMap = new HashMap<>(3);
        bannerMap.put("type", type);
        bannerMap.put("img_url", imgUrl);
        bannerMap.put("skip_content", skipContent);

        return bannerMap;
    }
    @Override
    public boolean addBanner(BannerRequest bannerRequest,Long operId) {
        Banner banner=new Banner();
        banner.setStatus(Constants.STATUS_NORMAL);
        // 上架做校验，如果超过五个，则状态改为下架状态
        if(Constants.STATUS_ENABLE.equals(bannerRequest.getBannerType())){
            List<Map<String, Object>> list=baseMapper.selectAllBannerByType(bannerRequest.getBannerType());
            if(list!=null && list.size()>=5){
                banner.setStatus(Constants.STATUS_DOWN);
            }
        }

        BeanUtils.copyProperties(bannerRequest, banner);
        if(bannerRequest.getCityIds() !=null && bannerRequest.getCityIds().size() >0 ) {
            banner.setCityIds(String.join(",",bannerRequest.getCityIds()));
        }
        String cityName=null;
        if(bannerRequest.getCityNames() !=null && bannerRequest.getCityNames().size() >0 ) {
            banner.setCityNames(String.join(",",bannerRequest.getCityNames()));
            cityName=String.join(",",bannerRequest.getCityNames());
        }
        //单地市
        if(Constants.SCOPE_MANY_CITIES.equals(bannerRequest.getScope())){
            if(StringUtils.isEmpty(cityName)){
                throw new AgException(StatusResultEnum.DB_SAVE_ERROR, "请选择城市");
            }
            //调用高德
            String [] locations= GADUtils.getLngLatFromOneAddr(cityName);
            if(locations!=null){
                //解析返回的经纬度
                banner.setLongitude(Double.valueOf(locations[0]));
                banner.setLatitude(Double.valueOf(locations[1]));
            }
        }
        //只针对首页Banner类型，并且只针对跳转类型为1
        if(Constants.BANNER_TYPE_SY.equals(bannerRequest.getBannerType())) {
            if(Constants.SKIP_TYPE_SP.equals(bannerRequest.getSkipType()) ){
                banner.setShopType(banner.getBannerType());
            }
        }
        //当跳转类型为店铺时，根据跳转内容里面的shopId，查询出该店铺的shopType
        if(Constants.SKIP_TYPE_DP.equals(bannerRequest.getSkipType())){
            if(StringUtils.isNotEmpty(bannerRequest.getSkipContent())){
                ShopResponse shopResponse=shopService.selectShopById(Long.valueOf(bannerRequest.getSkipContent()));
                banner.setShopType(shopResponse.getShopType());
            }
        }
        super.insert(banner);
        if(StringUtils.isNotEmpty(bannerRequest.getImgUrl())){
            imgLogService.addImgLog(bannerRequest.getImgUrl(), ImgLogConstants.FROM_ABLE_BANNER,banner.getId(),ImgLogConstants.FROM_FIELD_IMGURL,operId);
        }
        return  true;
    }

    @Override
    public boolean updateBanner(BannerRequest bannerRequest, Long id,Long operId) {
        Banner banner=selectBannerById(id);
        if(StringUtils.isNotEmpty(bannerRequest.getImgUrl())){
            imgLogService.updateImgLogsByTable(banner.getImgUrl(),ImgLogConstants.FROM_ABLE_BANNER,banner.getId(),bannerRequest.getImgUrl(),ImgLogConstants.FROM_FIELD_IMGURL);
        }
        BeanUtils.copyProperties(bannerRequest, banner);
        banner.setId(id);
        if( bannerRequest.getCityIds() !=null && bannerRequest.getCityIds().size() >0 ) {
            banner.setCityIds(String.join(",",bannerRequest.getCityIds()));
        }
        String cityName=null;
        if(bannerRequest.getCityNames() !=null && bannerRequest.getCityNames().size() >0 ) {
            banner.setCityNames(String.join(",",bannerRequest.getCityNames()));
            cityName=String.join(",",bannerRequest.getCityNames());
        }
        //表示单地市
        if(Constants.SCOPE_MANY_CITIES.equals(bannerRequest.getScope())){
            //调用高德
            String [] locations= GADUtils.getLngLatFromOneAddr(cityName);
            if(locations!=null){
                //解析返回的经纬度
                banner.setLongitude(Double.valueOf(locations[0]));
                banner.setLatitude(Double.valueOf(locations[1]));
            }
        }
        //当跳转类型为不跳转时置空skip_content
        if(Constants.SKIP_TYPE_NO.equals(banner.getSkipType())){
           baseMapper.removeSkipContentById(id);
        }
        //只针对首页Banner类型，并且只针对跳转类型为1
        if(Constants.BANNER_TYPE_SY.equals(bannerRequest.getBannerType())) {
            if(Constants.SKIP_TYPE_SP.equals(bannerRequest.getSkipType()) ){
                banner.setShopType(banner.getBannerType());
            }
        }
        //当跳转类型为店铺时，根据跳转内容里面的shopId，查询出该店铺的shopType
        if(Constants.SKIP_TYPE_DP.equals(bannerRequest.getSkipType())){
            if(StringUtils.isNotEmpty(bannerRequest.getSkipContent())){
                ShopResponse shopResponse=shopService.selectShopById(Long.valueOf(bannerRequest.getSkipContent()));
                banner.setShopType(shopResponse.getShopType());
            }
        }
        return super.updateById(banner);
    }
    @Override
    public boolean deleteBanner( Long id,Long operId) {
        Banner banner=selectBannerById(id);
        banner.setId(id);
        banner.setStatus(Constants.STATUS_DELETE);
        if(StringUtils.isNotEmpty(banner.getImgUrl())){
            imgLogService.deleteImgLogsByTable(banner.getImgUrl(),ImgLogConstants.FROM_ABLE_BANNER,id,operId,ImgLogConstants.FROM_FIELD_IMGURL);
        }
        return banner.updateById();
    }
    @Override
    public boolean updateBannerDown( Long id,Integer type) {
        Banner banner=super.selectById(id);
        // 上架做校验，只能上架5个；
        if(Constants.STATUS_ENABLE.equals(type)){
            List<Map<String, Object>> list=baseMapper.selectAllBannerByType(banner.getBannerType());
            if(list!=null && list.size()>=5){
                throw new AgException(StatusResultEnum.DB_SAVE_ERROR, "同种banner类型只能上架五个banner");
            }
        }
        banner.setStatus(type);
        return super.updateById(banner);
    }

    @Override
    public Page<Map<String, Object>> selectBannerPageByBannerType(Page<Map<String, Object>> page,Integer bannerType) {
        return page.setRecords(baseMapper.selectBannerPageByBannerType(page,bannerType));
    }

    @Override
    public Banner selectBannerById(Long Id) {
        return baseMapper.selectBannerById(Id);
    }

    @Override
    public  Map<String ,Object> selectBannerInfoById(Long bannerId) {
        Map<String ,Object> map=new HashMap<>(1);
        map.put("banner_info",baseMapper.selectBannerInfoById(bannerId));
        return map;
    }

    @Override
    public boolean checkBannerDown(Integer type){
        List<Map<String, Object>> list=baseMapper.selectAllBannerByType(type);
        if(list!=null && list.size()>=5){
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR, "同种banner类型只能上架五个banner");
        }
        return true ;
    }
}
