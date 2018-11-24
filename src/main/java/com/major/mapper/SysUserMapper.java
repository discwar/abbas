package com.major.mapper;

import com.major.entity.SysUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Select("SELECT u.id, u.username, u.password, u.salt, u.remark_name, u.login_ip, u.login_date, u.create_time, u.update_time, u.status, u.remark " +
            "FROM sys_user u " +
            "LEFT JOIN sys_user_role ur ON u.id = ur.sys_user_id " +
            "WHERE u.username = #{userName} and u.status=1 ")
    SysUser selectUserByUserName(@Param("userName") String userName);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Update("UPDATE sys_user SET login_ip=#{loginIp}, login_date=#{loginDate} WHERE id=#{id}")
    int updateUser(SysUser user);

    /**
     * 系统用户分页
     * @param page
     * @param ew
     * @return
     */
    @Select({
            "<script>"+
                    "select su.username,su.remark,r.role_name,su.id as sys_user_id, r.id as role_id  " +
                    "FROM " +
                    "sys_user su  " +
                    "LEFT JOIN sys_user_role sur ON su.id = sur.sys_user_id  " +
                    "LEFT JOIN role r ON sur.role_id=r.id  " +
                    " <where> ${ew.sqlSegment} </where> " +
                    "</script>"})
    List<Map<String, Object>> selectSysUserPage(Pagination page,  @Param("ew") Wrapper ew);

    /**
     * 根据用户id查询角色相关
     * @param sysUserId
     * @return
     */
    @Select("SELECT r.id, r.role_name, r.role_key FROM sys_user u " +
            "LEFT JOIN sys_user_role ur ON u.id = ur.sys_user_id " +
            "LEFT JOIN role r ON ur.role_id = r.id " +
            "WHERE ur.sys_user_id = #{sysUserId} limit 0,1")
    Map<String, Object> selectRoleBySysUserId(@Param("sysUserId") Long sysUserId);


}
