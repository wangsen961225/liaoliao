package com.liaoliao.util;

import java.util.List;

/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public abstract class StaticKey {
	
	public static final Integer ArticleStatusFalse = 0;//文章状态禁用
	
	public static final Integer ArticleStatusTrue = 1;//文章状态正常
	
	public static final Integer ArticleStatusLoding = 2;//文章等待审核
	
	public static final Integer VideoStatusFalse = 0;//视频状态禁用
	
	public static final Integer VideoStatusTrue = 1;//视频状态正常
	
	public static final Integer VideoStatusLoding = 2;//视频等待审核
	
	public static final Integer UserStatusFalse = 0;//用户状态禁用
	
	public static final Integer UserStatusTrue = 1;//用户状态正常
	
	public static final Integer UserStatusException = 2;//用户状态标红
	
	public static final Integer UserStatusNewTrue = 1;//是新用户
	
	public static final Integer UserStatusNewFalse = 0;//不是新用户
	
	public static final Integer UserVipStatusFalse = 0;//用户非会员
	
	public static final Integer UserVipStatusTrue = 1;//用户会员
	
	public static final Integer UserMobileBindStatusTrue = 1;//已绑定手机号
	
	public static final Integer UserMobileBindStatusFalse = 0;//未绑定手机号
	
	public static final Double UserDefaultMoney = 0.00;//初始额
	
	public static final Integer VideoNeiHanDuanZi = 1;//内涵段子-视频
	
	public static final Integer VideoXiaoKaXiu = 2;//小咖秀-视频
	
	public static final Integer Video360KuaiShiPin = 3;//360快视频-视频
	
	public static final Integer VideoMeiPai = 4;//美拍-视频
	
	public static final Integer ArticleJinRiTouTiao = 1;//今日头条-文章
	
	public static final Integer ArticleDongFangTouTiao = 2;//东方头条-文章
	
	public static final Integer ArticleQuTouTiao = 3;//东方头条-文章
	
	public static final Integer ArticleMobileSina = 4;//手机新浪-文章
	
	public static final Integer AdminGroupStatusFalse = 0;//用户组状态禁用
	
	public static final Integer AdminGroupStatusTrue = 1;//用户组状态正常
	
	public static final Integer PageNum=30;//分页       用处：fenrunLog记录条数
	
	public static final Integer ArticlePageNum=15;//文章分页
	
	public static final Integer VideoPageNum=15;//视频分页
	
	/**
	 * 分润
	 */
	
	public static final Integer FenrunContentToBank=-1;//分润contentId-提现
	
	public static final Integer FenrunContentSign=0;//分润contentId-签到
	
	public static final Integer FenrunContentOriginal=1;//分润contentId-原创分润总结
	
	public static final Integer FenrunContentNewUser=4;//分润contentId-新用户红包
	
	public static final Integer FenrunArticle=0;//分润日志来源种类-文章
	
	public static final Integer FenrunVideo=1;//分润日志来源种类-视频
	
	public static final Integer FenrunToBank=2;//分润日志来源种类-提现
	
	public static final Integer FenrunBroadcast=3;//分润日志来源种类-发广播
	
	public static final Integer FenrunNewUser=4;//分润日志来源种类-新用户红包
	
	public static final Integer FenrunSign=5;//分润日志来源种类-签到
	
	public static final Integer FenrunGrabVip=6;//分润日志来源种类-vip被抢红包
	
	public static final Integer FenrunGrabUser=7;//分润日志来源种类-普通用户抢红包
	
	public static final Integer FenrunInvitation=8;//分润日志来源种类-邀请注册
	
	public static final Integer FenrunDayFenrun=9;//分润日志来源种类-每日下级分润
	
	public static final Integer FenrunPassVideo=10;//分润日志来源种类-原创video通过审核
	
	public static final Integer FenrunPassArticle=11;//分润日志来源种类-原创Article通过审核
	
	public static final Integer FenrunOriginalArticle=12;//分润日志来源种类-原创文章
	
	public static final Integer FenrunOriginalVideo=13;//分润日志来源种类-原创视频
	
	public static final Integer FenrunDayTask=14;//分润日志来源种类-每日任务
	
	public static final Integer FenrunOriginalSum=15;//分润日志来源种类-原创作品十分钟统计
	
	public static final Integer FenrunWorldRedPackage=16;//分润日志来源种类-土豪世界发红包
	
	public static final Integer FenrunLookUser=17;//分润日志来源种类-查看用户详情(约她)
	/**
	 * 分润日志来源种类-退款
	 * @return 18
	 */
	public static final Integer FenrunRefund=18;//分润日志来源种类-退款
	/**
	 * 阅读文章翻倍
	 * @return 19
	 */
	public static final Integer FenrunReadArticleDouble=19;//分润日志来源种类-阅读文章翻倍
	/**
	 * 观看视频翻倍
	 * @return 20
	 */
	public static final Integer FenrunReadVideoDouble=20;//分润日志来源种类-观看视频翻倍
	
	
	/**
	 * 未关注 0
	 */
	public static final Integer FocusFlase=0;//未关注
	/**
	 *已关注  1
	 */
	public static final Integer FocusTrue=1;//已关注
	
	
	/**
	 * 
	 * 全局土豪发送世界红包
	 */
	public static List<Integer> redPackagelist=null;
	

	/**
	 * 广告状态禁用
	 * @const 0
	 */
	public static final Integer AdvertStatusFalse = 0;//广告状态禁用
	
	/**
	 * 广告状态正常
	 */
	public static final Integer AdvertStatusTrue = 1;//广告状态正常
	
	/**
	 * 提现状态-未处理
	 */
	public static final Integer ToBankStatusFalse = 0;//提现状态-未处理
	
	/**
	 * 提现状态-已到账
	 */
	public static final Integer ToBankStatusTrue = 1;//提现状态-已到账
	
	/**
	 * 测试人员邀请人，获得料币最小的通过金额
	 */
	public static final Double MinPassMoney = 0.0;//测试人员邀请人，获得料币最小的通过金额
	
	public static final long MinPassTime = 3000000;//通过的最短活跃天数
	
	public static final Integer RegisterByMobile = 3;//通过手机注册
	
	public static final Integer RegisterByWechat = 0;//通过微信注册
	
	public static final Integer RegisterByQQ = 1;//通过QQ注册
	
	public static final Integer RegisterByWeibo = 2;//通过微博注册
	
	public static final Integer AboutUsTypeQuestion = 1;//关于我们-常见问题
	
	public static final Integer InvitationStatusFalse = 0;//邀请状态；0：未处理，1：已处理。
	
	public static final Integer InvitationStatusTrue = 1;
	
	public static final Integer OriginalReadMoney = 1;//原创被阅读，作者获得的金额
	
	public static final Integer OriginalShareMoney = 1;//原创被分享，作者获得的金额
	
	public static final Integer OriginalTotalMoney = 1000000;//原创作品总价格，点击或者分享，作者可得1料币
	
	public static final Integer passArticleMoney=1000;//原创文章通过审核，奖励1000料币
	
	public static final Integer passVideoMoney=2000;//原创视频通过审核，奖励2000料币
	
	public static final Integer sumOriginalMoney=10*60*1000;//合并总结原创分润日志--10分钟
	
	/**
	 * 微信支付
	 */
	
	public static final String APPID = "wx5577772281c3cad2";   //APPId
	
	public static final String MCH_ID = "1484941002";  //商户号
	
	public static final String Key = "01BF633ED2784DCC90D38AB9292C4482";  //微信支付商户秘钥key
	
	public static final String notify_url = "http://app.127120.com:8080/api/notifyUrlWeixin";//微信支付回调函数
	
	public static final Integer WeixinPayFail = 0 ;
	
	public static final Integer WeixinPaySuccess = 1 ;
	
	public static final String  Attach = "1";//附加参数
	
	public static final String Body = "开通VIP";
	
	public static final String Detail = "开通VIP,金额：10";
	
	public static final Integer TotalFee = 1000;//VIP开通金额；单位是分，10元
	
	public static final String TradType = "APP";//产品类型
	
	public static final Double TestRate=0.35;//推广人员扣量百分比
	
	
	
	/**
	 * 返回值
	 */
	
//	全局接口
	/**
	 * 正确
	 * @return 0
	 */
	public static final Integer ReturnServerTrue=0;//正确
	
	public static final Integer ReturnClientNullError=400;//客户端传值字段为空
	
	/**
	 * 服务器查询为空或错误
	 * @return 500
	 */
	public static final Integer ReturnServerNullError=500;//服务器查询为空或错误
	
	public static final Integer ReturnClientTokenError=666;//客户端传值token错误
//	登录接口
	public static final Integer ReturnUserAccountNotExist=100;//用户不存在
	
	public static final Integer ReturnUserAccountError=120;//密码错误
	
	public static final Integer ReturnUserAccountDisable=130;//用户被禁用
	
//	注册接口
	public static final Integer ReturnUserAccountExist=110;//用户已存在
	
	public static final Integer ReturnUserAuthCodeError=140;//短信验证码错误
	
//判断昵称是否存在
	public static final Integer ReturnNiceNameAccountExist=150;//该昵称已存在
	
//	修改密码接口
	public static final Integer ReturnUserPassError=125;//修改密码：原密码错误
	
//	找回密码接口
	public static final Integer ReturnSessionInvalid=222;//session失效
	
//	版本更新接口
	/**
	 * 版本已是最新
	 * @return 900
	 */
	public static final Integer ReturnVersionCodeNew = 900;
	/**
	 * 有新版本
	 * @return 910
	 */
	public static final Integer ReturnVersionCodeUpdate = 910;

//	分润接口 & 检测签到状态接口
	/**
	 * 未签到 
	 * @return 200
	 */
	public static final Integer ReturnUserSignError = 200;
	/**
	 * 余额不足
	 * @return 310
	 */
	public static final Integer ReturnMoneyLow = 310;
	/**
	 * 此支付账号已被其他用户绑定
	 * @return 320
	 */
	public static final Integer ReturnBindPayError = 320;
	/**
	 * 红包溜走了
	 * @return 800
	 */
	public static final Integer ReturnCoinRunAway = 800;
	/**
	 * 用户非会员
	 * @return 190
	 */
	public static final Integer ReturnUserNotVip = 190;//用户非会员
	/**
	 * 霸屏上线未到冷却时间
	 * @return 710
	 */
	public static final Integer ReturnCooldownTimeError = 710;
	
	//极光推送额外透传类型
	/**
	 * 极光推送额外透传类型-霸屏登录
	 * @return "0"
	 */
	public static final String JPushVipLoginEffectType = "0";
	/**
	 * 极光推送额外透传类型-发送广播
	 * @return "1"
	 */
	public static final String JPushSendBroadcast = "1";
	/**
	 * 极光推送额外透传类型-全服红包
	 * @return "2"
	 */
	public static final String JPushSendRedPackage = "2";
	/**
	 * 极光推送额外透传类型-问题反馈处理结果
	 * @return "3"
	 */
	public static final String JPushSendFeedback = "3";
	/**
	 * 极光推送额外透传类型-原创文章或视频处理结果
	 * @return "4"
	 */
	public static final String JPushSendOriginal = "4";
	/**
	 * 极光推送额外透传类型-后台开启阅读翻倍功能
	 * @return "5"
	 */
	public static final String JPushSendOpenReadDouble = "5";
	/**
	 * 极光推送额外透传类型-后台关闭了阅读翻倍功能
	 * @return "6"
	 */
	public static final String JPushSendCloseReadDouble = "6";
	/**
	 * 极光推送额外透传类型-料料官方用户发送系统红包
	 * @return "7"
	 */
	public static final String JPushSendOfficialUserSendRedpackage = "7";
	
	public static final Integer ReturnBindPayFalse = 0;
	public static final Integer ReturnBindPayTrue = 1;
	
	
	// 料料官方用户：
	/**
	 * 料料视频官方用户
	 * @return 66
	 */
	public static final Integer liaoliaoVideoId = 66;
	/**
	 * 料料文章官方用户
	 * @return 67
	 */
	public static final Integer liaoliaoArticleId = 67;
	
	
	//任务状态
	/**
	 * 未领取任务
	 * @return 10
	 */
	public static final Integer NotReceiveTask = 10;
	/**
	 * 已领取任务
	 * @return 11
	 */
	public static final Integer ReceiveTask = 11;
	
	
	/**
	 * 阅读不翻倍:
	 * @return 0
	 */
	public static final Integer readNotDouble = 0;
	/**
	 * 阅读翻倍:
	 * @return  1
	 */
	public static final Integer readDouble = 1;
	
		
	/**
	 * 发送系统红包官方用户id
	 * @return 10029
	 */
	public static final Integer SystemRedpackage=10029;
	
	/**
	 * 應用寶料料下載地址
	 * @return "http://sj.qq.com/myapp/detail.htm?apkName=com.sjcm.liaoliao"
	 */
	public static final String YingyongbaoDownload="http://sj.qq.com/myapp/detail.htm?apkName=com.sjcm.liaoliao";
	
}
