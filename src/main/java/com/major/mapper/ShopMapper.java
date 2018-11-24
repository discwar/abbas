package com.major.mapper;

import com.major.entity.Shop;
import com.major.model.response.ShopResponse;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 店铺信息表 Mapper 接口
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-07
 */
public interface ShopMapper extends BaseMapper<Shop> {

    /**
     * 返回店铺的基本信息--通用版-管理员使用
     * 统计出当月的评分和订单额、订单数、购买人数并且是已支付的订单
     * @param shopId
     * @return
     */
    Map<String,Object> selectShopInfoByShopId(@Param("shopId") Long shopId );

    /** 管理员使用
     * 根据店铺列型和搜索参数返回该类型下所有的店铺列表--通用版
     * sql注解:第一个嵌套:查询统计订单相关信息（必须是已支付的情况下），第二个统计上架商品数量，第三个统计套票数量（可分离查询）
     * @return
     */
    List<Map<String, Object>> selectShopPage(Pagination page,@Param("ew") Wrapper ew);

    /**
     * 根据系统用户返回店铺信息
     * @param sysUserId
     * @return
     */
    @Select("SELECT id as shop_id,shop_name,sys_user_id,shop_type  FROM shop WHERE  sys_user_id =#{sysUserId} and status=1 ")
    List<Map<String,Object>> selectShopBySysUserId( @Param("sysUserId") Long sysUserId);

    /**
     * 只返回当前的店铺表信息
     * @param sysUserId
     * @param shopType
     * @return
     */
    @Select("SELECT s.id,s.sys_user_id,s.shop_name,s.shop_type,s.province,s.city,s.district,s.shop_address,s.longitude,s.latitude,s.shop_desc,s.shop_video,s.shop_logo,s.img_urls,   " +
            " s.license_img_urls,s.cover_url,s.credit_code,  " +
            " s.license_code ,s.is_quality_shop,s.is_new_farm,s.shop_sort,s.shop_no,s.is_relation_type,s.relation_id,s.ag_number " +
            " FROM  shop s WHERE s.sys_user_id=#{sysUserId} AND s.status=1 AND s.shop_type=#{shopType} ")
    Shop selectShopByShopId(@Param("sysUserId") Long sysUserId,@Param("shopType") Integer shopType);

    /**
     * 根据店铺类型返回信息
     * @param shopType
     * @return
     */
    @Select({
            "<script> "+
                    "SELECT id as shop_id ,shop_name FROM shop "+
                    "WHERE  status=1 and shop_type=#{shopType}  " +
                    " <if test='isRelationType !=null  '>" +
                    "            AND is_relation_type=#{isRelationType}   " +
                    "  </if> "+
                    "ORDER  BY create_time DESC  " +
                    "</script>"}  )
    List<Map<String,Object>> selectShopByshopType( @Param("shopType") Integer shopType,@Param("isRelationType") Integer isRelationType);

    /**
     * 查询当前水果商家关联的爱果小店
     * @param shopId
     * @return
     */
    @Select("SELECT id, shop_name,shop_type  FROM  shop WHERE relation_id=#{relationId} AND status=1 AND shop_type=1  ")
    Shop selectShopByRelationIdAndShopType(@Param("relationId") Long shopId);


    /**
     * 根据ID返回店铺信息
     * @param shopId
     * @return
     */
    @Select("SELECT " +
            " s.id,s.sys_user_id,s.shop_name,s.shop_type,s.province,s.city,s.district,s.shop_address,s.longitude,s.latitude,s.shop_desc,s.shop_video,s.shop_logo,s.img_urls,  " +
            "s.license_img_urls,s.cover_url,s.credit_code,  " +
            "s.license_code ,s.is_quality_shop,s.is_new_farm,s.shop_sort,s.shop_no,s.is_relation_type,s.relation_id,s.ag_number ,s.cardholder,s.card_number,s.banks_id,s.can_carry_money,b.bank_name,  " +
            " so.label,so.phone,so.legal_person, " +
            " su.username,so.id as shop_operate_id  " +
            "FROM " +
            " shop s " +
            "LEFT JOIN shop_operate so ON s.id = so.shop_id " +
            "LEFT JOIN sys_user su ON s.sys_user_id = su.id   " +
            "LEFT JOIN banks b ON s.banks_id=b.id  " +
            "WHERE s.id=#{shopId} AND s.status=1 ")
    ShopResponse selectShopById(@Param("shopId") Long shopId);

