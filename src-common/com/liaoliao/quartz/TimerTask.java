package com.liaoliao.quartz;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.jsoup.helper.StringUtil;
import org.springframework.context.ApplicationContext;

import com.liaoliao.profit.entity.FenrunLog;
import com.liaoliao.profit.service.FenrunLogService;
import com.liaoliao.redisclient.RedisService;
import com.liaoliao.sys.entity.Invitation;
import com.liaoliao.sys.entity.OriginalProfitLog;
import com.liaoliao.sys.service.HandleCountService;
import com.liaoliao.sys.service.InvitationService;
import com.liaoliao.sys.service.OriginalProfitLogService;
import com.liaoliao.user.entity.RedPackage;
import com.liaoliao.user.entity.Users;
import com.liaoliao.user.service.RedPackageService;
import com.liaoliao.user.service.UserService;
import com.liaoliao.util.ApplicationContextHelper;
import com.liaoliao.util.StaticKey;
import com.liaoliao.util.TimeKit;

public class TimerTask {
	ApplicationContext ctx = ApplicationContextHelper.getApplicationContext();
	
	public void clearUserDayMoney(){
		System.out.println("分润并清理每日收益开始!  "+TimeKit.dateToStr(new Date()));
		
		RedisService redisService  = (RedisService) ctx.getBean("redisService");
		UserService userService  = (UserService) ctx.getBean("userService");
		FenrunLog fenLog = null;
		Double parentProfitRate = 0.1;
		FenrunLogService fenrunLogService = (FenrunLogService) ctx.getBean("fenrunLogService");
//		获取分润提成比率
		String parentProfitRateStr = redisService.getConfigValue("inviteParentRate");
		if(StringUtil.isBlank(parentProfitRateStr)){
			System.out.println("请检查systemConfig表");
		}else{
			parentProfitRate= Double.valueOf(parentProfitRateStr);
		}
		List<Users> list = userService.findHasParent(1/parentProfitRate);//获取所有可分润上级的下级用户
		if(list==null||list.size()==0){
			userService.clearUserDayMoney();
			System.out.println("无数据-分润并清理每日收益结束!  "+TimeKit.dateToStr(new Date()));
			return;
		}
		for(Users child:list){
			Users parent = child.getParent();
			double addMoney = child.getDayMoney()*parentProfitRate;
//			double addMoney = Math.floor(child.getDayMoney()*parentProfitRate);
			Double totalMoney= parent.getTotalMoney()+addMoney;
			parent.setTotalMoney(totalMoney);
			fenLog = new FenrunLog();
			fenLog.setAddTime(new Date());
			fenLog.setUser(child.getParent());
			fenLog.setContentId(child.getId());
			fenLog.setMoney((int) addMoney);
			fenLog.setType(StaticKey.FenrunDayFenrun);
			fenrunLogService.saveFenrunLog(fenLog);
			userService.updateUser(parent);
			child.setDayMoney(0.0);
			userService.updateUser(child);
			
		}
	//	userService.clearUserDayMoney();
		System.out.println("分润并清理每日收益结束!  "+TimeKit.dateToStr(new Date()));
	}
	
	
	public void jiesuan(){
		System.out.println("每日结算开始...  "+TimeKit.dateToStr(new Date()));
		
		InvitationService invitationService = (InvitationService)ctx.getBean("invitationService");
		RedisService redisService  = (RedisService) ctx.getBean("redisService");
		UserService userService = (UserService)ctx.getBean("userService");
		FenrunLogService fenrunLogService = (FenrunLogService) ctx.getBean("fenrunLogService");
		Integer inviteChildMinMoney = 500;
//		下级达标最低料币数
		String inviteChildMinMoneyStr = redisService.getConfigValue("inviteChildMinMoney");
		if(StringUtil.isBlank(inviteChildMinMoneyStr)){
			System.out.println("请检查systemConfig表");
		}else{
			inviteChildMinMoney = Integer.valueOf(inviteChildMinMoneyStr);
		}
		//查询出状态是未处理并且下级总金额大于最低限额的用户
		List<Invitation> list = invitationService.findAllNoDeal(inviteChildMinMoney);
		
		if(list==null||list.size()==0){
			System.out.println("无数据-每日结算结束...  "+TimeKit.dateToStr(new Date()));
			return;
		}
		FenrunLog fenLog = null;
		Integer invitationMoney = 1000;
//		邀请好友上级获得料币数
		String invitationMoneyStr = redisService.getConfigValue("inviteParentMoney");
		if(StringUtil.isBlank(invitationMoneyStr)){
			System.out.println("请检查systemConfig表");
		}else{
			invitationMoney = Integer.valueOf(invitationMoneyStr);
		}
		for(Invitation in:list){
			in.setStatus(StaticKey.InvitationStatusTrue);
			in.getParent().setTotalMoney(in.getParent().getTotalMoney()+invitationMoney);
			in.getParent().setUnselfMoney(in.getParent().getUnselfMoney()+invitationMoney);
			
			fenLog=new FenrunLog();
			fenLog.setAddTime(new Date());
			fenLog.setUser(in.getParent());
			fenLog.setContentId(in.getId());
			fenLog.setMoney(invitationMoney);
			fenLog.setType(StaticKey.FenrunInvitation);
			
			fenrunLogService.saveFenrunLog(fenLog);
			invitationService.updataInvitation(in);
			userService.updateUser(in.getParent());
		}
		System.out.println("每日结算结束...  "+TimeKit.dateToStr(new Date()));
	}
	
	
	public void originalLog(){
		System.out.println("十分钟结算原创阅读收益开始...  "+TimeKit.dateToStr(new Date()));
		OriginalProfitLogService originalProfitLogService = (OriginalProfitLogService)ctx.getBean("originalProfitLogService");
		UserService userService = (UserService)ctx.getBean("userService");
		FenrunLogService fenrunLogService = (FenrunLogService) ctx.getBean("fenrunLogService");
	//	OriginalProfitLog opLog = originalProfitLogService.find
		List<OriginalProfitLog> oplList = originalProfitLogService.findData();
		if(oplList!=null&&oplList.size()>0){
			for(int i=0;i<oplList.size();i++){
				Map<String,Object> map = (Map)oplList.get(i);
				Integer id =Integer.valueOf(String.valueOf(map.get("id")));
				Integer money = Integer.valueOf(String.valueOf(map.get("money")));
				Integer userId = Integer.valueOf(String.valueOf(map.get("userId")));
				Integer contentId=Integer.valueOf(String.valueOf(map.get("contentId")));
				Integer type=Integer.valueOf(String.valueOf(map.get("type")));
				
				FenrunLog fenl = new FenrunLog();
				fenl.setAddTime(new Date());
				fenl.setContentId(contentId);
				fenl.setMoney(money);
				if(type==0){
					fenl.setType(StaticKey.FenrunOriginalArticle);
				}else{
					fenl.setType(StaticKey.FenrunOriginalVideo);
				}
				fenl.setUser(userService.findById(userId));
				fenrunLogService.saveFenrunLog(fenl);
			}
		}
		System.out.println("十分钟结算原创阅读收益结束...  "+TimeKit.dateToStr(new Date()));
	}
	
