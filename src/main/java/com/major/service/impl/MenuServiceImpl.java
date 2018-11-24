package com.major.service.impl;

import com.major.common.enums.StatusResultEnum;
import com.major.common.exception.AgException;
import com.major.common.util.TreeUtils;
import com.major.entity.Menu;
import com.major.mapper.MenuMapper;
import com.major.model.request.MenuRequest;
import com.major.service.IMenuService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    public static final String PERMISSION_STRING = "perms[\"{0}\"]";

    @Override
    public  boolean addMenu(MenuRequest menuRequest) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuRequest, menu);
       return  baseMapper.insertMenu(menu);
    }

    @Override
    public  boolean updateMenu(MenuRequest menuRequest , Long menu_id) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuRequest, menu);
        menu.setId(menu_id);
        return baseMapper.updateMenuByPrimaryKey(menu);
    }

    @Override
    public  boolean deleteMenu(Long menu_id) {
        List<Menu> menuList=baseMapper.selectMenuAllById(menu_id);
        if(menuList!=null && menuList.size()>0) {
            throw new AgException(StatusResultEnum.DB_SAVE_ERROR,"存在子菜单");
        }
        return super.deleteById(menu_id);
    }

    @Override
    public Set<String> selectPermsByUserId(Long userId) {
        List<String> perms = baseMapper.selectPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms) {
            if (StringUtils.isNotEmpty(perm)) {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }

        return permsSet;
    }

    @Override
    public List<Menu> selectMenusByUserId(Long userId) {
        List<Menu> menus = baseMapper.selectMenusByUserId(userId);
        List<Menu> menusResult=new ArrayList<>();
        //根据子菜单中的父Id返回父菜单
        for (Menu menu : menus){
            List<Menu> menus2=baseMapper.selectMenuByParentId(menu.getParentId());
            for (Menu menu2 : menus2){
                menusResult.add(menu2);
            }
            menusResult.add(menu);
        }
        //删除重复元素
        for (int i = 0; i < menusResult.size() - 1; i++) {
            for (int j = menusResult.size() - 1; j > i; j--) {
                if (menusResult.get(j).getMenuName().equals(menusResult.get(i).getMenuName())) {
                    //删除重复元素
                    menusResult.remove(j);
                }
            }
        }
        //排序
        menusResult.sort(Comparator.naturalOrder());
        return TreeUtils.getChildPerms(menusResult, 0);
    }


    @Override
    public LinkedHashMap<String, String> selectPermsAll() {
        LinkedHashMap<String, String> section = new LinkedHashMap<>();
        List<Menu> permissions = baseMapper.selectMenuAll();
        for (Menu menu : permissions) {
            section.put(menu.getUrl(), MessageFormat.format(PERMISSION_STRING, menu.getPerms()));
        }
        return section;
    }

    @Override
    public List<Menu> selectMenuAllByMenuType(String menuType) {
        List<Menu> menus = baseMapper.selectMenuAllByMenuType(menuType);
      return menus;
    }

    @Override
    public Map<String, Object> selectMenuAll() {
        List<Menu> menuList = baseMapper.selectMenuAll();
        Map<String, Object> map=new HashMap<>(1);
        map.put("menu_list", TreeUtils.getChildPerms(menuList, 0));
      return map;
    }
}
