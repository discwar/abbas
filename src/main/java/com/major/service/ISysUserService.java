package com.major.service;

import com.major.entity.SysUser;
import com.major.model.request.SysUserRequest;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

public interface ISysUserService extends IService<SysUser> {

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    SysUser selectUserByUserName(String userName);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int updateUser(SysUser user);

    /**
     * 添加管理员
     * @param sysUserRequest
     * @param username
     * @return
     */
    boolean addSysUser(SysUserRequest sysUserRequest, String username);

    /**
     * 修改管理员
     * @param sysUserRequest
     * @param username
     * @param userId
     * @return
     */
    boolean updateSysUser(SysUserRequest sysUserRequest, String username, Long userId);

    /**
     * 删除管理员
     * @param userId
     * @return
     */
    boolean deleteSysUser(Long userId);

    /**
     * 获取管理员分页列表
     * @param page
     * @return
     */
    Page<Map<String, Object>> selectSysUserPage(Page<Map<String, Object>> page, String userName, String remark, Long roleId);

    /**
     * 个人管理-修改密码
     * @param sysUserRequest
     * @param userId
     * @return
     */
    boolean changeSysUserPwd(SysUserRequest sysUserRequest, Long userId);

    SysUser selectUserById(Long userId);

    /**
     * 校验用户登入名是否存在同名
     * @param name
     * @return
     */
    boolean checkSysUserName(String name);

    /**
     * 重置密码
     * @param Id
     * @return
     */
    boolean resetSysUserPwd(Long Id);

    /**
     * 根据用户id查询角色相关
     * @param sysUserId
     * @return
     */
    Map<String, Object> selectRoleBySysUserId(Long sysUserId);
}

