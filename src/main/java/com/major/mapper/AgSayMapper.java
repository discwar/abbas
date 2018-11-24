package com.major.mapper;

import com.major.entity.AgSay;
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
 * <p>Create Time: 2018/7/18 19:35      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public interface AgSayMapper extends BaseMapper<AgSay> {

    @Select("SELECT *  FROM  ag_say WHERE id=#{id} ")
    AgSay selectAgSayById( @Param("id") Long id);

    @Select({
            "<script>"+
                    "SELECT * FROM ag_say <where> ${ew.sqlSegment} </where> " +
                    "</script>"})
    List<Map<String, Object>> selectAgSayPage(Pagination page,@Param("ew") Wrapper ew);

    /**
     * 获取爱果有话说信息列表
     * @param ew
     * @return
     */
    @Select("<script>" +
            "SELECT id, title " +
            "FROM ag_say " +
            "<where>" +
            "${ew.sqlSegment} " +
            "</where>" +
            "</script>")
    List<Map<String, Object>> selectAgSayBO(@Param("ew") Wrapper ew);

}
