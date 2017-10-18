package com.liaoliao.api;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liaoliao.common.service.CommonService;
import com.liaoliao.content.entity.Article;
import com.liaoliao.content.entity.OriginalArticleInfo;
import com.liaoliao.content.entity.OriginalVideoInfo;
import com.liaoliao.content.entity.Video;
import com.liaoliao.content.service.ArticleService;
import com.liaoliao.content.service.OriginalArticleInfoService;
import com.liaoliao.content.service.OriginalVideoInfoService;
import com.liaoliao.content.service.VideoService;
import com.liaoliao.profit.entity.BindPay;
import com.liaoliao.profit.entity.FenrunLog;
import com.liaoliao.profit.entity.ProfitTime;
import com.liaoliao.profit.entity.ToBank;
import com.liaoliao.profit.entity.WeixinPayLog;
import com.liaoliao.profit.service.BindPayService;
import com.liaoliao.profit.service.FenrunLogService;
import com.liaoliao.profit.service.ProfitTimeService;
import com.liaoliao.profit.service.ToBankService;
import com.liaoliao.profit.service.WeixinPayLogService;
import com.liaoliao.redisclient.RedisService;
import com.liaoliao.sys.entity.OriginalProfitLog;
import com.liaoliao.sys.service.HandleCountService;
import com.liaoliao.sys.service.OriginalProfitLogService;
import com.liaoliao.user.entity.RedPackage;
import com.liaoliao.user.entity.UserSign;
import com.liaoliao.user.entity.Users;
import com.liaoliao.user.service.RedPackageService;
import com.liaoliao.user.service.UserService;
import com.liaoliao.user.service.UserSignService;
import com.liaoliao.util.CommonUtil;
import com.liaoliao.util.StaticKey;
import com.liaoliao.util.TimeKit;
import com.liaoliao.weixinPay.Utils.HttpXmlUtils;
import com.liaoliao.weixinPay.Utils.JdomParseXmlUtils;
import com.liaoliao.weixinPay.Utils.ParseXMLUtils;
import com.liaoliao.weixinPay.Utils.RandCharsUtils;
import com.liaoliao.weixinPay.Utils.WXSignUtils;
import com.liaoliao.weixinPay.entity.Unifiedorder;
import com.liaoliao.weixinPay.entity.WXPayResult;

@Controller
@RequestMapping(value="/api")
public class ProfitAction {
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private UserSignService userSignService;
	
	@Autowired
	private FenrunLogService fenrunLogService;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private VideoService videoService;
	
	@Autowired
	private ToBankService toBankService;
	
	@Autowired
	private BindPayService bindPayService;
	
	@Autowired
	private HandleCountService handleCountService;
	
	@Autowired
	private WeixinPayLogService weixinPayLogService;
	
	@Autowired
	private ProfitTimeService profitTimeService;
	
	@Autowired
	private OriginalVideoInfoService originalVideoInfoService;
	
	@Autowired
	private OriginalArticleInfoService originalArticleInfoService;
	
	@Autowired
	private OriginalProfitLogService originalProfitLogService;
	
	@Autowired
	private RedPackageService redPackageService;
	
	private Integer page = 1;
	
