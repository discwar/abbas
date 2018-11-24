package com.major.service.impl;

import com.major.entity.Menu;
import com.major.entity.RoleMenu;
import com.major.mapper.RoleMenuMapper;
import com.major.service.IRoleMenuService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public void test() {
        Map<String, Object> map = new HashMap<>(1);
        map.put("role_id", 2);
        super.deleteByMap(map);

        List<RoleMenu> list = new ArrayList<>();

        RoleMenu rm1 = new RoleMenu();
        rm1.setRoleId(2);
        rm1.setMenuId(11);
        list.add(rm1);

        RoleMenu rm2 = new RoleMenu();
        rm2.setRoleId(2);
        rm2.setMenuId(12);
        list.add(rm2);

        super.insertBatch(list);

//        int i = 1/0;
    }

    @Override
   public boolean addRoleMenu(List<RoleMenu> roleMenuList){
        return   super.insertBatch(roleMenuList);
    }

    @Override
    public boolean deleteRoleMenu(Long roleId){
        Map<String, Object> map = new HashMap<>();
        map.put("role_id", roleId);
        return   super.deleteByMap(map);
    }

    public boolean deleteRoleMenuType(Long roleId){
        //先查找出角色关联的F类型的菜单数据，在删除
        List<Menu>  menuList=baseMapper.selectMenuAllByRoleId(roleId);
        if(menuList!=null && menuList.size()>0){
            for(Menu menu :menuList){
                baseMapper.deleteRoleByType(menu.getId(),roleId);
            }
        }
      return true;
    }
}
