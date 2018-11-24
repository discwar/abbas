package com.major.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.major.common.constant.Constants;
import com.major.common.constant.DynamicConfigConstants;
import com.major.common.constant.ImgLogConstants;
import com.major.common.constant.UserConstants;
import com.major.common.enums.*;
import com.major.common.exception.AgException;
import com.major.common.util.*;
import com.major.config.DadaConfig;
import com.major.config.OSSConfig;
import com.major.entity.*;
import com.major.mapper.ShopMapper;
import com.major.model.merchant.ShopAddModel;
import com.major.model.request.ShopRequest;
import com.major.model.request.ShopSearchRequest;
import com.major.model.request.UpdateShopRequest;
import com.major.model.response.DadaApiResponse;
import com.major.model.response.ShopResponse;
import com.major.service.*;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;

/**
 * <p>
 * 店铺信息表 服务实现类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-07
 */
@Slf4j
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {

    @Autowired
    private SysUserServiceImpl sysUserService;

    @Autowired
    private IShopOperateService iShopOperateService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @Autowired
    private OSSConfig ossConfig;

    @Autowired
    private DadaConfig dadaConfig;

    @Autowired
    private IImgLogService imgLogService;

    @Autowired
    private IShopOperateService shopOperateService;

    @Autowired
    private IDynamicConfigService dynamicConfigService;

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean addShop(ShopRequest shopRequest,Long operId) {
        //查询是否有用户名同名
        if(shopRequest.getLoginName()==null || shopRequest.getPassWord()==null) {
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"用户名/密码不能为空");
        }
        SysUser sysUserOld= sysUserService.selectUserByUserName(shopRequest.getLoginName());
        if(sysUserOld!= null) {
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"存在登入账号同名");
        }
        if(shopRequest.getPassWord().length() < UserConstants.PASSWORD_MIN_LENGTH ||
                shopRequest.getPassWord().length() > UserConstants.PASSWORD_MAX_LENGTH) {
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"密码长度不符合规范");
        }
        if(shopRequest.getShopDesc()!=null && shopRequest.getShopDesc().length()>Constants.DB_FIELD_512 ) {
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"店铺简介过长");
        }
        if(shopRequest.getLabel()!=null ) {
            StringUtils.checkLabelLenth(shopRequest.getLabel());
        }
        //针对农场类型店铺判断
        if(ShopTypeEnum.FARM.getValue().equals(shopRequest.getShopType()) && shopRequest.getIsNewFarm()==null) {
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"请选择是否为新农场");
        }
        if(ShopTypeEnum.FARM.getValue().equals(shopRequest.getShopType()) && shopRequest.getShopSort()==null) {
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"请填写排序");
        }
        //针对农场类型时，店铺图片地址必须大于等于三张
        if(ShopTypeEnum.FARM.getValue().equals(shopRequest.getShopType())){
           if(null==shopRequest.getImgUrls()|| shopRequest.getImgUrls().size()<0 || shopRequest.getImgUrls().size()<3){
               throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"店铺图片必须要有三张");
           }
        }

        //达达门店编号，针对附近商家和爱果小店
        String originShopId=null;
        //添加系统用户
        SysUser sysUser = new SysUser();
        sysUser.randomSalt();
        if(ShopTypeEnum.NEAR_FRUIT.getValue().equals(shopRequest.getShopType())  || ShopTypeEnum.AIGUO_SHOP.getValue().equals(shopRequest.getShopType())) {
            originShopId=  addDadaShop(shopRequest,dadaConfig);
            if(originShopId==null){
                throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"添加达达门店失败");
            }
        }
        //更改手机号为登录名
        sysUser.setUsername(shopRequest.getLoginName());
        String password = Md5Utils.encryptPassword(shopRequest.getPassWord(), sysUser.getCredentialsSalt(),2);
        sysUser.setPassword(password);
        sysUser.setStatus(Constants.STATUS_ENABLE);
        sysUserService.insert(sysUser);

        //添加商家表
        Shop shop=new Shop();
        BeanUtils.copyProperties(shopRequest, shop);
        shop.setSysUserId(sysUser.getId());
        shop.setStatus(Constants.STATUS_ENABLE);

        if(shopRequest.getImgUrls() !=null && shopRequest.getImgUrls().size() >0 ) {
            shop.setImgUrls(String.join(",",shopRequest.getImgUrls()));
        }
        if(shopRequest.getLicenseImgUrls() !=null && shopRequest.getLicenseImgUrls().size() >0 ) {
            shop.setLicenseImgUrls(String.join(",",shopRequest.getLicenseImgUrls()));
        }
        shop.setShopNo(originShopId);
        super.insert(shop);

        imgLogService.addImgLog(shopRequest.getShopLogo(), ImgLogConstants.FROM_ABLE_SHOP,shop.getId(),ImgLogConstants.FROM_FIELD_SHOP_LOG,operId);
        imgLogService.addImgLog(shop.getImgUrls(),ImgLogConstants.FROM_ABLE_SHOP,shop.getId(),ImgLogConstants.FROM_FIELD_SHOP_IMGUTLS,operId);
        imgLogService.addImgLog(shop.getLicenseImgUrls(),ImgLogConstants.FROM_ABLE_SHOP,shop.getId(),ImgLogConstants.FROM_FIELD_LICENSE_IMG_URLS,operId);
        imgLogService.addImgLog(shop.getCoverUrl(),ImgLogConstants.FROM_ABLE_SHOP,shop.getId(),ImgLogConstants.FROM_FIELD_COVER_URL,operId);
        // 分配角色
        this.bindRole(sysUser.getId(),shopRequest.getShopType());
        //添加店铺运营表
        ShopOperate shopOperate=new ShopOperate();
        shopOperate.setShopId(shop.getId());
        shopOperate.setLegalPerson(shopRequest.getLegalPerson());
        shopOperate.setPhone(shopRequest.getPhone());
        shopOperate.setShopStatus(Constants.STATUS_ENABLE);
        shopOperate.setStatus(Constants.STATUS_ENABLE);
        shopOperate.setLabel(shopRequest.getLabel());
        //当店铺类型为农场、进出口、采摘园时默认配送方式为物流配送
        if(ShopTypeEnum.FARM.getValue().equals(shopRequest.getShopType()) || ShopTypeEnum.PLUCKING_GARDEN.getValue().equals(shopRequest.getShopType()) ||
                ShopTypeEnum.IMPORT_FRUIT.getValue().equals(shopRequest.getShopType())){
            shopOperate.setDeliveryType(DeliveryTypeEnum.LOGISTICS_DISTRIBUTION.getValue());
        }
        if(ShopTypeEnum.NEAR_FRUIT.getValue().equals(shopRequest.getShopType()) || ShopTypeEnum.AIGUO_SHOP.getValue().equals(shopRequest.getShopType())){
            //先默认为爱果配送
            shopOperate.setDeliveryType(DeliveryTypeEnum.AG_DISTRIBUTION.getValue());
            //默认附近水果和爱果店铺订单预计送达时间（分钟）
            shopOperate.setEstimatedExpendTime(Constants.estimatedExpendTime);
        }
        return shopOperateService.addShopOperate(shopOperate);
    }

    /**
     * 系统用户绑定权限
     * @param id
     * @param shopType
     */
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public void bindRole(Long id, Integer shopType) {
        String roleKey = ShopRoleEnum.getShopRoleEnum(shopType).getRoleKey();
        Role role = roleService.selectRoleByKey(roleKey);
        if(role==null){
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,roleKey+":角色未创建");
        }
        sysUserRoleService.sysUserBindRole(id,role.getId());
    }

    @Override
    public boolean updateShop(UpdateShopRequest updateShopRequest, Long shopId, Long operId) {
        //针对农场类型时，店铺图片地址必须大于等于三张
        if(ShopTypeEnum.FARM.getValue().equals(updateShopRequest.getShopType())){
            if(null==updateShopRequest.getImgUrls()|| updateShopRequest.getImgUrls().size()<0 || updateShopRequest.getImgUrls().size()<3){
                throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"店铺图片必须要有三张");
            }
        }
        if(updateShopRequest.getLabel()!=null ) {
            StringUtils.checkLabelLenth(updateShopRequest.getLabel());
           shopOperateService.updateLabelByShopId(shopId,updateShopRequest.getLabel());
        }
        //当shopId为1时，修改所有总部自营的店铺信息
        if(shopId.equals(Constants.SHOP_ZY_ID)){
            updateShopRequest.setShopName(null);
            updateShopRequest.setShopType(null);
            this.update(updateShopRequest, GoodsTypeEnum.GIFT_BOX.getValue().longValue(),operId);
            this.update(updateShopRequest,GoodsTypeEnum.SEASON_HOT.getValue().longValue(),operId);
            this.update(updateShopRequest,GoodsTypeEnum.PRE_SALE.getValue().longValue(),operId);
            this.update(updateShopRequest,GoodsTypeEnum.ONE_DOLLAR_BUY.getValue().longValue(),operId);
        }
        //当为水果店并且有关联爱果小店时，也一同修改
        Shop shop=baseMapper.selectShopByRelationIdAndShopType(shopId);
        if(null!=shop){
            updateShopRequest.setShopName(null);
            updateShopRequest.setShopType(null);
            this.update(updateShopRequest,shop.getId(),operId);
        }
        return   this.update(updateShopRequest,shopId,operId);
    }

    public boolean update(UpdateShopRequest updateShopRequest, Long shopId, Long operId){
        Shop shop=super.selectById(shopId);
        //修改时，先查询该条记录是否有图片地址，并且判断数据库中保存的图片地址是否和传入的图片地址一致，
        // 有的话先批量删除原来oss上的图片，在删除数据库表img_log中对应的数据
        if(updateShopRequest.getShopLogo()!=null && shop.getShopLogo()!=null && !shop.getShopLogo().equalsIgnoreCase(updateShopRequest.getShopLogo()) ) {
            imgLogService.updateImgLogsByTable(shop.getShopLogo(),ImgLogConstants.FROM_ABLE_SHOP,shopId,updateShopRequest.getShopLogo(),ImgLogConstants.FROM_FIELD_SHOP_LOG);
        }
        if(updateShopRequest.getImgUrls() !=null && updateShopRequest.getImgUrls().size() >0 ) {
            imgLogService.updateImgLogsByTable(shop.getImgUrls(),ImgLogConstants.FROM_ABLE_SHOP,shopId,String.join(",",updateShopRequest.getImgUrls()),ImgLogConstants.FROM_FIELD_SHOP_IMGUTLS);
            shop.setImgUrls(String.join(",",updateShopRequest.getImgUrls()));
        }
        imgLogService.updateImgLogsByTable(shop.getLicenseImgUrls(),ImgLogConstants.FROM_ABLE_SHOP,shopId,String.join(",",updateShopRequest.getLicenseImgUrls()),ImgLogConstants.FROM_FIELD_LICENSE_IMG_URLS);
        shop.setLicenseImgUrls(String.join(",",updateShopRequest.getLicenseImgUrls()));

        imgLogService.updateImgLogsByTable(shop.getCoverUrl(),ImgLogConstants.FROM_ABLE_SHOP,shopId,String.join(",",updateShopRequest.getCoverUrl()),ImgLogConstants.FROM_FIELD_COVER_URL);

        if(StringUtils.isEmpty(updateShopRequest.getLabel())){
            shopOperateService.removeLabel(shopId);
        }
        BeanUtils.copyProperties(updateShopRequest, shop);
      return   super.updateById(shop);
    }

    @Override
    public boolean deleteShop(Long shopId,Long operId) {
        Shop shop=super.selectById(shopId);
        shop.deleteById();
        if(!StringUtils.isEmpty(shop.getShopLogo())){
            //删除oss上的图片和删除数据库中数据
            imgLogService.deleteImgLogsByTable(shop.getShopLogo(),ImgLogConstants.FROM_ABLE_SHOP,shopId,operId,ImgLogConstants.FROM_FIELD_SHOP_LOG);
        }
        if(!StringUtils.isEmpty(shop.getImgUrls())){
            imgLogService.deleteImgLogsByTable(shop.getImgUrls(),ImgLogConstants.FROM_ABLE_SHOP,shopId,operId,ImgLogConstants.FROM_FIELD_SHOP_IMGUTLS);
        }
        if(!StringUtils.isEmpty(shop.getLicenseImgUrls())){
            imgLogService.deleteImgLogsByTable(shop.getLicenseImgUrls(),ImgLogConstants.FROM_ABLE_SHOP,shopId,operId,ImgLogConstants.FROM_FIELD_LICENSE_IMG_URLS);
        }
        if(!StringUtils.isEmpty(shop.getCoverUrl())){
            imgLogService.deleteImgLogsByTable(shop.getCoverUrl(),ImgLogConstants.FROM_ABLE_SHOP,shopId,operId,ImgLogConstants.FROM_FIELD_COVER_URL);
        }
        return iShopOperateService.deleteShopOperate(shopId);
    }

    @Override
    public Map<String,Object> selectShopInfoByShopId(Long shopId) {
        Map<String,Object> map=new HashMap<>();
        ShopOperate shopOperate=shopOperateService.selectShopOperateByShopIdOne(shopId);
        String promotionPlans=null;
        if(!StringUtils.isEmpty(shopOperate.getPromotionPlans())){
            promotionPlans=("满"+shopOperate.getPromotionPlans()).replaceAll("-","减").replaceAll(",",",满");
        }
        map.put("shopInfo",baseMapper.selectShopInfoByShopId(shopId));
        map.put("promotion_plans",promotionPlans);
        return map;
    }

    @Override
    public Page<Map<String, Object>> selectShopPage(Page<Map<String, Object>> page, ShopSearchRequest shopSearchRequest,Integer shopType) {
        Wrapper ew = new EntityWrapper();
        ew.where("s.shop_type={0}",shopType);
        ew.and("s.status={0}",Constants.STATUS_ENABLE);
        if(null!=shopSearchRequest.getShopName()){
          ew.like("s.shop_name",shopSearchRequest.getShopName());
        }
        if(StringUtil.isNotEmpty(shopSearchRequest.getShopAddress())){
            String [] address=shopSearchRequest.getShopAddress().split(",");
            ew.and("s.province={0}",address[0]);
            ew.and("s.city={0}",address[1]);
            ew.and("s.district={0}",address[2]);
        }
        if(StringUtil.isNotEmpty(shopSearchRequest.getSalesCountStart())) {
            //大于
            ew.ge("ord.sales_count",shopSearchRequest.getSalesCountStart());
        }
        if(StringUtil.isNotEmpty(shopSearchRequest.getSalesCountStop())) {
            //小于
            ew.le("ord.sales_count",shopSearchRequest.getSalesCountStop());
        }
        if(StringUtil.isNotEmpty(shopSearchRequest.getTotalOrderStart())) {
            ew.ge("o.real_total_amount",shopSearchRequest.getTotalOrderStart());
        }
        if(StringUtil.isNotEmpty(shopSearchRequest.getTotalOrderStop())) {
            ew.le("o.real_total_amount",shopSearchRequest.getTotalOrderStop());
        }
        if(StringUtil.isNotEmpty(shopSearchRequest.getGroundNumberStart())) {
            ew.ge("gs.ground_number",shopSearchRequest.getGroundNumberStart());
        }
        if(StringUtil.isNotEmpty(shopSearchRequest.getGroundNumberStop())) {
            ew.le("gs.ground_number",shopSearchRequest.getGroundNumberStop());
        }
        if(StringUtil.isNotEmpty(shopSearchRequest.getScoreStart())) {
            ew.ge("so.score",shopSearchRequest.getScoreStart());
        }
        if(StringUtil.isNotEmpty(shopSearchRequest.getScoreStop())) {
            ew.le("so.score",shopSearchRequest.getScoreStop());
        }
        if(StringUtil.isNotEmpty(shopSearchRequest.getRefundStart())) {
            ew.ge("o.is_refund",shopSearchRequest.getRefundStart());
        }
        if(StringUtil.isNotEmpty(shopSearchRequest.getRefundStop())) {
            ew.le("o.is_refund",shopSearchRequest.getRefundStop());
        }
        if(StringUtil.isNotEmpty(shopSearchRequest.getCommentStart())) {
            ew.ge("o.is_comment",shopSearchRequest.getCommentStart());
        }
        if(StringUtil.isNotEmpty(shopSearchRequest.getCommentStop())) {
            ew.le("o.is_comment",shopSearchRequest.getCommentStop());
        }
        if(StringUtil.isNotEmpty(shopSearchRequest.getTicketStart())) {
            ew.ge("c.ticket_number",shopSearchRequest.getTicketStart());
        }
        if(StringUtil.isNotEmpty(shopSearchRequest.getTicketStop())) {
            ew.le("c.ticket_number",shopSearchRequest.getTicketStop());
        }
        if(null!=shopSearchRequest.getCreateTimeStart()) {
            ew.ge("s.create_time",shopSearchRequest.getCreateTimeStart());
        }
        if(null!=shopSearchRequest.getCreateTimeStop()) {
            ew.le("s.create_time",shopSearchRequest.getCreateTimeStop());
        }
        if(StringUtil.isNotEmpty(shopSearchRequest.getSpecialGramStart())) {
            ew.ge("sg.special_gram_number",shopSearchRequest.getSpecialGramStart());
        }
        if(StringUtil.isNotEmpty(shopSearchRequest.getSpecialGramStop())) {
            ew.le("sg.special_gram_number",shopSearchRequest.getSpecialGramStop());
        }
        ew.groupBy("s.id");
        ew.orderBy("s.id DESC,s.create_time DESC");
        return page.setRecords(baseMapper.selectShopPage(page,ew));
   }

    @Override
    public Shop selectShopBySysUserId( Long sysUserId,Integer shopType) {
     return baseMapper.selectShopByShopId(sysUserId,shopType);
    }

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public  boolean addAGShop(ShopRequest shopRequest,Long operId) throws Exception{
        if(shopRequest.getIsRelation()==null) {
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"请选择是否需要关联店铺");
        }
        //爱果小店编号格式：爱果小店NO1,思路：查找数据库中有几个店铺类型为爱果小店+1
        Map<String ,Long> map=baseMapper.countAgShopNum();
        Long num=map.get("num");
        Integer shopNumber=num.intValue()+1;
        String shopName=Constants.SHOP_NAME_AG+shopNumber;
        //表示关联店铺
        if(Constants.SHOP_ISRELATION_FALSE.equals(shopRequest.getIsRelation())) {
            if(null==shopRequest.getShopId()) {
                throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"请选择关联店铺");
            }
            Shop shopOld=super.selectById(shopRequest.getShopId());
            ShopOperate shopOperateOld=shopOperateService.selectShopOperateByShopIdOne(shopRequest.getShopId());

            if(Constants.SHOP_IS_RELATION_TYPE_TRUE.equals(shopOld.getIsRelationType())){
                throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"该店铺已经关联过爱果小店了");
            }
            Shop shopNew=new Shop();
            BeanUtils.copyProperties(shopOld, shopNew);
            shopNew.setShopName(shopRequest.getShopName()+"NO"+shopNumber);
            //当为爱果小店添加时，此状态表示已经关联过了
            shopNew.setIsRelationType(Constants.SHOP_IS_RELATION_TYPE_TRUE);
            shopNew.setRelationId(shopRequest.getShopId());
            shopNew.setAgNumber(shopName);
            shopNew.setShopType(Constants.SHOP_TYPE_AG);
            shopNew.setCreateTime(new Date());
            super.insert(shopNew);
            //修改已经选择的水果店铺：该关联状态为是
            shopOld.setIsRelationType(Constants.SHOP_IS_RELATION_TYPE_TRUE);
            super.updateById(shopOld);
            //把当前店铺的用户角色变更到爱果小店角色
            Role role = roleService.selectRoleByKey(ShopRoleEnum.ROLE_FRUIT_AGL.getRoleKey());
            roleService.changeRole(shopOld.getSysUserId().longValue(),role.getId());

            //添加店铺运营表
            ShopOperate shopOperate=new ShopOperate();

            //把关联店铺的运营表信息复制给新的店铺
            BeanUtils.copyProperties(shopOperateOld, shopOperate);
            shopOperate.setShopId(shopNew.getId());
            shopOperate.setStatus(Constants.STATUS_NORMAL);
            //先默认为爱果配送
            shopOperate.setDeliveryType(DeliveryTypeEnum.AG_DISTRIBUTION.getValue());
            return iShopOperateService.addShopOperate(shopOperate);
        }
        //当为不选择关联店铺时，表示重新添加店铺
        shopRequest.setShopName(shopRequest.getShopName()+"NO"+shopNumber);
        shopRequest.setShopType(Constants.SHOP_TYPE_AG);
        shopRequest.setAgNumber(shopName);
        return this.addShop(shopRequest,operId);
    }

    @Override
    public ShopResponse selectShopById(Long shopId) {
        ShopResponse shopResponse =baseMapper.selectShopById(shopId);
        if(shopResponse==null) {
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"找不到该店铺数据");
        }
        return shopResponse;
    }


    @Override
    public  List<Map<String,Object>> selectShopInfoBySysUserId(  Long sysUserId) {
        return baseMapper.selectShopBySysUserId(sysUserId);
    }

    @Override
   public Map<String,Object> selectShopByShopType (Integer shopType){
       Map<String,Object> map=new HashMap<>(1);
       Integer isRelationType=null;
       if(shopType.equals(Constants.SHOP_TYPE_NEAR_FRUIT)){
           //当为水果店类型时，表示筛选的是没有关联过爱果小店的所有店铺
           isRelationType=Constants.SHOP_IS_RELATION_TYPE_FALSE;
       }
       map.put("shop_list",baseMapper.selectShopByshopType(shopType,isRelationType));
        return map;
   }

    @Override
    public Page<Map<String, Object>>  selectShopByShopName (Page<Map<String, Object>> page,String shopName,Integer shopType) {
       return page.setRecords(baseMapper.selectShopByShopName(page,shopName,shopType));
    }

    @Override
    public Map<String, Object> getShopQRCode(Long shopId) {
        Map<String, Object> shopInfo = this.checkShopQRCode(shopId);
        String qrCode = (String) shopInfo.get("qr_code_url");
        if (qrCode != null && qrCode.length() > 0) {
            return shopInfo;
        }

        String agAppSecret = dynamicConfigService.getDynamicDesc(DynamicConfigConstants.DynamicTypeEnum.SYS.getValue(), DynamicConfigConstants.APP_SECRET);
        String agLogoUrl = dynamicConfigService.getDynamicDesc(DynamicConfigConstants.DynamicTypeEnum.SYS.getValue(), DynamicConfigConstants.AG_LOGO_URL);

        try{
            String appSecret = Md5Utils.stringToMD5(agAppSecret);
            String data = String.format("%10d", shopId);
            //加密shopId
            String value = Rc4Utils.encryptRC4String(data, appSecret).toUpperCase();
            //下载log
            File file = OSSUtils.download(agLogoUrl);
            //生成二维码
            String pathName = QrCodeUtils.encodeRandom(value, file.getPath(),true);
            File path = new File(pathName);
            //上传
            String url = OSSUtils.upload(path, ossConfig, shopId);
            shopInfo.put("qr_code_url", url);
            //添加到店铺运营表
            Integer operateId = (Integer) shopInfo.get("operate_id");
            iShopOperateService.updateShopOperateQR(operateId.longValue(),url);
            //使用之后删除
            file.delete();
            path.delete();
        } catch (Exception e) {
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR, "生成二维码失败");
        }
        return shopInfo;
    }

    /**
     * 检查店家是否已经生成二维码
     */
    private Map<String, Object> checkShopQRCode(Long shopId){
        Map<String, Object> map = baseMapper.checkShopQRCode(shopId);
        return map;
    }

    /**
     * 封装达达添加门店请求体
     * @param shopRequest
     * @param dadaConfig
     * @return
     */
    private static String addDadaShop(ShopRequest shopRequest,DadaConfig dadaConfig){
        String originShopId=null;
        ShopAddModel shopAddModel = new ShopAddModel();
        shopAddModel.setStationName(shopRequest.getShopName());
        shopAddModel.setAreaName(shopRequest.getDistrict());
        //业务类型 9,水果生鲜
        shopAddModel.setBusiness(9);
        String cityName = shopRequest.getCity().replace("市","");
        shopAddModel.setCityName(cityName);
        shopAddModel.setStationAddress(shopRequest.getShopAddress());
        shopAddModel.setLng(shopRequest.getLongitude());
        shopAddModel.setLat(shopRequest.getLatitude());

        shopAddModel.setContactName(shopRequest.getLegalPerson());
        shopAddModel.setPhone(shopRequest.getPhone());
        //格式必须为list 不然报body出错
        List<ShopAddModel> shopAddList = new ArrayList<ShopAddModel>();
        shopAddList.add(shopAddModel);
        String params=JSON.toJSONString(shopAddList);
        DadaApiResponse dadaApiResponse=   DadaUtils.callRpc(params,dadaConfig,DadaUtils.SHOP_ADD_URL);
        if(!dadaApiResponse.getStatus().equals("success")){
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,dadaApiResponse.getMsg());
        }
        //解析json对象
        JSONObject jsStr = JSONObject.fromObject(dadaApiResponse.getResult());
        for (Object fan : jsStr.getJSONArray("successList")) {
            JSONObject object = (JSONObject)fan;
            originShopId=object.get("originShopId").toString();
        }
        return originShopId;
    }

    @Override
    public List<Map<String, Object>> getStatusEnableShopIdList() {
        return baseMapper.selectShopScoreById();
    }

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public StringBuffer updateShopScore(List<Map<String, Object>> idList)  {
        StringBuffer logs = new StringBuffer("店铺积分统计的shopId：");
        for (Map<String,Object> map:idList
             ) {
            updateShopScoreByMap(map);
            logs.append(map.get("shop_id")).append(",");
        }
        return logs;
    }


    private void updateShopScoreByMap(Map<String, Object> shopScoreMap) {

            Double lastMonthScore = Double.valueOf((String)shopScoreMap.get("score")) ;
            Long  lastMonthOrderCount = (Long) shopScoreMap.get("month_order_count");

            //更新并记录
            baseMapper.updateShopScore(lastMonthScore, (Long) shopScoreMap.get("shop_id"));
            LogShopScore logShopScore = new LogShopScore();
            logShopScore.setShopId((Long) shopScoreMap.get("shop_id"));
            logShopScore.setMonthOrderCount(lastMonthOrderCount);
            logShopScore.setScore(lastMonthScore);
            logShopScore.insert();
        }

    @Override
    public List<Map<String, Object>> getShopMonthOrderCount() {
        return baseMapper.getShopMonthOrderCount();
    }

    @Override
    public StringBuffer updateMonthOrderCount(List<Map<String, Object>> shopMonthOrderCount) {
        StringBuffer logs = new StringBuffer();
        for (Map<String,Object> map : shopMonthOrderCount
             ) {
            updateMonthOrderCountByMap(map);
            logs.append(map.get("shop_id")).append(",");
        }
        return logs;
    }

    private void updateMonthOrderCountByMap(Map<String, Object> map) {
        Long shopId = (Long) map.get("shop_id");
        Long monthOrderCount = (Long) map.get("month_order_count");
        baseMapper.updateMonthOrderCountByMap(shopId,monthOrderCount);
    }

    @Override
    public Map<String,Object> selectShopByBankInfo(Long sysUserId,Integer shopType){
        Map<String,Object> map=new HashMap<>(1);
        map.put("shop_banks_info",baseMapper.selectShopByBankInfo(sysUserId,shopType));
        return map;
    }

    @Override
    public Map<String,Object> selectShopByCarryMoneyInfo(Long shopId){
        return baseMapper.selectShopByCarryMoneyInfo(shopId);
    }

    @Override
    public Page<Map<String, Object>> selectWithdrawalPageById (Page<Map<String, Object>> page,Long shopId) {
        return page.setRecords(baseMapper.selectWithdrawalPageById(page,shopId));
    }
}
