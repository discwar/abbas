package com.major.mapper;

import com.major.entity.Menu;
import com.major.entity.RoleMenu;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    /**
     * 根据menuId删除
     */
    @Delete("delete from role_menu where menu_id=#{menuId} and role_id=#{roleId} ")
    int deleteRoleByType(@Param("menuId") Long menuId, @Param("roleId") Long roleId);

    /**
     * 查找当前角色的菜单类型为F的数据
     * @param roleId
     * @return
     */
    @Select("SELECT m.id,m.menu_type " +
            "FROM menu m " +
            "left join role_menu rm ON m.id=rm.menu_id  WHERE m.menu_type='F' and rm.role_id=#{roleId}  ")
    List<Menu> selectMenuAllByRoleId(@Param("roleId") Long roleId);


}
