package com.liaoliao.admin.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import com.liaoliao.admin.entity.AdminGroup;
import com.liaoliao.admin.entity.AdminUser;
import com.liaoliao.admin.entity.Navigation;
import com.liaoliao.admin.entity.Permission;
import com.liaoliao.admin.service.AdminGroupService;
import com.liaoliao.admin.service.AdminUserService;
import com.liaoliao.admin.service.NavigationService;
import com.liaoliao.admin.service.PermissionService;
import com.liaoliao.util.CommonUtil;
import com.liaoliao.util.StaticKey;
import com.liaoliao.util.TimeKit;



@Controller("adminLogin")
@RequestMapping("/sys")
public class AdminController {
	
	@Autowired
	private AdminUserService adminUserService;
	
	@Autowired
	private AdminGroupService adminGroupService;
	
	@Autowired
	private PermissionService permissionService;
	
	@Autowired
	private NavigationService navigationService;
	
	private Integer page = 1;
	
	/**
	 * 跳转到登录页面
	 * @param request  153.85 
	 * @return
	 */
	@RequestMapping(value="/toLogin")
	public String toLogin(HttpServletRequest request){
		return "login";
	}
	
	/**
	 * 主界面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/theHome")
	public String theHome(HttpServletRequest request){
		return "index";
	}
	//用户退出
   @RequestMapping("/logout")
    public String logout(HttpSession session){
        AdminUser adminUser = (AdminUser)session.getAttribute("adminUser");
        if(adminUser==null){
        	return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/sys/toLogin";
        }
        System.out.println("用户[" + adminUser.getUserName() + "]准备登出");
        SecurityUtils.getSubject().logout();
        System.out.println("用户[" + adminUser.getUserName() + "]已登出");
        return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/sys/toLogin";
    }
	/**
	 * 验证登录：
	 * @param request
	 * @param response
	 * @param userName
	 * @param passCode
	 * @return
	 */
	@RequestMapping(value="/Login")
	public String Login(HttpServletRequest request,HttpServletResponse response,String userName,String passWord){
		 	UsernamePasswordToken token=null;
		 	 AdminUser nowAu=null;
	        UsernamePasswordToken requestToken=(UsernamePasswordToken)request.getSession().getAttribute("token");
	        if(requestToken != null && requestToken.getUsername().equals(userName)){
	        	nowAu=adminUserService.findByUserName(userName);
	    		AdminUser sessionAu= (AdminUser) request.getSession().getAttribute("adminUser");
	    		if(nowAu == null && sessionAu == null){
	    			return InternalResourceViewResolver.FORWARD_URL_PREFIX +"/sys/toLogin";
	    		}
	    		AdminUser adminUser = null;
	    		if(nowAu==null){
	    			adminUser=sessionAu;
	    		}else{
	    			adminUser=nowAu;
	    		}
//	    		request.setAttribute("adminUser", adminUser);
	    		HttpSession session = request.getSession();
	    		session.setAttribute("adminUser", adminUser);
	    		session.setAttribute("token", token);
	    		List<Permission> pList=permissionService.findByGroupId(adminUser.getAdminGroup().getId());
//	    		request.setAttribute("list", pList);
	    		session.setAttribute("list", pList);
	    		return "redirect:/sys/theHome";
	        }
	        passWord = CommonUtil.sha1(userName+passWord);
	        if(requestToken==null||!(requestToken.getUsername()).equals(userName)){
	        	  token = new UsernamePasswordToken(userName, passWord);
	        }else{
	        	token=requestToken;
	        }
	        token.setRememberMe(true);
	        Subject currentUser = SecurityUtils.getSubject();
	        try {
	            currentUser.login(token);
	        }catch(Exception e){
//	        	e.printStackTrace();
	        	System.out.println("用户名或密码不正确");
	            request.setAttribute("message_login", "用户名或密码不正确");
	            return InternalResourceViewResolver.FORWARD_URL_PREFIX +"/sys/toLogin";
	        }    
	       
	        //验证是否登录成功
	        if(currentUser.isAuthenticated()){
	    		nowAu=adminUserService.findByUserName(userName);
	    		AdminUser sessionAu= (AdminUser) request.getSession().getAttribute("adminUser");
	    		if(nowAu == null && sessionAu == null){
	    			return InternalResourceViewResolver.FORWARD_URL_PREFIX +"/sys/toLogin";
	    		}
	    		AdminUser adminUser = null;
	    		if(nowAu==null){
	    			adminUser=sessionAu;
	    		}else{
	    			adminUser=nowAu;
	    		}
//	    		request.setAttribute("adminUser", adminUser);
	    		HttpSession session = request.getSession();
	    		session.setAttribute("adminUser", adminUser);
	    		session.setAttribute("token", token);
	    		List<Permission> pList=permissionService.findByGroupId(adminUser.getAdminGroup().getId());
//	    		request.setAttribute("list", pList);	
	    		session.setAttribute("list", pList);
	    		System.out.println("用户[" + adminUser.getUserName() + "]已登录  "+TimeKit.dateToStr(new Date()));
	    		return "redirect:/sys/theHome";
	        }else{
	        	System.out.println("未通过！");
	            token.clear();
	            return InternalResourceViewResolver.REDIRECT_URL_PREFIX +"/sys/toLogin";
	        }
	}

	
	/**
	 * 获取导航
	 * @param request
	 * @param groupId
	 * @return
	 */
	//@RequestMapping("/navigation")
	/*public String Navigation(HttpServletRequest request,Integer groupId){
		List<Permission> pList=permissionService.findByGroupId(groupId);
		request.setAttribute("list", pList);	
		return "index";
	}
	*/
	
