package com.test;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("menuService")
public class MenuServiceImpl implements MenuService {
    @Resource(name = "menuDao")
    private MenuDao menuDao;
    /**
     * 查询所有菜单
     *
     * @return
     */
    public List<Menu> getAllMenuList() {
        return menuDao.getAllMenuList();
    }
    /**
     * @nanme 添加
     * @param menu
     * @return 返回值
     * @throws Exception
     */
    public void addMenu(Menu menu){
        menuDao.addMenu(menu);
    }
}