    /**
     * 针对新增banner选择跳转店铺列表
     * @param shopName
     * @param shopType
     * @return
     */
    @Select({
            "<script> "+
                    "SELECT id as shop_id ,shop_name ,shop_type  FROM shop   "+
                    "WHERE  status=1  " +
                    " <if test='shopName !=null  '>" +
                    "            AND shop_name like concat('%',#{shopName},'%')   " +
                    "  </if> "+
                    "        <if test='shopType!=null '>  " +
                    "            AND shop_type =#{shopType}  " +
                    "        </if>  " +
                    "ORDER  BY create_time DESC  " +
                    "</script>"}  )
    List<Map<String,Object>> selectShopByShopName( Pagination page,@Param("shopName") String shopName,@Param("shopType") Integer shopType);


    /**
     * 检查店家是否已经生成二维码
     * @param shopId
     * @return
     */
    @Select("SELECT " +
            " s.id, so.id as operate_id ," +
            "s.shop_name," +
            " so.qr_code_url  " +
            "FROM " +
            " shop s " +
            " JOIN shop_operate so ON s.id = so.shop_id  " +
            "WHERE " +
            " s.`status` = 1 and s.id = #{id}")
    Map<String,Object> checkShopQRCode(@Param("id") Long shopId);

    /**
     * 查询爱果小店的数量
     * @return
     */
    @Select("SELECT COUNT(id) as num FROM shop WHERE shop_type=1 AND `status`=1 " )
    Map<String ,Long> countAgShopNum();

    /**
     * 置空店铺logo
     * @param shopId
     * @return
     */
    @Update("update shop set shop_logo = null,update_time=now() where id =#{id}")
    int removeShopLogo(@Param("id") Long shopId);

    /**
     * 置空店铺图片
     * @param shopId
     * @return
     */
    @Update("update shop set img_urls = null,update_time=now() where id =#{id}")
    int removeImgUrls(@Param("id") Long shopId);

    /**
     * 统计评分
     * @return
     */
    List<Map<String,Object>> selectShopScoreById();

    /**
     * 更新评分
     * @param lastMonthScore
     * @param shopId
     */
    @Update("update shop_operate set score = #{score}  where shop_id = #{id}")
    void updateShopScore(@Param("score") Double lastMonthScore, @Param("id") Long shopId);

    /**
     * 今天往前三十天的店铺订单销量
     * @return
     */
    List<Map<String, Object>> getShopMonthOrderCount();

    /**
     * 修改店铺月销量
     * @param shopId
     * @param monthOrderCount
     */
    @Update("update shop_operate set month_order_count = #{month_order_count}  where shop_id = #{id}")
    void updateMonthOrderCountByMap(@Param("id") Long shopId, @Param("month_order_count") Long monthOrderCount);

    /**
     * 根据当前用户id返回银行信息
     * @param sysUserId
     * @param shopType
     * @return
     */
    @Select("SELECT s.id,b.bank_name,s.cardholder,s.card_number from shop s  " +
            "LEFT JOIN banks b ON s.banks_id=b.id where s.sys_user_id=#{sysUserId} and s.shop_type=#{shopType} ")
    Map<String,Object> selectShopByBankInfo(@Param("sysUserId") Long sysUserId,@Param("shopType") Integer shopType);

    /**
     * 查看店铺的可提现金额和冻结金额
     * 如果有关联爱果小店，只取水果店的shopId
     * @param shopId
     * @return
     */
    @Select("SELECT " +
            "s.id,s.shop_name,s.sys_user_id, " +
            "s.shop_type, (CASE WHEN s.can_carry_money IS NULL THEN '0' ELSE s.can_carry_money END ) AS can_carry_money ,SUM(lw.money) AS freeze_money " +
            "FROM " +
            "shop s  " +
            "LEFT JOIN log_withdraw_cash lw ON s.id=lw.target_id AND lw.target_type=1 AND lw.cash_status=0 " +
            "WHERE  " +
            "s.`status` = 1  AND s.id=#{shopId}  ")
    Map<String,Object> selectShopByCarryMoneyInfo(@Param("shopId") Long shopId);


    /**
     * 商家提现管理列表
     *
     * @param page
     * @param shopId
     * @return
     */
    @Select("SELECT " +
            "lw.id AS withdraw_cash_id,b.bank_name,s.card_number,s.cardholder,lw.money,lw.create_time,lw.cash_status " +
            "FROM log_withdraw_cash  lw" +
            "LEFT JOIN shop s   ON s.id=lw.target_id  " +
            "LEFT JOIN banks b ON b.id=s.banks_id " +
            "WHERE " +
            " s.`status` = 1 AND lw.target_type=1   AND s.id=#{shopId}")
    List<Map<String,Object>> selectWithdrawalPageById( Pagination page,@Param("shopId") Long shopId);

}
