package com.liaoliao.moneymanagement.Controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liaoliao.profit.entity.MoneyManagement;
import com.liaoliao.profit.entity.UserMoneyManagement;
import com.liaoliao.profit.service.MoneyManagementService;
import com.liaoliao.profit.service.UserMoneyManagementService;
import com.liaoliao.redisclient.RedisService;
import com.liaoliao.user.entity.Users;
import com.liaoliao.user.service.UserService;
import com.liaoliao.util.CommonUtil;
import com.liaoliao.util.StaticKey;



@Controller
@RequestMapping(value="/api")
public class MoneyManagementController {
	@Autowired
	private RedisService redisService;
	@Autowired
	private  MoneyManagementService moneyManagementService;
	@Autowired
	private  UserService userservice;
	@Autowired
	private  UserMoneyManagementService userMoneyManagementService;
	
	/**
	 * 展示所有有效的理财产品
	 * @param request
	 * @return
	 */
	@RequestMapping("/showAll")
	@ResponseBody
	public  Map<String, Object> showAll(HttpServletRequest request,Integer userId){
		List<MoneyManagement> result= moneyManagementService.queryAll();
		Users users=userservice.queryOne(userId);
		Map<String, Object> map=new HashMap<String,Object>();
		if(users==null){
			map.put("code","1");
			map.put("msg", "查询失败,用户不存在.");
			return map;
		}
		/*if(!redisService.getValidate(request,userId)){
			map.put("msg", "token失效或错误");
			map.put("code", StaticKey.ReturnClientTokenError);
			return map;
		}*/
		List<UserMoneyManagement> resultsum=userMoneyManagementService.querySum();
		Double fm=users.getFreezeMoney();
		if(fm==null){
			fm=0.0;
		}
		List<Object> list=new ArrayList<Object>();
		Map<String, String> ma=null;
		int i=0;
		for (MoneyManagement moneyManagement : result) {
			ma=new HashMap<String,String>();
			ma.put("id", moneyManagement.getId());
			ma.put("name",moneyManagement.getName());
			ma.put("floatNumber",moneyManagement.getFloatNumber().toString());
			ma.put("porductDescribe",moneyManagement.getProductDescribe());
			ma.put("porductType",moneyManagement.getPorductType());
			ma.put("returnMoneyWay",moneyManagement.getReturnMoneyWay());
			ma.put("period", moneyManagement.getbuyMouth()+"");
			ma.put("interestRate", moneyManagement.getInterestRate());
			ma.put("zMoney", resultsum.get(i)+"");
			i++;
			list.add(ma);
		}
		if(users.getManagementMoney()==null){
			users.setManagementMoney(0.0);
		}
		map.put("YesterdayEarnings", users.getManagementMoney());
		map.put("msg","查询成功");
		map.put("code", "0");
		map.put("freeMoney", fm);
		map.put("list", list);
		return map;
	}
	
	/**
	 * 用户购买理财产品
	 * @param id 客户id
	 * @param name 产品名称
	 * @return 
	 */
	@RequestMapping("/userBuyMoneyManagement")
	@ResponseBody
	public Map<String, Object> buyMoneyManagementByUser(HttpServletRequest request,Integer userId,Double money,String productId){
		Map<String, Object> map=new HashMap<String,Object>();
		Users user=userservice.queryOne(userId);
		MoneyManagement management=moneyManagementService.queryOne(productId);
		if(money<1000){
			map.put("msg","购买金额不得小于1000");
			map.put("code", "1");
			return map;
		}
		if(user==null ||management==null){
			map.put("msg","购买失败");
			map.put("code", "1");
			return map;
		}
		if(!redisService.getValidate(request,userId)){
			map.put("msg", "token失效或错误");
			map.put("code", StaticKey.ReturnClientTokenError);
			return map;
		}
		//Double sInteger=user.getTotalMoney()-user.getFreezeMoney();
		Double sInteger= user.getTotalMoney() - user.getFreezeMoney() - user.getPayMoney() - user.getToBankMoney();
		if(sInteger<money){
			map.put("msg","余额不足");
			map.put("code", "2");
			return map;
		}
		int yue=management.getbuyMouth();
		double lixi=Double.parseDouble(management.getInterestRate());
		Double yuMoney=yue*lixi*money;
		UserMoneyManagement modle=new UserMoneyManagement();
		modle.setMoneyId(management.getId());
		modle.setUserId(userId);
		modle.setPredictMoney(yuMoney);
		String id=CommonUtil.uuid();
		modle.setId(id);
		modle.setMoney(money);
		modle.setBuyDate(new Date());
		modle.setStatus(StaticKey.managementMoneyStatusing);
		user.setDayMoney(user.getDayMoney()+money);
		if(user.getManagementMoney()==null){
			user.setManagementMoney(0.0);
		}
		user.setFreezeMoney(user.getFreezeMoney()+money);
		//user.setPayMoney(user.getPayMoney()+money);
		userservice.updateUser(user);
		userMoneyManagementService.save(modle);
		map.put("msg","购买成功");
		map.put("code", "0");
		return map;
	}
	
