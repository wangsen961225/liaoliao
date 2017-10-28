package com.liaoliao.sys.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liaoliao.listener.MyContextLoader;
import com.liaoliao.quartz.ScheduleJob;
import com.liaoliao.util.JPushUtil;
import com.liaoliao.util.StaticKey;
import com.liaoliao.util.TimeKit;



@Controller
@RequestMapping("/sys")
public class QuartzController {
	
	@Autowired
	private Scheduler scheduler;
	 
	
	/**
	 * 进入定时器管理界面
	 * @param jobName
	 * @param jobGroup
	 * @return
	 */
	@RequestMapping("/quartzPage")
	public String quartzPage(String jobName,String jobGroup){
		return "quartzManager/quartz";
	}
	
	
	    /** 
	     * 任务创建与更新(未存在的就创建，已存在的则更新) 
	     * @param request 
	     * @param response 
	     * @param scheduleJob 
	     * @param model 
	     * @return 
	     */  
	    @RequestMapping("/startQuartz")  
	    @ResponseBody
	    public Map<String,Object> updateQuartz(HttpServletRequest request,String jobName,String jobGroup,String time){  
	    	Map<String,Object> map = new HashMap<>(); 
	    	ScheduleJob job = new ScheduleJob();
	    	job.setJobGroup(jobGroup.trim());
	    	job.setJobName(jobName.trim());
	        try {  
	                //获取触发器标识  
	                TriggerKey triggerKey = TriggerKey.triggerKey(jobName.trim(), jobGroup.trim());  
	                //获取触发器trigger  
	                CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);  
	          //   if(null==trigger){//不存在任务  
	                    //创建任务  
	                    JobDetail jobDetail = JobBuilder.newJob(QuartzJob.class)  
	                            .withIdentity(jobName.trim(), jobGroup.trim())  
	                            .build();  
	                    jobDetail.getJobDataMap().put("scheduleJob", job);  
	             //       String timer="0 0/10 * * * ?";
	                    
	                    //表达式调度构建器  
	                    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time);  
	                    //按新的cronExpression表达式构建一个新的trigger  
	                    trigger = TriggerBuilder.newTrigger()  
	                            .withIdentity(job.getJobName(), job.getJobGroup())  
	                            .withSchedule(scheduleBuilder)  
	                            .build();  
	                    scheduler.scheduleJob(jobDetail, trigger);
	                    map.put("msg", "开启成功!");
	               /* }else{
	                	//存在任务  
	                    // Trigger已存在，那么更新相应的定时设置  
	                    //表达式调度构建器  
	                    CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(time);  
	                    //按新的cronExpression表达式重新构建trigger  
	                    trigger = trigger.getTriggerBuilder()  
	                            .withIdentity(triggerKey)  
	                            .withSchedule(scheduleBuilder)  
	                            .build();  
	                    //按新的trigger重新设置job执行  
	                    scheduler.rescheduleJob(triggerKey, trigger); 
	                    map.put("msg", "设置新时间,启动成功!");
	                }*/
	        } catch (SchedulerException e) {  
	            e.printStackTrace();  
	        } 
	        if("阅读翻倍".equals(jobName)){
	        	Map<String, String> extras = new HashMap<String,String>();
				//状态,用户id
				
				extras.put("type", StaticKey.JPushSendOpenReadDouble);
				extras.put("beginTime", MyContextLoader.getContext().getAttribute("beginReadDoubleTime").toString());
				extras.put("closeTime", MyContextLoader.getContext().getAttribute("closeReadDoubleTime").toString());
				//发送通知
				JPushUtil.sendAllMessage("开启阅读翻倍", extras,86400 );
	        }
	        return map;
	    }  
	    
	    /** 
	     * 删除任务 
	     * @param request 
	     * @param response 
	     * @param scheduleJob 
	     * @param model 
	     * @return 
	     */  
	    @RequestMapping("/removeQuartz") 
	    @ResponseBody
	    public Map<String,Object> deleteQuartz(HttpServletRequest request,String jobName,String jobGroup){  
	    	Map<String,Object> map= new HashMap<>();
	    	JobKey jobKey = JobKey.jobKey(jobName.trim(), jobGroup.trim());
	        try {  
	            scheduler.deleteJob(jobKey);  
	        } catch (SchedulerException e) {  
	            e.printStackTrace();  
	        }
	        if("阅读翻倍".equals(jobName)){
	        	MyContextLoader.getContext().setAttribute("readDouble", StaticKey.readNotDouble);
	        }
	        System.out.println("任务="+jobName+"=已结束");
	        
	        if("阅读翻倍".equals(jobName)){
	        	Map<String, String> extras = new HashMap<String,String>();
				//状态,用户id
				
				extras.put("type", StaticKey.JPushSendCloseReadDouble);
				extras.put("beginTime", MyContextLoader.getContext().getAttribute("beginReadDoubleTime").toString());
				extras.put("closeTime", new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString());
				//发送通知
				JPushUtil.sendAllMessage("关闭阅读翻倍", extras,86400 );
	        }
	        map.put("msg", "关闭成功!");
	        return map;
	    }  
	       
	    /** 
	     * 暂停任务 
	     * @param request 
	     * @param response 
	     * @param job 
	     * @param model 
	     * @return 
	     */  
	    @RequestMapping("/pauseQuartz")
	    @ResponseBody
	    public Map<String,Object> pauseQuartz(HttpServletRequest request,String jobName,String jobGroup){ 
	    	Map<String,Object> map = new HashMap<>();
	    	ScheduleJob job = new ScheduleJob();
	    	job.setJobGroup(jobGroup);
	    	job.setJobName(jobName);
	        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);  
	        try {  
	            scheduler.pauseJob(jobKey);
	        } catch (SchedulerException e) {  
	            e.printStackTrace();  
	        }
	        return map;
	    }  
	    
	    
	    /** 
	     * 恢复任务 
	     * @param request 
	     * @param response 
	     * @param scheduleJob 
	     * @param model 
	     * @return 
	     */  
	    @RequestMapping("/resumeQuartz")  
	    @ResponseBody
	    public Map<String,Object> resumeQuartz(HttpServletRequest request,String jobName,String jobGroup){ 
	    	Map<String,Object> map = new HashMap<>();
	    	ScheduleJob job = new ScheduleJob();
	    	job.setJobGroup(jobGroup);
	    	job.setJobName(jobName);
	        JobKey jobKey = JobKey.jobKey(jobName, jobGroup);  
	        try {  
	            scheduler.resumeJob(jobKey);  
	        } catch (SchedulerException e) {  
	            e.printStackTrace();  
	        } 
	        return map;
	    }  
	    
	    /**
	     * 判断quart是否启动
	     * @param request
	     * @param jobName
	     * @param jobGroup
	     * @return
	     * @throws SchedulerException
	     */
	    @RequestMapping("/findQuartz") 
	    @ResponseBody
	    public Map<String,Object> findQuartz(HttpServletRequest request) throws SchedulerException{  
	    	Map<String,Object> map = new HashMap<String,Object>();
	    	String jobGroup="1";
            TriggerKey triggerArticle = TriggerKey.triggerKey("抓取文章", jobGroup);  
            CronTrigger Article = (CronTrigger) scheduler.getTrigger(triggerArticle);
            
            TriggerKey triggerVideo = TriggerKey.triggerKey("抓取视频", jobGroup); 
            CronTrigger video = (CronTrigger) scheduler.getTrigger(triggerVideo);
            
            TriggerKey triggerTimer = TriggerKey.triggerKey("自动", jobGroup); 
            CronTrigger timer = (CronTrigger) scheduler.getTrigger(triggerTimer);
            
            TriggerKey triggerVIPLogin = TriggerKey.triggerKey("霸屏", jobGroup); 
            CronTrigger VIPLogin = (CronTrigger) scheduler.getTrigger(triggerVIPLogin);
            
            TriggerKey triggerReadDouble = TriggerKey.triggerKey("阅读翻倍", jobGroup); 
            CronTrigger readDouble = (CronTrigger) scheduler.getTrigger(triggerReadDouble);
            
            if(Article==null){
            	map.put("article", 0);//未开启
            }else{
            	map.put("article", 1);//已开启
            }
			if(video==null){
				map.put("video", 0);   //未开启      	
			}else{
				map.put("video", 1);//已开启
			}
			if(timer==null){
				map.put("timer", 0);//未开启
			}else{
				map.put("timer", 1);//已开启
			}
			if(VIPLogin==null){
				map.put("VIPLogin", 0);//未开启
			}else{
				map.put("VIPLogin", 1);//已开启
			}
			if(readDouble==null){
				map.put("readDouble", 0);//未开启
			}else{
				map.put("readDouble", 1);//已开启
			}
            return map;
	    }
	    
	    
	    @RequestMapping("/setReadDoubleTime") 
	    public String setReadDoubleTime(HttpServletRequest request,String beginReadDoubleTime,String closeReadDoubleTime) throws ParseException{
	    	ServletContext context = MyContextLoader.getContext();
	    	context.setAttribute("beginReadDoubleTime", beginReadDoubleTime);
	    	context.setAttribute("closeReadDoubleTime",closeReadDoubleTime);
	    	context.setAttribute("readDouble", 0);//0:不翻倍 1:翻倍
	    	
			return "quartzManager/quartz";  
	    }
}

