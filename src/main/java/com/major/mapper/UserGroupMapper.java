package com.major.mapper;

import com.major.entity.UserGroup;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户组表 Mapper 接口
 * </p>
 *
 * @author LianGuoQing
 * @since 2018-08-14
 */
public interface UserGroupMapper extends BaseMapper<UserGroup> {

    /**
     * 获取用户群组分页
     * @param page
     * @return
     */
    @Select("SELECT id,group_name,status,search_type,create_time FROM user_group WHERE status=1 ORDER BY  create_time DESC ")
    List<Map<String, Object>> selectUserGroupPage(Pagination page);

    /**
     * 获取用户群组不分页
     * @return
     */
    @Select("SELECT id,group_name  FROM user_group WHERE status=1 ORDER BY  create_time DESC ")
    List<Map<String, Object>> selectUserGroupList();


}
