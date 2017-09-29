package com.liaoliao.sys.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liaoliao.redisclient.RedisService;
import com.liaoliao.sys.entity.AboutUs;
import com.liaoliao.sys.entity.SystemConfig;
import com.liaoliao.sys.entity.SystemMessage;
import com.liaoliao.sys.service.AboutUsService;
import com.liaoliao.sys.service.SystemConfigService;
import com.liaoliao.sys.service.SystemMessageService;
import com.liaoliao.user.service.UserService;
import com.liaoliao.util.JPushUtil;



@Controller
@RequestMapping("/sys")
public class SystemConfigController {
	
	@Autowired
	private SystemConfigService systemConfigService;
	
	@Autowired
	private SystemMessageService systemMessageService;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private AboutUsService aboutUsService;
	 
	private Integer page=1;
	
	private Integer pageSize = 10;
	 

	
	
	/**
	 * 获取列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/systemConfigList")
	public String systemConfigList(HttpServletRequest request,Integer pageNo){
		if(pageNo==null){
			pageNo=page;
		}else{
			pageNo=pageNo>1?pageNo:page;
		}
		Integer count = null;
		count=systemConfigService.findCount();
		if(count==null){
			request.setAttribute("count",0);
			request.getSession().setAttribute("count", 0);
		}else{
			request.setAttribute("count", (int)(Math.ceil((double)count/10)));
			request.getSession().setAttribute("count", (int)(Math.ceil((double)count/10)));
		}
		request.setAttribute("url", "/sys/systemConfigList");	
		request.setAttribute("pageNo", pageNo);	
//		request.setAttribute("condition", "&aaa=???&bbb=???");	//配置额外的参数
		
		List<SystemConfig> list=systemConfigService.findAll(pageNo);
		request.setAttribute("list", list);
		return "sys/systemConfigList";
	}
	
	
	
	/**
	 * 异步获取
	 * @param id
	 * @return
	 */
	@RequestMapping("/findEntityByKey")
	@ResponseBody
	public Map<String,Object> findEntityByKey(String keyName){
		Map<String,Object> map = new HashMap<String,Object>();
		SystemConfig systemConfig = systemConfigService.findEntityByKey(keyName);
		map.put("keyTitle", systemConfig.getKeyTitle());
		map.put("keyName", systemConfig.getKeyName());
		map.put("content", systemConfig.getContent());
		map.put("sort", systemConfig.getSort());
		return map;
	}
	
	
	/**
	 * ajax异步修改
	 * @param request
	 * @param id
	 * @param type
	 * @param content
	 * @param position
	 * @return
	 */
	@RequestMapping("/modifySystemConfig")
	public String modifySystemConfig(HttpServletRequest request,String keyName,String keyTitle,String content,Integer sort){
		if(StringUtils.isBlank(keyName)||StringUtils.isBlank(content)){
			return "error";
		}
		SystemConfig systemConfig = systemConfigService.findEntityByKey(keyName);
		systemConfig.setKeyName(keyName);
		systemConfig.setKeyTitle(keyTitle);
		systemConfig.setContent(content);
		systemConfig.setSort(sort);
//		更新数据库数据
		systemConfigService.updateSystemConfig(systemConfig);
//		更新redis数据
		redisService.set(keyName, content, -1);
		return "forward:/sys/systemConfigList";
	}

	
	    
	    /**
	     * 进入发布系统消息页面
	     */
	    @RequestMapping("/sendMsgPage")
	    public String sendMsgPage(){
	    	return "sys/sendMsgPage";
	    }
	
	    
	    /**
	     * 发送系统消息
	     * @param title
	     * @param message
	     * @return
	     */
	    @RequestMapping("/sendMessage")
	    @ResponseBody
	    public Map<String,Object> sendMessage(String title ,String  content){
	    	Map<String,Object> map = new HashMap<>();
	    	SystemMessage sm=new SystemMessage();
	    	sm.setContent(content);
	    	sm.setTitle(title);
	    	sm.setAddTime(new Date());
	    	systemMessageService.saveMsg(sm);
	    	JPushUtil.sendAllsetNotification(content);
	    	map.put("data", 1);
	    	return map;
	    }
	    
	    
	    /**
	     * 关于我们
	     * @return
	     */
	   @RequestMapping("/aboutUs")
	   public String aboutUsList(HttpServletRequest request,Integer pageNo){
			if(pageNo==null){
				pageNo=page;
			}else{
				pageNo=pageNo>1?pageNo:page;
			}
			Long count = null;
			count=aboutUsService.findCount();
			if(count==null){
				request.setAttribute("count",0);
				request.getSession().setAttribute("count", 0);
			}else{
				request.setAttribute("count", (int)(Math.ceil((double)count/pageSize)));
				request.getSession().setAttribute("count", (int)(Math.ceil((double)count/pageSize)));
			}
			request.setAttribute("url", "/sys/aboutUs");
			request.setAttribute("pageNo", pageNo);
//			request.setAttribute("condition", "");
		   List<AboutUs> list = aboutUsService.findAll(pageNo,pageSize);
		   request.setAttribute("list",list);
		   return "sys/aboutUs";
	   }
	   
	   
	    /**
	     * 关于我们-ajax修改界面
	     * @return
	     */
	   @RequestMapping("/findContentById")
	   @ResponseBody
	   public Map<String,Object> findContentById(Integer id){
		   Map<String,Object> map = new HashMap<>();
		   AboutUs au = aboutUsService.findContentById(id);
		   map.put("au", au);
		   return map;
	   }
	   
	   
	   /**
	    * 修改 常见问题
	    * @return
	    */
	   @RequestMapping("/modifyAboutUs")
	   public String modifyAboutUs(HttpServletRequest request,Integer id,String content,String title,Integer type,Integer sort){
		   AboutUs au = aboutUsService.findContentById(id);
		   if(content!=null && !("".equals(content))){
			   au.setContent(content);
		   }
		   if(title!=null && !("".equals(title))){
			   au.setTitle(title);
		   }
		   if(type!=null && !("".equals(type))){
			   au.setType(type);
		   }
		   if(sort!=null){
			   au.setSort(sort);
		   }
		   aboutUsService.updateAboutUs(au);
		   return "forward:/sys/aboutUs";
	   }
	   
	   /**
	    * 添加 常见问题
	    * @return
	    */
	   @RequestMapping("/addAboutUs")
	   public String addAboutUs(HttpServletRequest request,String content,String title,Integer type,Integer sort){
		   AboutUs au = new AboutUs();
		   au.setContent(content);
		   au.setTitle(title);
		   au.setType(type);
		   au.setSort(sort);
		   aboutUsService.saveAboutUs(au);
		   return "forward:/sys/aboutUs";  
	   }
	    
	   
	   
	   /**
	    * 异步删除aboutUs
	    * @param request
	    * @param id
	    * @return
	    */
	   @RequestMapping("/deleteAboutUs")
	   @ResponseBody
	   public Map<String,Object> deleteAboutUs(HttpServletRequest request,Integer id){
		   Map<String,Object>  map  = new HashMap<>();
		   AboutUs au = aboutUsService.findContentById(id);
		   aboutUsService.deleteAboutUs(au);
		   return map;
	   }
	    
}























