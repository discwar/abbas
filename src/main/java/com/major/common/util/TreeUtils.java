package com.major.common.util;

import com.major.entity.Menu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 权限数据处理
 *
 */
public class TreeUtils {

    /**
     * 根据父节点的ID获取所有子节点
     * 
     * @param list 所有菜单列表
     * @param parentId 传入的父节点ID
     * @return 返回所有包含子菜单的菜单列表
     */
    public static List<Menu> getChildPerms(List<Menu> list, int parentId) {
        List<Menu> returnList = new ArrayList<>();

        for (Iterator<Menu> iterator = list.iterator(); iterator.hasNext();) {
            Menu menu = iterator.next();

            if (menu.getParentId() == parentId) {
                recursionFn(list, menu);
                returnList.add(menu);
            }
        }

        return returnList;
    }

    /**
     * 递归列表
     * 
     * @param list
     * @param menu
     */
    private static void recursionFn(List<Menu> list, Menu menu) {
        // 得到子节点列表
        List<Menu> childList = getChildList(list, menu);
        menu.setChildren(childList);

        for (Menu tChild : childList) {
            if (hasChild(list, tChild)) {
                // 判断是否有子节点
                Iterator<Menu> it = childList.iterator();
                while (it.hasNext()) {
                    Menu n = it.next();
                    recursionFn(list, n);
                }
            }
        }
    }

    /**
     * 如果遍历所有菜单，如果有菜单parentId等于当前菜单ID，即当前菜单找到子菜单
     * @param list 所有菜单列表
     * @param currentMenu 当前菜单
     * @return 得到子节点列表
     */
    private static List<Menu> getChildList(List<Menu> list, Menu currentMenu) {
        List<Menu> childList = new ArrayList<>();

        Iterator<Menu> it = list.iterator();
        while (it.hasNext()) {
            Menu menu = it.next();
            if (menu.getParentId().longValue() == currentMenu.getId().longValue()) {
                childList.add(menu);
            }
        }

        return childList;
    }

//    List<Menu> returnList = new ArrayList<Menu>();
//
//    /**
//     * 根据父节点的ID获取所有子节点
//     *
//     * @param list 分类表
//     * @param typeId 传入的父节点ID
//     * @param prefix 子节点前缀
//     */
//    public List<Menu> getChildPerms(List<Menu> list, int typeId, String prefix) {
//        if (list == null) {
//            return null;
//        }
//
//        for (Iterator<Menu> iterator = list.iterator(); iterator.hasNext();) {
//            Menu node = (Menu) iterator.next();
//            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
//            if (node.getParentId() == typeId) {
//                recursionFn(list, node, prefix);
//            }
//            // 二、遍历所有的父节点下的所有子节点
//            /*
//             * if (node.getParentId()==0) { recursionFn(list, node); }
//             */
//        }
//
//        return returnList;
//    }
//
//    private void recursionFn(List<Menu> list, Menu node, String p) {
//        // 得到子节点列表
//        List<Menu> childList = getChildList(list, node);
//        if (hasChild(list, node)) {
//            // 判断是否有子节点
//            returnList.add(node);
//            Iterator<Menu> it = childList.iterator();
//            while (it.hasNext()) {
//                Menu n = (Menu) it.next();
//                n.setMenuName(p + n.getMenuName());
//                recursionFn(list, n, p + p);
//            }
//        } else {
//            returnList.add(node);
//        }
//    }

    /**
     * 判断是否有子节点
     */
    private static boolean hasChild(List<Menu> list, Menu t) {
        return getChildList(list, t).size() > 0 ? true : false;
    }

}
