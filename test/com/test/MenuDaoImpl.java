package com.test;

import org.springframework.stereotype.Repository;

import com.liaoliao.basedao.BaseDaoImpl;

import java.util.List;

@Repository("menuDao")
public class MenuDaoImpl extends BaseDaoImpl<Menu,Long> implements MenuDao {


    /**
     * 查询所有菜单
     *
     * @return
     */
    public List<Menu> getAllMenuList() {
        List<Menu> menuList = getListByHQL("from Menu");
        return menuList;
    }

    /**
     * @param menu
     * @return 返回值
     * @throws Exception
     * @nanme 添加
     */
    @Override
    public void addMenu(Menu menu) {
         save(menu);
    }
}
