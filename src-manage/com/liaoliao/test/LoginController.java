package com.liaoliao.test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.liaoliao.user.entity.Users;
import com.liaoliao.user.service.UserService;
import com.liaoliao.util.StaticKey;
import com.liaoliao.util.TimeKit;


@Controller
@RequestMapping(value="/test")
public class LoginController {
	private static final Map<String, String> staticMap = new HashMap<String, String>();
	
	static{
		staticMap.put("AdminTest01", "v07b_t4g2");
		staticMap.put("AdminTest02", "2gqn_r4c6");
		staticMap.put("AdminTest03", "w5ee_fzho");
		staticMap.put("AdminTest04", "ag80_phtd");
		staticMap.put("AdminTest05", "zvb2_hn7p");
	}
	
	@Autowired
	private UserService userService;
	
	private Integer page = 1;
	
	
	/**
	 * 跳转到测试登录页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/toLogin")
	public String toTestLogin(HttpServletRequest request){
		return "testPage/testLogin";
	}
	
	/**
	 * 验证登录
	 * @param request
	 * @param passWord
	 * @param userName
	 * @return
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request,String passWord,String userName,Integer pageNo){
		if(StringUtils.isBlank(userName)||StringUtils.isBlank(passWord)){
			return "redirect:/test/toLogin";
		}
		if(staticMap.get(userName)==null){
			return "redirect:/test/toLogin";
		}
		Integer userId=null;
		if(userName.equals("AdminTest01")){
			userId=1;
		}
		if(userName.equals("AdminTest02")){
			userId=2;
		}
		if(userName.equals("AdminTest03")){
			userId=3;
		}
		if(userName.equals("AdminTest04")){
			userId=4;
		}
		if(userName.equals("AdminTest05")){
			userId=5;
		}
		request.getSession().setAttribute("userId", userId);
		request.getSession().setAttribute("userName", userName);
		return InternalResourceViewResolver.REDIRECT_URL_PREFIX +"/test/dateList";
	}
	
	/**
	 * 用户列表界面
	 * @param request
	 * @param pageNo
	 * @return
	 */
/*	@RequestMapping("/dateList")
	public String dateList(HttpServletRequest request,Integer pageNo,Integer standard){
		String userName = (String) request.getSession().getAttribute("userName");
		System.out.println(userName);
		if(userName==null||staticMap.get(userName)==null){
			return InternalResourceViewResolver.REDIRECT_URL_PREFIX +"/test/toLogin";
		}
		Integer userId =(Integer) request.getSession().getAttribute("userId");
		if(standard==null||"".equals(standard)){
			standard=0;
		}
		if(pageNo==null){
			pageNo=page;
		}else{
			pageNo=pageNo>1?pageNo:page;
		}
		Integer total = null;
		
		total=userService.findCountByParentId(userId);
		Integer passTotal = userService.findPassUser(userId);//达标人数
		Integer todayNum = userService.findCountByTime(userId,TimeKit.dayStart(),new Date());//今日邀请人数
		Integer totalNum=total;//总人数
		page(request,total,totalNum,passTotal,todayNum);
		request.setAttribute("standard", standard);
		request.setAttribute("url", "/test/dateList");	
		request.setAttribute("pageNo", pageNo);	
//		request.setAttribute("condition", "");	
		List<Users> list = userService.findByParentId(userId,pageNo,standard);
		request.setAttribute("list", list);	
		request.setAttribute("passTotal", passTotal);	
		request.getSession().setAttribute("userId", userId);
		request.getSession().setAttribute("userName", userName);
		return "testPage/DataPage";
	}
	*/
	/**
	 * 根据时间查询
	 * @param request
	 * @param startTime
	 * @param endTime
	 * @param pageNo
	 * @return
	 */
	@RequestMapping("/dateList")
	public String dateList(HttpServletRequest request,String startTime,String endTime,Integer standard,Integer pageNo){
		Map<String,Object> map = new HashMap<>();
		String userName = (String) request.getSession().getAttribute("userName");
		if(userName==null||staticMap.get(userName)==null){
			return InternalResourceViewResolver.REDIRECT_URL_PREFIX +"/test/toLogin";
		}
		Integer userId = (Integer)request.getSession().getAttribute("userId");
		map.put("userId", userId);
		Date start = null;
		Date end = null;
		if(startTime!=null&&!("".equals(startTime))){
			try {
				 start = TimeKit.StrToDate(startTime);
				 end = TimeKit.StrToDate(endTime);
				 map.put("start", start);
				 map.put("end", end);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			request.setAttribute("time", startTime+" - "+endTime);
		}else{
		    String	stime="2017-8-21";
		    String	dtime="2017-8-21";
		//	request.setAttribute("time", stime+" - "+dtime);
		}
		if(standard==null||"".equals(standard)||standard==0){
			standard=0;
		}
		
		map.put("standard", standard);
		if(pageNo==null){
			pageNo=page;
		}else{
			pageNo=pageNo>1?pageNo:page;
		}
		Integer total=userService.findCountByParentId(userId);//总人数
		Integer count=0;//分页数(达标或者未达标)
		Integer passNum=0;//达标人数
		Integer noPassNum=0;//未达标人数
		Integer exceptionNum=0;//
		String condition="&standard="+standard;
		passNum=userService.findPassUser(userId);
		exceptionNum =userService.findCountException(userId);
	//	noPassNum=total-passNum;//未达标人数；
		passNum=passNum-(int)(passNum*StaticKey.TestRate);//扣除部分达标人数之后的达标人数
		
		noPassNum=userService.findNoPassUser(userId);
		
		total=passNum+noPassNum;//扣除部分达标人数之后的总数
		//end:固定值：总人数；达标人数；未达标人数；	
		request.setAttribute("total",total);
		request.setAttribute("passNum", passNum);
		request.setAttribute("noPassNum", noPassNum);
		request.setAttribute("exceptionNum", exceptionNum);
		
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("standard", standard);
		request.setAttribute("url", "/test/findByTime");
		if(startTime!=null&&!("".equals(startTime))){
			condition+="&startTime="+startTime+"&endTime="+endTime;
		}
		request.setAttribute("condition", condition);
		if(standard==0){//未达标人数，不需要扣量
			List<Users> list = userService.findByNoPass(map,pageNo);
			request.setAttribute("count", (int)(Math.ceil((double)noPassNum/10)));
			request.setAttribute("list", list);	
			return "testPage/DataPage";
		}else if(standard==1){//达标人数，需要扣量
			List<Users> list = userService.findAllPass(map);//取得所有达标的人数
			//passNum;
			List<Users> subList = list.subList(list.size()-passNum, list.size());
			List<Users> showList=null; //现在页面上面的list列表
			if(startTime!=null&&!("".equals(startTime))){
				List<Users> timeList = new ArrayList<>();
				for(Users u:subList){
					if(u.getLoginTime().getTime() >= start.getTime()&&u.getLoginTime().getTime()<=end.getTime()){
						timeList.add(u);
					}
				}
				if(timeList.size()<=10){
					showList = timeList;
				}else if(pageNo*10>timeList.size()){
					showList=timeList.subList((pageNo-1)*10, timeList.size());
				}else{
					showList=subList.subList((pageNo-1)*10,(pageNo-1)*10+10 );
				}
			}else{
				if(subList.size()<=10){
					showList = subList;
				}else if(pageNo*10>subList.size()){
					showList=subList.subList((pageNo-1)*10, subList.size());
				}else{
					showList=subList.subList((pageNo-1)*10,(pageNo-1)*10+10 );
				}
			}
			request.setAttribute("count", (int)(Math.ceil((double)passNum/10)));
			request.setAttribute("list", showList);
			return "testPage/DataPage";
			
		}else{//标红用户
			List<Users> list = userService.findByException(map,pageNo);
			request.setAttribute("count", (int)(Math.ceil((double)exceptionNum/10)));
			request.setAttribute("list", list);	
			return "testPage/DataPage";
		}
		
	}
	public void page(HttpServletRequest request,Integer total,Integer totalNum,Integer passTotal,Integer todayNum){
		if(total==null){
			request.setAttribute("count",0);
			request.setAttribute("total",0);
			request.setAttribute("totalNum", 0);
			request.setAttribute("passTotal", 0);
			request.setAttribute("todayNum", 0);
		}else{
			request.setAttribute("count", (int)(Math.ceil((double)total/10)));
			request.setAttribute("total",total);
			request.setAttribute("totalNum",totalNum);
			request.setAttribute("passTotal",passTotal);
			request.setAttribute("todayNum",todayNum);
		}
	}
	
	/**
	 * 注销登录
	 * @param request
	 * @return
	 */
	@RequestMapping(value="logout")
	public String logout(HttpServletRequest request){
		 Enumeration em = request.getSession().getAttributeNames();
		  while(em.hasMoreElements()){
		   request.getSession().removeAttribute(em.nextElement().toString());
		  }
		return InternalResourceViewResolver.REDIRECT_URL_PREFIX +"/test/toLogin";
	}

}
