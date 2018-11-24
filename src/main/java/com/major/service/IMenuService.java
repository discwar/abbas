package com.major.service;

import com.major.entity.Menu;
import com.major.model.request.MenuRequest;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IMenuService extends IService<Menu> {

    boolean addMenu(MenuRequest menuRequest);

    boolean updateMenu(MenuRequest menuRequest ,Long menu_id);

    boolean deleteMenu(Long menu_id);
    /**
     * 根据用户ID查询权限
     * @param userId 系统用户ID
     * @return 权限列表
     */
    Set<String> selectPermsByUserId(Long userId);

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<Menu> selectMenusByUserId(Long userId);

    /**
     * 查询系统所有权限
     *
     * @return 权限列表
     */
    Map<String, String> selectPermsAll();

    /**
     * 根据菜单类型返回数据
     * @param menuType
     * @return
     */
    List<Menu> selectMenuAllByMenuType(String menuType);

    /**
     * 返回所有菜单数据
     * @return
     */
    Map<String, Object>   selectMenuAll();


}