	/**
	 * 跳转到admin页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/toAdminPage")
	public String toAdminPage(HttpServletRequest request,Integer pageNo){
		if(pageNo==null){
			pageNo=page;
		}else{
			pageNo=pageNo>1?pageNo:page;
		}
		Integer count = null;
		count=adminUserService.findCount();
		if(count==null){
			request.setAttribute("count",0);
			request.getSession().setAttribute("count", 0);
		}else{
			request.setAttribute("count", (int)(Math.ceil((double)count/10)));
			request.getSession().setAttribute("count", (int)(Math.ceil((double)count/10)));
		}
		request.setAttribute("url", "/sys/toAdminPage");
		request.setAttribute("pageNo", pageNo);	
		
		List<AdminUser> auList=adminUserService.findAll(pageNo);
		List<AdminGroup> agList=adminGroupService.findAll();
		request.setAttribute("list", auList);
		request.setAttribute("agList", agList);
		return "admin/adminUser";
	}
	
	
	
	/**
	 * 添加管理员用户
	 * @return
	 */
	@RequestMapping(value="/addAdminUser")
	public String addAdminUser(Integer groupId,String userName,String passWord){
		System.out.println(groupId+","+userName+","+passWord);
		if(groupId==null||"".equals(groupId)||StringUtils.isBlank(userName)||StringUtils.isBlank(passWord)){
			return "/sys/theHome";
		}
		AdminGroup ag=adminGroupService.findById(groupId);
		if(ag==null){
			return "/sys/theHome";
		}
		String pw=CommonUtil.sha1(userName+passWord);
		AdminUser au = new AdminUser();
		au.setAddTime(new Date());
		au.setAdminGroup(ag);
		au.setPassWord(pw);
		au.setStatus(1);
		au.setUserName(userName);
		adminUserService.saveAdminUser(au);
		return "forward:/sys/toAdminPage";
	}
	
	
	/**
	 * 修改管理员用户ajax请求
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/getAdminUser")
	public Map<String,Object> getAdminUser(Integer id){
		System.out.println(11);
		Map<String,Object> map = new HashMap<>();
		if(id==null||"".equals(id)){
			map.put("msg", 0);
			return map;
		}
		AdminUser au = adminUserService.findById(id);
		map.put("id", au.getId());
		map.put("userName", au.getUserName());
		map.put("groupName", au.getAdminGroup().getGroupName());
		map.put("groupId", au.getAdminGroup().getId());
		map.put("msg", 1);
		return map;
	}
	
	
	@RequestMapping(value="/modAdminUser")
	public String modAdminUser(Integer groupId,String userName,String passWord,Integer userId){
		System.out.println(groupId+","+userName+","+passWord+","+userId);
		if(StringUtils.isBlank(passWord)){
			return "/sys/theHome";
		}
		AdminUser au = adminUserService.findById(userId);
		au.setUserName(userName);
		au.setAdminGroup(adminGroupService.findById(groupId));
		au.setPassWord(CommonUtil.sha1(userName+passWord));
		adminUserService.updateAdminUser(au);
		return "forward:/sys/toAdminPage";
	}
	
	
/*	
	*//**
	 * 用户组管理
	 * @param request
	 * @param groupName
	 * @param info
	 * @return
	 *//*
	@RequestMapping("/addGroup")
	public String addGroup(HttpServletRequest request,String groupName,String info){
		AdminGroup ag=new AdminGroup();
		ag.setGroupName(groupName);
		ag.setInfo(info);
		ag.setAddTime(new Date());
		ag.setStatus(StaticKey.AdminGroupStatusTrue);
		adminGroupService.saveAdminGroup(ag);
		List<AdminGroup> groupList=adminGroupService.findAll();
		request.setAttribute("groupList", groupList);
		return "admin/adminGroupList";
	}
	*/
	
