package com.major.mapper;

import com.major.entity.StoredConfig;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 储值送好礼配置表 Mapper 接口
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-11-15
 */
public interface StoredConfigMapper extends BaseMapper<StoredConfig> {

    /**
     * 分页
     * @param page
     * @return
     */
    @Select({
            "<script> "+
                    "  SELECT id,`name`,threshold,img_url,coupon_ids,stored_give ,create_time FROM stored_config " +
                    "        ORDER BY  " +
                    "        create_time DESC "+
                    "</script>"}  )
    List<Map<String,Object> > selectStoredConfigPage(Pagination page);
}
