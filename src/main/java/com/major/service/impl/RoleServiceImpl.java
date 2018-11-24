package com.major.service.impl;

import com.major.common.constant.Constants;
import com.major.common.enums.ShopRoleEnum;
import com.major.common.enums.StatusResultEnum;
import com.major.common.exception.AgException;
import com.major.common.util.ShiroUtils;
import com.major.entity.Menu;
import com.major.entity.Role;
import com.major.entity.RoleMenu;
import com.major.entity.SysUserRole;
import com.major.mapper.RoleMapper;
import com.major.model.request.RoleRequest;
import com.major.model.response.SysUserRoleResponse;
import com.major.service.IRoleMenuService;
import com.major.service.IRoleService;
import com.major.service.IShopService;
import com.major.service.ISysUserRoleService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private  IRoleMenuService roleMenuService;

    @Autowired
    private    MenuServiceImpl menuService;

    @Autowired
    private ISysUserRoleService sysUserRoleService;

    @Autowired
    private IShopService shopService;
    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRoleKeys(Long userId) {
        List<Role> roleList = baseMapper.selectRolesByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (Role role : roleList) {
            if (role != null) {
                permsSet.addAll(Arrays.asList(role.getRoleKey().trim().split(",")));
            }
        }

        return permsSet;
    }

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean addRole(RoleRequest roleRequest,String username) {
        Role role =new Role();
        role.setRoleName(roleRequest.getRoleName());
        role.setRoleKey(roleRequest.getRoleKey());
        role.setCreator(username);
        role.setStatus(Constants.STATUS_NORMAL);
        super.insert(role);

       //判断菜单集合是否有值
        List<RoleMenu> roleMenuList=new ArrayList<>();
        if(roleRequest.getMenuId().size()>0 && roleRequest.getMenuId()!=null) {
            for(String st :roleRequest.getMenuId()){
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setRoleId(role.getId().intValue());
                    roleMenu.setMenuId(Integer.valueOf(st));
                    roleMenuList.add(roleMenu);
            }
        }
        //表示返回所有按钮类型的菜单数据
        List<Menu> menuList=menuService.selectMenuAllByMenuType("F");
        if(menuList.size()>0 && menuList!=null) {
            for(Menu menu : menuList) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setRoleId(role.getId().intValue());
                roleMenu.setMenuId(menu.getId().intValue());
                roleMenuList.add(roleMenu);
            }
        }
        boolean flag=roleMenuService.addRoleMenu(roleMenuList);
        if(!flag) {
            throw new AgException(StatusResultEnum.ERROR);
        }
       //清除权限缓存
        ShiroUtils.clearCachedAuthorizationInfo();
       return  true;
    }

    @Override
    @Transactional(
            rollbackFor = {Exception.class}
    )
    public boolean updateRole(RoleRequest roleRequest, String username, Long roleId) {
        //先判断菜单集合是否有值
        List<RoleMenu> roleMenuList=new ArrayList<>();
        if(roleRequest.getMenuId()!=null &&  roleRequest.getMenuId().size()>0 ) {
            try{
                //再删除该角色下的菜单
                roleMenuService.deleteRoleMenu(roleId);
                //再添加新的菜单***添加的只是菜单类型
                for(String st :roleRequest.getMenuId()) {
                        RoleMenu roleMenu = new RoleMenu();
                        roleMenu.setRoleId(roleId.intValue());
                        roleMenu.setMenuId(Integer.valueOf(st));
                        roleMenuList.add(roleMenu);
                }
                //表示返回所有按钮类型的菜单数据***添加的是按钮类型
                List<Menu> menuList=menuService.selectMenuAllByMenuType("F");
                if(menuList.size()>0 && menuList!=null) {
                    for(Menu menu : menuList) {
                        RoleMenu roleMenu = new RoleMenu();
                        roleMenu.setRoleId(roleId.intValue());
                        roleMenu.setMenuId(menu.getId().intValue());
                        roleMenuList.add(roleMenu);
                    }
                }
            }catch (Exception e){
                throw new AgException(StatusResultEnum.ERROR);
            }
        }else {
            //当菜单getMenuId没有数据时，更新F类型的菜单
            //表示返回所有按钮类型的菜单数据***添加的是按钮类型
            List<Menu> menuList=menuService.selectMenuAllByMenuType("F");
            if(menuList.size()>0 && menuList!=null) {
                roleMenuService.deleteRoleMenuType(roleId);
                for(Menu menu : menuList) {
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setRoleId(roleId.intValue());
                    roleMenu.setMenuId(menu.getId().intValue());
                    roleMenuList.add(roleMenu);
                }
            }
        }
        roleMenuService.addRoleMenu(roleMenuList);
        //清除权限缓存
        ShiroUtils.clearCachedAuthorizationInfo();
        //如果有更改role表的信息再更新
            Role role =new Role();
            role.setId(roleId);
            role.setRoleName(roleRequest.getRoleName());
            role.setModifier(username);
            role.setUpdateTime(new Date());
        return   super.updateById(role);
    }

    @Override
    public boolean deleteRole(String username, Long roleId) {
        boolean flag=roleMenuService.deleteRoleMenu(roleId);
        if(!flag) {
            throw new AgException(StatusResultEnum.ERROR);
        }
        Role role =new Role();
        role.setId(roleId);
        role.setModifier(username);
        roleMenuService.deleteRoleMenu(roleId);
        //清除权限缓存
        ShiroUtils.clearCachedAuthorizationInfo();
        return role.deleteById();
    }

    @Override
    public Page<Map<String, Object>> selectTipsPage(Page<Map<String, Object>> page){
        return page.setRecords(baseMapper.selectRolePageList(page, Constants.STATUS_ENABLE));
   }

    @Override
    public Map<String, Object> selectRoleList() {
        Map<String, Object> resultMap = new HashMap<>(1);
        List<Map<String, Object>> tipsCategoryList = baseMapper.selectRoleList(Constants.STATUS_ENABLE);
        resultMap.put("role_list", tipsCategoryList);
        return resultMap;
    }

    @Override
    public Map<String, Object> selectRoleListByRoleId(long roleId) {
        Map<String, Object> resultMap = new HashMap<>(1);
        List<Map<String, Object>> tipsCategoryList = baseMapper.selectRoleListByRoleId(roleId,Constants.STATUS_ENABLE);
        List<String> menuIdList=new ArrayList<>();
        for(int i=0;i<tipsCategoryList.size();i++ ) {
            menuIdList.add(tipsCategoryList.get(i).get("menu_id").toString());
        }
        resultMap.put("menu_id_list", menuIdList);
        return resultMap;
    }

    //用户变更角色
    @Override
    public boolean changeRole(Long sysUserId,Long roleId) throws Exception {
        //先删除用户角色管理表信息
        sysUserRoleService.deleteSysUserRole(sysUserId);
        //再添加
        SysUserRole sysUserRole=new SysUserRole();
        sysUserRole.setRoleId(roleId.intValue());
        sysUserRole.setSysUserId(sysUserId.intValue());
        //清除权限缓存
        ShiroUtils.clearCachedAuthorizationInfo();
        return sysUserRoleService.addSysUserRole(sysUserRole);
    }

    @Override
    public  Role selectRoleByAG(){
        return baseMapper.selectRoleByAG();
    }

    @Override
    public Role selectRoleByKey(String roleKey) {
        EntityWrapper<Role> ew = new EntityWrapper<>();
        ew.where("role_key = {0}",roleKey)
                .and("status = {0}",Constants.STATUS_ENABLE);
        Role role = this.selectOne(ew);
        if (role == null){
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,roleKey+":角色尚未创建");
        }
        return role;
    }

    @Override
    public void checkRoleShop(Long userId){
        SysUserRoleResponse sysUserRoleResponse=baseMapper.selectSysUserRoleBySysUserId(userId);
        if(null==sysUserRoleResponse){
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"用户尚未分配角色");
        }
        //根据用户当前的roleKey，如果是店铺管理几种类型的话，在去校验该用户是否有绑定店铺
        if(ShopRoleEnum.ROLE_AIGUO_SHOP.getRoleKey().equals(sysUserRoleResponse.getRoleKey()) || ShopRoleEnum.ROLE_NEAR_FRUIT.getRoleKey().equals(sysUserRoleResponse.getRoleKey()) ||
                ShopRoleEnum.ROLE_FARM.getRoleKey().equals(sysUserRoleResponse.getRoleKey())  || ShopRoleEnum.ROLE_PLUCKING_GARDEN.getRoleKey().equals(sysUserRoleResponse.getRoleKey())  ||
                ShopRoleEnum.ROLE_IMPORT_FRUIT.getRoleKey().equals(sysUserRoleResponse.getRoleKey()) ||  ShopRoleEnum.ROLE_FRUIT_AGL.getRoleKey().equals(sysUserRoleResponse.getRoleKey())  ){
            List<Map<String,Object>> list=  shopService.selectShopInfoBySysUserId(userId);
            if(null==list || list.size()<=0){
                throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"角色分配错误，您没有绑定店铺");
            }
        }
    }


}
