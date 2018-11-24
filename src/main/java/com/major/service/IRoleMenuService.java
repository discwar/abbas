package com.major.service;

import com.major.entity.RoleMenu;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>Title: Module Information </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/8/1 09:32      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */
public interface IRoleMenuService extends IService<RoleMenu> {

    void test();

    boolean addRoleMenu(List<RoleMenu> roleMenuList);

    boolean deleteRoleMenu(Long roleId);

    /**
     * 只删除menu_type为F
     * @param roleId
     * @return
     */
    boolean deleteRoleMenuType(Long roleId);
}
