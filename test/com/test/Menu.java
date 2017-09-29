package com.test;

import javax.persistence.*;

@Entity
public class Menu {
    private Integer menuId;
    private String menuName;
    private String menuUrl;
    private Integer parentId;
    private Integer menuDesc;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "menu_id", nullable = false)
    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    @Basic
    @Column(name = "menu_name", nullable = true, length = 30)
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Basic
    @Column(name = "menu_url", nullable = true, length = 30)
    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    @Basic
    @Column(name = "parent_id", nullable = true)
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "menu_desc", nullable = true)
    public Integer getMenuDesc() {
        return menuDesc;
    }

    public void setMenuDesc(Integer menuDesc) {
        this.menuDesc = menuDesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Menu menu = (Menu) o;

        if (menuId != null ? !menuId.equals(menu.menuId) : menu.menuId != null) return false;
        if (menuName != null ? !menuName.equals(menu.menuName) : menu.menuName != null) return false;
        if (menuUrl != null ? !menuUrl.equals(menu.menuUrl) : menu.menuUrl != null) return false;
        if (parentId != null ? !parentId.equals(menu.parentId) : menu.parentId != null) return false;
        if (menuDesc != null ? !menuDesc.equals(menu.menuDesc) : menu.menuDesc != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = menuId != null ? menuId.hashCode() : 0;
        result = 31 * result + (menuName != null ? menuName.hashCode() : 0);
        result = 31 * result + (menuUrl != null ? menuUrl.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (menuDesc != null ? menuDesc.hashCode() : 0);
        return result;
    }
}
