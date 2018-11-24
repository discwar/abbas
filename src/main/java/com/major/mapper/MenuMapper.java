package com.major.mapper;

import com.major.entity.Menu;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Select("SELECT distinct m.perms FROM menu m " +
            "LEFT JOIN role_menu rm ON m.id = rm.menu_id " +
            "LEFT JOIN sys_user_role ur ON rm.role_id = ur.role_id " +
            "WHERE ur.sys_user_id = #{userId} and m.visible=0  ")
    List<String> selectPermsByUserId(@Param("userId") Long userId);

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    @Select("SELECT m.id, m.menu_name, m.parent_id, m.menu_sort, m.url, m.perms, m.icon, m.create_time " +
            "FROM menu m " +
            "LEFT JOIN role_menu rm on m.id = rm.menu_id " +
            "LEFT JOIN sys_user_role ur on rm.role_id = ur.role_id " +
            "WHERE ur.sys_user_id = #{userId} " +
            "AND m.menu_type<>'F' and m.visible=0  "+
            "ORDER BY m.menu_sort")
    @Results({
            @Result(property = "menuName", column = "menu_name"),
            @Result(property = "parentId", column = "parent_id"),
            @Result(property = "menuSort", column = "menu_sort"),
            @Result(property = "createTime", column = "create_time")
    })
    List<Menu> selectMenusByUserId(Long userId);


    /**
     * 返回所有菜单列表
     * @return
     */
    @Select("SELECT m.id, m.menu_name, m.parent_id, m.menu_sort, m.url, m.menu_type, m.visible, m.perms, m.icon, m.create_time " +
            "FROM menu m WHERE  m.menu_type<>'F' ORDER BY m.menu_sort ")
    List<Menu> selectMenuAll();

    /**
     * 根据菜单类型返回数据
     * @param menuType
     * @return
     */
    @Select("SELECT m.id, m.menu_name, m.parent_id, m.menu_sort, m.url, m.menu_type, m.visible, m.perms, m.icon, m.create_time " +
            "FROM menu m WHERE m.menu_type=#{menuType} ")
    List<Menu> selectMenuAllByMenuType(@Param("menuType") String menuType);

    /**
     * 删除时:根据菜单ID放入到父菜单查找条件，如果有值相当于这是父菜单不能删除
     * @param parentId
     * @return
     */
    @Select("SELECT id ,parent_id FROM menu WHERE parent_id=#{parentId} AND menu_type<>'F'  ")
    List<Menu> selectMenuAllById(@Param("parentId") Long parentId);

    /**
     * 改写添加方法
     * @param menu
     * @return
     */
   boolean insertMenu(Menu menu);

    /**
     * 改写修改方法
     * @param menu
     * @return
     */
   boolean updateMenuByPrimaryKey(Menu menu);

    /**
     * 根据子菜单反推出所有的父菜单
     * @param parentId
     * @return
     */
    @Select("select id,menu_name,parent_id, menu_sort, url, menu_type,visible,perms, icon,create_time  from menu where FIND_IN_SET(id,getParentList(#{parentId})   ) and visible=0 ")
    List<Menu> selectMenuByParentId(@Param("parentId") Long parentId);
}