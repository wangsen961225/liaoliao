package com.liaoliao.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liaoliao.common.service.CommonService;
import com.liaoliao.content.entity.Article;
import com.liaoliao.content.service.ArticleService;
import com.liaoliao.profit.entity.FenrunLog;
import com.liaoliao.profit.service.FenrunLogService;
import com.liaoliao.redisclient.RedisService;
import com.liaoliao.sys.entity.District;
import com.liaoliao.sys.service.DistrictService;
import com.liaoliao.user.entity.FocusLog;
import com.liaoliao.user.entity.RedPackage;
import com.liaoliao.user.entity.Users;
import com.liaoliao.user.service.FocusLogService;
import com.liaoliao.user.service.RedPackageService;
import com.liaoliao.user.service.UserService;
import com.liaoliao.util.ApplicationContextHelper;
import com.liaoliao.util.JPushUtil;
import com.liaoliao.util.RandomKit;
import com.liaoliao.util.StaticKey;

/**
 * 交互
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/api")
public class InteractiveAction {
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FocusLogService focusLogService;
	
	@Autowired
	private DistrictService districtService;
	
	@Autowired
	private FenrunLogService fenrunLogService;
	
	@Autowired
	private RedPackageService redPackageService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private CommonService commonService;
	
	
	ApplicationContext ctx = ApplicationContextHelper.getApplicationContext();
	/**
	 * 关注别人
	 * @param request
	 * @param userId
	 * @param focusId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/focus")
	public Map<String,Object> Focus(HttpServletRequest request,Integer userId,Integer focusId){
		Map<String,Object> map = new HashMap<>(); 
		if(userId==null||focusId==null||"".equals(focusId)){
			map.put("msg", "有参数为空");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		if(!redisService.getValidate(request,userId)){
			map.put("msg", "token失效或错误");
			map.put("code", StaticKey.ReturnClientTokenError);
			return map;
		}
		FocusLog fl = focusLogService.findByFocusId(userId,focusId);
		if(fl!=null&&fl.getStatus()==1){
			map.put("msg", "已关注!");
			map.put("code", StaticKey.ReturnServerTrue);
			return map;
		}
		
		Users user = userService.findById(userId);
		Users focusUser = userService.findById(focusId);
		if(user==null||focusUser==null){
			map.put("msg", "用户不存在！");
			map.put("code", StaticKey.ReturnUserAccountNotExist);
			return map;
		}
		if(user.getId()==focusUser.getId()){
			map.put("msg", "无法关注自己!");
			map.put("code", StaticKey.ReturnServerTrue);
			return map;
		}
		if(fl==null){
			fl= new FocusLog();
			fl.setAddTime(new Date());
			fl.setFocusUser(focusUser);
			fl.setStatus(1);
			fl.setUser(user);
			focusLogService.saveFocusLog(fl);
		}else{
			fl.setStatus(1);
			focusLogService.updateFocusLog(fl);
		}
		map.put("msg", "success");
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
	
	/**
	 * 我的料友列表
	 * @param request
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/FocusList")
	public Map<String,Object> FocusList(HttpServletRequest request,Integer userId){
		Map<String,Object> map = new HashMap<>(); 
	if(userId==null){
			map.put("msg", "有参数为空");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
			if(!redisService.getValidate(request,userId)){
			map.put("msg", "token失效或错误");
			map.put("code", StaticKey.ReturnClientTokenError);
			return map;
		}
		Users user = userService.findById(userId);
		if(user==null){
			map.put("msg", "用户不存在！");
			map.put("code", StaticKey.ReturnUserAccountNotExist);
			return map;
		}
		List<FocusLog> list = focusLogService.findByUserId(userId);
		List<Map<String,Object>> data = new ArrayList<>();
		Map<String,Object> item = null;
		for(FocusLog fl:list){
			item = new HashMap<>();
			item.put("id", fl.getFocusUser().getId());
			item.put("name", fl.getFocusUser().getNickName());
			if(fl.getFocusUser().getAvatar()==null){
				item.put("avatar", "");
			}else
				item.put("avatar",fl.getFocusUser().getAvatar());
			
			item.put("avatar", fl.getFocusUser().getAvatar());
			item.put("sex", fl.getFocusUser().getSex());
			item.put("age", fl.getFocusUser().getAge());
			if(fl.getFocusUser().getBirthdate()==null){
				item.put("birthdate", "");
			}else
			item.put("birthdate", fl.getFocusUser().getBirthdate());
			
			if(fl.getFocusUser().getDistrict()==null){
				item.put("district", "");
				item.put("city", "");
				item.put("province", "");
			}else{
				Integer districtId = fl.getFocusUser().getDistrict().getId();
				District district = districtService.findById(districtId);
				if(district==null){
					item.put("district", "");
					item.put("city", "");
					item.put("province", "");
				}else{
					item.put("district", district.getName());
					if(district.getCity()==null){
						item.put("city", "");
					}else{
						item.put("city", district.getCity().getName());
						if(district.getCity().getProvince()==null){
							item.put("province", "");
						}else{
							item.put("province", district.getCity().getProvince().getName());
						}
					}
				}
			}
			data.add(item);
		}
		map.put("list", data);
		map.put("msg", "success");
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
	
	
	/**
	 * 料友信息
	 * @param request
	 * @param userId
	 * @param focusId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/focusUserInfo")
	public Map<String,Object> focusUserInfo(HttpServletRequest request,Integer userId,Integer focusId){
		Map<String,Object> map = new HashMap<>(); 
		if(userId==null||focusId==null||"".equals(focusId)){
			map.put("msg", "有参数为空");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		if(!redisService.getValidate(request,userId)){
			map.put("msg", "token失效或错误");
			map.put("code", StaticKey.ReturnClientTokenError);
			return map;
		}
		Users user = userService.findById(userId);
		Users focusUser = userService.findById(focusId);
		if(user==null||focusUser==null){
			map.put("msg", "用户不存在！");
			map.put("code", StaticKey.ReturnUserAccountNotExist);
			return map;
		}
		if((user.getTotalMoney()-user.getPayMoney()-user.getFreezeMoney()-user.getToBankMoney())<500){
			map.put("msg", "余额不足!");
			map.put("code", StaticKey.ReturnMoneyLow);
			return map;
		}
		user.setPayMoney(user.getPayMoney()+500);
		FenrunLog fl = new FenrunLog();
		fl.setAddTime(new Date());
		fl.setMoney(-500);
		fl.setUser(user);
		fl.setType(StaticKey.FenrunLookUser);
		fl.setContentId(focusId);
		commonService.updateUserAndSaveProfitLog(fl, user);
		
		map.put("qq", focusUser.getQq());
		map.put("wechat", focusUser.getWechat());
		map.put("msg", "success");
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
	
	
	/**
	 * 土豪发送世界红包
	 * @param request
	 * @param userId
	 * @param money
	 * @param number
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/sendReadPackage")
	public Map<String,Object> sendReadPackage(HttpServletRequest request,Integer userId,Integer money,Integer number){
		Map<String,Object> map = new HashMap<>();
		if(userId==null||"".equals(userId)||money==null||"".equals(money)||number==null||"".equals(number)){
			map.put("msg", "有参数为空!");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
			if(!redisService.getValidate(request,userId)){
			map.put("msg", "token失效或错误!");
			map.put("code", StaticKey.ReturnClientTokenError);
			return map;
		}
		if(money<number){
			map.put("msg", "金额小于份数!");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		Users user = userService.findById(userId);
		if(user==null){
			map.put("msg", "用户不存在!");
			map.put("code", StaticKey.ReturnUserAccountNotExist);
			return map;
		}
		
		//如果是系统红包,发送通知
		if(user.getNickName()=="料料官方活动"){
			JPushUtil.sendAllsetNotification("通知: 天降红包~~ 金额:"+money+"~ 剩余数量:"+number);
		}
		
		
		
		
		
		
		
		
		
		
		int userMoney=(int) (user.getTotalMoney()-user.getFreezeMoney()-user.getPayMoney()-user.getToBankMoney());
		if(userMoney<money){
			map.put("msg", "余额不足!");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		user.setPayMoney(user.getPayMoney()+money);
		userService.updateUser(user);
		List<Integer> list=null;
		if(number==1){
			list=new ArrayList<>();
			list.add(money);
		}else
		list = RandomKit.randomRedPackage(money, number);
		StaticKey.redPackagelist=list;
		Map<String, String> extras = new HashMap<String, String>();
		// 添加附加信息
		extras.put("type", StaticKey.JPushSendRedPackage);
		extras.put("userId", String.valueOf(userId));
		int time = 100;//红包推送生存时间(S)
		JPushUtil.sendAllMessage(user.getNickName(),extras,time);
		int maxMoney=0;
		for(int i=0;i<list.size();i++){
			maxMoney=maxMoney>list.get(i)?maxMoney:list.get(i);
		}
		RedPackage rp = new RedPackage();//土豪发放红包记录
		rp.setAddTime(new Date());
		rp.setMoney(money);
		rp.setNumber(number);
		rp.setUser(user);
		rp.setBalance(money);
		rp.setStatus(1);
		redPackageService.saveRedPackage(rp);
		FenrunLog fl = new FenrunLog();
		fl.setAddTime(new Date());
		fl.setMoney(-money);
		fl.setUser(user);
		fl.setContentId(rp.getId());
		fl.setType(StaticKey.FenrunWorldRedPackage);
		fenrunLogService.saveFenrunLog(fl);
		/*Integer rpId = rp.getId();
		new Thread() {
			  public void run() {
			    	try {
						this.sleep(110*1000);
						if(rpId!=null){
						RedPackageService redPackageService  = (RedPackageService) ctx.getBean("redPackageService");
						RedPackage redP=redPackageService.findById(rpId);
						if(redP.getBalance()>0){
							FenrunLog fel = new FenrunLog();
							fel.setAddTime(new Date());
							fel.setMoney(redP.getBalance());
							System.out.println(rpId+","+redP.getBalance());
							fel.setContentId(redP.getId());
							fel.setUser(user);
							fel.setType(StaticKey.FenrunRefund);
							fenrunLogService.saveFenrunLog(fel);
						 }
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
				 }   
			   }
			  }.start();*/
		map.put("maxMoney",maxMoney);
		map.put("msg", "success");
		map.put("code", StaticKey.ReturnServerTrue);
		
		return map;
		
	}
	
	/**
	 * 料友广场(四十人)
	 * @return
	 */
	@RequestMapping("/focusUserSquare")
	@ResponseBody
	public Map<String,Object> focusUserSquare(HttpServletRequest request,Integer userId){
		Map<String,Object> data = new HashMap<String,Object>();
		List<Map<String,Object>> userList = new ArrayList<Map<String,Object>>();
		for(int i=0;i<34;i++){
			Map<String, Object> map = this.getFocusUser(request, 1, userId);
			if(map.get("item")!=null){
				userList.add((Map<String, Object>) map.get("item"));
			}
		}
		
		for(int i=0;i<6;i++){
			Map<String, Object> map = this.getFocusUser(request, 2, userId);
			if(map.get("item")!=null){
				userList.add((Map<String, Object>) map.get("item"));
			}
		}
		
		
		if((userList.size()-40)<0){
			for(int i=0;i<(40-userList.size());i++){
				Map<String, Object> map = this.getFocusUser(request, 0, userId);
				if(map.get("item")!=null){
					userList.add((Map<String, Object>) map.get("item"));
				}
			}
		}
		
		System.out.println(userList.size()+"///////////////////////////////////////////////////////////////////////");
		
		
		data.put("list", userList);
		data.put("msg", "正确");
		data.put("code",StaticKey.ReturnServerTrue);
		return data;
		
		/*Map<String,Object> data = new HashMap<String,Object>();
       List<Users> list = new ArrayList<Users>();
       //0:性别待确认   1:性别男     2:性别女
       Long acc0 = userService.accountBySex(0);
       Long acc1 = userService.accountBySex(1);
       Long acc2 = userService.accountBySex(2);
       
       int account0 = 0;
       int account1 = 0;
       int account2 = 0;
       if(acc0!=null&&!("".equals(acc0))){
    	   account0 = new Long(acc0).intValue();
       }
       
       if(acc1!=null&&!("".equals(acc1))){
    	   account1 = new Long(acc1).intValue();
       }
       
       if(acc2!=null&&!("".equals(acc2))){
    	   account2 = new Long(acc2).intValue();
       }
       
       if((account0+account1+account2)==0){
			data.put("msg", "服务器查询为空或错误");
			data.put("code", StaticKey.ReturnServerNullError);
			return data;
		}
       
       Random ranAccount = new Random();
       int boyAccount =0;
       if((account1-6)>0){
    	   boyAccount = ranAccount.nextInt(account1 - 6);
       }
       int girlAccount = 0;
       if((account2-34)>0){
    	   girlAccount = ranAccount.nextInt(account2 - 34);
       }else{
    	   girlAccount = ranAccount.nextInt(34 - account2);
       }
       int unknownAccount = 0;
       if((account0-40)>0){
    	   unknownAccount = ranAccount.nextInt(account0 - 40);
       }
       
		List<Users> boyList = userService.findBySex(1,6,boyAccount);  //性别,数量,开始位置
		for (Users users : boyList) {
			list.add(users);
		}
		List<Users> girlList = userService.findBySex(2,34,girlAccount);
		for (Users users : girlList) {
			list.add(users);
		}
		
		List<Users> unknownList = null;
		if((boyList.size()+girlList.size())<40){
			unknownList = userService.findBySex(0,(40-list.size()),unknownAccount);
		}
		if(unknownList!=null&&unknownList.size()>0){
			for (Users users : unknownList) {
				list.add(users);
			}
		}
		
		
		Random ran = new Random();
		List<Map<String,Object>> userList = new ArrayList<Map<String,Object>>();
		Map<String,Object> item = null;
		int nextInt = 0;
		int j = 0;
		List<Users> randomList = new ArrayList<Users>();
		FocusLog fl = null;
		while(list.size()>0){
			item = new HashMap<String,Object>();
			nextInt = ran.nextInt(list.size());
			Users user = list.get(nextInt);
			item.put("id", user.getId());
			
			String avatar = null;
			if(user.getAvatar()==null){
				avatar = "";
			}else{
				avatar = user.getAvatar();
			}
			item.put("avatar",avatar );
			
			String nickName = null;
			if(user.getNickName()==null){
				nickName = "";
			}else{
				nickName = user.getNickName();
			}
			item.put("sex",user.getSex());
			item.put("nickName",nickName );
			if(userId!=null&&redisService.getValidate(request,userId)){
					fl = focusLogService.findByFocusId(userId, user.getId());
					if(fl!=null&&fl.getStatus()==1){
						item.put("focusStatus", StaticKey.FocusTrue);
					}else{
						item.put("focusStatus", StaticKey.FocusFlase);
					}
			}else{
				item.put("focusStatus", StaticKey.FocusFlase);
			}
			userList.add(item);
//			Users users = list.get(nextInt);
			randomList.add(list.get(nextInt));
			//item.put(String.valueOf(j), list.get(nextInt));
			list.remove(nextInt);
		}*/
	
		
		/*List<Map<String,Object>> userList1 = new ArrayList<>();
		Map<String,Object> ma=null;
		for(Users u:randomList){
			ma=new HashMap<>();
			ma.put("name", u.getNickName());
			ma.put("id", u.getId());
			ma.put("avatar", u.getAvatar());
			ma.put("sex", u.getSex());
			userList.add(ma);
		}*/
		/*data.put("data", userList);
		
		data.put("list", userList);
		data.put("msg", "正确");
		data.put("code",StaticKey.ReturnServerTrue);
		return data;*/
	}
	
	/**
	 * 文章作者信息
	 */
	@RequestMapping("/articleAuthInfo")
	@ResponseBody
	Map<String,Object> articleAuthInfo(HttpServletRequest request,Integer articleId){
		Map<String,Object> data = new HashMap<String,Object>();
		if(articleId==null&&("".equals(articleId))){
			data.put("msg", "客户端传值字段为空");
			data.put("code", StaticKey.ReturnClientNullError);
			return data;
		}
		//根据文章的id查询Article
		Article article = articleService.findById(articleId);
		
		/*if(article==null&&("".equals(article))){
			data.put("msg", "服务器查询为空或错误");
			data.put("code", StaticKey.ReturnServerNullError);
			return data;
		}*/
		if(article==null && (article.getType()!=1)){
			data.put("msg", "服务器查询为空或错误");
			data.put("code", StaticKey.ReturnServerNullError);
			return data;
		}
		Integer userId = article.getSourceId();
		
		//数据库source_id暂时为1,应为67
		if((article.getType()==0)){
			userId = StaticKey.liaoliaoArticleId;
		}
		
		if(userId==null&&("".equals(userId))){
			data.put("msg", "服务器查询为空或错误");
			data.put("code", StaticKey.ReturnServerNullError);
			return data;
		}
		Users users = userService.findById(userId);
		data.put("avatar", users.getAvatar());  //头像
		data.put("nickName", users.getNickName()); //昵称
		data.put("userId", userId);
		
		List<FocusLog> focusLog =  focusLogService.findBeConcernedByUserId(userId);
		Integer beConcernedFocus = 0;
		if(focusLog!=null && focusLog.size()>0){
			beConcernedFocus = focusLog.size();
		}
		data.put("beConcernedFocus", beConcernedFocus);
		data.put("msg", "正确");
		data.put("code", StaticKey.ReturnServerTrue);
		return data;
		
	}
	
	
	@RequestMapping("/getFocusUser")
	@ResponseBody
	Map<String,Object> getFocusUser(HttpServletRequest request,Integer sex, Integer userId){   //0:保密  1: 女   2: 男
		Map<String,Object> data = new HashMap<String,Object>();
		//随机获取一条
		Users user = userService.findByRand(sex);
		
		//查找用户关注的料友并判断随机出来的料友是否已关注
		List<FocusLog> list = list = focusLogService.findByUserId(userId);
		
		boolean b = true;
		if(list!=null&&user!=null&&list.size()>0){
		int i=34; //防止死循环
		while(b&&i>0){
				for (FocusLog focusLog : list) {
					if(user.getId()==focusLog.getFocusUser().getId()){
						b=true;
						i--;
						break;
					}else{
						b=false;
						
					}
					
				}
			}
		}else{
			b=false;
		}
		if(b){
			data.put("msg", "查询为空");
			data.put("code",StaticKey.ReturnServerNullError);
			return data;
		}
	
		
		Map<String,Object> item = new HashMap<String,Object>();
		
		FocusLog fl = null;
			item.put("id", user.getId());
			
			String avatar = null;
			if(user.getAvatar()==null){
				avatar = "";
			}else{
				avatar = user.getAvatar();
			}
			item.put("avatar",avatar );
			
			String nickName = null;
			if(user.getNickName()==null){
				nickName = "";
			}else{
				nickName = user.getNickName();
			}
			item.put("sex",user.getSex());
			item.put("nickName",nickName );
			if(userId!=null&&redisService.getValidate(request,userId)){
					fl = focusLogService.findByFocusId(userId, user.getId());
					if(fl!=null&&fl.getStatus()==1){
						item.put("focusStatus", StaticKey.FocusTrue);
					}else{
						item.put("focusStatus", StaticKey.FocusFlase);
					}
			}else{
				item.put("focusStatus", StaticKey.FocusFlase);
			}

		data.put("item", item);
		
		data.put("msg", "正确");
		data.put("code",StaticKey.ReturnServerTrue);
		
		return data;
	}	
	
	
	
	
}


