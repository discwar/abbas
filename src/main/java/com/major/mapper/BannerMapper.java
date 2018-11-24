package com.major.mapper;

import com.major.entity.Banner;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/13 10:24      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public interface BannerMapper extends BaseMapper<Banner> {

    /**
     * 获取详情
     * @param Id
     * @return
     */
    @Select("SELECT id,name,banner_sort,banner_type,img_url,skip_type,skip_content,shop_type,status,scope,city_names,city_ids,longitude,latitude,create_time FROM banner WHERE id = #{Id} AND  status  <> 0 ")
    Banner selectBannerById(@Param("Id") Long Id );


    /**
     * 获取Banner分页列表
     * @param page
     * @param bannerTypes
     * @return
     */
    @Select("SELECT " +
            "id,name,banner_sort,banner_type,img_url,skip_type,skip_content,shop_type,status,scope,city_names,city_ids,longitude,latitude,create_time  " +
            "FROM " +
            "banner " +
            "WHERE  " +
            "banner_type= #{bannerType} " +
            "AND status  <> 0  order by status,banner_sort ")
    List<Map<String, Object>> selectBannerPageByBannerType(Pagination page, @Param("bannerType") Integer bannerTypes);

    /**
     * 获取banner详情
     * @param bannerId
     * @return
     */
    @Select("SELECT  " +
            "b.id,b.`name`,b.banner_sort,b.banner_type,b.img_url,b.skip_type,b.scope,b.city_names,  " +
            "( CASE WHEN b.skip_type=1 THEN g.goods_des  " +
            "  WHEN b.skip_type=2 THEN  CONCAT(s.shop_name,'-',s.shop_type)   " +
            "  WHEN b.skip_type=3 THEN t.tip_des  " +
            "  ELSE  b.skip_content  " +
            "END  " +
            ")as skip_content_des  " +
            "FROM banner b  " +
            "LEFT JOIN(  " +
                        "SELECT  g.id,  " +
                        "GROUP_CONCAT( g.`name`, '-',  s.shop_name SEPARATOR '|' ) AS goods_des " +
                        "FROM goods g   " +
                        "LEFT JOIN shop s ON g.shop_id=s.id GROUP BY g.id " +
                        ") g ON b.skip_content=g.id  " +
            "LEFT JOIN shop s ON b.skip_content=s.id  " +
            "LEFT JOIN(  " +
                    "SELECT   " +
                    "t.id ,GROUP_CONCAT( t.title, '-',tc.`name` SEPARATOR '|' ) AS tip_des  " +
                    "FROM tips t  " +
                    "LEFT JOIN tips_category tc ON t.tips_category_id=tc.id  " +
                    "GROUP BY t.id  " +
                    ")  t ON  b.skip_content=t.id  WHERE b.id=#{bannerId}  ")
    Map<String ,Object> selectBannerInfoById(@Param("bannerId") Long bannerId);

    /**
     * 同种类型只能上架五个
     * @param bannerType
     * @return
     */
    @Select("SELECT id,name,banner_sort,banner_type FROM  banner where status=1 and banner_type=#{bannerType}  ")
    List<Map<String, Object>> selectAllBannerByType( @Param("bannerType") Integer bannerType);

    /**
     * 置空skip_content
     * @param bannerId
     * @return
     */
    @Update("update banner  set skip_content=null,update_time=now() where id=#{bannerId}")
    int removeSkipContentById(@Param("bannerId") Long bannerId);

}
