package com.liaoliao.api;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liaoliao.listener.MySessionContext;
import com.liaoliao.profit.entity.FenrunLog;
import com.liaoliao.profit.service.FenrunLogService;
import com.liaoliao.redisclient.RedisService;
import com.liaoliao.sys.entity.AdvertClicks;
import com.liaoliao.sys.entity.TaskLog;
import com.liaoliao.sys.entity.UserTask;
import com.liaoliao.sys.service.AdvertClicksService;
import com.liaoliao.sys.service.AdvertService;
import com.liaoliao.sys.service.HandleCountService;
import com.liaoliao.sys.service.TaskLogService;
import com.liaoliao.sys.service.UserTaskService;
import com.liaoliao.user.entity.BanUser;
import com.liaoliao.user.entity.UserLogin;
import com.liaoliao.user.entity.UserToken;
import com.liaoliao.user.entity.Users;
import com.liaoliao.user.service.BanUserService;
import com.liaoliao.user.service.UserLoginService;
import com.liaoliao.user.service.UserService;
import com.liaoliao.user.service.UserTokenService;
import com.liaoliao.util.CommonUtil;
import com.liaoliao.util.MessageKit;
import com.liaoliao.util.RandomKit;
import com.liaoliao.util.StaticKey;

@Controller
@RequestMapping(value="/api")
public class AccountAction {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserTokenService userTokenService;
	
	@Autowired
	private RedisService redisService;
	
	@Autowired
	private UserLoginService userLoginService;
	
	@Autowired
	private HandleCountService handleCountService;
	
	@Autowired
	private BanUserService banUserService;
	
	@Autowired
	private TaskLogService taskLogService;
	
	@Autowired
	private UserTaskService userTaskService;
	
	@Autowired
	private FenrunLogService fenrunLogService;
	
	@Autowired
	AdvertService advertService;
	