	/**
	 * 购买理财产品历史记录
	 * @param id
	 * @return
	 */
	@RequestMapping("/userBuyMoneyManagementHistory")
	@ResponseBody
	public Map<String, Object> userBuyMoneyManagementHistory(HttpServletRequest request,Integer id){
		Map<String, Object> map=new HashMap<String,Object>();
		List<UserMoneyManagement> list=userMoneyManagementService.queryAll(id);
		Users users=userservice.queryOne(id);
		if(list==null||list.size()==0){
			map.put("msg","尚未购买理财");
			map.put("code", "1");
		}
		if(!redisService.getValidate(request,id)){
			map.put("msg", "token失效或错误");
			map.put("code", StaticKey.ReturnClientTokenError);
			return map;
		}
		List<Object> lists=new ArrayList<Object>();
		Map<String, String> ma=null;
		for (UserMoneyManagement userMoneyManagement : list) {
			ma=new HashMap<String ,String>();
			String pString=userMoneyManagement.getMoneyId();
			MoneyManagement management=moneyManagementService.queryOne(pString);
			ma.put("productName", management.getName());
			ma.put("money", userMoneyManagement.getMoney().toString());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ma.put("buyDate", sdf.format(userMoneyManagement.getBuyDate()));
			ma.put("status", userMoneyManagement.getStatus());
			//ma.put("yuMoney", userMoneyManagement.getYuMoney()+"");
			lists.add(ma);
		}
		map.put("msg","购买记录");
		map.put("code", "0");
		map.put("list", lists);
		map.put("money", users.getYesterdayMoney());
		return map;
	}
	/*@RequestMapping("/aa")
	public  void buyMoneyManagement(){
		List<UserMoneyManagement> list=userMoneyManagementService.queryAll();
		List<Users> userlists=userservice.findAll();
		for (Users users : userlists) {
			users.setYesterdayMoney(0.0);
			userservice.updateUser(users);
		}
		
		for (UserMoneyManagement userMoneyManagement : list) {
			String pid=userMoneyManagement.getMoneyId();
			Users users=userservice.queryOne(userMoneyManagement.getUserId());
			MoneyManagement moneyManagement=moneyManagementService.queryOne(pid);
			Date buyDate=userMoneyManagement.getBuyDate();
			int yue=moneyManagement.getbuyMouth();
			Calendar c = Calendar.getInstance();
			Calendar c1 = Calendar.getInstance();
			c.setTime(buyDate);  
			c.add(Calendar.MONTH, yue);
			c1.add(Calendar.DAY_OF_MONTH, 1);
			Date date=c.getTime();
			Date date1=new Date();
			Date date2=c1.getTime();
			Double mday=0.0;
			int days = ((int)(date.getTime()/1000)-(int)(buyDate.getTime()/1000))/3600/24;//购买到结算的总天数
			if(date.getTime()<date1.getTime()){
				userMoneyManagement.setStatus(StaticKey.managementMoneyStatused);
				//userMoneyManagement.setYuMoney(0.0);
				userMoneyManagementService.revise(userMoneyManagement);
				userservice.updateUser(users);
			}else if(date2.getTime()!=date1.getTime()){
				
				Double mDouble=userMoneyManagement.getMoney();//购买金额
				Double bDouble=Double.parseDouble(moneyManagement.getInterestRate());//利息
				Double zDouble=mDouble*yue*bDouble;//每个用户每个产品的预计总利润
				//userMoneyManagement.setYuMoney(zDouble);
				userMoneyManagementService.revise(userMoneyManagement);
				mday=zDouble/days;//每个用户每个产品每天的利润
				if(users.getYesterdayMoney()==null){
					users.setYesterdayMoney(0.0);
				}
				users.setYesterdayMoney(users.getYesterdayMoney()+mday);
				users.setManagementMoney(users.getManagementMoney()+users.getYesterdayMoney());
				userservice.updateUser(users);
			}
		}
	}*/
}
