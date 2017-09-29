package com.liaoliao.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liaoliao.content.entity.Feedback;
import com.liaoliao.content.service.FeedbackService;
import com.liaoliao.redisclient.RedisService;
import com.liaoliao.sys.entity.AboutUs;
import com.liaoliao.sys.entity.AppVersion;
import com.liaoliao.sys.entity.TaskLog;
import com.liaoliao.sys.entity.UserTask;
import com.liaoliao.sys.service.AboutUsService;
import com.liaoliao.sys.service.AppVersionService;
import com.liaoliao.sys.service.UserTaskService;
import com.liaoliao.sys.service.HandleCountService;
import com.liaoliao.sys.service.TaskLogService;
import com.liaoliao.user.entity.Users;
import com.liaoliao.user.service.UserService;
import com.liaoliao.util.StaticKey;
import com.liaoliao.util.TimeKit;

@Controller
@RequestMapping(value="/api")
public class SystemAction {
	
	@Autowired
	private FeedbackService feedbackService;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private AppVersionService appVersionService;
	
	@Autowired
	private AboutUsService aboutUsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserTaskService userTaskService;
	
	@Autowired
	private TaskLogService taskLogService;
	
	@Autowired
	private HandleCountService handleCountService;
	
	
	
	/**
	 * 用户反馈、意见建议
	 * @return
	 */
	@RequestMapping("/feedback")
	@ResponseBody
	public Map<String,Object> feedback(HttpServletRequest request,Integer userId,String content,String imgUrl){
		Map<String,Object> map=new HashMap<>();
		if(userId==null||"".equals(userId)||StringUtils.isBlank(content)){
			map.put("msg", "有属性值为空!");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		if(!redisService.getValidate(request,userId)){
			map.put("msg", "token失效或错误");
			map.put("code", StaticKey.ReturnClientTokenError);
			return map;
		}
		String[] imgs =null;
		String contentImg="";
		if(imgUrl!=null||!("".equals(imgUrl))){
			imgs =imgUrl.split(",");
			contentImg="<div><div class=\"content\">";
			for(int i=0;i<imgs.length;i++){
				contentImg+="<p><img style=\"width:100%\" src=http://appliaoliao.oss-cn-hangzhou.aliyuncs.com/images/"+imgs[i]+"?x-oss-process=style/liaoliao_icon></p>";
			}
			contentImg+=content+"</div></div>";
		}
		//content = CommonUtil.emojiFilter(content);
		Feedback feedback = new Feedback();
		feedback.setUser(userService.findById(userId));
		feedback.setContent(contentImg);
		feedback.setWord(content);
		feedback.setAddTime(new Date());
		feedback.setStatus(1);
		feedbackService.saveFeedback(feedback);
//		统计每日userFeedback用户反馈次数
//		handleCountService.handleCountPlusOne("userFeedback");
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
	
	
	/**
	 * 反馈完成之后获得微信的联系二维码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/weinxinIcon")
	@ResponseBody
	public Map<String,Object> weinxinIcon(HttpServletRequest request){
		Map<String,Object> map=new HashMap<>();
//		微信提现二维码url
		String wechatUrl = redisService.getConfigValue("wechatUrl");
		
		map.put("wechatUrl", wechatUrl);
		map.put("msg", "success");
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
	
	
	
	/**
	 * 获取协议
	 * @return
	 */
	@RequestMapping("/getAgreement")
	@ResponseBody
	public Map<String,Object> getAgreement(){
		Map<String,Object> map=new HashMap<>();
		String agreement = redisService.getConfigValue("agreement");
//		统计每日agreementShow展示次数
//		handleCountService.handleCountPlusOne("agreementShow");
		map.put("agreement", agreement);
		map.put("msg", "success");
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
	
	
	/**
	 * 常见问题
	 * @return
	 */
	@RequestMapping("/getFAQ")
	@ResponseBody
	public Map<String,Object> getFAQ(){
		Map<String,Object> map=new HashMap<>();
		List<AboutUs> questionList = aboutUsService.findQuestionList();
		LinkedList<Map<String,Object>> list = new LinkedList<>();
		Map<String,Object> listMap = null;
		for(AboutUs aboutUs : questionList){
			listMap = new LinkedHashMap<>();
			listMap.put("question", aboutUs.getTitle());
			listMap.put("anwser", aboutUs.getContent());
			list.add(listMap);
		}
		map.put("list", list);
		map.put("msg", "success");
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
	
	/**
	 * 检查更新
	 * @return
	 */
	@RequestMapping("/checkVersion")
	@ResponseBody
	public Map<String,Object> checkVersion(Integer versionCode){
		Map<String,Object> map=new HashMap<>();
		if(versionCode==null||"".equals(versionCode)){
			map.put("msg", "versionCode为空");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;	
		}
		AppVersion maxVersion = appVersionService.findMaxVersion();
		if(maxVersion == null){
			map.put("msg", "异常！");
			map.put("code", StaticKey.ReturnServerNullError);
			return map;	
		}
		if(versionCode>=maxVersion.getVersionCode()){
			map.put("msg", "已是最新版本！");
			map.put("code", StaticKey.ReturnVersionCodeNew);
			return map;	
		}
		Integer maxVersionCode = maxVersion.getVersionCode();
		if(versionCode < maxVersionCode){
			map.put("versionName", maxVersion.getVersionName());
			map.put("updateInfo", maxVersion.getUpdateInfo());
			map.put("downloadUrl", maxVersion.getDownloadUrl());
			if(maxVersionCode-versionCode > 1){
				map.put("type", 1);
			}else{
				map.put("type", maxVersion.getType());
			}
			map.put("msg", "有新版本！");
			map.put("code", StaticKey.ReturnVersionCodeUpdate);
		}
		return map;	
	}
	
	
	
	
		/**
		 * 系统消息
		 */
		/*@RequestMapping("/sysMsg")
		@ResponseBody
		public Map<String,Object> sysMsg(HttpServletRequest request){
			Map<String,Object> map=new HashMap<>();
			return map;
		}*/
	
	
		/**
		 * 客户端初始化
		 */
		@RequestMapping("/initialize")
		@ResponseBody
		public Map<String,Object> initialize(HttpServletRequest request,Integer userId){
			Map<String,Object> map=new HashMap<>();
//			土豪登场持续时间
			int vipEffectTime = 0;
			String vipEffectTimeStr = redisService.getConfigValue("vipEffectTime");
			if(StringUtils.isBlank(vipEffectTimeStr)){
				System.out.println("请检查SystemConfig表数据");
			}else{
				vipEffectTime = Integer.valueOf(vipEffectTimeStr);
			}
//			观看视频获得收益时间
			int videoCoinTime = 99;
			String videoCoinTimeStr = redisService.getConfigValue("videoCoinTime");
			if(StringUtils.isBlank(videoCoinTimeStr)){
			}else{
				videoCoinTime = Integer.valueOf(videoCoinTimeStr);
			}
			
//			控制世界发言(快来发送"快来发送世界广播吧")  0: 不播放     1：播放
			Integer SignMessageOrNot = 0;
			String SignMessageOrNotStr = redisService.getConfigValue("SignMessageOrNot");
			if(StringUtils.isBlank(SignMessageOrNotStr)){
			}else{
				SignMessageOrNot = Integer.valueOf(SignMessageOrNotStr);
			}
			if(userId!=null){
				if(redisService.getValidate(request,userId)){
					Users user  = userService.findById(userId);
					if(user!=null){
						if(user.getMobile()!=null){
							map.put("mobileBindStatus", 1);
						}
					}
				}
			}
			map.put("vipEffectTime", vipEffectTime);
			map.put("videoCoinTime", videoCoinTime);
			map.put("SignMessageOrNot", SignMessageOrNot);
			map.put("code", StaticKey.ReturnServerTrue);
//			统计每日startApp打开app次数
			handleCountService.handleCountPlusOne("startApp");
			return map;
		}
		
		/**
		 * 假的广播
		 */
		@RequestMapping("/unrealBroadcast")
		@ResponseBody
		public Map<String,Object> unrealBroadcast(HttpServletRequest request){
			Map<String,Object> map = new HashMap<>();
			Map<String,Object> linkMap = null;
			List<Map<String,Object>> list = new LinkedList<>();
			String name = redisService.getConfigValue("unrealBroadcastName");
//					"栀子花开,雪儿,豆子,踏雪寻梅,白衣之夏,刘六斤,特 仑 苏,My misaka,K.球球,且斩桃花三两斤,willin,小晨曦,那个人,"
//					+ "爱学习的小白,Louis,十三.,五月布拉吉,画心.,清荷茗韵,不语,Sube,Sun',空缱绻,din,Sunshine`,___平行线°,若近似远,爱笑小王子,"
//					+ "白开水,伍小宝依悠,懒洋洋,Baby乖乖,﹟人生路口,゛ャ不乖゛ャ,叶落╮似流年,梨花未央,小小疙瘩,猪在江湖^@^,先生__请保持距离,夏夜星辰,"
//					+ "ゞ苏小苏ぺ,农民伯伯,花茶,Aloha'Oe,瑶瑶,恋雪,坐着火箭去旅行,ゞ随心O_o,夏沫烟雨つ,跳动的音符,笨女孩,   西西,"
//					+ "傻小影,梅之夏初,丨杰然不同,泡沫  。,摩西,糖豆芽芽,若惜,我全部的浪漫情怀";
			String message = redisService.getConfigValue("unrealBroadcastMessage");
//			String message = "我来了,今天天气不错,有没有土豪啊,求土豪上线抢料币~,发个广播666,我也发一条,我们这边也下雨了,"
//					+ "大土豪来啦,有木有帅哥啊~,就是，哪里有帅哥,你们不要只找帅哥啊，有妹子在呢,么么哒";
		    String[] arrayName = name.split(",");
		    String[] arrayMessage = message.split(",");
		    List<String> listName = Arrays.asList(arrayName);
		    List<String> listMessage = Arrays.asList(arrayMessage);
		    int maxSizeName = listName.size();
		    int maxSizeMessage = listMessage.size();
		    for (int i = 0; i < 10; i++) {
		    	linkMap = new LinkedHashMap<>();
		    	int randomSizeName = ThreadLocalRandom.current().nextInt(0, maxSizeName);
		    	int randomSizeMessage = ThreadLocalRandom.current().nextInt(0, maxSizeMessage);
		    	String messageStr = listName.get(randomSizeName)+" : "+listMessage.get(randomSizeMessage);
		    	linkMap.put("message", messageStr);
		    	list.add(linkMap);
			}
		    map.put("code", StaticKey.ReturnServerTrue);
		    map.put("msg", "success");
		    map.put("list", list);
			return map;
		}
	
		
		
		
		/**
		 * 统计签到完调转到任务列表的次数
		 * @param request
		 * @param userId
		 * @return
		 */
		@RequestMapping("/jumpToTaskList")
		public void jumpToTaskList(HttpServletRequest request)  {
//				统计每日签到完跳转到每日任务列表的次数
				handleCountService.handleCountPlusOne("jumpToTaskList");
		}
		
		/**
		 * 每日任务列表
		 * @param request
		 * @return
		 * 0：一次性任务   1：每日任务
		 * status  :0:未完成   1：完成
		 * @throws InterruptedException 
		 */
		@RequestMapping("/DayTaskList")
		@ResponseBody
		public Map<String,Object> DayTaskList(HttpServletRequest request,Integer userId)  {
			Map<String,Object> map = new HashMap<>();
			//如果已经登录了，先查询日志表里面有没有日志，如果没有，那么就显示全部
			if(userId!=null&&!("".equals(userId))){
				/*if(!redisService.getValidate(request,userId)){
					map.put("msg", "token失效或错误");
					map.put("code", StaticKey.ReturnClientTokenError);
					return map;
				}*/
			Users user  =  userService.findById(userId);
			List<Map<String,Object>> list = new ArrayList<>();
			Map<String,Object> item=null;
			//查询一次性任务：
			List<UserTask> taskList =userTaskService.findByType(0);
		//	TaskLog userTaskLog =null;
			for(UserTask ut:taskList){
				TaskLog tl = taskLogService.findExist(ut.getId(),userId);
				if(tl==null){
					tl =new TaskLog();
					tl.setStatus(0);
					tl.setUser(user);
					tl.setUserTask(ut);
					tl.setObtain(0);
					taskLogService.savaTaskLog(tl);
				}
				item = new HashMap<>();
				item.put("id", ut.getId());
				item.put("content", ut.getContent());
				item.put("money", ut.getMoney());
				item.put("type", ut.getType());
				item.put("status", tl.getStatus());
				item.put("obtain", tl.getObtain());
				list.add(item);
			}
			//查询每日任务：		
			List<UserTask> taskDayList = userTaskService.findByType(1);
		//	TaskLog dayLog =null;
			for(UserTask ut:taskDayList){
				TaskLog tl = taskLogService.findDayExist(TimeKit.todayStart(),ut.getId(),userId);
				if(tl==null){
					tl =new TaskLog();
					tl.setStatus(0);
					tl.setUser(user);
					tl.setUserTask(ut);
					tl.setObtain(0);
					taskLogService.savaTaskLog(tl);
				}
				item = new HashMap<>();
				item.put("id", ut.getId());
				item.put("content", ut.getContent());
				item.put("money", ut.getMoney());
				item.put("type", ut.getType());
				item.put("status", tl.getStatus());
				item.put("obtain", tl.getObtain());
				list.add(item);
			 }
			map.put("list", list);
			map.put("msg", "success");
			map.put("code", StaticKey.ReturnServerTrue);
			return map;
			}else{
			//如果没登录，那么就显示全部任务
			List<UserTask> taskList = userTaskService.findAll();
			List<Map<String,Object>>  list = new ArrayList<>();
			Map<String,Object> itemMap=null;
			for(UserTask dt:taskList){
				itemMap = new HashMap<>();
				itemMap.put("id", dt.getId());
				itemMap.put("content", dt.getContent());
				itemMap.put("money", dt.getMoney());
				itemMap.put("type", dt.getType());
				itemMap.put("status", 0);
				list.add(itemMap);
			}
			map.put("list", list);
			map.put("msg", "success");
			map.put("code", StaticKey.ReturnServerTrue);
			return map;
		  }
		}
		
		
		
		/**
		 * 改变任务状态
		 * @param request
		 * @param userId
		 * @param taskId
		 * @return
		 */
		@RequestMapping("/changeTaskStatus")
		@ResponseBody
		public Map<String,Object> changeTaskStatus(HttpServletRequest request,Integer userId,Integer taskId){
			Map<String,Object> map = new HashMap<String,Object>();
			
			if(userId==null||("".equals(userId))){
				map.put("code", StaticKey.ReturnClientTokenError);
				map.put("msg","客户端传值token错误");
				return map;
			}
			
			if(taskId==null||("".equals(taskId))){
				map.put("code", StaticKey.ReturnClientNullError);
				map.put("msg","客户端传值字段为空");
				return map;
			}
			
			if(!redisService.getValidate(request,userId)){
				map.put("msg", "token失效或错误");
				map.put("code", StaticKey.ReturnClientTokenError);
				return map;
			}
			
			UserTask userTask = userTaskService.findById(taskId);
			System.out.println(userTask.getType());
			if(userTask == null || "".equals(userTask)){
				map.put("code", StaticKey.ReturnServerNullError);
				map.put("msg", "服务器查询为空或错误");
				return map;
			}
			
			//查询日志表里面有没有日志,有日志,修改; 无日志,提示查询为空
			TaskLog taskLog = null;
			if(userTask.getType()==0){
				taskLog = taskLogService.findExist(taskId,userId);
			}else if(userTask.getType()==1){
				taskLog = taskLogService.findDayExist(TimeKit.todayStart(),taskId,userId);
			}else{
				map.put("msg", "服务器数据类型出错!");
				map.put("code", StaticKey.ReturnServerNullError);
				return map;
			}
			
			if(taskLog==null||"".equals(taskLog)){
				map.put("code", StaticKey.ReturnServerNullError);
				map.put("msg", "服务器查询为空或错误");
				return map;
			}
			
			Integer status = null;
			if(taskLog != null && !("".equals(taskLog))&&taskLog.getStatus()<2){
				status = taskLog.getStatus() +1;
				taskLog.setStatus(status);
				taskLogService.changeTaskStatus(taskLog);
			}
			
			map.put("id", taskId);
			map.put("status", status);
			map.put("code", StaticKey.ReturnServerTrue);
			map.put("msg", "正确");
				
			return map;
		}
		

}
