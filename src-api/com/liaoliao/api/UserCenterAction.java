package com.liaoliao.api;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liaoliao.content.entity.Article;
import com.liaoliao.content.entity.Video;
import com.liaoliao.content.service.ArticleService;
import com.liaoliao.content.service.VideoService;
import com.liaoliao.profit.entity.FenrunLog;
import com.liaoliao.profit.service.FenrunLogService;
import com.liaoliao.redisclient.RedisService;
import com.liaoliao.sys.entity.BroadcastLog;
import com.liaoliao.sys.entity.City;
import com.liaoliao.sys.entity.District;
import com.liaoliao.sys.entity.Invitation;
import com.liaoliao.sys.entity.Province;
import com.liaoliao.sys.entity.TaskLog;
import com.liaoliao.sys.entity.UserTask;
import com.liaoliao.sys.service.BroadcastLogService;
import com.liaoliao.sys.service.CityService;
import com.liaoliao.sys.service.DistrictService;
import com.liaoliao.sys.service.HandleCountService;
import com.liaoliao.sys.service.InvitationService;
import com.liaoliao.sys.service.ProvinceService;
import com.liaoliao.sys.service.TaskLogService;
import com.liaoliao.sys.service.UserTaskService;
import com.liaoliao.user.entity.RedPackage;
import com.liaoliao.user.entity.UserSign;
import com.liaoliao.user.entity.Users;
import com.liaoliao.user.service.RedPackageService;
import com.liaoliao.user.service.UserService;
import com.liaoliao.user.service.UserSignService;
import com.liaoliao.util.CommonUtil;
import com.liaoliao.util.JPushUtil;
import com.liaoliao.util.RC4Kit;
import com.liaoliao.util.StaticKey;
import com.liaoliao.util.StringKit;
import com.liaoliao.util.TimeKit;

@Controller
@RequestMapping(value="/api")
public class UserCenterAction {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private FenrunLogService fenrunLogService;
	
	@Autowired
	private UserSignService userSignService;
	
	@Autowired
	private HandleCountService handleCountService;
	
	@Autowired
	private InvitationService invitationService;
	
	@Autowired
	private BroadcastLogService broadcastLogService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private VideoService videoService;
	
	@Autowired
	private UserTaskService userTaskService;
	
	@Autowired
	private TaskLogService taskLogService;
	
	@Autowired
	private DistrictService districtService;
	
	@Autowired
	private ProvinceService provinceService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private RedPackageService redPackageService;
	
	private Integer page = 1;
	
	
	/**
	 * 获取个人信息
	 * 
	 * @param request
	 * @param userId
	 * @return
	 */
/*	@RequestMapping(value="/getUserInfo")
	@ResponseBody
	public Map<String,Object> getUserInfo(HttpServletRequest request,Integer userId){
		Map<String,Object> map=new HashMap<String,Object>();
		if(userId==null||"".equals(userId)){
			map.put("msg", "id为空");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		if(!redisService.getValidate(request,userId)){
			map.put("msg", "token失效或错误");
			map.put("code", StaticKey.ReturnClientTokenError);
			return map;
		}
		Users user=userService.findById(userId);
		map.put("avatar", user.getAvatar());
		map.put("nickName", user.getNickName());
		map.put("totalMoney", user.getTotalMoney());
		Double balanceMoney = user.getTotalMoney()-user.getFreezeMoney()-user.getPayMoney()-user.getToBankMoney();
		DecimalFormat df = new DecimalFormat("#.##");
		balanceMoney = Double.parseDouble(df.format(balanceMoney));
		map.put("balanceMoney", balanceMoney);
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}*/
	
	
	
