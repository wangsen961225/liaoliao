package com.liaoliao.sys.controller;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liaoliao.sys.entity.Advert;
import com.liaoliao.sys.entity.AppVersion;
import com.liaoliao.sys.service.AppVersionService;
import com.liaoliao.util.StaticKey;



@Controller
@RequestMapping("/sys")
public class AppVersionController {
	
	
	@Autowired
	private AppVersionService appVersionService;
	
	private Integer page = 1;
	
	
	
	/**
	 * 获取列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/appVersionList")
	public String appVersionList(HttpServletRequest request,Integer pageNo){
		if(pageNo==null){
			pageNo=page;
		}else{
			pageNo=pageNo>1?pageNo:page;
		}
		Integer count = null;
		count=appVersionService.findCount();
		if(count==null){
			request.setAttribute("count",0);
			request.getSession().setAttribute("count", 0);
		}else{
			request.setAttribute("count", (int)(Math.ceil((double)count/5)));
			request.getSession().setAttribute("count", (int)(Math.ceil((double)count/5)));
		}
		request.setAttribute("url", "/sys/appVersionList");	
		request.setAttribute("pageNo", pageNo);	
//		request.setAttribute("condition", "&aaa=???&bbb=???");	//配置额外的参数
		
		
		List<AppVersion> list=appVersionService.findAll(pageNo);
		request.setAttribute("list", list);
		return "sys/appVersionList";
	}
	
	
	/**
	 * 添加
	 * @param request
	 * @return
	 */
	@RequestMapping("/addAppVersion")
	public String addAppVersion(HttpServletRequest request,Integer versionCode, String versionName,String updateInfo,String downloadUrl,Integer type){
		if(StringUtils.isBlank(versionName)||StringUtils.isBlank(updateInfo)||versionCode==null){
			return "error";
		}
		AppVersion appVersion = new AppVersion();
		appVersion.setVersionCode(versionCode);
		appVersion.setVersionName(versionName);
		appVersion.setUpdateInfo(updateInfo);
		appVersion.setDownloadUrl(downloadUrl);
		appVersion.setType(type);
		appVersion.setAddTime(new Date());
		appVersionService.saveAppVersion(appVersion);
	   return "forward:/sys/appVersionList";
	}
	
	/**
	 * 异步获取
	 * @param id
	 * @return
	 */
	@RequestMapping("/getAppVersion")
	@ResponseBody
	public Map<String,Object> getAppVersion(Integer id){
		Map<String,Object> map = new HashMap<String,Object>();
		AppVersion appVersion = appVersionService.findById(id);
		map.put("id", appVersion.getId());
		map.put("versionCode", appVersion.getVersionCode());
		map.put("updateInfo", appVersion.getUpdateInfo());
		map.put("versionName", appVersion.getVersionName());
		map.put("downloadUrl", appVersion.getDownloadUrl());
		map.put("type", appVersion.getType());
		return map;
	}
	
	
	/**
	 * ajax异步修改
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/modifyAppVersion")
	public String modifyAppVersion(HttpServletRequest request,Integer id, Integer versionCode, String versionName,String updateInfo,String downloadUrl,Integer type){
		if(StringUtils.isBlank(versionName)||StringUtils.isBlank(updateInfo)||versionCode==null||id==null){
			return "error";
		}
		AppVersion appVersion = appVersionService.findById(id);
		appVersion.setVersionCode(versionCode);
		appVersion.setVersionName(versionName);
		appVersion.setUpdateInfo(updateInfo);
		appVersion.setDownloadUrl(downloadUrl);
		appVersion.setAddTime(new Date());
		appVersion.setType(type);
		appVersionService.updateAppVersion(appVersion);
		return "forward:/sys/appVersionList";
	}
	

	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteAppVersion")
	@ResponseBody
	public Map<String,Object> deleteAppVersion(Integer id){
		Map<String,Object> map = new HashMap<String,Object>();
		AppVersion appVersion = appVersionService.findById(id);
		appVersionService.delAppVersion(appVersion);
		return map;	
	}
	
}






















