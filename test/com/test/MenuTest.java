package com.test;

import java.sql.SQLException;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml","classpath:springMVC.xml"})
public class MenuTest extends AbstractJUnit4SpringContextTests {
    @Resource(name = "menuService")
    private MenuService menuService;
    @Test
    public void  addMenu(){
        Menu menu = new Menu();
        menu.setParentId(1);
        menu.setMenuName("测试1");
        menu.setMenuDesc(1);
        menu.setMenuUrl("111");
        menuService.addMenu(menu);
    }
    
    private ApplicationContext ctx = null;
    
	@Test
	public void testDataSource() throws SQLException {
		// 检查spring配置
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		System.out.println(ctx);

		// 检查数据库连接
		DataSource dataSource = ctx.getBean(DataSource.class);
		System.out.println(dataSource.getConnection().toString());

		// 检查hibernate配置
		SessionFactory sessionFactory = ctx.getBean(SessionFactory.class);
		System.out.println(sessionFactory);

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		// 测试数据库
		Menu menu = new Menu();
        menu.setParentId(1);
        menu.setMenuName("测试2");
        menu.setMenuDesc(1);
        menu.setMenuUrl("222");
		session.save(menu);
		tx.commit();
		session.close();
	}
}
