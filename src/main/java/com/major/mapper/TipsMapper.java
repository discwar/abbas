package com.major.mapper;

import com.major.entity.Tips;
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
 * <p>Create Time: 2018/7/18 19:35      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public interface TipsMapper extends BaseMapper<Tips> {

    /**
     * 根据状态和分类ID获取小贴士信息列表，分页显示
     *
     * @param page 翻页对象，可以作为 xml 参数直接使用，传递参数 Page 即自动分页
     * @param status 状态：0-删除；1-上架；2-下架
     * @return
     */

    List<Map<String, Object>> selectTipsList(Pagination page,
                                             @Param("status") Integer status,
                                             @Param("title") String title,
                                             @Param("categoryId") Integer categoryId,
                                             @Param("startTime") String startTime,
                                             @Param("endTime") String endTime);

    @Select({
            "<script> "+
                    "SELECT  " +
                    "t.title,t.summary,tc.name as category_name,t.id as tip_id  " +
                    "FROM tips t  " +
                    "LEFT JOIN tips_category tc ON t.tips_category_id=tc.id  " +
                    "        WHERE  " +
                    "          t.`status`=1  " +
                    "        <if test='title!=null '>  " +
                    "            AND t.title  like concat('%',#{title},'%')  " +
                    "        </if>  " +
                    "        GROUP BY t.create_time DESC "+
                    "</script>"}  )
    List<Map<String, Object>> selectTipsListByTitle(Pagination page,@Param("title") String title);

    /**
     * 获取当前小贴士
     * @param Id
     * @return
     */
    @Select(" SELECT t.title,t.summary,tc.name as category_name,t.id as tip_id ,t.content,t.cover_url ,t.tips_category_id   " +
            "FROM tips t  " +
            "LEFT JOIN tips_category tc ON t.tips_category_id=tc.id  where t.id=#{Id}" )
    Map<String,Object> selectTipsById(@Param("Id") Long Id);

    /**
     * 置空
     * @param id
     * @return
     */
    @Update("update tips set tips_category_id = null,update_time=now() where id =#{id}")
    int removeTipsCategoryId(@Param("id") Long id);
}