	/**
	 * 检测签到状态
	 * @return
	 */
	@RequestMapping(value="/signStatus")
	@ResponseBody
	public Map<String,Object> signStatus(HttpServletRequest request,Integer userId){
		Map<String,Object> map=new HashMap<>();
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
		
		UserSign userSign = userSignService.findById(userId);
		if(userSign==null){
			map.put("msg", "从未签到");
			map.put("code", StaticKey.ReturnUserSignError);
			return map;
		}
		long lastSignTime = userSign.getSignTime().getTime();
		long todayTime = TimeKit.todayStart().getTime();
//		今日未签到
		if(lastSignTime<todayTime){
			map.put("msg", "今日未签到");
			map.put("code", StaticKey.ReturnUserSignError);
			return map;
		}
		map.put("msg", "success");
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
	
	
	/**
	 * 执行签到
	 * @return
	 */
	@RequestMapping(value="/dailySign")
	@ResponseBody
	public Map<String,Object> dailySign(HttpServletRequest request,Integer userId){
		Map<String,Object> map=new HashMap<>();
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
			map.put("code", StaticKey.ReturnUserAccountError);
			return map;
		}
		
		if(user.getVipStatus()==StaticKey.UserVipStatusTrue){
//			会员无需签到
			map.put("code", StaticKey.ReturnServerTrue);
			map.put("msg", "会员免签到");
			return map;
		}
		
		UserSign userSign = userSignService.findById(userId);
		
//		从未签到
		if(userSign==null){
			userSign = new UserSign(); 
			userSign.setUserId(userId);
			userSign.setSignTime(new Date());
			userSignService.saveOrUpdateUserSign(userSign);
			
//			签到送料币数
			int signCoin = 1;
			String signCoinStr = redisService.getConfigValue("signCoin");
			if(StringUtils.isBlank(signCoinStr)){
				System.out.println("请检查SystemConfig表数据");
			}else{
				signCoin = Integer.valueOf(signCoinStr);
			}
//			更新用户总获得金额
			double newTotalMoney = user.getTotalMoney()+signCoin;
			user.setTotalMoney(newTotalMoney);
//			更新最后使用时间
			user.setLoginTime(new Date());
			userService.updateUser(user);
			
			FenrunLog fenrun = new FenrunLog();
//			添加分润记录
			fenrun.setContentId(StaticKey.FenrunContentSign);
			fenrun.setType(StaticKey.FenrunSign);
			fenrun.setMoney(signCoin);
			fenrun.setUser(user);
			fenrun.setAddTime(new Date());
			fenrunLogService.saveFenrunLog(fenrun);
//			统计每日signSuccess签到成功次数
			handleCountService.handleCountPlusOne("signSuccess");
			
//			统计每日分润料币总金额
			handleCountService.handleCountTotalMoney("totalProfitMoney",signCoin);
			map.put("signCoin", signCoin);
			map.put("code", StaticKey.ReturnServerTrue);
			return map;
		}
		
		long lastSignTime = userSign.getSignTime().getTime();
		long todayTime = TimeKit.todayStart().getTime();
//		今日未签到
		if(lastSignTime<todayTime){
			userSign.setSignTime(new Date());
			userSignService.saveOrUpdateUserSign(userSign);
			
//			签到送料币数
			int signCoin = 1;
			String signCoinStr = redisService.getConfigValue("signCoin");
			if(StringUtils.isBlank(signCoinStr)){
				System.out.println("请检查SystemConfig表数据");
			}else{
				signCoin = Integer.valueOf(signCoinStr);
			}
//			更新用户总获得金额
			double newTotalMoney = user.getTotalMoney()+signCoin;
			user.setTotalMoney(newTotalMoney);
//			更新最后使用时间
			user.setLoginTime(new Date());
			userService.updateUser(user);
			
			FenrunLog fenrun = new FenrunLog();
//			添加分润记录
			fenrun.setContentId(0);
			fenrun.setType(StaticKey.FenrunSign);
			fenrun.setMoney(signCoin);
			fenrun.setUser(user);
			fenrun.setAddTime(new Date());
			fenrunLogService.saveFenrunLog(fenrun);
//			统计每日signSuccess签到成功次数
			handleCountService.handleCountPlusOne("signSuccess");
//			统计每日分润料币总金额
			handleCountService.handleCountTotalMoney("totalProfitMoney",signCoin);
			map.put("signCoin", signCoin);
			map.put("code", StaticKey.ReturnServerTrue);
			return map;
		}
//		今日已签到，不再重复签到
		map.put("code", StaticKey.ReturnServerTrue);
		map.put("msg", "今日已签到，无需签到");
		return map;
	}
	