	/**
	 * 新用户奖励
	 * @return
	 */
	@RequestMapping(value="/newUserReward")
	@ResponseBody
	public Map<String,Object> newUserReward(HttpServletRequest request,Integer userId){
		Map<String,Object> map=new HashMap<String,Object>();
		if(userId==null){
			map.put("msg", "参数有误");
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
		if(user.getTotalMoney()!=0 || user.getToBankMoney()!=0 || user.getFreezeMoney()!=0
				|| user.getDayMoney()!=0 || user.getPayMoney()!=0){
			map.put("msg", "非新用户");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
//		新用户送料币数
		int newUserRewardCoin = 1000;
		String newUserRewardCoinStr = redisService.getConfigValue("newUserRewardCoin");
		if(StringUtils.isBlank(newUserRewardCoinStr)){
			System.out.println("请检查SystemConfig表数据");
		}else{
			newUserRewardCoin = Integer.valueOf(newUserRewardCoinStr);
		}
//		分钱
		user.setTotalMoney((double)newUserRewardCoin);
//		保存非自身获得的钱记录值
		user.setUnselfMoney((double)newUserRewardCoin);
		userService.updateUser(user);
//		记录分钱
		FenrunLog userLog = new FenrunLog();
		userLog.setMoney(newUserRewardCoin);
		userLog.setAddTime(new Date());
		userLog.setContentId(StaticKey.FenrunContentNewUser);
		userLog.setType(StaticKey.FenrunNewUser);
		userLog.setUser(user);
		fenrunLogService.saveFenrunLog(userLog);
		
		map.put("msg", "success");
		map.put("code", StaticKey.ReturnServerTrue);
		map.put("rewardCoin", newUserRewardCoin);
		return map;
	}
	
	/**
	 * 阅读获得分润
	 * @param request
	 * @param id
	 * @param type : 0:文章   1：视频
	 * @return
	 */
	@RequestMapping(value="/coinByRead")
	@ResponseBody
	public Map<String,Object> coinByRead(HttpServletRequest request,Integer userId,Integer contentId,Integer type){
		Map<String,Object> map=new HashMap<String,Object>();
		
		if(userId==null||contentId==null||type==null||(type!=0&&type!=1)){
			map.put("msg", "参数有误");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		if(!redisService.getValidate(request,userId)){
			map.put("msg", "token失效或错误");
			map.put("code", StaticKey.ReturnClientTokenError);
			return map;
		}
		Users user = userService.findById(userId);
//		如果非会员，检测签到状态
		if(user.getVipStatus()!=StaticKey.UserVipStatusTrue){
			UserSign userSign = userSignService.findById(userId);
//			从未签到
			if(userSign==null){
				map.put("msg", "未签到");
				map.put("code", StaticKey.ReturnUserSignError);
				return map;
			}
			long lastSignTime = userSign.getSignTime().getTime();
			long todayTime = TimeKit.todayStart().getTime();
//			今日未签到
			if(lastSignTime<todayTime){
				map.put("msg", "未签到");
				map.put("code", StaticKey.ReturnUserSignError);
				return map;
			}
		}
		if(type==0){
			Article article = articleService.findById(contentId);
			if(article==null){
				map.put("msg", "文章不存在");
				map.put("code", StaticKey.ReturnServerNullError);
				return map;
			}
			OriginalArticleInfo oai=originalArticleInfoService.findByArticleId(article.getId());
			if(article.getType()==1){
				if(oai.getCountMoney()>0){
					Users originalUser = userService.findById(article.getSourceId());//获取原创作品作者
					if(originalUser!=null){
						Integer money=StaticKey.OriginalReadMoney;
						if(originalUser.getVipStatus()==StaticKey.UserVipStatusTrue){
							money=StaticKey.OriginalReadMoney*2;
						}
						originalUser.setTotalMoney(originalUser.getTotalMoney()+money);
						originalUser.setDayMoney(originalUser.getDayMoney()+money);
						oai.setCountMoney(oai.getCountMoney()-money);
						userService.updateUser(originalUser);
						originalArticleInfoService.updateOAI(oai);
						OriginalProfitLog opl = new OriginalProfitLog();
						opl.setAddTime(new Date());
						opl.setContentId(article.getId());
						opl.setType(0);
						opl.setMoney(money);
						opl.setUser(originalUser);
						originalProfitLogService.saveOriginalProfitLog(opl);
					}
				}
			}
		}else if(type==1){
			Video video = videoService.findById(contentId);
			if(video==null){
				map.put("msg", "视频不存在");
				map.put("code", StaticKey.ReturnServerNullError);
				return map;
			}
			
			OriginalVideoInfo ovi=originalVideoInfoService.findByVideoId(video.getId());
			if(video.getType()==1){
				if(ovi.getCountMoney()>0){
					Users originalUser = userService.findById(video.getSourceId());//获取原创作品作者
					if(originalUser!=null){
						Integer money=StaticKey.OriginalReadMoney;
						if(originalUser.getVipStatus()==StaticKey.UserVipStatusTrue){
							money=StaticKey.OriginalReadMoney*2;
						}
						originalUser.setTotalMoney(originalUser.getTotalMoney()+money);
						originalUser.setDayMoney(originalUser.getDayMoney()+money);
						ovi.setCountMoney(ovi.getCountMoney()-money);
						userService.updateUser(originalUser);
						originalVideoInfoService.updateOVI(ovi);
						OriginalProfitLog opl = new OriginalProfitLog();
						opl.setAddTime(new Date());
						opl.setContentId(video.getId());
						opl.setType(0);
						opl.setMoney(money);
						opl.setUser(originalUser);
						originalProfitLogService.saveOriginalProfitLog(opl);
					}
				}
			}
			
		}else{
			map.put("msg", "类型错误");
			map.put("code", StaticKey.ReturnServerNullError);
			return map;
		}

//		检测上次分润时间
		ProfitTime profitTime = profitTimeService.findByUserId(userId);
		if(profitTime==null){
			profitTime = new ProfitTime();
			profitTime.setUserId(userId);
			profitTime.setAddTime(new Date());
			profitTimeService.saveOrUpdateProfitTime(profitTime);
		}else{
			long nowTime = new Date().getTime();
			long lastTime = profitTime.getAddTime().getTime();
			long cooldownTime = nowTime-lastTime;
//			阅读文章获取收益时间
			int articleCoinTime = 99;
			String articleCoinTimeStr = redisService.getConfigValue("articleCoinTime");
			if(StringUtils.isBlank(articleCoinTimeStr)){
				System.out.println("请检查SystemConfig表数据");
			}else{
				articleCoinTime = Integer.valueOf(articleCoinTimeStr);
			}
			
			if(cooldownTime < articleCoinTime*1000){
				map.put("msg", "呀，红包溜走了");
				map.put("code", StaticKey.ReturnCoinRunAway);
				return map;
			}
			profitTime.setAddTime(new Date());
			profitTimeService.saveOrUpdateProfitTime(profitTime);
		}
		
		//呀，红包溜走了
		int randomInt = ThreadLocalRandom.current().nextInt(0, 99);
//		阅读文章&播放视频 红包溜走的概率
		double coinRunAway = 0.0;
		String coinRunAwayStr = redisService.getConfigValue("coinRunAway");
		if(StringUtils.isBlank(coinRunAwayStr)){
			System.out.println("请检查SystemConfig表数据");
		}else{
			coinRunAway = Double.valueOf(coinRunAwayStr);
		}
		
		if(randomInt < 100*coinRunAway){
			map.put("msg", "呀，红包溜走了");
			map.put("code", StaticKey.ReturnCoinRunAway);
			return map;
		}
		
		FenrunLog fenrun=new FenrunLog();
		double money=0.0;
		int coin = 0;

		if(type==0){
//			阅读文章最小收益值
			int minArticleMoney = 1;
			String minArticleMoneyStr = redisService.getConfigValue("minArticleMoney");
			if(StringUtils.isBlank(minArticleMoneyStr)){
				System.out.println("请检查SystemConfig表数据");
			}else{
				minArticleMoney = Integer.valueOf(minArticleMoneyStr);
			}
//			阅读文章最大收益值
			int maxArticleMoney = 1;
			String maxArticleMoneyStr = redisService.getConfigValue("maxArticleMoney");
			if(StringUtils.isBlank(maxArticleMoneyStr)){
				System.out.println("请检查SystemConfig表数据");
			}else{
				maxArticleMoney = Integer.valueOf(maxArticleMoneyStr);
			}
			
			coin = ThreadLocalRandom.current().nextInt(minArticleMoney, maxArticleMoney);
			money=user.getTotalMoney()+coin;
			fenrun.setType(StaticKey.FenrunArticle);
			
		}else{
//			阅读视频最小收益值
			int minVideoMoney = 1;
			String minVideoMoneyStr = redisService.getConfigValue("minVideoMoney");
			if(StringUtils.isBlank(minVideoMoneyStr)){
				System.out.println("请检查SystemConfig表数据");
			}else{
				minVideoMoney = Integer.valueOf(minVideoMoneyStr);
			}
//			阅读视频最大收益值
			int maxVideoMoney = 1;
			String maxVideoMoneyStr = redisService.getConfigValue("maxVideoMoney");
			if(StringUtils.isBlank(maxVideoMoneyStr)){
				System.out.println("请检查SystemConfig表数据");
			}else{
				maxVideoMoney = Integer.valueOf(maxVideoMoneyStr);
			}
			
			coin = ThreadLocalRandom.current().nextInt(minVideoMoney, maxVideoMoney);
			money=user.getTotalMoney()+coin;
			fenrun.setType(StaticKey.FenrunVideo);
		}
		//如果是会员，获取金额翻倍
		//获取vip阅读倍数：
		int userVipGetMoney = 1;
		String userVipGetMoneyStr = redisService.getConfigValue("userVipGetMoney");
		if(StringUtils.isBlank(userVipGetMoneyStr)){
			System.out.println("请检查SystemConfig表数据");
		}else{
			userVipGetMoney = Integer.valueOf(userVipGetMoneyStr);
		}
//		vip
		if(user.getVipStatus() == StaticKey.UserVipStatusTrue){
			user.setTotalMoney(user.getTotalMoney()+coin*userVipGetMoney);
			if(user.getParent()!=null){
				user.setDayMoney(user.getDayMoney()+coin*userVipGetMoney);
			}
//			更新最后使用时间
			user.setLoginTime(new Date());
			fenrun.setMoney(coin*userVipGetMoney);
			map.put("coin", coin*userVipGetMoney);
//			统计每日分润料币总金额
			handleCountService.handleCountTotalMoney("totalProfitMoney",coin*userVipGetMoney);
		}else{
//		非vip
			user.setTotalMoney(money);
			if(user.getParent()!=null){
				user.setDayMoney(user.getDayMoney()+coin);
			}
//			更新最后使用时间
			user.setLoginTime(new Date());
			fenrun.setMoney(coin);
			map.put("coin", coin);
//			统计每日分润料币总金额
			handleCountService.handleCountTotalMoney("totalProfitMoney",coin);
		}
		
		fenrun.setContentId(contentId);
		fenrun.setUser(user);
		fenrun.setAddTime(new Date());
//		更新用户、保存分润记录
		commonService.updateUserAndSaveProfitLog(fenrun,user);
		
//		统计每日profitSuccess成功分润次数
		handleCountService.handleCountPlusOne("profitSuccess");
		
		map.put("msg", "success");
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
	
	
	/**
	 * 用户抢vip用户登录时的红包
	 * @param request
	 * @param userId
	 * @param vipId
	 * @return
	 */
	@RequestMapping(value="/grabHongbao")
	@ResponseBody
	public Map<String,Object> grabHongbao(HttpServletRequest request,Integer userId,Integer vipId){
		Map<String,Object> map=new HashMap<String,Object>();
		if(userId==null||"".equals(userId)||vipId==null||"".equals(vipId)){
			map.put("msg", "参数异常");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		
		if(!redisService.getValidate(request,userId)){
			map.put("msg", "token失效或错误");
			map.put("code", StaticKey.ReturnClientTokenError);
			return map;
		}
		if(userId==vipId){
			map.put("msg", "好可惜，红包飞走了~");
			map.put("code", StaticKey.ReturnCoinRunAway);
			return map;
		}
		
//		土豪登场每时间间隔内能被抢红包次数
		String grabHongbaoTimes = redisService.getConfigValue("grabHongbaoTimes");
//		土豪登场再次能被抢红包冷却时间(小时)
		double grabHongbaoFreeze = 24;
		String grabHongbaoFreezeStr = redisService.getConfigValue("grabHongbaoFreeze");
		if(StringUtils.isBlank(grabHongbaoFreezeStr)){
			System.out.println("请检查SystemConfig表数据");
		}else{
			grabHongbaoFreeze = Double.valueOf(grabHongbaoFreezeStr);
		}
		
		long expiredTime = (long) grabHongbaoFreeze*60*60;
		String grabHongbaoSize = redisService.get("GrabHongbao_"+vipId);
		
		if(StringUtils.isBlank(grabHongbaoSize)){
			redisService.set("GrabHongbao_"+vipId, grabHongbaoTimes, expiredTime);
			grabHongbaoSize = grabHongbaoTimes;
		}
		
		int grabSize = Integer.valueOf(grabHongbaoSize)-1;
		
		redisService.set("GrabHongbao_"+vipId, String.valueOf(grabSize), expiredTime);
		
		if(grabSize < 0){
			map.put("msg", "好可惜，红包飞走了~");
			map.put("code", StaticKey.ReturnCoinRunAway);
			return map;
		}
		
		Users user=userService.findById(userId);
		Users vipUser=userService.findById(vipId);
		if(user == null || vipUser == null){
			map.put("msg", "好可惜，红包飞走了~");
			map.put("code", StaticKey.ReturnCoinRunAway);
			return map;
		}
//		土豪登场土豪剩余料币低于多少值时不可抢
		int grabHongbaoBalance = 0;
		String grabHongbaoBalanceStr = redisService.getConfigValue("grabHongbaoBalance");
		if(StringUtils.isBlank(grabHongbaoBalanceStr)){
			System.out.println("请检查SystemConfig表数据");
		}else{
			grabHongbaoBalance = Integer.valueOf(grabHongbaoBalanceStr);
		}
		if((vipUser.getTotalMoney()-vipUser.getPayMoney()-vipUser.getToBankMoney()-vipUser.getFreezeMoney()) <= grabHongbaoBalance){
			map.put("msg", "好可惜，红包飞走了~");
			map.put("code", StaticKey.ReturnCoinRunAway);
			return map;
		}
//		抢土豪红包最小值
		int grabHongbaoMin = 0;
		String grabHongbaoMinStr = redisService.getConfigValue("grabHongbaoMin");
		if(StringUtils.isBlank(grabHongbaoMinStr)){
			System.out.println("请检查SystemConfig表数据");
		}else{
			grabHongbaoMin = Integer.valueOf(grabHongbaoMinStr);
		}
		
//		抢土豪红包最大值
		int grabHongbaoMax = 0;
		String grabHongbaoMaxStr = redisService.getConfigValue("grabHongbaoMax");
		if(StringUtils.isBlank(grabHongbaoMaxStr)){
			System.out.println("请检查SystemConfig表数据");
		}else{
			grabHongbaoMax = Integer.valueOf(grabHongbaoMaxStr);
		}
		
		Integer grabMoney = ThreadLocalRandom.current().nextInt(grabHongbaoMin, grabHongbaoMax);
		if(grabMoney==0){
			map.put("msg", "好可惜，红包飞走了~");
			map.put("code", StaticKey.ReturnCoinRunAway);
			return map;
		}
		
		if((vipUser.getTotalMoney()-vipUser.getPayMoney()-vipUser.getToBankMoney()-vipUser.getFreezeMoney())<grabMoney){
			map.put("msg", "好可惜，红包飞走了~");
			map.put("code", StaticKey.ReturnCoinRunAway);
			return map;
		}
		
		vipUser.setPayMoney(vipUser.getPayMoney()+grabMoney);
		FenrunLog VipLog = new FenrunLog();
		VipLog.setMoney(-grabMoney);
		VipLog.setAddTime(new Date());
		VipLog.setContentId(user.getId());
		VipLog.setType(StaticKey.FenrunGrabVip);
		VipLog.setUser(vipUser);
		
		user.setTotalMoney(user.getTotalMoney()+grabMoney);
		FenrunLog userLog = new FenrunLog();
		userLog.setMoney(grabMoney);
		userLog.setAddTime(new Date());
		userLog.setContentId(vipUser.getId());
		userLog.setType(StaticKey.FenrunGrabUser);
		userLog.setUser(user);
		
		//更新用户信息以及保存记录
		commonService.updateUserAndSaveProfitLog(VipLog,vipUser);
		commonService.updateUserAndSaveProfitLog(userLog,user);
//		统计每日graspSuccess成功抢红包次数
		handleCountService.handleCountPlusOne("graspSuccess");
		map.put("money", grabMoney);
		map.put("msg", "success");
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
	
	
	/**
	 * 用户抢土豪发放的世界红包
	 * @param request
	 * @param userId
	 * @param vipId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/grabRedPackage")
	public Map<String,Object> grabRedPackage(HttpServletRequest request,Integer userId,Integer richId){
		Map<String,Object> map=new HashMap<String,Object>();
		if(userId==null||"".equals(userId)||richId==null||"".equals(richId)){
			map.put("msg", "参数异常");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		if(!redisService.getValidate(request,userId)){
			map.put("msg", "token失效或错误");
			map.put("code", StaticKey.ReturnClientTokenError);
			return map;
		}
		/*if(userId==richId){
			map.put("msg", "好可惜，红包飞走了~");
			map.put("code", StaticKey.ReturnCoinRunAway);
			return map;
		}*/
		List<Integer> list = StaticKey.redPackagelist;
		if(list==null||list.size()<=0){
			map.put("msg", "好可惜，红包飞走了~");
			map.put("code", StaticKey.ReturnCoinRunAway);
			return map;
		}
		RedPackage rp = redPackageService.findByUserId(richId);
		if(rp==null||rp.getBalance()<=0){
			map.put("msg", "好可惜，红包飞走了~");
			map.put("code", StaticKey.ReturnCoinRunAway);
			return map;
		}
		
		FenrunLog fl = fenrunLogService.findByRedPackageId(userId,rp.getId());
		if(fl!=null){
			map.put("msg", "您已抢过这个红包了!");
			map.put("code", StaticKey.ReturnCoinRunAway);
			return map;
		}
		Users user=userService.findById(userId);
		Users vipUser=userService.findById(richId);
		if(user == null || vipUser == null){
			map.put("msg", "好可惜，红包飞走了~");
			map.put("code", StaticKey.ReturnCoinRunAway);
			return map;
		}
		
		int random = (int)Math.floor(Math.random()*list.size());
		Integer grabMoney = list.get(random);//随机到的金额
		if(list.size()==1){
			rp.setStatus(0);
		}
		rp.setBalance(rp.getBalance()-grabMoney);
		redPackageService.updateRedPackage(rp);
		
		list.remove(random);//将随机到的金额从list里面删除
		
		user.setTotalMoney(user.getTotalMoney()+grabMoney);
		FenrunLog userLog = new FenrunLog();
		userLog.setMoney(grabMoney);
		userLog.setAddTime(new Date());
		userLog.setContentId(rp.getId());
		userLog.setType(StaticKey.FenrunGrabUser);
		userLog.setUser(user);
		
		//更新用户信息以及保存记录
		commonService.updateUserAndSaveProfitLog(userLog,user);
		map.put("money", grabMoney);
		map.put("msg", "success");
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
	
	
	/**
	 * 料币提现界面：展示余额
	 * @return
	 */
	@RequestMapping(value="/toTheBank")
	@ResponseBody
	public Map<String,Object> toTheBank(HttpServletRequest request,Integer userId){
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
		
		double balanceMoney = user.getTotalMoney()-user.getFreezeMoney()-user.getPayMoney()-user.getToBankMoney();
		DecimalFormat df = new DecimalFormat("#.##");    
		balanceMoney = Double.parseDouble(df.format(balanceMoney));
		
//		可提现人民币额度1、2
		String toBankMoneyStr1 = redisService.getConfigValue("toBankMoney1");
		String toBankMoneyStr2 = redisService.getConfigValue("toBankMoney2");
//		料币兑换率
		String liaoCoinRateStr = redisService.getConfigValue("liaoCoinRate");
		
		if(StringUtils.isBlank(toBankMoneyStr1)||StringUtils.isBlank(toBankMoneyStr2)||StringUtils.isBlank(liaoCoinRateStr)){
			map.put("msg", "error");
			map.put("code", StaticKey.ReturnServerNullError);
			return map;
		}
//		提现额度
		int toBankMoney1 = Integer.valueOf(toBankMoneyStr1);
		int toBankMoney2 = Integer.valueOf(toBankMoneyStr2);
//		料币兑换率
		double liaoCoinRate = Double.valueOf(liaoCoinRateStr);
		
		BindPay bindPay = bindPayService.findByUser(user);
		if(bindPay==null){
			map.put("bindPayStatus", StaticKey.ReturnBindPayFalse);
		}else{
			map.put("bindPayStatus", StaticKey.ReturnBindPayTrue);
			map.put("trueName", bindPay.getTrueName());
			map.put("payAccount", bindPay.getPayAccount());
		}
		
//		统计每日tobankShow展示次数
//		handleCountService.handleCountPlusOne("tobankShow");
		map.put("msg", "success");
		map.put("code", StaticKey.ReturnServerTrue);
		if(balanceMoney<=0){
			map.put("userMoney", 0);
		}else
		map.put("userMoney", (int)balanceMoney);
		map.put("toBankOne", toBankMoney1);
		map.put("toBankTwo", toBankMoney2);
		map.put("liaoCoinRate", liaoCoinRate);
		return map;
	}
	
	/**
	 * 料币提现提交
	 * @return
	 */
	@RequestMapping(value="/withdrawCash")
	@ResponseBody
	public Map<String,Object> withdrawCash(HttpServletRequest request,Integer userId,Integer type){
		Map<String,Object> map=new HashMap<>();
		if(userId==null||type==null){
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
		
		int toBankMoney = 0;
		if(type==1){
//			料币兑换人民币额度一
			String toBankMoneyStr1 = redisService.getConfigValue("toBankMoney1");
			if(StringUtils.isBlank(toBankMoneyStr1)){
				map.put("msg", "error");
				map.put("code", StaticKey.ReturnServerNullError);
				return map;
			}
			toBankMoney = Integer.valueOf(toBankMoneyStr1);
			
		}
		if(type==2){
//			料币兑换人民币额度二
			String toBankMoneyStr2 = redisService.getConfigValue("toBankMoney2");
			if(StringUtils.isBlank(toBankMoneyStr2)){
				map.put("msg", "error");
				map.put("code", StaticKey.ReturnServerNullError);
				return map;
			}
			toBankMoney = Integer.valueOf(toBankMoneyStr2);
			
		}

		
		double balanceMoney = user.getTotalMoney()-user.getFreezeMoney()-user.getPayMoney()-user.getToBankMoney();
		DecimalFormat df = new DecimalFormat("#.##");
		balanceMoney = Double.parseDouble(df.format(balanceMoney));
//		料币兑换率
		double liaoCoinRate = 0.001;
		String liaoCoinRateStr = redisService.getConfigValue("liaoCoinRate");
		if(StringUtils.isBlank(liaoCoinRateStr)){
			System.out.println("请检查SystemConfig表数据");
		}else{
			liaoCoinRate = Double.valueOf(liaoCoinRateStr);
		}
		
		int toBankLiaoCoin = (int) (toBankMoney/liaoCoinRate);
		
		if((int)balanceMoney < toBankLiaoCoin){
			map.put("msg", "余额不足！");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
//		更新提现料币总数
		double newFreezeMoney = user.getFreezeMoney()+toBankLiaoCoin;
		user.setFreezeMoney(newFreezeMoney);
		userService.updateUser(user);
		
//		添加提现记录
		ToBank toBank = new ToBank();
		toBank.setToBankMoney(toBankMoney);
		toBank.setToBankCoin(toBankLiaoCoin);
		toBank.setToBankStatus(StaticKey.ToBankStatusFalse);
		toBank.setUser(user);
		toBank.setAddTime(new Date());
		toBank.setAddIp(CommonUtil.getIpAddr(request));
		toBankService.saveToBank(toBank);
		
//		添加分润记录
		FenrunLog fenrunLog = new FenrunLog();
		fenrunLog.setMoney(-toBankLiaoCoin);
		fenrunLog.setContentId(StaticKey.FenrunContentToBank);
		fenrunLog.setType(StaticKey.FenrunToBank);
		fenrunLog.setUser(user);
		fenrunLog.setAddTime(new Date());
		fenrunLogService.saveFenrunLog(fenrunLog);
//		微信提现二维码url
		String wechatUrl = redisService.getConfigValue("wechatUrl");
		
//		统计每日drawMoney提现次数
//	handleCountService.handleCountPlusOne("drawMoney");
		map.put("msg", "success");
		map.put("code", StaticKey.ReturnServerTrue);
		map.put("wechatUrl", wechatUrl);
		return map;
	}
	
	/**
	 * 料币提现记录列表
	 * @return
	 */
	@RequestMapping(value="/toTheBankLog")
	@ResponseBody
	public Map<String,Object> toTheBankLog(HttpServletRequest request,Integer userId,Integer pageNo){
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
		
		if(pageNo==null){
			pageNo=page;
		}else{
			pageNo=pageNo>1?pageNo:page;
		}
		
		Users user = userService.findById(userId);
		if(user==null){
			map.put("msg", "用户不存在！");
			map.put("code", StaticKey.ReturnUserAccountError);
			return map;
		}
		
		List<ToBank> list = toBankService.findByUser(user,pageNo);
		List<Map<String,Object>> datas=new ArrayList<>();
		Map<String, Object> item = null;
		for(ToBank toBank:list){
			item=new HashMap<>();
			item.put("toBankMoney", toBank.getToBankMoney());
			item.put("toBnakStatus", toBank.getToBankStatus());
			item.put("addTime", toBank.getAddTime());
			datas.add(item);
		}
		
		Long toBankCount = toBankService.findToBankCountByUser(user);
		if(toBankCount==null){
			toBankCount = 0L;
		}
		map.put("toBankCount", toBankCount);
		map.put("datas", datas);
		map.put("msg", "success");
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
	
	
	/**
	 * 绑定提现账号
	 * @return
	 */
	@RequestMapping(value="/bindPayAccount")
	@ResponseBody
	public Map<String,Object> bindPayAccount(HttpServletRequest request, Integer userId, String trueName, String payAccount){
		Map<String,Object> map=new HashMap<>();
		if(userId==null||StringUtils.isBlank(payAccount)||StringUtils.isBlank(trueName)){
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
		
		BindPay bindPayUser = bindPayService.findByUser(user);
		if(bindPayUser!=null){
			map.put("msg", "此用户已绑定！");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		
		BindPay bindPayAccount = bindPayService.findByPayAccount(payAccount);
		if(bindPayAccount!=null){
			map.put("msg", "此提现账号已被其他料料用户绑定");
			map.put("code", StaticKey.ReturnBindPayError);
			return map;
		}
		
		BindPay bindPay = new BindPay();
		bindPay.setUser(user);
		bindPay.setPayAccount(payAccount);
		bindPay.setTrueName(trueName);
		bindPay.setAddTime(new Date());
		bindPay.setAddIp(CommonUtil.getIpAddr(request));
		bindPayService.saveBindPay(bindPay);
		
		map.put("msg", "success");
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
	
	
	/**
	 * 获取用户的分润记录
	 * @param request
	 * @param userId
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value="/getFenrunLog")
	@ResponseBody
	public Map<String,Object> getFenrunLog(HttpServletRequest request,Integer userId,Integer pageNo){
		Map<String,Object> map=new HashMap<String,Object>();
		if(userId==null||"".equals(userId)){
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
		
		if(pageNo==null){
			pageNo=page;
		}else{
			pageNo=pageNo>1?pageNo:page;
		}
		List<FenrunLog> list=fenrunLogService.findByUserId(userId,pageNo);
		List<Map<String,Object>> datas=new ArrayList<>();
		Map<String, Object> item = null;
		for(FenrunLog fl:list){
			item=new HashMap<>();
			if(fl.getType()==StaticKey.FenrunArticle){
				item.put("info", "阅读料料头条");
			}
			if(fl.getType()==StaticKey.FenrunVideo){
				item.put("info", "观看料料视频");
			}
			if(fl.getType()==StaticKey.FenrunToBank){
				item.put("info", "料币提现兑换");
			}
			if(fl.getType()==StaticKey.FenrunSign){
				item.put("info", "每日签到");
			}
			if(fl.getType()==StaticKey.FenrunBroadcast){
				item.put("info", "发布广播");
			}
			if(fl.getType()==StaticKey.FenrunGrabUser){
				item.put("info", "抢土豪红包");
			}
			if(fl.getType()==StaticKey.FenrunGrabVip){
				item.put("info", "被屌丝掠夺");
			}
			if(fl.getType()==StaticKey.FenrunInvitation){
				item.put("info", "邀请好友");
			}
			if(fl.getType()==StaticKey.FenrunNewUser){
				item.put("info", "新手红包");
			}
			if(fl.getType()==StaticKey.FenrunInvitation){
				item.put("info", "小奴隶上交");
			}
			if(fl.getType()==StaticKey.FenrunDayFenrun){
				item.put("info", "矿工进贡");
			}
			if(fl.getType()==StaticKey.FenrunPassVideo){
				item.put("info", "视频审核通过");
			}
			if(fl.getType()==StaticKey.FenrunPassArticle){
				item.put("info", "文章审核通过");
			}
			if(fl.getType()==StaticKey.FenrunOriginalArticle){
				item.put("info", "原创文章收益");
			}
			if(fl.getType()==StaticKey.FenrunOriginalVideo){
				item.put("info", "原创视频收益");
			}
			if(fl.getType()==StaticKey.FenrunDayTask){
				item.put("info", "料料任务奖励");
			}
			if(fl.getType()==StaticKey.FenrunOriginalSum){
				item.put("info", "原创作品收益");
			}
			if(fl.getType()==StaticKey.FenrunWorldRedPackage){
				item.put("info", "土豪世界发红包啦");
			}
			if(fl.getType()==StaticKey.FenrunLookUser){
				item.put("info", "约ta~");
			}
			if(fl.getType()==StaticKey.FenrunRefund){
				item.put("info", "红包退款");
			}
			item.put("money", fl.getMoney()/*+"料币"*/);
			item.put("addTime", fl.getAddTime());
			item.put("type", fl.getType());
			item.put("id", fl.getId());
			datas.add(item);
		}
		
//		余额
		double balanceMoney = user.getTotalMoney()-user.getFreezeMoney()-user.getPayMoney()-user.getToBankMoney();
//		DecimalFormat df = new DecimalFormat("#.##");
//		balanceMoney = Double.parseDouble(df.format(balanceMoney));
		
//		统计每日profitList展示次数
		handleCountService.handleCountPlusOne("profitList");
		map.put("datas", datas);
		map.put("userMoney", (int)balanceMoney);
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}

	
	/**
	 * 获取用户原创分润记录
	 * @param request
	 * @param userId
	 * @param pageNo
	 * @return
	 */
	@RequestMapping(value="/getOriginalLog")
	@ResponseBody
	public Map<String,Object> getOriginalLog(HttpServletRequest request,Integer userId,Integer pageNo){
		Map<String,Object> map=new HashMap<String,Object>();
	/*	if(userId==null||"".equals(userId)){
			map.put("msg", "参数异常");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		if(!redisService.getValidate(request,userId)){
			map.put("msg", "token失效或错误");
			map.put("code", StaticKey.ReturnClientTokenError);
			return map;
		}*/
		
		Users user = userService.findById(userId);
		if(user==null){
			map.put("msg", "用户不存在！");
			map.put("code", StaticKey.ReturnUserAccountError);
			return map;
		}
		
		if(pageNo==null){
			pageNo=page;
		}else{
			pageNo=pageNo>1?pageNo:page;
		}
		List<FenrunLog> list=fenrunLogService.findOriginalLogByUserId(userId,pageNo);
		//System.out.println(userId);
		List<Map<String,Object>> datas=new ArrayList<>();
		Map<String, Object> item = null;
		for(FenrunLog fl:list){
			item=new HashMap<>();
			if(fl.getType()==StaticKey.FenrunOriginalArticle){
				Article a = articleService.findById(fl.getContentId());
				item.put("type", 0);
				item.put("title",a.getTitle());
			}else{
				Video a = videoService.findById(fl.getContentId());
				item.put("type", 1);
				item.put("title",a.getTitle());
			}
			item.put("money", fl.getMoney()/*+"料币"*/);
			datas.add(item);
		}
		
		map.put("datas", datas);
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
	
	
	
	/**
	 * 进入开通会员界面
	 * @return
	 */
	@RequestMapping(value="/vipShow")
	@ResponseBody
	public Map<String,Object> chongzhiVIP(HttpServletRequest request){
		Map<String,Object> map=new HashMap<>();

//		统计每日vipShow展示次数
		handleCountService.handleCountPlusOne("vipShow");
		
		map.put("code", StaticKey.ReturnServerTrue);
		map.put("msg", "success");
		return map;
	}
	
	
	/**
	 * 微信统一下单
	 * @param request
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/weixinPay")
	@ResponseBody
	public Map<String,Object> weixinPay(HttpServletRequest request,Integer userId){
		String ip = CommonUtil.getIpAddr(request);
		Map<String,Object> map = new HashMap<>();
		//参数组
		String appid = StaticKey.APPID;
		String mch_id = StaticKey.MCH_ID;
		String nonce_str = RandCharsUtils.getRandomString(32);
		String body = StaticKey.Body;
		String detail = StaticKey.Detail; //商品名称明细列表
		String attach = StaticKey.Attach; //附加参数 
		String out_trade_no = RandCharsUtils.getRandomString(32);
		int total_fee = StaticKey.TotalFee;
		String spbill_create_ip = ip;
		String time_start = RandCharsUtils.timeStart();
		String time_expire = RandCharsUtils.timeExpire();
		String notify_url = StaticKey.notify_url; //回调函数
		String trade_type = StaticKey.TradType;
		
		//参数：开始生成签名
		SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
		parameters.put("appid", appid);
		parameters.put("mch_id", mch_id);
		parameters.put("nonce_str", nonce_str);
		parameters.put("body", body);
		parameters.put("nonce_str", nonce_str);
		parameters.put("detail", detail);
		parameters.put("attach", attach);
		parameters.put("out_trade_no", out_trade_no);
		parameters.put("total_fee", total_fee);
		parameters.put("time_start", time_start);
		parameters.put("time_expire", time_expire);
		parameters.put("notify_url", notify_url);
		parameters.put("trade_type", trade_type);
		parameters.put("spbill_create_ip", spbill_create_ip);
		//第一次签名
		String sign = WXSignUtils.createSign("UTF-8", parameters);
		
		//存放数据，发送到微信
		Unifiedorder unifiedorder = new Unifiedorder();
		unifiedorder.setAppid(appid);
		unifiedorder.setMch_id(mch_id);
		unifiedorder.setNonce_str(nonce_str);
		unifiedorder.setSign(sign);
		unifiedorder.setBody(body);
		unifiedorder.setDetail(detail);
		unifiedorder.setAttach(attach);
		unifiedorder.setOut_trade_no(out_trade_no);
		unifiedorder.setTotal_fee(total_fee);
		unifiedorder.setSpbill_create_ip(spbill_create_ip);
		unifiedorder.setTime_start(time_start);
		unifiedorder.setTime_expire(time_expire);
		unifiedorder.setNotify_url(notify_url);
		unifiedorder.setTrade_type(trade_type);
		//构造xml参数
		String xmlInfo = HttpXmlUtils.xmlInfo(unifiedorder);
		String wxUrl = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		String method = "POST";
		//发送到微信,统一下单，并验证参数签名
		String weixinPost = HttpXmlUtils.httpsRequest(wxUrl, method, xmlInfo).toString();
		
		//获取返回值之后进行二次签名
		Map<String,Object> weixinMap=ParseXMLUtils.jdomParseXml(weixinPost);
		String prepay_id=(String) weixinMap.get("prepay_id");
		String return_msg=(String) weixinMap.get("return_msg");
		if(prepay_id==null){
			map.put("code",StaticKey.ReturnServerNullError);
			map.put("msg", return_msg);
			return map;
		}
		
		//第二次签名
		SortedMap<Object,Object> parametersAgain = new TreeMap<Object,Object>();
		Long timestamp=System.currentTimeMillis()/1000L;
		parametersAgain.put("appid", appid);
		parametersAgain.put("partnerid", StaticKey.MCH_ID);
		parametersAgain.put("prepayid", weixinMap.get("prepay_id"));
		parametersAgain.put("noncestr", nonce_str);
		parametersAgain.put("timestamp", timestamp);
		parametersAgain.put("package", "Sign=WXPay");
		//二次签名
		String signAgain = WXSignUtils.createSign("UTF-8", parametersAgain);
		
		Users user = userService.findById(userId);
		
		//创建订单号
		WeixinPayLog weixinPaylog = new WeixinPayLog();
		weixinPaylog.setBody(body);
		weixinPaylog.setUser(user);
		weixinPaylog.setSpbillCreateIp(ip);
		weixinPaylog.setStatus(StaticKey.WeixinPayFail);
		weixinPaylog.setTotalFee(total_fee);
		weixinPaylog.setTradeType(trade_type);
		weixinPaylog.setOutTradeNo(out_trade_no);
		weixinPaylog.setAddTime(new Date());
		weixinPayLogService.saveWeixinPayLog(weixinPaylog);
		
		map.put("prepay_id", weixinMap.get("prepay_id"));
		map.put("nonce_str", nonce_str);
		map.put("sign", signAgain);
		map.put("TimeStamp", timestamp);
		map.put("code",StaticKey.ReturnServerTrue);
		map.put("msg", "success");
		
		return map;
	}
	
	/** 
     * 微信支付回调 
     * @param request 
     * @param resposne 
     */  
    @RequestMapping(value="/notifyUrlWeixin")  
    public void notifyWeixinPayment(HttpServletRequest request,HttpServletResponse response){
        try{  
            BufferedReader reader = request.getReader();  
            String line = "";  
            StringBuffer inputString = new StringBuffer();  
            try{  
                PrintWriter writer = response.getWriter();  
                while ((line = reader.readLine()) != null) {  
                    inputString.append(line);  
                }  
                if(reader!=null){  
                    reader.close();  
                }  
                if(!StringUtils.isEmpty(inputString.toString())){  
                    WXPayResult wxPayResult = JdomParseXmlUtils.getWXPayResult(inputString.toString());  
                    if("SUCCESS".equalsIgnoreCase(wxPayResult.getReturn_code())){
                        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
                        parameters.put("appid", wxPayResult.getAppid());  
                        parameters.put("attach", wxPayResult.getAttach()); 
                        parameters.put("bank_type", wxPayResult.getBank_type());
                        parameters.put("cash_fee", wxPayResult.getCash_fee()); 
                        parameters.put("fee_type", wxPayResult.getFee_type());  
                        parameters.put("is_subscribe", wxPayResult.getIs_subscribe());
                        parameters.put("mch_id", wxPayResult.getMch_id());  
                        parameters.put("nonce_str", wxPayResult.getNonce_str());  
                        parameters.put("openid", wxPayResult.getOpenid());  
                        parameters.put("out_trade_no", wxPayResult.getOut_trade_no());  
                        parameters.put("result_code", wxPayResult.getResult_code());  
                        parameters.put("return_code", wxPayResult.getReturn_code());  
                        parameters.put("time_end", wxPayResult.getTime_end());  
                        parameters.put("total_fee", wxPayResult.getTotal_fee());  
                        parameters.put("trade_type", wxPayResult.getTrade_type());  
                        parameters.put("transaction_id", wxPayResult.getTransaction_id());  
                        //反校验签名  
                        String sign = WXSignUtils.createSign("UTF-8", parameters);
                        
                        if(sign.equals(wxPayResult.getSign())){
                        	if("SUCCESS".equalsIgnoreCase(wxPayResult.getResult_code())){
                        		WeixinPayLog weixinPayLog=weixinPayLogService.findByOutTradNo(wxPayResult.getOut_trade_no());
                        		if(weixinPayLog.getStatus()==StaticKey.WeixinPaySuccess){
                        			writer.write(HttpXmlUtils.backWeixin("SUCCESS","OK")); 
                        		}else{
		                        	weixinPayLog.setStatus(StaticKey.WeixinPaySuccess);
		                        	weixinPayLog.setPayTime(new Date());
		                        	Users user = weixinPayLog.getUser();
		                        	user.setVipStatus(StaticKey.UserVipStatusTrue);
		                        	userService.updateUser(user); //更新用户vip状态
		                        	weixinPayLogService.updateWeixinPayLog(weixinPayLog);
		                            writer.write(HttpXmlUtils.backWeixin("SUCCESS","OK"));  
                        		}
                        	}
                        }else{  
                            writer.write(HttpXmlUtils.backWeixin("FAIL","签名失败"));  
                        }  
                    }else{  
                        writer.write(HttpXmlUtils.backWeixin("FAIL",wxPayResult.getReturn_msg()));  
                    }  
  
                    if(writer!=null){  
                        writer.close();  
                    }  
                }else{  
                    writer.write(HttpXmlUtils.backWeixin("FAIL","未获取到微信返回的结果"));  
                }  
            }catch(Exception e){  
                e.printStackTrace();  
            }  
        }catch(Exception ex){  
            ex.printStackTrace();  
        }  
    }

    
    
}
