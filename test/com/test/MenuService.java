package com.test;


import java.util.List;

public interface MenuService {
    /**
     * 查询所有菜单
     * @return
     */
    public List<Menu> getAllMenuList();
    /**
     * @nanme 添加
     * @param 参数名
     * @return 返回值
     * @throws Exception
     */
    void addMenu(Menu menu);
}