	public void clearOnline(){
//		System.out.println("a");
		HandleCountService handleCountService = (HandleCountService)ctx.getBean("handleCountService");
		handleCountService.clearOnline();
	}
	
	
	/**
	 * 红包余额退回
	 */
	public void dealwithRedPackage(){
		RedPackageService redPackageService = (RedPackageService)ctx.getBean("redPackageService");
		FenrunLogService fenrunLogService = (FenrunLogService)ctx.getBean("fenrunLogService");
		UserService userService = (UserService)ctx.getBean("userService");
		List<RedPackage> list = redPackageService.findUnDeal();
		
		Date date =null;
		for(RedPackage rp:list){
//			date = new Date(new Date().getTime()-1*60*100);//一分钟之前的数据
			date = new Date(new Date().getTime()-24*60*60*1000);//24小时之前的数据24*60*60*1000
			if(rp.getAddTime().getTime()<= date.getTime()){
				if(rp.getBalance()>0){
					FenrunLog fenl = new FenrunLog();
					fenl.setAddTime(new Date());
					fenl.setContentId(rp.getId());
					fenl.setMoney(rp.getBalance());
					fenl.setType(StaticKey.FenrunRefund);
					fenl.setUser(rp.getUser());
					Users user = rp.getUser();
					user.setTotalMoney(user.getTotalMoney()+rp.getBalance());
					rp.setBalance(0);
					rp.setStatus(0);
					fenrunLogService.saveFenrunLog(fenl);
					redPackageService.updateRedPackage(rp);
					userService.updateUser(user);
				}
			}
		}
		
	}
	

}
