package com.major.mapper;

import com.major.entity.Role;
import com.major.model.response.SysUserRoleResponse;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Select("SELECT r.id, r.role_name, r.role_key FROM sys_user u " +
            "LEFT JOIN sys_user_role ur ON u.id = ur.sys_user_id " +
            "LEFT JOIN role r ON ur.role_id = r.id " +
            "WHERE ur.sys_user_id = #{userId}")
    @Results({
            @Result(property = "roleName", column = "role_name"),
            @Result(property = "roleKey", column = "role_key")
    })
    List<Role> selectRolesByUserId(@Param("userId") Long userId);

    /**
     * 角色分页
     * @param page
     * @param status
     * @return
     */
    @Select("SELECT r.role_name, r.id as role_id , r.role_key FROM  role r  " +
            "WHERE r.status = #{status} order by r.create_time desc ")
    List<Map<String, Object>> selectRolePageList(Pagination page, @Param("status") Integer status);

    /**
     * 角色所有
     * @param status
     * @return
     */
    @Select("SELECT id as role_id , r.role_name   FROM  role r  " +
            "WHERE r.status = #{status}")
    List<Map<String, Object>> selectRoleList(@Param("status") Integer status);

    /**
     * 获取当前角色的菜单
     * @param roleId
     * @param status
     * @return
     */
    @Select("SELECT " +
            " m.id AS menu_id  " +
            "FROM " +
            "role r " +
            "LEFT JOIN role_menu rm ON r.id = rm.role_id " +
            "LEFT JOIN menu m ON rm.menu_id = m.id " +
            "WHERE " +
            "r.`status`=#{status}  AND r.id=#{roleId} AND m.menu_type<>'F'  ORDER BY m.menu_sort " )
    List<Map<String, Object>> selectRoleListByRoleId(@Param("roleId") Long roleId,@Param("status") Integer status);


    @Select("SELECT id , r.role_name   FROM  role r  " +
            "WHERE r.role_name ='水果商家爱果小店管理'  and r.`status`=1 ")
    Role selectRoleByAG();

    /**
     * 根据用户的id返回角色相关
     * @param userId
     * @return
     */
    @Select("SELECT ur.sys_user_id,ur.role_id, r.role_name, r.role_key  " +
            "FROM sys_user u " +
            "LEFT JOIN sys_user_role ur ON u.id = ur.sys_user_id " +
            "LEFT JOIN role r ON ur.role_id = r.id " +
            "WHERE ur.sys_user_id = #{userId} and r.status=1 ")
    SysUserRoleResponse selectSysUserRoleBySysUserId(@Param("userId") Long userId);

}
