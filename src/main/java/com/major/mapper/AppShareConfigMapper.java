package com.major.mapper;

import com.major.entity.AppShareConfig;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * APP分享配置表 Mapper 接口
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-10-11
 */
public interface AppShareConfigMapper extends BaseMapper<AppShareConfig> {

    /**
     * 分页
     * @param page
     * @return
     */
    @Select({
            "<script> "+
                    "   select id,share_title,share_desc,share_image_url,share_url,share_type,create_time,update_time from app_share_config " +
                    "        WHERE  1=1 " +
                    "        <if test='shareType!=null '>  " +
                    "            AND share_type =#{shareType} " +
                    "        </if>  " +
                    "        ORDER BY  " +
                    "        create_time DESC "+
                    "</script>"}  )
    List<Map<String,Object> > selectAppShareConfigPage(Pagination page,@Param("shareType") Integer shareType);

    /**
     * 获取某条数据
     * @param id
     * @return
     */
    @Select("select * from app_share_config  where id=#{id}")
    Map<String,Object> selectAppShareConfigById(@Param("id") Long id);

    /**
     * 根据分享类型返回数据
     * @param shareType
     * @return
     */
    @Select("select id,share_title,share_desc,share_image_url,share_url,share_type,create_time,update_time from app_share_config where share_type=#{shareType} ")
    List<Map<String,Object> > selectAppShareConfigByShareType(@Param("shareType") Integer shareType);
}