	@Autowired
	AdvertClicksService advertClicksService;
	
	
	/**
	 * 用户登录
	 * @param request
	 * @param mobile
	 * @param passWord
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login")
	@ResponseBody
	public Map<String,Object> login(HttpServletRequest request,String mobile,String passWord) {
		Map<String,Object> map=new HashMap<String,Object>();
		if(mobile==null||"".equals(mobile)||StringUtils.isBlank(passWord)){
			map.put("msg", "有属性值为空!");
			map.put("token", "");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		Users user=userService.findByMobile(mobile);
		if(user==null){
			map.put("msg", "用户不存在！");
			map.put("token", "");
			map.put("code", StaticKey.ReturnUserAccountNotExist);
			return map;
		}
		String passwordSHA1 = CommonUtil.sha1(mobile+passWord+"shijie");
		if(!(user.getPassWord().equals(passwordSHA1))){
			map.put("msg", "密码错误!");
			map.put("token", "");
			map.put("code", StaticKey.ReturnUserAccountError);
			return map;
		}
		if(user.getStatus()==StaticKey.UserStatusFalse){
			map.put("msg", "用户状态禁用!");
			map.put("token", "");
			map.put("code", StaticKey.ReturnUserAccountDisable);
			return map;
		}
		
		user.setLoginTime(new Date());
		userService.updateUser(user);
		
		String token = CommonUtil.uuid();
		UserToken userToken = new UserToken();
		userToken.setUid(user.getId());
		userToken.setToken(token);
		userTokenService.saveOrUpdateUserToken(userToken);
		redisService.set(String.valueOf(user.getId()), token, 60*60*24*15);
		
//		判断新用户状态
		if(user.getTotalMoney()==0 && user.getToBankMoney()==0 && user.getFreezeMoney()==0
				&& user.getDayMoney()==0 && user.getPayMoney()==0){
			map.put("newUserStatus", StaticKey.UserStatusNewTrue);
		}else{
			map.put("newUserStatus", StaticKey.UserStatusNewFalse);
		}
		
		map.put("userId", user.getId());
		map.put("vipStatus", user.getVipStatus());
		map.put("nickName", user.getNickName());
		map.put("avatar", "https://ooo.0o0.ooo/2017/08/18/59965f98cd541.png");
		map.put("mobileBindStatus", StaticKey.UserMobileBindStatusTrue);
		map.put("token", token);
		map.put("code", StaticKey.ReturnServerTrue);
		map.put("msg", "success");
		return map;
	}
	
	
	
	/**
	 * 用户注册
	 * @param mobile
	 * @param passWord
	 * @return
	 */
	@RequestMapping(value="/register")
	@ResponseBody
	public Map<String,Object> register(HttpServletRequest request,String mobile,String passWord,String authCode){
		Map<String,Object> map=new HashMap<String,Object>();
//		HttpSession session = request.getSession();
		String sessionId = request.getHeader("sessionId");
		HttpSession session = MySessionContext.getSession(sessionId);
		if(session==null){
			map.put("msg", "session失效,请重新获取验证码");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		if(mobile==null||"".equals(mobile)||StringUtils.isBlank(passWord)||StringUtils.isBlank(authCode)){
			map.put("msg", "有属性值为空!");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		
		Users userHas = userService.findByMobile(mobile);
		if(userHas!=null){
			map.put("msg", "该用户已注册,请直接登录");
			map.put("code", StaticKey.ReturnUserAccountExist);
			return map;
		}
		
		String lastMobile = (String) session.getAttribute("mobile");
		String lastAuthCode = (String) session.getAttribute("authCode");
		if(lastMobile==null){
			map.put("msg", "session失效");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		if(!lastMobile.equals(mobile)){
			map.put("msg", "恶意注册");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		
		if(!lastAuthCode.equals(authCode)){
			map.put("msg", "验证码输入错误");
			map.put("code", StaticKey.ReturnUserAuthCodeError);
			return map;
		}
		
		String passwordSHA1 = CommonUtil.sha1(mobile+passWord+"shijie");
		Users user = new Users();
		user.setMobile(mobile);
		user.setPassWord(passwordSHA1);
		user.setNickName(RandomKit.randomName());
		user.setDayMoney(StaticKey.UserDefaultMoney);
		user.setFreezeMoney(StaticKey.UserDefaultMoney);
		user.setTotalMoney(StaticKey.UserDefaultMoney);
		user.setPayMoney(StaticKey.UserDefaultMoney);
		user.setTobankMoney(StaticKey.UserDefaultMoney);
		user.setUnselfMoney(StaticKey.UserDefaultMoney);
		user.setStatus(StaticKey.UserStatusTrue);
		user.setVipStatus(StaticKey.UserVipStatusFalse);
		user.setSourceType(StaticKey.RegisterByMobile);
		user.setAddIp(CommonUtil.getIpAddr(request));
		user.setAddTime(new Date());
		user.setSex(0);
		user.setAvatar("http://appliaoliao.oss-cn-hangzhou.aliyuncs.com/sys_imgs/defaultAvatar.png?x-oss-process=style/blank_style");
		userService.saveUser(user);
		//用手机号码注册
		TaskLog taskLog = taskLogService.findTask(user.getId(),2);
		if(taskLog==null){
			taskLog = new TaskLog();
			taskLog.setFinishTime(new Date());
			taskLog.setStatus(1);
			taskLog.setUser(user);
			taskLog.setObtain(0);
			UserTask ut = userTaskService.findById(2);
			taskLog.setUserTask(ut);//查询出用户完成手机号码注册这条记录
			taskLogService.savaTaskLog(taskLog);
		}else if(taskLog.getStatus()==0){
			taskLog.setStatus(1);
			taskLog.setObtain(0);
			taskLog.setFinishTime(new Date());
			taskLogService.updateTaskLog(taskLog);
		}
		
//		统计每日registerUser用户注册量
		handleCountService.handleCountPlusOne("registerUser");
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
	
	/**
	 * 修改密码
	 * @return
	 */
	@RequestMapping(value="/changePass")
	@ResponseBody
	public Map<String,Object> changePass(HttpServletRequest request,Integer userId,String oldPassWord,String newPassWord){
		Map<String,Object> map=new HashMap<String,Object>();
		if(userId==null||"".equals(userId)||StringUtils.isBlank(oldPassWord)||StringUtils.isBlank(newPassWord)){
			map.put("msg", "有属性值为空!");
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
			map.put("msg", "该用户不存在");
			map.put("code", StaticKey.ReturnUserAccountNotExist);
			return map;
		}
		String oldPassWordSHA1 = user.getPassWord();
		String passwordSHA1 = CommonUtil.sha1(user.getMobile()+oldPassWord+"shijie");
		if(!oldPassWordSHA1.equals(passwordSHA1)){ 
			map.put("msg", "原密码错误");
			map.put("code", StaticKey.ReturnUserPassError);
			return map;
		}
		String newPassWordSHA1 = CommonUtil.sha1(user.getMobile()+newPassWord+"shijie");
		user.setPassWord(newPassWordSHA1);
		userService.updateUser(user);
//		统计每日changePassword量
//		handleCountService.handleCountPlusOne("changePassword");
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
	
	/**
	 * 找回密码
	 * @return
	 */
	@RequestMapping(value="/findLossPass")
	@ResponseBody
	public Map<String,Object> findLossPass(HttpServletRequest request,String mobile,String authCode,String newPassWord){
		Map<String,Object> map=new HashMap<String,Object>();
		
//		HttpSession session = request.getSession();
//		String sessionId = request.getParameter("sessionId");//放到请求头,使用getHeader()
		
		String sessionId = request.getHeader("sessionId");
		HttpSession session = MySessionContext.getSession(sessionId);
		if(sessionId==null&&session==null&&"".equals(sessionId)){
			map.put("code", StaticKey.ReturnSessionInvalid);
			map.put("msg", "session失效");
			return map;
		}
		
		if(StringUtils.isBlank(mobile)||StringUtils.isBlank(authCode)||StringUtils.isBlank(newPassWord)){
			map.put("msg", "有属性值为空!");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		
		Users user = userService.findByMobile(mobile);
		if(user==null){
			map.put("msg", "该用户不存在");
			map.put("code", StaticKey.ReturnUserAccountNotExist);
			return map;
		}
	         
		String lastMobile = (String) session.getAttribute("mobile");
		System.out.println(mobile);
		String lastAuthCode = (String) session.getAttribute("authCode");
		if(lastMobile==null||!lastMobile.equals(mobile)){
			map.put("msg", "恶意注册或session失效");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		
		if(lastAuthCode==null||!lastAuthCode.equals(authCode)){
			map.put("msg", "验证码输入错误");
			map.put("code", StaticKey.ReturnUserAuthCodeError);
			return map;
		}
		
		String newPassWordSHA1 = CommonUtil.sha1(user.getMobile()+newPassWord+"shijie");
		user.setPassWord(newPassWordSHA1);
		userService.updateUser(user);
//		统计每日findLosspass找回密码量
//		handleCountService.handleCountPlusOne("findLosspass");
		map.put("msg", "success");
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
	
	/**
	 * 获取手机验证码
	 * 
	 */
	@RequestMapping(value="/messageAuthCode")
	@ResponseBody
	public Map<String,Object> messageAuthCode(HttpServletRequest request,String mobile){
		Map<String,Object> map=new HashMap<String,Object>();
		
		/*	Users user = userService.findByMobile(mobile);
		if(user!=null){
			map.put("message", "用户已存在");
			map.put("code", StaticKey.ReturnUserAccountExist);
			return map;
		}*/
		
		HttpSession session = request.getSession();
		String messageCode = RandomKit.messageCode();
		String messageContent = "亲爱的，您的验证码是:"+messageCode+"。有效期为15分钟，料料君在等你哦~"+"【料料】";
		session.setAttribute("mobile", mobile);
		session.setAttribute("authCode", messageCode);
//		调用282930短信接口，发送短信
		Boolean returnStatus = MessageKit.sendMessage(messageContent, mobile);
		if(!returnStatus){
			map.put("message", "验证码发送失败");
			map.put("code", StaticKey.ReturnServerNullError);
		}
//		统计每日msgCode发送量
		handleCountService.handleCountPlusOne("msgCode");
		map.put("code", StaticKey.ReturnServerTrue);
		map.put("sessionId", session.getId());
//		MySessionContext.AddSession(session);
		return map;
	}
	
	/**
	 * 修改昵称
	 * @return
	 */
	@RequestMapping(value="/changeNickName")
	@ResponseBody
	public Map<String,Object> changeNickName(HttpServletRequest request,Integer userId,String newNickName,Integer getTask){
		Map<String,Object> map=new HashMap<String,Object>();
		if(userId==null||"".equals(userId)||StringUtils.isBlank(newNickName)){
			map.put("msg", "参数异常!");
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
			map.put("msg", "该用户不存在");
			map.put("code", StaticKey.ReturnUserAccountNotExist);
			return map;
		}
		
		
		
		newNickName = CommonUtil.emojiFilter(newNickName);
		
		Users u = userService.findByNiceName(newNickName);
		if(u!=null){
			map.put("msg", "该昵称已存在");
			map.put("code", StaticKey.ReturnNiceNameAccountExist);
			return map;
		}
		
		user.setNickName(newNickName);
		userService.updateUser(user);
		
		if(getTask != null || 0!=getTask){
			//判断修改昵称任务
			TaskLog taskLog = taskLogService.findTask(userId,1);
			if(taskLog==null){
				taskLog = new TaskLog();
				taskLog.setFinishTime(new Date());
				taskLog.setStatus(2);
				taskLog.setUser(user);
				taskLog.setObtain(0);//未领取奖励
				UserTask ut = userTaskService.findById(1);
				taskLog.setUserTask(ut);//查询出用户完成修改昵称这条记录
				taskLogService.savaTaskLog(taskLog);
			}else if(taskLog.getStatus()==1){
				taskLog.setStatus(2);
				taskLog.setFinishTime(new Date());
				taskLogService.updateTaskLog(taskLog);
			}
		}
//		统计每日changeNickname修改量
//		handleCountService.handleCountPlusOne("changeNickname");
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
	
	
	/**
	 * 第三方登录
	 * @param request
	 * @param mobile
	 * @param passWord
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/userLogin")
	@ResponseBody
	public Map<String,Object> userLogin(HttpServletRequest request,String nickName,String avatar,String openId,Integer sourceType,Integer sex) {
		Map<String,Object> map=new HashMap<String,Object>();
		if(StringUtils.isBlank(openId)||sourceType==null){
			map.put("msg", "参数异常");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		UserLogin userLogin = userLoginService.findByOpenId(openId, sourceType);
//		如果曾经已授权，则直接登录成功
		if(userLogin!=null){
			Users user = userLogin.getUser();
			user.setLoginTime(new Date());
			userService.updateUser(user);
			
			String token = CommonUtil.uuid();
			UserToken userToken = new UserToken();
			userToken.setUid(user.getId());
			userToken.setToken(token);
			userTokenService.saveOrUpdateUserToken(userToken);
			redisService.set(String.valueOf(user.getId()), token, 60*60*24*15);
			
			if(user.getMobile()!=null){
				map.put("mobileBindStatus", StaticKey.UserMobileBindStatusTrue);
			}
			else{
				map.put("mobileBindStatus", StaticKey.UserMobileBindStatusFalse);
			}
			
//			判断新用户状态
			if(user.getTotalMoney()==0 && user.getToBankMoney()==0 && user.getFreezeMoney()==0
					&& user.getDayMoney()==0 && user.getPayMoney()==0){
				map.put("newUserStatus", StaticKey.UserStatusNewTrue);
			}else{
				map.put("newUserStatus", StaticKey.UserStatusNewFalse);
			}
			
			map.put("msg", "success");
			map.put("userId", user.getId());
			map.put("vipStatus", user.getVipStatus());
			map.put("nickName", user.getNickName());
			map.put("avatar", user.getAvatar());
			map.put("token", token);
			map.put("code", StaticKey.ReturnServerTrue);
			return map;
		}
		
		nickName = CommonUtil.emojiFilter(nickName);
		Users user = new Users();
		user.setNickName(nickName);
		user.setAvatar(avatar);
		user.setDayMoney(StaticKey.UserDefaultMoney);
		user.setFreezeMoney(StaticKey.UserDefaultMoney);
		user.setTotalMoney(StaticKey.UserDefaultMoney);
		user.setPayMoney(StaticKey.UserDefaultMoney);
		user.setTobankMoney(StaticKey.UserDefaultMoney);
		user.setUnselfMoney(StaticKey.UserDefaultMoney);
		user.setStatus(StaticKey.UserStatusTrue);
		user.setVipStatus(StaticKey.UserVipStatusFalse);
		user.setSourceType(sourceType);
		user.setLoginTime(new Date());
		user.setAddTime(new Date());
		user.setAddIp(CommonUtil.getIpAddr(request));
		user.setSex(sex);
		userService.saveUser(user);
		
		userLogin = new UserLogin();
		userLogin.setOpenId(openId);
		userLogin.setSourceType(sourceType);
		userLogin.setUser(user);
		userLoginService.saveUserLogin(userLogin);
		
		String token = CommonUtil.uuid();
		UserToken userToken = new UserToken();
		userToken.setUid(user.getId());
		userToken.setToken(token);
		userTokenService.saveOrUpdateUserToken(userToken);
		redisService.set(String.valueOf(user.getId()), token, 60*60*24*15);
		
//		统计每日registerUser用户注册量
		handleCountService.handleCountPlusOne("registerUser");
		
//		新用户
		map.put("newUserStatus", StaticKey.UserStatusNewTrue);
		
		map.put("msg", "success");
		map.put("userId", user.getId());
		map.put("vipStatus", user.getVipStatus());
		map.put("nickName", nickName);
		map.put("avatar", avatar);
		map.put("token", token);
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
	
	
	
	/**
	 * 记录：疑似模拟器
	 */
	@RequestMapping(value="/banUser")
	@ResponseBody
	public Map<String,Object> banUser(HttpServletRequest request,Integer userId,Integer cause) {
		Map<String,Object> map=new HashMap<String,Object>();
		if(userId==null||"".equals(userId)||cause==null){
			map.put("msg", "参数异常!");
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
			map.put("msg", "该用户不存在");
			map.put("code", StaticKey.ReturnUserAccountNotExist);
			return map;
		}
//		Users表标红
		if(user.getStatus()!=StaticKey.UserStatusException){
			user.setStatus(StaticKey.UserStatusException);
			userService.updateUser(user);
		}
		
//		BanUser banUser = banUserService.findByUserId(userId);
		//已被越狱会被记录,如果已被封禁但未被越狱,将会增加被封禁次数
		BanUser banUser = banUserService.findNotBreakoutByUserId(userId);
		if(banUser==null){
			banUser = new BanUser();
			banUser.setUser(user);
			if(cause==0){
				banUser.setBanCause("疑似脚本");
			}
			if(cause==1){
				banUser.setBanCause("疑似模拟器"); 
			}
			if(cause==2){
				banUser.setBanCause("疑似脚本,模拟器"); //脚本和模拟器都使用
			}
			banUser.setBanTimes(1);
//			banUser表标红
			banUser.setUserStatus(StaticKey.UserStatusException);
			banUser.setAddTime(new Date());
			banUserService.saveOrUpdateBanUser(banUser);
			map.put("code", StaticKey.ReturnServerTrue);
			map.put("msg", "success");
			return map;
		}
		banUser.setBanTimes(banUser.getBanTimes()+1);
		banUser.setAddTime(new Date());
		banUserService.saveOrUpdateBanUser(banUser);
		map.put("code", StaticKey.ReturnServerTrue);
		map.put("msg", "success");
		return map;
	}
	
	/**
	 * 已封禁用户列表-小黑屋
	 */
	@RequestMapping(value="/bannedUserList")
	@ResponseBody
	public Map<String,Object> bannedUserList(HttpServletRequest request) {
		Map<String,Object> map=new HashMap<String,Object>();
		List<BanUser> bannedUserList = banUserService.bannedUserListByTime();
		System.out.println(bannedUserList.size());
		List<Map<String,Object>> list = new LinkedList<>();
		Map<String,Object> listMap = null;
		for(BanUser banUser : bannedUserList){
			listMap = new LinkedHashMap<>();
			listMap.put("nickName", banUser.getUser().getNickName());
			listMap.put("banCause", banUser.getBanCause());
//			listMap.put("dealTime", banUser.getDealTime());
			listMap.put("dealTime", banUser.getAddTime());
			list.add(listMap);
		}
		map.put("list", list);
		map.put("code", StaticKey.ReturnServerTrue);
		map.put("msg", "success");
		return map;
	}
	
	/**
	 * 查询自己是否在小黑屋
	 * ps:直接根据userId在BanUser表中查未越狱BanUser，返回null则不在小黑屋
	 */
	@RequestMapping(value="/getUserStatus")
	@ResponseBody
	public Map<String,Object> getUserStatus(HttpServletRequest request,Integer userId) {
		Map<String,Object> map=new HashMap<String,Object>();
		if(userId==null){
			map.put("msg", "参数异常!");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		BanUser banUser = banUserService.findNotBreakoutByUserId(userId);
		if(banUser==null || banUser.getUserStatus()==StaticKey.UserStatusTrue||banUser.getDealTime()!=null||banUser.getBreakoutId()!=null){
			map.put("userStatus", StaticKey.UserStatusTrue);
		}
		else{
			map.put("userStatus", StaticKey.UserStatusFalse);
		}
		map.put("code", StaticKey.ReturnServerTrue);
		map.put("msg", "success");
		return map;
	}
		
		
		
	/**
	 * 根据手机号码判断该用户是否存在
	 * @param request
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value="/getUser")
	@ResponseBody
	public Map<String,Object> getUser(HttpServletRequest request,String mobile) {
		Map<String,Object> map = new HashMap<>();
		if(StringUtils.isBlank(mobile)){
			map.put("code", StaticKey.ReturnClientNullError);
			map.put("msg", "参数为空!");
			return map;
		}
		Users user = userService.findByMobile(mobile);
		if(user==null||"".equals(user)){
			map.put("code",StaticKey.ReturnServerNullError);
			map.put("msg", "账号不存在!");
			return map;
		}
		map.put("code", StaticKey.ReturnServerTrue);
		map.put("msg", "账号存在!");
		return map;
	}
	
	/**
	 * 统计在线人数
	 * @param request
	 * @param accountOnline
	 */
	@RequestMapping(value="/accountOnline")
	public void accountOnline(HttpServletRequest request){
		handleCountService.handleCountPlusOne("accountOnline");
	}
	
	/**
	 * 保存用户点击广告信息
	 * @param request
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/countAdvertClicks")
	@ResponseBody
	public Map<String,Object> countAdvertClicks(HttpServletRequest request,Integer userId) {
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
		
		AdvertClicks ac = new AdvertClicks();
		ac.setUser(user);
		ac.setAddTime(new Date());
		
		advertClicksService.countAdvertClicks(ac);
		map.put("code", StaticKey.ReturnServerTrue);
		map.put("msg", "保存成功");
		return map;
	}
	
	/**
	 * 获取用户当日和总共点击广告的次数
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/findAdvertClicks")
	public Map<String,Object> findAdvertClicks(HttpServletRequest request,Integer userId){
		Map<String,Object> map = new HashMap<String,Object>();
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
		
		Integer dayCount = advertClicksService.findDayCountAdvertClicksById(userId);
		Integer totalCount = advertClicksService.findTotalCountAdvertClicksById(userId);
		
		map.put("dayCount", dayCount);
		map.put("totalCount", totalCount);
		map.put("code", StaticKey.ReturnServerTrue);
		map.put("msg", "成功");
		
		return map;
	}
	
	/**
	 * 
	 * @param request
	 * @param userId : 关在小黑屋中的用户id
	 * @param breakoutId : 帮助越狱人的id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/breakout")
	public Map<String,Object> breakout(HttpServletRequest request,Integer userId,Integer breakoutId){
		Map<String,Object> map = new HashMap<>();
		if(userId==null||"".equals(userId)||breakoutId==null||"".equals(breakoutId)){
			map.put("msg", "有参数为空!");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		/*	if(!redisService.getValidate(request,userId)){
			map.put("msg", "token失效或错误!");
			map.put("code", StaticKey.ReturnClientTokenError);
			return map;
		}*/
		Users user = userService.findById(userId);
		if(user==null){
			map.put("msg", "用户不存在!");
			map.put("code", StaticKey.ReturnUserAccountNotExist);
			return map;
		}
		Users breakoutUser = userService.findById(breakoutId);
		if(breakoutUser==null){
			map.put("msg", "用户不存在!");
			map.put("code", StaticKey.ReturnUserAccountNotExist);
			return map;
		}
		//判断用户是否在小黑屋
		BanUser users = banUserService.findNotBreakoutByUserId(userId);
		if(users==null){
			map.put("msg", "用户不在小黑屋");
			map.put("code", StaticKey.ReturnUserAccountNotExist);
			return map;
		}
		
		//判断帮助越狱人的余额是否充足
		int userMoney=(int) (breakoutUser.getTotalMoney()-breakoutUser.getFreezeMoney()-breakoutUser.getPayMoney()-breakoutUser.getToBankMoney());
		if(userMoney<StaticKey.BreakoutMoney){
			map.put("msg", "余额不足!");
			map.put("code", StaticKey.ReturnMoneyLow);
			return map;
		}
		breakoutUser.setPayMoney(breakoutUser.getPayMoney()+StaticKey.BreakoutMoney);
		userService.updateUser(breakoutUser);
		//将用户状态改为正常
		user.setStatus(StaticKey.UserStatusTrue);
		userService.updateUser(user);
		
		//添加分润
		FenrunLog fl = new FenrunLog();
		fl.setAddTime(new Date());
		fl.setMoney(-StaticKey.BreakoutMoney);
		fl.setUser(breakoutUser);
		fl.setContentId(StaticKey.FenrunContentBreakout);
		fl.setType(StaticKey.Breakout);
		fenrunLogService.saveFenrunLog(fl);
		
		//改变小黑屋中用户的状态为正常   2-->1,帮助越狱人的id
		BanUser banUser = banUserService.findNotBreakoutByUserId(userId);
		banUser.setUserStatus(StaticKey.UserStatusTrue);
		banUser.setBreakoutId(breakoutId);
		banUser.setDealTime(new Date());
		banUserService.saveOrUpdateBanUser(banUser);
		
		map.put("msg", "success");
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
}

