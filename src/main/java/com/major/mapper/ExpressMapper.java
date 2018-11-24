package com.major.mapper;

import com.major.entity.Express;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 快递表 Mapper 接口
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-29
 */
public interface ExpressMapper extends BaseMapper<Express> {

    @Select("SELECT * FROM express WHERE status=1")
    List<Express> selectExpressAll();

    /**
     * 获取所有快递公司分页列表
     * @param page
     * @param name
     * @return
     */
    @Select({
            "<script> "+
                    "      SELECT *  FROM express WHERE  " +
                    "         `status`=1  " +
                    "        <if test='name!=null '>  " +
                    "            AND name =#{name}  " +
                    "        </if>  " +
                    "        ORDER BY  " +
                    "        create_time DESC "+
                    "</script>"}  )
    List<Map<String, Object>> selectExpressPage(Pagination page,
                                              @Param("name") String name);


}
