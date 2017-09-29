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
import com.liaoliao.sys.service.AdvertService;
import com.liaoliao.util.StaticKey;



@Controller
@RequestMapping("/sys")
public class AdvertController {
	
	
	@Autowired
	private AdvertService advertService;
	
	private Integer page = 1;
	
	
	/**
	 * 获取广告列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/advertList")
	public String advertList(HttpServletRequest request,Integer pageNo){
		if(pageNo==null){
			pageNo=page;
		}else{
			pageNo=pageNo>1?pageNo:page;
		}
		Integer count = null;
		count=advertService.findCount();
		
		if(count==null){
			request.setAttribute("count",0);
			request.getSession().setAttribute("count", 0);
		}else{
			request.setAttribute("count", (int)(Math.ceil((double)count/5)));
			request.getSession().setAttribute("count", (int)(Math.ceil((double)count/5)));
		}
		request.setAttribute("url", "/sys/advertList");
		request.setAttribute("pageNo", pageNo);	
		
		List<Advert> list=advertService.findAll(pageNo);
		request.setAttribute("list", list);
		return "advert/advertList";
	}
	
	
	/**
	 * 添加新广告
	 * @param request
	 * @param type
	 * @param content
	 * @param position
	 * @return
	 */
	@RequestMapping("/addAdvert")
	public String addAdvert(HttpServletRequest request,String sourceName,Integer type,Integer sort,String content,String position){
		if(StringUtils.isBlank(sourceName)||StringUtils.isBlank(position)||type==null){
			return "error";
		}
		Advert ad = new Advert();
		ad.setSourceName(sourceName);
		ad.setType(type);
		ad.setSort(sort);
		ad.setContent(content);
		ad.setPosition(position);
		ad.setStatus(StaticKey.AdvertStatusTrue);
		ad.setAddTime(new Date());
		advertService.saveAdvert(ad);
	   return "forward:/sys/advertList";
	}
	
	/**
	 * 异步获取广告信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/getAdvert")
	@ResponseBody
	public Map<String,Object> getAdvert(Integer id){
		Map<String,Object> map = new HashMap<String,Object>();
		Advert ad = advertService.findById(id);
		map.put("id", ad.getId());
		map.put("sourceName", ad.getSourceName());
		map.put("type", ad.getType());
		map.put("sort", ad.getSort());
		map.put("content", ad.getContent());
		map.put("position", ad.getPosition());
		map.put("status", ad.getStatus());
		return map;
	}
	
	
	/**
	 * ajax异步修改广告信息
	 * @param request
	 * @param id
	 * @param type
	 * @param content
	 * @param position
	 * @return
	 */
	@RequestMapping("/modifyAdvert")
	public String modifyAdvert(HttpServletRequest request,Integer id,String sourceName,Integer type,Integer sort,String content,String position){
		if(StringUtils.isBlank(position)||StringUtils.isBlank(content)||type==null||id==null){
			return "error";
		}
		Advert ad = advertService.findById(id);
		ad.setSourceName(sourceName);
		ad.setType(type);
		ad.setSort(sort);
		ad.setContent(content);
		ad.setPosition(position);
		ad.setAddTime(new Date());
		advertService.updateAdvert(ad);
		return "forward:/sys/advertList";
	}
	
	/**
	 * 封禁广告
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/banAdvert")
	@ResponseBody
	public Map<String,Object> banAdvert(HttpServletRequest request,Integer id){
		Map<String,Object> map = new HashMap<String,Object>();
		Advert ad = advertService.findById(id);
		ad.setStatus(StaticKey.AdvertStatusFalse);
		advertService.updateAdvert(ad);
		return map;	
	}
	
	
	/**
	 * 解封广告
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/openAdvert")
	@ResponseBody
	public Map<String,Object> openAdvert(HttpServletRequest request,Integer id){
		Map<String,Object> map = new HashMap<String,Object>();
		Advert ad = advertService.findById(id);
		ad.setStatus(StaticKey.AdvertStatusTrue);
		advertService.updateAdvert(ad);
		return map;	
	}
	
	
	
	/**
	 * 删除广告
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteAdvert")
	@ResponseBody
	public Map<String,Object> deleteAdvert(Integer id){
		Map<String,Object> map = new HashMap<String,Object>();
		Advert ad = advertService.findById(id);
		advertService.delAdvert(ad);
		return map;	
	}
	
}






