	/**
	 * 权限列表:
	 * @return
	 */
	@RequestMapping("/permissionList")
	public String permissionList(HttpServletRequest request){
		List<Navigation> list= navigationService.findParent();
		request.setAttribute("list", list);	
		return "permission/permissionList";
	}
	
	/**
	 * 跳转到添加权限页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toAddPermission")
	public String toAddPermission(HttpServletRequest request){
		List<Navigation> list= navigationService.findParent();
		request.setAttribute("list", list);	
		return "permission/addPermission";
	}
	
	/**
	 * ajax方法
	 * 通过上级id查找子权限
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/childNavigation")
	@ResponseBody
	public Map<String,Object> childNavigation(HttpServletRequest request,Integer id){
		Map<String,Object> map=new HashMap<String,Object>();
		List<Navigation> list=navigationService.findByParentId(id);
		// Map-->List-->Map 三层转换(保留)
			List<Map<String, Object>> datas = new ArrayList<>();
			Map<String, Object> item = null;
			for(Navigation navigation : list){
				item = new LinkedHashMap<>();
				item.put("id", navigation.getId());
				item.put("navigationName", navigation.getNavigationName());
				item.put("navigationUrl", navigation.getNavigationUrl());
				item.put("parentName", navigation.getNavigation().getNavigationName());
				item.put("sort", navigation.getSort());
				datas.add(item);
			}
		 map.put("list", datas);
		return map;
	}
	
	/**
	 * 添加权限
	 * @param request
	 * @param parentId
	 * @param navigationName
	 * @param navigationUrl
	 * @return
	 */
	@RequestMapping("/addPermission")
	public String addPermission(HttpServletRequest request,Integer parentId,String navigationName,String navigationUrl){
		Integer count=navigationService.findCount();
		if(parentId==null||"".equals(parentId)){
			Navigation na=new Navigation();
			na.setNavigationName(navigationName);
			na.setSort(count+1);
			navigationService.saveNa(na);
		}else{
			Navigation parent=navigationService.findById(parentId);
			Navigation na=new Navigation();
			na.setNavigationName(navigationName);
			na.setNavigation(parent);
			na.setNavigationUrl(navigationUrl);
			na.setSort(count+1);
			navigationService.saveNa(na);
		}
		return "forward:/sys/permissionList";
	}
	
	
	/**
	 * 修改权限管理
	 * @param request
	 * @param nid
	 * @param parentId
	 * @param navigationName
	 * @param navigationUrl
	 * @return
	 */
	@RequestMapping("/modifyNavigation")
	public String modifyNavigation(HttpServletRequest request,Integer nid,Integer parentId,String navigationName,String navigationUrl){
		if(nid==null){
			return "400";//错误页面
		}
		Navigation navigation=navigationService.findById(nid);
		Navigation parentNav=null;
		if(parentId!=null){
		parentNav=navigationService.findById(parentId);
		}
		if(parentNav==null){
			navigation.setNavigation(null);
		}
		if(parentNav!=null){
			navigation.setNavigation(parentNav);
		}
		navigation.setNavigationName(navigationName);
		navigation.setNavigationUrl(navigationUrl);
		navigationService.updataNavigation(navigation);
		
		
		return "forward:/sys/permissionList";
	}
	
	
	
