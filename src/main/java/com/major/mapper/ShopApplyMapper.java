package com.major.mapper;

import com.major.entity.ShopApply;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 店铺申请表 Mapper 接口
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-24
 */
public interface ShopApplyMapper extends BaseMapper<ShopApply> {

    /**
     * 获取所有的店铺申请分页列表
     * @param page
     * @param createTimeStart
     * @param createTimeStop
     * @param shopType
     * @param shopAddress
     * @param shopName
     * @param telephone
     * @param status
     * @return
     */
    @Select({
            "<script> "+
                   "SELECT " +
                    "sa.id as apply_id,sa.create_time,sa.shop_type,sa.shop_address, " +
                    "sa.shop_name,sa.contacts,sa.telephone,sa.`status`  " +
                    "FROM shop_apply sa "+
                    "WHERE  1=1" +
                    " <if test='createTimeStart !=null  '>" +
                    "            AND sa.create_time  <![CDATA[ >= ]]>#{createTimeStart}  " +
                    "  </if> "+
                    " <if test='createTimeStop !=null  '>" +
                    "            AND sa.create_time <![CDATA[ <= ]]>#{createTimeStop}  " +
                    "  </if> "+
                    " <if test='shopType !=0   and shopType!=null '>" +
                    "            AND sa.shop_type=#{shopType}" +
                    "  </if> "+
                    " <if test='shopAddress !=null  '>" +
                    "            AND sa.shop_address like concat('%',#{shopAddress},'%')" +
                    "  </if> "+
                    " <if test='shopName !=null  '>" +
                    "            AND sa.shop_name like concat('%',#{shopName},'%')" +
                    "  </if> "+
                    " <if test='telephone !=null  '>" +
                    "            AND sa.telephone  like concat('%',#{telephone},'%') " +
                    "  </if> "+
                    " <if test='status !=0 and status!=null '>" +
                    "            AND sa.`status`=#{status}" +
                    "  </if> "+
                    " ORDER BY sa.create_time DESC  " +
                    "</script>"}  )
    List<Map<String,Object>> selectShopApplyPage(Pagination page,    @Param("createTimeStart") String createTimeStart, @Param("createTimeStop") String createTimeStop,
                                                          @Param("shopType") Integer shopType, @Param("shopAddress") String shopAddress,
                                                          @Param("shopName") String shopName, @Param("telephone") String telephone, @Param("status") Integer status);

    /**
     * 查看店铺申请详情
     * @param applyId
     * @return
     */
    @Select(" SELECT " +
            "u.username,u.phone,sa.create_time,sa.shop_type,sa.shop_name,sa.shop_address,sa.contacts,sa.telephone, " +
            "sa.face_img_url,sa.environment_img_url,sa.business_license,sa.food_business_license,sa.status,sa.remark " +
            "FROM shop_apply sa " +
            "LEFT JOIN `user` u ON sa.user_id=u.id " +
            "WHERE sa.id=#{applyId}")
    Map<String,Object> selectShopApplyById(@Param("applyId") Long applyId);


}
