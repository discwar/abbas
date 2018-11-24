package com.major.mapper;


import com.major.entity.Area;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
public interface AreaMapper extends BaseMapper<Area> {

    /**
     * 模糊查询名称
     *
     * @return
     */
    @Select("SELECT id,name FROM area WHERE  name like concat('%',#{name},'%') and status=1 and area_type <> 3 ")
    List<Map<String, String>> selectProvinceCityByName(@Param("name") String name);

    /**
     * 区域分页
     * @param page
     * @param ew
     * @return
     */
    @Select({
            "<script>"+
                    "SELECT b.`name` as parent_name,a.`name` ,a.area_key,a.area_type,a.`status`,a.id FROM area  a  " +
                    "LEFT JOIN area b ON  a.parent_id=b.id    " +
                    "  <where> ${ew.sqlSegment} </where> " +
                    "</script>"})
    List<Map<String, Object>> selectAreaPage(Pagination page,
                                                               @Param("ew") Wrapper ew);
}
