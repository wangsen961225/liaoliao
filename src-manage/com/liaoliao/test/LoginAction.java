package com.liaoliao.test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liaoliao.redisclient.RedisService;
import com.liaoliao.sys.service.HandleCountService;
import com.liaoliao.user.entity.UserLogin;
import com.liaoliao.user.entity.UserToken;
import com.liaoliao.user.entity.Users;
import com.liaoliao.user.service.UserLoginService;
import com.liaoliao.user.service.UserService;
import com.liaoliao.user.service.UserTokenService;
import com.liaoliao.util.CommonUtil;
import com.liaoliao.util.RandomKit;
import com.liaoliao.util.StaticKey;

@Controller
@RequestMapping(value="/api")
public class LoginAction {
	
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
	
	/**
	 * 推广注册
	 * @param mobile
	 * @param passWord
	 * @return
	 */
	@RequestMapping(value="/extendRegister")
	@ResponseBody
	public Map<String,Object> register(HttpServletRequest request,String mobile,String passWord,String authCode,Integer parentId){
		Map<String,Object> map=new HashMap<String,Object>();
		HttpSession session = request.getSession();
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
		
		Users parent = userService.findById(parentId);
		
		Users user = new Users();
		user.setParent(parent);
		user.setMobile(mobile);
		user.setPassWord(passwordSHA1);
		user.setNickName(RandomKit.randomName());
		user.setDayMoney(StaticKey.UserDefaultMoney);
		user.setFreezeMoney(StaticKey.UserDefaultMoney);
		user.setTotalMoney(StaticKey.UserDefaultMoney);
		user.setPayMoney(StaticKey.UserDefaultMoney);
		user.setTobankMoney(StaticKey.UserDefaultMoney);
		user.setStatus(StaticKey.UserStatusTrue);
		user.setVipStatus(StaticKey.UserVipStatusFalse);
		user.setSourceType(StaticKey.RegisterByMobile);
		user.setAddIp(CommonUtil.getIpAddr(request));
		user.setAddTime(new Date());
		userService.saveUser(user);
		
//		统计每日registerUser用户注册量
		handleCountService.handleCountPlusOne("registerUser");
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
	
	
	/**
	 * 推广第三方登录
	 * @param request
	 * @param mobile
	 * @param passWord
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/extendUserLogin")
	@ResponseBody
	public Map<String,Object> userLogin(HttpServletRequest request,String nickName,String avatar,String openId,Integer sourceType,Integer parentId) {
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
		Users parent = userService.findById(parentId);
		Users user = new Users();
		user.setParent(parent);
		user.setNickName(nickName);
		user.setAvatar(avatar);
		user.setDayMoney(StaticKey.UserDefaultMoney);
		user.setFreezeMoney(StaticKey.UserDefaultMoney);
		user.setTotalMoney(StaticKey.UserDefaultMoney);
		user.setPayMoney(StaticKey.UserDefaultMoney);
		user.setTobankMoney(StaticKey.UserDefaultMoney);
		user.setStatus(StaticKey.UserStatusTrue);
		user.setVipStatus(StaticKey.UserVipStatusFalse);
		user.setSourceType(sourceType);
		user.setLoginTime(new Date());
		user.setAddTime(new Date());
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
		
		map.put("msg", "success");
		map.put("userId", user.getId());
		map.put("vipStatus", user.getVipStatus());
		map.put("nickName", nickName);
		map.put("avatar", avatar);
		map.put("token", token);
		map.put("code", StaticKey.ReturnServerTrue);
		return map;
	}
}
