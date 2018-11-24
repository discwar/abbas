package com.major.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>Title: Module Information         </p>
 * <p>Description: Function Description </p>
 * <p>Copyright: Copyright (c) 2018  </p>
 * <p>Company: AG Co., Ltd.             </p>
 * <p>Create Time: 2018/7/18 19:32      </p>
 *
 * @author LianGuoQing
 * <p>Update Time:                      </p>
 * <p>Updater:                          </p>
 * <p>Update Comments:                  </p>
 */

@ToString
@TableName("menu")
public class Menu extends Model<Menu> implements Comparable<Menu>{

    @TableId(value="id", type= IdType.AUTO)
    /** 菜单ID */
    private Long id;
    /** 菜单名称 */
    private String menuName;
    /** 父菜单ID */
    private Long parentId;
    /** 菜单排序 */
    private Integer menuSort;
    /**
     * 类型：M-目录；C-菜单；F-按钮
     */
    private String menuType;
    /**
     * 菜单状态：0-显示；1-隐藏
     */
    private String visible;
    /** 菜单URL */
    private String url;
    /** 权限字符串 */
    private String perms;
    /** 创建时间 */
    private Date createTime;
    private Date updateTime;
    /** 菜单图标 */
    private String icon;
    /** 子菜单 */


    private List<Menu> children = new ArrayList<>();

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
    //排序
    @Override
    public int compareTo(Menu s) {
                if(this.getMenuSort() >= s.getMenuSort()){
                        return 1;
                    }
               return -1;
            }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getMenuSort() {
        return menuSort;
    }

    public void setMenuSort(Integer menuSort) {
        this.menuSort = menuSort;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }


    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    public String getVisible() {
        return visible;
    }

    public void setVisible(String visible) {
        this.visible = visible;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
