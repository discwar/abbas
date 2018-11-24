package com.major.service;

import com.major.entity.UserGroup;
import com.major.model.request.UserGroupRequest;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户组表 服务类
 * </p>
 *
 * @author zhangzhenliang
 * @since 2018-08-15
 */
public interface IUserGroupService extends IService<UserGroup> {

    /**
     * 添加群组
     * @param userGroupRequest
     * @return
     */
    boolean addUserGroup(UserGroupRequest userGroupRequest);

    /**
     * 分页查询所有群组
     * @param page
     * @return
     */
    Page<Map<String, Object>> selectUserGroupPage(Page<Map<String, Object>> page);

    /**
     * 修改-只能修改名称
     * @param userGroupRequest
     * @param groupId
     * @return
     */
    boolean updateUserGroup(UserGroupRequest userGroupRequest,Long groupId);

    /**
     * 删除分组
     * @param groupId
     * @return
     */
     boolean deleteUserGroup(Long groupId);

    /**
     * 获取当前群组信息
     * @param groupId
     * @return
     */
    UserGroup selectUserGroupById(Long groupId);


    List<Map<String, Object>> selectUserGroupList();
}