	/**
	 * 用户组权限配置:
	 * @return
	 */
	@RequestMapping("/groupPermission")
	public String groupPermission(HttpServletRequest request){
		List<AdminGroup> list=adminGroupService.findAll();
		request.setAttribute("list", list);
		return "permission/groupPermission";	
	}

	
	/**
	 * ajax删除导航
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteNavigation")
	@ResponseBody
	public Map<String,Object> deleteNavigation(Integer id){
		Map<String,Object> map=new HashMap<String,Object>();
		List<Navigation> nList=navigationService.findByParentId(id);
		Navigation navigation=navigationService.findById(id);
		if(nList!=null){
			for(int i=0;i<nList.size();i++){
				List<Permission> pList=permissionService.findByNavigationId(nList.get(i).getId());
				if(pList!=null){
					for(Permission p:pList){
						permissionService.delPermission(p);
					}
				}
				Navigation na=navigationService.findById(nList.get(i).getId());
				navigationService.deleteNavigation(na);
			}
		}
		navigationService.deleteNavigation(navigation);
		
		map.put("msg", 1);
		return map;
	}

	
	/**
	 * 进入修改用户权限
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/changePermission")
	public String changePermission(HttpServletRequest request,Integer id){
		List<Permission> pList=permissionService.findByGroupId(id);
		List<Navigation> nList=navigationService.findAll();
		/*for(int i=0;i<nList.size();i++){
			for(int j=0;j<pList.size();j++){
				if(nList.get(i).getId()==pList.get(j).getNavigation().getId()){
					nList.remove(i);
					i=0;
				}
			}
		}*/
		request.setAttribute("pList", pList);
		request.setAttribute("nList", nList);
		request.setAttribute("groupId", id);
		return  "permission/changePermission";
	}
	
	
	/**
	 * 添加新的用户组 
	 * @param request
	 * @param groupName
	 * @param info
	 * @return
	 */
	@RequestMapping("addAdminGroup")
	public String addAdminGroup(HttpServletRequest request,String groupName,String info){
		System.out.println(groupName+","+info);
		AdminGroup ag = new AdminGroup();
		ag.setAddTime(new Date());
		ag.setGroupName(groupName);
		ag.setInfo(info);
		ag.setStatus(StaticKey.AdminGroupStatusTrue);
		adminGroupService.saveAdminGroup(ag);
		return "forward:/sys/groupPermission";
	}
	
	
	/**
	 * 提交修改之后的权限
	 * @param request
	 * @param selVal
	 * @return
	 */
	@RequestMapping("updateGroupPermission")
	public String updateGroupPermission(HttpServletRequest request,String[] selVal,Integer groupId){
		List group=new ArrayList();
		List permission=new ArrayList();
		for(int i=0;i<selVal.length;i++){
			String[] str=selVal[i].split(" ");//第一个值是上级id,第二个值是子权限id
			int val=Integer.valueOf(str[0]);//获取上级id
			boolean flag=true;
			for(int a=0;i<group.size();a++){
				int groupVal=Integer.valueOf(group.get(a).toString());
				if(groupVal==val){
					flag=false;
					}
			  }
			if(flag){
			  group.add(val);
			}
			permission.add(Integer.valueOf(str[1]));
		}
		
		//整理group
		List<Integer> listTemp= new ArrayList<Integer>();  
		 Iterator<Integer> it=group.iterator();  
		 while(it.hasNext()){  
		  int a=it.next();  
		  if(listTemp.contains(a)){  
		   it.remove();  
		  }  
		  else{  
		   listTemp.add(a);  
		  }  
		 }  
		
		AdminGroup ag=adminGroupService.findById(groupId);
		List<Permission> perList=permissionService.findByGroupId(groupId);
		for(Permission p:perList){//删除所有权限再重新添加
			permissionService.delPermission(p);
		}
		//添加上级菜单到权限表
		Permission per=null;
		for(int i=0;i<listTemp.size();i++){
			Navigation na=navigationService.findById(Integer.valueOf(listTemp.get(i).toString()));
			per=new Permission();
			per.setAdminGroup(ag);
			per.setNavigation(na);
			permissionService.savePermission(per);
		}
		//添加下级菜单到权限表
		for(int i=0;i<permission.size();i++){
			Navigation na=navigationService.findById(Integer.valueOf(permission.get(i).toString()));
			per=new Permission();
			per.setAdminGroup(ag);
			per.setNavigation(na);
			permissionService.savePermission(per);
		}
		
		return "forward:/sys/groupPermission";	
	}
	
	
	
	/**
	 * ajax根据id获取菜单内容
	 * @return
	 */
	@RequestMapping("/getNavigation")
	@ResponseBody
	public Map<String,Object> getNavigation(HttpServletRequest request,Integer id){
		Map<String,Object> map=new HashMap<>();
		Navigation navitaion=navigationService.findById(id);
		map.put("id", navitaion.getId());
		map.put("navigationName", navitaion.getNavigationName());
		map.put("navigationUrl", navitaion.getNavigationUrl());
		if(navitaion.getNavigation()!=null){
			map.put("parentName", navitaion.getNavigation().getNavigationName());
			map.put("parentId", navitaion.getNavigation().getId());
		}else{
			map.put("parentName", "");
			map.put("parentId", "");
		}
		Integer count =navigationService.findParenCount();
		map.put("count", count);
		return map;
	}
	
	/**
	 * 上传头像
	 * @return
	 */
	@RequestMapping("/toUploadPage")
	public  String toUploadPage(){
		return "admin/upload";
	}
	
	
	
}






















