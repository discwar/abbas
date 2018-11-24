package com.major.service;

import com.major.entity.Role;
import com.major.model.request.RoleRequest;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;
import java.util.Set;

public interface IRoleService extends IService<Role> {

    /**
     * 根据用户ID查询角色
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> selectRoleKeys(Long userId);

    /**
     * 添加角色
     * @param roloeRequest
     * @param username
     * @return
     */
    boolean addRole(RoleRequest roloeRequest, String username);

    /**
     * 修改角色
     * @param roloeRequest
     * @param username
     * @return
     */
    boolean updateRole(RoleRequest roloeRequest, String username, Long roleId);

    /**
     * 删除角色
     * @param username
     * @return
     */
    boolean deleteRole(String username, Long roleId);

    /**
     * 获取角色分页列表
     * @param page
     * @return
     */
    Page<Map<String, Object>> selectTipsPage(Page<Map<String, Object>> page);

    /**
     *查询所有角色
     * @return
     */
    Map<String, Object> selectRoleList();

    /**
     * 根据当前角色返回该角色下所有的菜单信息
     * @param roleId
     * @return
     */
    Map<String, Object> selectRoleListByRoleId(long roleId);


    /**
     * 变更角色
     * @param sysUserId
     * @param roleId
     * @return
     * @throws Exception
     */
   boolean changeRole(Long sysUserId,Long roleId) throws Exception;

    /**
     * 针对爱果小店添加店铺
     * @return
     */
    Role selectRoleByAG();

    /**
     * 根据roleKey查找角色
     * @param roleKey
     * @return
     */
    Role selectRoleByKey(String roleKey);

    /**
     * 校验用户分配的角色是否有绑定店铺
     * @param userId
     */
    void checkRoleShop(Long userId);

}
