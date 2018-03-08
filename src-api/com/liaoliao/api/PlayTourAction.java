package com.liaoliao.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.comparators.ComparatorChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liaoliao.content.entity.Article;
import com.liaoliao.content.entity.Video;
import com.liaoliao.content.service.ArticleService;
import com.liaoliao.content.service.VideoService;
import com.liaoliao.palyTour.entity.PlayHistory;
import com.liaoliao.palyTour.service.PlayHistoryService;
import com.liaoliao.profit.entity.FenrunLog;
import com.liaoliao.profit.service.FenrunLogService;
import com.liaoliao.redisclient.RedisService;
import com.liaoliao.user.entity.Users;
import com.liaoliao.user.service.UserService;
import com.liaoliao.util.CommonUtil;
import com.liaoliao.util.StaticKey;

@Controller
@RequestMapping(value="/api")
public class PlayTourAction {
	@Autowired
	private RedisService redisService;
	@Autowired
	private UserService userService;
	@Autowired
	private VideoService videoService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	private PlayHistoryService playHistoryService;
	@Autowired
	private  FenrunLogService fenrunLogService;
	/**
	 * 打赏料币
	 * @param request
	 * @param rederId 读者
	 * @param authorId 作者
	 * @param money 打赏金额
	 * @param articleId 文章ID或者是视频ID
	 * @return
	 */
	
	@RequestMapping(value="/playTour")
	@ResponseBody
	public Map<String,Object> playTour(HttpServletRequest request,Integer rederId,Integer authorId,Double money,Integer articleId,Integer type){
		Map<String,Object> map=new HashMap<String,Object>();
		if(!redisService.getValidate(request,rederId)){
			map.put("msg", "token失效或错误");
			map.put("code", StaticKey.ReturnClientTokenError);
			return map;
		}
		if(type==null||money==null){
			map.put("msg", "字段不能为空.");
			map.put("code", StaticKey.ReturnClientNullError);
			return map;
		}
		if(type==1){
					Video video=videoService.findById(articleId);
					video.setPlayMoneySum(video.getPlayMoneySum()+money);
					videoService.updateVideo(video);
				}else if(type==0){
					Article article=articleService.findById(articleId);
					article.setPlayMoneySum(article.getPlayMoneySum()+money);
					articleService.updateArticle(article);
				}
		PlayHistory playHistory1=playHistoryService.queryOne(articleId, type, rederId);
		if(playHistory1!=null){
			map.put("msg", "您已经打赏过咯。");
			map.put("code", 110);
			return map;
		}
		Users reder=userService.queryOne(rederId);
		Users author=userService.queryOne(authorId);
		if(reder.getId().equals(author.getId())){
			map.put("msg", "用户不存在或还是让其他人打赏吧");
			map.put("code", 100);
			return map;
		}
		if(reder==null||author==null){
			map.put("msg", "用户不存在或还是让其他人打赏吧");
			map.put("code", 100);
			return map;
		}
		double balanceMoney = reder.getTotalMoney() - reder.getFreezeMoney() - reder.getPayMoney() - reder.getToBankMoney();
		if(balanceMoney-money<0){
			map.put("msg", "余额不足。");
			map.put("code", 310);
			return map;
		}
		if(type==1){
			Video video=videoService.findById(articleId);
			if(video==null){
				map.put("msg", "用户不存在或还是让其他人打赏吧");
				map.put("code", 100);
				return map;
			}else{
				reder.setTotalMoney(reder.getTotalMoney()-money);
				reder.setDayMoney(reder.getDayMoney()+money);
				author.setTotalMoney(author.getTotalMoney()+money);
				userService.updateUser(author);
				userService.updateUser(reder);
				PlayHistory playHistory =new PlayHistory();
				playHistory.setId(CommonUtil.uuid());
				playHistory.setType(type);
				playHistory.setAuthorId(authorId);
				playHistory.setRederId(rederId);
				playHistory.setMoney(money);
				playHistory.setArticleId(articleId);
				playHistory.setPlayDate(new Date());
				playHistoryService.add(playHistory);
				//记录在分润表中
				FenrunLog fenrunLog=new FenrunLog();
				fenrunLog.setAddTime(new Date());
				fenrunLog.setType(StaticKey.FenrunPlayTour);
				fenrunLog.setUser(reder);
				fenrunLog.setMoney(-money.intValue());
				fenrunLog.setContentId(articleId);
				FenrunLog fenrunLog1=new FenrunLog();
				fenrunLog1.setAddTime(new Date());
				fenrunLog1.setType(StaticKey.FenrunPlayTour);
				fenrunLog1.setUser(author);
				fenrunLog1.setMoney(money.intValue());
				fenrunLog1.setContentId(articleId);
				fenrunLogService.saveFenrunLog(fenrunLog);
				fenrunLogService.saveFenrunLog(fenrunLog1);
			}
		}else if(type==0){
			Article article=articleService.findById(articleId);
			 if(article==null){
				 map.put("msg", "用户不存在或还是让其他人打赏吧");
					map.put("code", 100);
					return map;
			 }else{
				 	reder.setTotalMoney(reder.getTotalMoney()-money);
					reder.setDayMoney(reder.getDayMoney()+money);
					author.setTotalMoney(author.getTotalMoney()+money);
					userService.updateUser(author);
					userService.updateUser(reder);
					PlayHistory playHistory =new PlayHistory();
					playHistory.setId(CommonUtil.uuid());
					playHistory.setType(type);
					playHistory.setAuthorId(authorId);
					playHistory.setRederId(rederId);
					playHistory.setMoney(money);
					playHistory.setArticleId(articleId);
					playHistory.setPlayDate(new Date());
					playHistoryService.add(playHistory);
					//记录在分润表中
					FenrunLog fenrunLog=new FenrunLog();
					fenrunLog.setAddTime(new Date());
					fenrunLog.setType(StaticKey.FenrunPlayTour);
					fenrunLog.setUser(reder);
					fenrunLog.setMoney(-money.intValue());
					fenrunLog.setContentId(articleId);
					FenrunLog fenrunLog1=new FenrunLog();
					fenrunLog1.setAddTime(new Date());
					fenrunLog1.setType(StaticKey.FenrunPlayTour);
					fenrunLog1.setUser(author);
					fenrunLog1.setMoney(money.intValue());
					fenrunLog1.setContentId(articleId);
					fenrunLogService.saveFenrunLog(fenrunLog);
					fenrunLogService.saveFenrunLog(fenrunLog1);
					
			 }
		}
		map.put("msg", "打赏成功。");
		map.put("code", 0);
		return map;
	}
	
