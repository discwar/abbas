package com.major.mapper;

import com.major.entity.Tips;
import com.major.entity.TipsCategory;
import com.baomidou.mybatisplus.mapper.BaseMapper;
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
public interface TipsCategoryMapper extends BaseMapper<TipsCategory> {
    /**
     * 查询所有分类分页列表，包括上架和下架，除去删除
     * @return
     */
    @Select("SELECT " +
            "tc.id, " +
            "tc.`name`, tc.status , " +
            "tc.category_sort, "+
            "COUNT(t.tips_category_id) AS tip_count " +
            "FROM " +
            "tips_category tc " +
            "LEFT JOIN tips t ON tc.id = t.tips_category_id " +
            "WHERE " +
            "tc.`status`<> 0 "+
            "GROUP BY " +
            "tc.`name` ")
    List<Map<String, Object>> selectTipsCategoryPage(Pagination page);

    /**
     * 查询所有分类信息
     * @return
     */
    @Select(" SELECT tc.id,tc.`name` FROM tips_category tc WHERE  tc.`status`=1  ORDER BY  tc.category_sort ")
    List<Map<String, Object>> selectTipsCategoryList();

    /**
     * 根据分类id查找小贴士
     * @param categoryId
     * @return
     */
    @Select("select * from tips where  tips_category_id=#{categoryId}   and status <> 0 ")
    List<Tips> selectTipsByCategoryId(@Param("categoryId") Long categoryId);


}