	/**
	 * 发广播
	 * @return
	 */
	@RequestMapping(value="/sendBroadcast")
	@ResponseBody
	public Map<String,Object> sendBroadcast(HttpServletRequest request, Integer userId, String content, Integer type){
		Map<String,Object> map=new HashMap<>();
		if(userId==null||StringUtils.isBlank(content)||type==null||(type!=1&&type!=2&&type!=3)){
			map.put("msg", "参数异常");
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
			map.put("code", StaticKey.ReturnUserAccountError);
			return map;
		}
		
		int broadcastPrice = 0;
		String broadcastTime = null;
		if(type==1){
//			类型一广播料币金额
			String broadcastPriceStr1 = redisService.getConfigValue("broadcastPrice1");
//			类型一广播持续时长
			String broadcastTimeStr1 = redisService.getConfigValue("broadcastTime1");
			if(StringUtils.isBlank(broadcastPriceStr1)||StringUtils.isBlank(broadcastTimeStr1)){
				map.put("msg", "error");
				map.put("code", StaticKey.ReturnServerNullError);
				return map;
			}
			int broadcastPrice1 = Integer.valueOf(broadcastPriceStr1);
			broadcastPrice = broadcastPrice1;
			broadcastTime = broadcastTimeStr1;
		}
		if(type==2){
//			类型二广播料币金额
			String broadcastPriceStr2 = redisService.getConfigValue("broadcastPrice2");
//			类型二广播持续时长
			String broadcastTimeStr2 = redisService.getConfigValue("broadcastTime2");
			if(StringUtils.isBlank(broadcastPriceStr2)||StringUtils.isBlank(broadcastTimeStr2)){
				map.put("msg", "error");
				map.put("code", StaticKey.ReturnServerNullError);
				return map;
			}
			int broadcastPrice2 = Integer.valueOf(broadcastPriceStr2);
			broadcastPrice = broadcastPrice2;
			broadcastTime = broadcastTimeStr2;
		}
		if(type==3){
//			类型三广播料币金额
			String broadcastPriceStr3 = redisService.getConfigValue("broadcastPrice3");
//			类型三广播持续时长
			String broadcastTimeStr3 = redisService.getConfigValue("broadcastTime3");
			if(StringUtils.isBlank(broadcastPriceStr3)||StringUtils.isBlank(broadcastTimeStr3)){
				map.put("msg", "error");
				map.put("code", StaticKey.ReturnServerNullError);
				return map;
			}
			int broadcastPrice3 = Integer.valueOf(broadcastPriceStr3);
			broadcastPrice = broadcastPrice3;
			broadcastTime = broadcastTimeStr3;
		}
		double balanceMoney = user.getTotalMoney()-user.getFreezeMoney()-user.getPayMoney()-user.getToBankMoney();
		DecimalFormat df = new DecimalFormat("#.##");    
		balanceMoney = Double.parseDouble(df.format(balanceMoney));
//		余额
		int money = (int) balanceMoney;
		
//		余额不足
		if(money<broadcastPrice){
			map.put("msg", "余额不足");
			map.put("code", StaticKey.ReturnMoneyLow);
			return map;
		}
//		更新用户总支付金额
		double newPayMoney = user.getPayMoney()+broadcastPrice;
//		会员半价
		if(user.getVipStatus()==StaticKey.UserVipStatusTrue){
			newPayMoney = user.getPayMoney()+broadcastPrice/2;
		}
		user.setPayMoney(newPayMoney);
		userService.updateUser(user);
		
		//添加发言记录
		BroadcastLog bl = new BroadcastLog();
		bl.setAddTime(new Date());
		//content=CommonUtil.emojiFilter(content);
		bl.setContent(CommonUtil.emojiFilter(content));
		bl.setPrice(newPayMoney);
		bl.setUser(user);
		bl.setStatus(1);
		broadcastLogService.saveBLog(bl);
		
		FenrunLog fenrun = new FenrunLog();
//		添加分润记录
		fenrun.setContentId(0);
		fenrun.setType(StaticKey.FenrunBroadcast);
		fenrun.setMoney(-broadcastPrice);
//		会员半价
		if(user.getVipStatus()==StaticKey.UserVipStatusTrue){
			fenrun.setMoney(-broadcastPrice/2);
		}
		fenrun.setUser(user);
		fenrun.setAddTime(new Date());
		fenrunLogService.saveFenrunLog(fenrun);
		
		Map<String, String> extras = new HashMap<String, String>();
		// 添加附加信息
		extras.put("type", StaticKey.JPushSendBroadcast);
		extras.put("time", broadcastTime);
		
		JPushUtil.sendAllMessage(content,extras,1800);
//		统计每日sendBroadcast发送广播次数
		handleCountService.handleCountPlusOne("sendBroadcast");
		map.put("msg", "success");
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
	
	/**
	 * 获取广播金额和用户余额
	 * @return
	 */
	@RequestMapping(value="/getBroadcastPrice")
	@ResponseBody
	public Map<String,Object> getBroadcastPrice(HttpServletRequest request,Integer userId){
		Map<String,Object> map=new HashMap<>();
		
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
//		类型一广播料币金额
		String broadcastPriceStr1 = redisService.getConfigValue("broadcastPrice1");
//		类型二广播料币金额
		String broadcastPriceStr2 = redisService.getConfigValue("broadcastPrice2");
//		类型三广播料币金额
		String broadcastPriceStr3 = redisService.getConfigValue("broadcastPrice3");
//		类型一广播持续时长
		String broadcastTimeStr1 = redisService.getConfigValue("broadcastTime1");
//		类型二广播持续时长
		String broadcastTimeStr2 = redisService.getConfigValue("broadcastTime2");
//		类型三广播持续时长
		String broadcastTimeStr3 = redisService.getConfigValue("broadcastTime3");
		
		if(StringUtils.isBlank(broadcastPriceStr1)||StringUtils.isBlank(broadcastPriceStr2)
				||StringUtils.isBlank(broadcastPriceStr3)||StringUtils.isBlank(broadcastTimeStr1)
				||StringUtils.isBlank(broadcastTimeStr2)||StringUtils.isBlank(broadcastTimeStr3)){
			map.put("msg", "异常");
			map.put("code", StaticKey.ReturnServerNullError);
			return map;
		}
//		广播单价
		int broadcastPrice1 = Integer.valueOf(broadcastPriceStr1);
		int broadcastPrice2 = Integer.valueOf(broadcastPriceStr2);
		int broadcastPrice3 = Integer.valueOf(broadcastPriceStr3);
		
//		广播时长
		int broadcastTime1 = Integer.valueOf(broadcastTimeStr1);
		int broadcastTime2 = Integer.valueOf(broadcastTimeStr2);
		int broadcastTime3 = Integer.valueOf(broadcastTimeStr3);
		
		Map<String, Object> broadcastOne = new HashMap<String, Object>();
		Map<String, Object> broadcastTwo = new HashMap<String, Object>();
		Map<String, Object> broadcastThree = new HashMap<String, Object>();
		
		broadcastOne.put("price", broadcastPrice1);
		broadcastOne.put("time", broadcastTime1);
		broadcastTwo.put("price", broadcastPrice2);
		broadcastTwo.put("time", broadcastTime2);
		broadcastThree.put("price", broadcastPrice3);
		broadcastThree.put("time", broadcastTime3);

		
		Users user = userService.findById(userId);
		if(user==null){
			map.put("msg", "用户不存在！");
			map.put("code", StaticKey.ReturnUserAccountError);
			return map;
		}
		
		double balanceMoney = user.getTotalMoney()-user.getFreezeMoney()-user.getPayMoney()-user.getToBankMoney();
		DecimalFormat df = new DecimalFormat("#.##");    
		balanceMoney = Double.parseDouble(df.format(balanceMoney));
		
//		统计每日broadcastShow展示次数
		handleCountService.handleCountPlusOne("broadcastShow");
		map.put("msg", "success");
		map.put("code", StaticKey.ReturnServerTrue);
		map.put("broadcastOne", broadcastOne);
		map.put("broadcastTwo", broadcastTwo);
		map.put("broadcastThree", broadcastThree);
		map.put("userMoney", (int)balanceMoney);
		return map;
	}
	
	
	/**
	 * 霸屏上线
	 * @return
	 */
	@RequestMapping(value="/vipLoginEffect")
	@ResponseBody
	public Map<String,Object> vipLoginEffect(HttpServletRequest request, Integer userId){
		Map<String,Object> map=new HashMap<>();
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
			map.put("code", StaticKey.ReturnUserAccountError);
			return map;
		}

		if(user.getVipStatus()!=StaticKey.UserVipStatusTrue){
			map.put("msg", "用户非会员！");
			map.put("code", StaticKey.ReturnUserNotVip);
			return map;
		}
		
		long nowTime = new Date().getTime();
		long lastTime = -1;
		String lastTimeStr = redisService.get("vipEffectLastTime_"+userId);
		if(!StringUtil.isBlank(lastTimeStr)){
			lastTime = Long.valueOf(lastTimeStr);
		}
		redisService.set("vipEffectLastTime_"+userId,String.valueOf(nowTime),7*24*60*60);
		long cooldownTime = nowTime-lastTime;
//		vip再次霸屏上线冷却时间(小时)
		double vipEffectFreeze = 24;
		String vipEffectFreezeStr = redisService.getConfigValue("vipEffectFreeze");
		if(StringUtils.isBlank(vipEffectFreezeStr)){
			System.out.println("请检查SystemConfig表数据");
		}else{
			vipEffectFreeze = Double.valueOf(vipEffectFreezeStr);
		}
		
		if(cooldownTime < vipEffectFreeze*60*60*1000){
			map.put("msg", "冷却时间内仅闪亮登场一次，未到冷却时间");
			map.put("cooldownTime", cooldownTime);
			map.put("code", StaticKey.ReturnCooldownTimeError);
			return map;
		}
		
		Map<String, String> extras = new HashMap<String, String>();
		// 添加附加信息
		extras.put("type", StaticKey.JPushVipLoginEffectType);
		extras.put("userId", String.valueOf(user.getId()));
		
		JPushUtil.sendAllMessage(user.getNickName(),extras,1800);
		user.setLoginTime(new Date());
		userService.updateUser(user);
//		统计每日vipEffect霸屏上线次数
		handleCountService.handleCountPlusOne("vipEffect");
		map.put("msg", "success");
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
	
	/**
	 * 获取邀请页url：
	 * @return
	 */
	@RequestMapping(value="/getInviteUrl")
	@ResponseBody
	public Map<String,Object> getInviteUrl(HttpServletRequest request,Integer userId) {//,Integer getTask
		Map<String,Object> map=new HashMap<String,Object>();
		//System.out.println(getTask);
		/*if(null==getTask || getTask==0){
			map.put("msg", "该用户未领取任务");
			map.put("code", StaticKey.NotReceiveTask);
			return map;
		}*/
		
		if(userId==null){
			map.put("code", StaticKey.ReturnClientNullError);
			map.put("msg", "参数异常");
			return map;
		}
		if(!redisService.getValidate(request,userId)){
			map.put("msg", "token失效或错误");
			map.put("code", StaticKey.ReturnClientTokenError);
			return map;
		}
		
//		RC4加密
		String idStr = RC4Kit.encry_RC4_string(String.valueOf(userId), "liao");
		
		Long inviteCount = invitationService.getInviteCount(userId);
//		邀请好友获得料币数
		int invitationMoney = 1;
		String invitationMoneyStr = redisService.getConfigValue("invitationMoney");
		if(StringUtils.isBlank(invitationMoneyStr)){
			System.out.println("请检查SystemConfig表数据");
		}else{
			invitationMoney = Integer.valueOf(invitationMoneyStr);
		}
		
//		统计每日inviteShow展示次数
		handleCountService.handleCountPlusOne("inviteShow");
		map.put("inviteUrl", "/share/invitation/"+idStr);
		map.put("inviteCount", inviteCount);
		map.put("inviteMoney", invitationMoney);
		return map;
	}
	
	/**
	 * 获取邀请人列表：
	 * @return
	 */
	@RequestMapping(value="/getInviteList")
	@ResponseBody
	public Map<String,Object> getInviteList(HttpServletRequest request,Integer userId,Integer pageNo) {
		Map<String,Object> map=new HashMap<String,Object>();
		if(userId==null){
			map.put("code", StaticKey.ReturnClientNullError);
			map.put("msg", "参数异常");
			return map;
		}
		
		if(!redisService.getValidate(request,userId)){
			map.put("msg", "token失效或错误");
			map.put("code", StaticKey.ReturnClientTokenError);
			return map;
		}
		
		if(pageNo==null){
			pageNo=page;
		}else{
			pageNo=pageNo>1?pageNo:page;
		}
		
//		List<Users> userList = userService.getInviteList(userId,pageNo);
		
		List<Invitation> invitationList = invitationService.getInviteList(userId,pageNo);
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String, Object> item = null;
		for(Invitation invitation:invitationList){
			item=new HashMap<>();
			item.put("nickName", invitation.getChild().getNickName());
			item.put("money", invitation.getMoney());
			if(invitation.getStatus()==StaticKey.InvitationStatusTrue){
				item.put("status", "已进贡");
			}else{
				item.put("status", "挖矿中");
			}
			item.put("addTime", invitation.getAddTime());
			list.add(item);
		}
		map.put("list", list);
		map.put("code", StaticKey.ReturnServerTrue);
		map.put("msg", "success");
		return map;
	}
	
	/**
	 * 邀请分享计数：
	 * @return
	 */
	@RequestMapping(value="/inviteHandle")
	@ResponseBody
	public Map<String,Object> inviteHandle(HttpServletRequest request) {
		Map<String,Object> map=new HashMap<String,Object>();
//		统计每日shareInvite成功分享量
		handleCountService.handleCountPlusOne("shareInvite");
		map.put("code", StaticKey.ReturnServerTrue);
		map.put("msg", "success");
		return map;
	}
	
	
	
 	/**
	 * 查看最新五条广播
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@ResponseBody
	@RequestMapping(value="/broadcastLog")
	public Map<String,Object> broadcastLog(HttpServletRequest request){
		Map<String,Object> map = new HashMap<>();
		List<BroadcastLog> bl = broadcastLogService.findLog();
		List<Map<String,Object>> list = new ArrayList<>();
		Map<String,Object> itmeMap =null;
		for(BroadcastLog b:bl){
			itmeMap=new HashMap<>();
			itmeMap.put("content", b.getContent());
			itmeMap.put("addTime", b.getAddTime());
			itmeMap.put("userName", b.getUser().getNickName());
			itmeMap.put("price", b.getPrice());
			list.add(itmeMap);
		}
		map.put("list", list);
		map.put("code", StaticKey.ReturnServerTrue);
		map.put("msg", "success");
		return map;
	}
	
	
	/**
	 * 新用户绑定手机号
	 * @param request
	 * @param userId
	 * @param phone
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/bindPhone")
	public Map<String,Object> bindPhone(HttpServletRequest request,Integer userId,String phone,String passWord,String phoneCode){
		Map<String,Object> map=new HashMap<String,Object>();
		if(userId==null||phone==null||passWord==null||phoneCode==null){
			map.put("code", StaticKey.ReturnClientNullError);
			map.put("msg", "参数异常");
			return map;
		}
		
		if(!redisService.getValidate(request,userId)){
			map.put("msg", "token失效或错误");
			map.put("code", StaticKey.ReturnClientTokenError);
			return map;
		}
		HttpSession session = request.getSession();
		String lastMobile = (String) session.getAttribute("mobile");
		String lastAuthCode = (String) session.getAttribute("authCode");
		
		System.out.println(phone+","+passWord+","+phoneCode+","+lastMobile+","+lastAuthCode);
		if(lastMobile==null){
			map.put("msg", "session失效");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		if(!lastMobile.equals(phone)){
			map.put("msg", "恶意注册");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		
		if(!lastAuthCode.equals(phoneCode)){
			map.put("msg", "验证码输入错误");
			map.put("code", StaticKey.ReturnUserAuthCodeError);
			return map;
		}
		Users user = userService.findById(userId);
		if(user.getMobile()!=null){
			map.put("msg", "该账号已经绑定手机号码!");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		
		user.setMobile(phone);
		user.setPassWord(passWord);
		userService.updateUser(user);
		//判断绑定手机任务
		TaskLog taskLog = taskLogService.findTask(userId, 2);
		if(taskLog==null){
			taskLog = new TaskLog();
			taskLog.setFinishTime(new Date());
			taskLog.setStatus(1);
			taskLog.setUser(user);
			UserTask ut = userTaskService.findById(2);
			taskLog.setUserTask(ut);//查询出用户完成修改昵称这条记录
			taskLogService.savaTaskLog(taskLog);
		}else if(taskLog.getStatus()==0){
			taskLog.setStatus(1);
			taskLog.setFinishTime(new Date());
			taskLogService.updateTaskLog(taskLog);
		}
		
		map.put("msg", "success");
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
	
	/**
	 * 用户原创作品审核列表：
	 * @param request
	 * @param userId
	 * @return type:  0:文章     1：视频
	 */
	@ResponseBody
	@RequestMapping(value="/originalList")
	public Map<String,Object> originalList(HttpServletRequest request,Integer userId){
		Map<String,Object> map = new HashMap<>();
	/*	if(userId==null||type==null){
			map.put("msg", "有参数为空");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		
		if(!redisService.getValidate(request,userId)){
			map.put("msg", "token失效或错误");
			map.put("code", StaticKey.ReturnClientTokenError);
			return map;
		}*/
		List<Map<String,Object>>  list = new ArrayList<>();
		Map<String,Object> itemMap =null;
	//	if(type==0){
		List<Article> artilceList = articleService.findBySourceId(userId);
		for(Article a:artilceList){
			itemMap = new HashMap<>();
			itemMap.put("img", a.getImgUrl());
			itemMap.put("title", a.getTitle());
			itemMap.put("status", a.getStatus());
			itemMap.put("data", a.getAddTime());
			itemMap.put("type", 0);
			list.add(itemMap);
		 }
//		}else{
			List<Video> videoList = videoService.findBySourceId(userId);
			for(Video a:videoList){
				itemMap = new HashMap<>();
				itemMap.put("videoUrl", a.getVideoUrl());
				itemMap.put("title", a.getTitle());
				itemMap.put("status", a.getStatus());
				itemMap.put("data", a.getAddTime());
				itemMap.put("type", 1);
				list.add(itemMap);
			}
	//	}
		map.put("list", list);
		map.put("code", StaticKey.ReturnServerTrue);
		map.put("msg", "success");
		return map;
	}
	
	
	
	/**
	 *	完成每日任务，领取奖励接口
	 * @param request
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/endTask")
	public Map<String,Object> endTask(HttpServletRequest request,Integer userId,Integer taskId){
		Map<String,Object> map = new HashMap<>();
		if(userId==null||"".equals(userId)){
			map.put("msg", "有参数为空");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		if(!redisService.getValidate(request,userId)){
			map.put("msg", "token失效或错误");
			map.put("code", StaticKey.ReturnClientTokenError);
			return map;
		}
		if(taskId==null||"".equals(taskId)){
			map.put("msg", "有参数为空");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		UserTask ut = userTaskService.findById(taskId);
		if(ut==null){
			map.put("msg", "taskId传值错误");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		Users user = userService.findById(userId);
		TaskLog tl=null;
		if(ut.getType()==1){
			  tl = taskLogService.findDayExist(TimeKit.todayStart(), taskId, userId);
		}else if(ut.getType()==0){
			  tl = taskLogService.findTask(userId, taskId);
		}else{
			map.put("msg", "服务器数据类型出错!");
			map.put("code", StaticKey.ReturnServerNullError);
			return map;
		}
		if(tl==null||tl.getStatus()!=2){
//			if(tl==null||tl.getStatus()!=1){
			map.put("msg", "任务未完成!");
			map.put("code", StaticKey.ReturnServerNullError);
			return map;
		}else{
			if(tl.getObtain()==0){
				tl.setObtain(1);
				taskLogService.updateTaskLog(tl);
				user.setDayMoney(user.getDayMoney()+ut.getMoney());
				user.setTotalMoney(user.getTotalMoney()+ut.getMoney());
				userService.updateUser(user);
				
				FenrunLog fenl = new FenrunLog();
				fenl.setAddTime(new Date());
				fenl.setContentId(taskId);
				fenl.setMoney(ut.getMoney());
				fenl.setType(StaticKey.FenrunDayTask);
				fenl.setUser(user);
				fenrunLogService.saveFenrunLog(fenl);
				map.put("msg", "领取成功!");
				map.put("code", StaticKey.ReturnServerTrue);
				return map;
			}else{
				map.put("msg", "奖励已领取!");
				map.put("code", StaticKey.ReturnServerTrue);
				return map;
			}
			
		}
	}
	
	/**
	 *	验证昵称是否唯一
	 * @param request
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/testNickName")
	public Map<String,Object> testNickName(HttpServletRequest request,Integer userId,String nickName){
		Map<String,Object> map = new HashMap<>();
		if(userId==null||nickName==null){
			map.put("msg", "有参数为空");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		if(!redisService.getValidate(request,userId)){
			map.put("msg", "token失效或错误");
			map.put("code", StaticKey.ReturnClientTokenError);
			return map;
		}
		
		Users user = userService.findByNiceName(nickName);
		if(user!=null){
			map.put("msg", "用户名已存在");
			map.put("code", StaticKey.ReturnUserAccountExist);
			return map;
		}
		map.put("msg", "success");
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
	
	
	/**
	 * 完善个人信息
	 * @param request
	 * @param userId
	 * @param avatar
	 * @param sex
	 * @param province
	 * @param city
	 * @param district
	 * @param birthdate
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/perfectUserInfo")
	public Map<String,Object> perfectUserInfo (HttpServletRequest request,Integer userId,Integer age,
			String avatar,Integer sex,String province,String city,String district,
			String birthdate,String qq,String wechat,Integer getTask){
		Map<String,Object> map = new HashMap<>();
		if(getTask==null || getTask==0){
			map.put("msg", "该用户未领取任务");
			map.put("code", StaticKey.NotReceiveTask);
			return map;
		}
		
		if(userId==null||
				StringKit.isNotEmpty(avatar)||
				StringKit.isNotEmpty(province)||
				StringKit.isNotEmpty(city)||
				StringKit.isNotEmpty(district)||
				StringKit.isNotEmpty(birthdate)||
				age==null||"".equals(age)){
			map.put("msg", "有参数为空");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		if((qq==null||"".equals(qq))&&(StringUtils.isBlank(wechat))){
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
		if(qq!=null||!("".equals(qq))){
			user.setQq(qq);
		}else if(!StringUtils.isBlank(wechat)){
			user.setWechat(wechat);
		}else{
			map.put("msg", "有参数为空");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
	//	avatar=;
		user.setSex(sex);
		user.setAvatar("http://appliaoliao.oss-cn-hangzhou.aliyuncs.com/images/"+avatar+"?x-oss-process=style/blank_style");
		user.setAge(age);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			user.setBirthdate(sdf.parse(birthdate));
		} catch (ParseException e) {
			e.printStackTrace();
			map.put("msg", "时间格式错误");
			map.put("code", StaticKey.ReturnServerNullError);
			return map;
		}
		Province pro = provinceService.findByName(province);
		if(pro==null){
			pro = new Province();
			pro.setName(province);
			provinceService.saveProvince(pro);
		}
		City cit = cityService.findByName(city); 
		if(cit==null){
			cit = new City();
			cit.setName(city);
			cit.setProvince(pro);
			cityService.saveCity(cit);
		}
		District dist = districtService.findByName(district);
		if(dist==null){
			dist = new District();
			dist.setName(district);
			dist.setCity(cit);
			districtService.saveDistrict(dist);
		}
		user.setDistrict(dist);
		userService.updateUser(user);
		//判断修完善资料任务
		TaskLog taskLog = taskLogService.findTask(userId,5);
		if(taskLog==null){
			taskLog = new TaskLog();
			taskLog.setFinishTime(new Date());
			taskLog.setStatus(2);
			taskLog.setUser(user);
			taskLog.setObtain(0);//未领取奖励
			UserTask ut = userTaskService.findById(5);
			taskLog.setUserTask(ut);//查询出用户完善资料这条记录
			taskLogService.savaTaskLog(taskLog);
		}else if(taskLog.getStatus()==1){
			taskLog.setStatus(2);
			taskLog.setFinishTime(new Date());
			taskLogService.updateTaskLog(taskLog);
		}
		
		
		
		map.put("msg", "success");
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
	
	
	/**
	 * 用户个人信息
	 * @param request
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/userInfo")
	public Map<String,Object> userInfo(HttpServletRequest request,Integer userId){
		Map<String,Object> map = new HashMap<>();
		if(userId==null){
			map.put("msg", "有参数为空");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
	/*	if(!redisService.getValidate(request,userId)){
			map.put("msg", "token失效或错误");
			map.put("code", StaticKey.ReturnClientTokenError);
			return map;
		}*/
		Users user = userService.findById(userId);
		if(user==null){
			map.put("msg", "用户不存在");
			map.put("code", StaticKey.ReturnUserAccountNotExist);
			return map;
		}
		
		map.put("age", user.getAge());
		map.put("name", user.getNickName());
		map.put("avatar", user.getAvatar());
		map.put("birthdate", user.getBirthdate());
		map.put("sex", user.getSex());
		if(user.getDistrict()==null){
			map.put("district", "");
			map.put("city", "");
			map.put("province", "");
		}else{
			Integer districtId = user.getDistrict().getId();
			District district = districtService.findById(districtId);
			if(district==null){
				map.put("district", "");
				map.put("city", "");
				map.put("province", "");
			}else{
				map.put("district", district.getName());
				if(district.getCity()==null){
					map.put("city", "");
				}else{
					map.put("city", district.getCity().getName());
					if(district.getCity().getProvince()==null){
						map.put("province", "");
					}else{
						map.put("province", district.getCity().getProvince().getName());
					}
				}
			}
		}
		map.put("qq", user.getQq());
		map.put("wechat", user.getWechat());
		map.put("msg", "success");
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
    
	
	/**
	 * 用户更改头像
	 * @param request
	 * @param userId
	 * @param avatar
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/changeAvatar")
	public Map<String,Object> changeAvatar(HttpServletRequest request,Integer userId,String avatar){
		Map<String,Object> map = new HashMap<>();
		if(userId==null||avatar==null||"".equals(avatar)){
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
			map.put("msg", "用户不存在，请重新刷新!");
			map.put("code", StaticKey.ReturnUserAccountNotExist);
			return map;
		}
	//	avatar=;
		user.setAvatar("http://appliaoliao.oss-cn-hangzhou.aliyuncs.com/images/"+avatar+"?x-oss-process=style/blank_style");
		userService.updateUser(user);
		map.put("msg", "success");
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
	
	
	
	/**
	 * 用户红包记录
	 * @param request
	 * @param userId
	 * @param avatar
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/redPackageLog")
	public Map<String,Object> redPackageLog(HttpServletRequest request,Integer userId){
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
			map.put("msg", "用户不存在，请重新刷新!");
			map.put("code", StaticKey.ReturnUserAccountNotExist);
			return map;
		}
		List<RedPackage> rpList = redPackageService.findListByUserId(userId);
		List<Map<String,Object>> list= new ArrayList<>();
		Map<String,Object> item =null;
		for(RedPackage r:rpList){
			item=new HashMap<>();
			item.put("id", r.getId());
			item.put("money", r.getMoney());
			item.put("addTime", r.getAddTime());
			item.put("number", r.getNumber());
			list.add(item);
		}
		map.put("list", list);
		map.put("msg", "success");
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
	
	
	/**
	 * 用户红包记录详情
	 * @param request
	 * @param userId
	 * @param avatar
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/redPackageLogDetails")
	public Map<String,Object> redPackageLogDetails(HttpServletRequest request,Integer userId,Integer redPackageId){
		Map<String,Object> map = new HashMap<>();
		if(userId==null||redPackageId==null||"".equals(redPackageId)){
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
			map.put("msg", "用户不存在，请重新刷新!");
			map.put("code", StaticKey.ReturnUserAccountNotExist);
			return map;
		}
		List<FenrunLog> fl = fenrunLogService.findRedPackageLog( redPackageId); 
		List<Map<String,Object>> list= new ArrayList<>();
		Map<String,Object> item =null;
		for(FenrunLog f:fl){
			item=new HashMap<>();
			item.put("id", f.getId());
			item.put("money", f.getMoney());
			item.put("addTime", f.getAddTime());
			item.put("name", f.getUser().getNickName());
			list.add(item);
		}
		map.put("list", list);
		map.put("msg", "success");
		map.put("code", StaticKey.ReturnServerTrue);
	   return map;
	}
	
	
	////////////////////////////////////////////////////////////////
	/**
	 * 用户原创作品审核通过列表：
	 * @param request
	 * @param userId
	 */
	@ResponseBody
	@RequestMapping(value="/originalPassedList")
	public Map<String,Object> originalPassedList(HttpServletRequest request,Integer userId){
		Map<String,Object> map = new HashMap<>();
	
		List<Map<String,Object>>  list = new ArrayList<>();
		Map<String,Object> itemMap =null;
		List<Article> artilceList = articleService.findPassedBySourceId(userId);
		for(Article a:artilceList){
			if(a!=null){
				itemMap = new HashMap<>();
				itemMap.put("id", a.getId());
				itemMap.put("img", a.getImgUrl());
				itemMap.put("title", a.getTitle());
				itemMap.put("status", a.getStatus());
				itemMap.put("data", a.getAddTime());
				itemMap.put("type", 0);
				list.add(itemMap);
			}
		 }
			List<Video> videoList = videoService.findPassedBySourceId(userId);
			for(Video a:videoList){
				if(a!=null){
					itemMap = new HashMap<>();
					itemMap.put("id", a.getId());
					itemMap.put("videoUrl", a.getVideoUrl());
					itemMap.put("title", a.getTitle());
					//itemMap.put("status", a.getStatus());
					itemMap.put("data", a.getAddTime());
					itemMap.put("type", 1);
					list.add(itemMap);
				}
			}
		Integer count = null;
		if(list.size()>0){
			count = list.size();
		}	
		map.put("count", count);
		map.put("code", StaticKey.ReturnServerTrue);
		map.put("msg", "success");
		return map;
	}

	
	@ResponseBody
	@RequestMapping(value="/countSignNumProfit")
	public Map<String,Object> countSignNumProfit(HttpServletRequest request,Integer userId){
		Map<String,Object> map = new HashMap<>();
		if(userId==null){
			map.put("code", StaticKey.ReturnClientNullError);
			map.put("msg", "参数为空!");
			return map;
		}
		Users user = userService.findById(userId);
		if(user==null||"".equals(user)){
			map.put("code",StaticKey.ReturnServerNullError);
			map.put("msg", "账号不存在!");
			return map;
		}
		
		Integer totalProfit = fenrunLogService.countSignProfit(userId);
		Integer totalSign = fenrunLogService.countSignNum(userId);
		
		if(totalProfit==null){
			totalProfit=0;
		}
		if(totalSign==null){
			totalSign=0;
		}
		map.put("totalProfit", totalProfit);
		map.put("totalSign", totalSign);
		
		map.put("code", StaticKey.ReturnServerTrue);
		map.put("msg", "success");
		return map;
	}
	
	
	
	
	
	
	
	
}