	/**
	 * 通过文章或视频ID查询打赏金额最高的前六并得到打赏总人数和总金额
	 * @param request
	 * @param articleId
	 * @return
	 */
	@RequestMapping(value="/playTourByArticleId")
	@ResponseBody
	public Map<String,Object> playTourByArticleId(HttpServletRequest request,Integer articleId){
		Map<String,Object> map=new HashMap<String,Object>();
		List<FenrunLog> list=fenrunLogService.findByContentIdFive(articleId);
		/*if (list==null||list.size()<=0) {
			 map.put("msg", "未被打赏");
			 map.put("code", StaticKey.PlayTourNo);
			 return map;
		}*/
		 Map<String, String> ma=null;
		 Map<String, String> ma1=null;
		Integer money=0;
		 List<Object> lists=new ArrayList<Object>();
		 int a=0;
		 int[] integers=null;
		 if(list.size()!=0){
			 integers =new  int[3];
			 for(int i=0;i<integers.length;i++){
				 integers[i]=ThreadLocalRandom.current().nextInt(list.indexOf(list.get(1)), list.indexOf(list.get(1))+5000);
			 } 
		 }else{
			 integers =new  int[3];
			 for(int i=0;i<integers.length;i++){
				 integers[i]=ThreadLocalRandom.current().nextInt(1000, 20000);
			 } 
		 }
		 Arrays.sort(integers); 
		if(list.size()<3){
			List<Users> list2=userService.getUser(3-list.size());
			for (Users user : list2) {
				a++;
				ma=new HashMap<String,String>();
				ma.put("money", integers[3-a]+"");
				ma.put("userName",user.getNickName());
				ma.put("userAvatar", user.getAvatar());
				ma.put("date", user.getLoginTime()+"");
				ma.put("num", a+"");
				lists.add(ma);
			}
			//list.addAll(list2);
		}/*else if(list.size()<3){
			 int[] ints=new  int[3];
			 for(int i=0;i<ints.length;i++){
				 ints[i]=list.get(i).getMoney();
			 }
			 for(int i=3-ints.length;i<ints.length;i++){
				 integers[i]=ThreadLocalRandom.current().nextInt(100, 1000);
			 }
			 ints
		}*/
		Integer sumPeason=playHistoryService.findCount(articleId);
		if(sumPeason==0){
			sumPeason=ThreadLocalRandom.current().nextInt(3, 100);
		}
		//	List<PlayHistory> sumMoney=playHistoryService.findSums(articleId);
		
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 Users user=null;
		for (FenrunLog fenrunLog : list) {
			a++;
			money+=fenrunLog.getMoney();
			ma1=new HashMap<String,String>();
			ma1.put("money", String.valueOf(-fenrunLog.getMoney()));
			user=fenrunLog.getUser();
			ma1.put("userName",user.getNickName());
			ma1.put("userAvatar", user.getAvatar());
			ma1.put("date", formatter.format(fenrunLog.getAddTime()));
			ma1.put("num", a+"");
			lists.add(ma1);
		}
		if(money==0){
			money=-ThreadLocalRandom.current().nextInt(1000, 10000);
		}
		for (int j : integers) {
			
		}
		map.put("msg", "前"+a+"信息如下:");
		map.put("code",StaticKey.PlayTourYes);
		map.put("sumPeason", sumPeason);
		map.put("sumMoney", -money);
		map.put("list", lists);
		return map;
	}
	/**
	 * (废弃)
	 * 打赏历史记录 谁打赏了你的那篇文章或视频  你打赏了哪篇文章或视频
	 * @param request
	 * @param id 用户ID
	 * @return
	 */
	@RequestMapping(value="/playTourHistory")
	@ResponseBody
	public Map<String,Object> playTourHistory(HttpServletRequest request,Integer id){
		Users users =new Users();
		Map<String,Object> map=new HashMap<String,Object>();
		 List<Object> lists=new ArrayList<Object>();
		 users=userService.queryOne(id);
		   if(users==null){
			   map.put("msg", "用户不存在");
			   map.put("code", 2);
			   return map;
		   }
		   if(!redisService.getValidate(request,id)){
				map.put("msg", "token失效或错误");
				map.put("code", StaticKey.ReturnClientTokenError);
				return map;
			}
		
		List<PlayHistory> list=playHistoryService.queryAllAuthorId(id);
		List<PlayHistory> listReder=playHistoryService.queryAllRederId(id);
		System.out.println(list);
		if(list==null||list.size()==0||listReder==null||listReder.size()==0){
			map.put("msg", "尚未打赏.");
			map.put("code", 1);
			return map;
		}
		for (PlayHistory playHistory : listReder) {
			playHistory.setMoney(-playHistory.getMoney());
		}
		list.addAll(listReder);
	       Collections.sort(list, new Comparator<PlayHistory>(){  
	            public int compare(PlayHistory playHistory1, PlayHistory playHistory2) {
	                //按照时间进行升序排列  
	               if(playHistory1.getPlayDate().getTime() < playHistory2.getPlayDate().getTime()){  
	                    return 1;  
	                }  
	                if(playHistory1.getPlayDate().getTime() == playHistory2.getPlayDate().getTime()){  
	                    return 0;  
	                }  
	                return -1;  
	            }  
	        });   
			Map<String, String> ma=null;
			
	       for (PlayHistory playHistory : list) {
	    		ma=new HashMap<String,String>();
	    	   if(playHistory.getMoney()<0){
	    		    users=userService.queryOne(playHistory.getRederId());
	    		   /*if(users==null){
	    			   map.put("msg", "用户不存在");
	    			   map.put("code", 2);
	    			   return map;
	    		   }*/
	    		   ma.put("name",users.getNickName()); 
	    	   }else{
	    		   users=userService.queryOne(playHistory.getAuthorId());
	    		  /* if(users==null){
	    			   map.put("msg", "用户不存在");
	    			   map.put("code", 2);
	    			   return map;
	    		   }*/
	    		   ma.put("name",users.getNickName()); 
	    	   }
	    	   Video video=videoService.findById(playHistory.getArticleId());
	    	   
	    	   if(video==null){
	    		   Article article=articleService.findById(playHistory.getArticleId());
	    		   if(article==null){
	    			   map.put("msg", "文章或视频不存在");
	    			   map.put("code", 3);
	    			   return map;
	    		   }else{
	    			   String strs="";
	    			   if(article.getTitle().length()>10){
	    				     strs= article.getTitle().substring(0,5)+"...";
	    			   }else{
	    				   strs=article.getTitle();
	    			   }
	    			   ma.put("articleId", strs);
	    		   }
	    	   }else {
	    		   String strs="";
	    		   if(video.getTitle().length()>10){
    				   strs= video.getTitle().substring(0,5)+"...";
    			   }else{
    				   strs= video.getTitle();
    			   }
	    		   ma.put("articleId", strs);
			}
	    	   ma.put("money", playHistory.getMoney().toString());
	    	   lists.add(ma);
		}
	   	map.put("msg", "查询成功");
		map.put("code", 0);
		map.put("list", lists);
		return map;
	}
}