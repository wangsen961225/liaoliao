package com.liaoliao.listener;

//@WebListener
//public class JobListener implements ServletContextListener {
//
//	 public void contextDestroyed(ServletContextEvent arg0) {
//	  Scheduler job = (Scheduler) SpringBeanLoader.getSpringBean("quartzScheduler");
//	  try {
//	   if(job.isStarted()){
//	    job.shutdown();
//	    Thread.sleep(1000);
//	   }
//	  } catch (SchedulerException e) {
////	   e.printStackTrace();
//	  } catch (InterruptedException e) {
////	   e.printStackTrace();
//	  }
//
//	 }
//
//	 public void contextInitialized(ServletContextEvent arg0) {
//	 }
//}

/**
 * @author Vio
 * 避免tomcat关闭时quartz的线程还在运行
 *
 */
/*@WebListener
public class QuartzContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		WebApplicationContext webApplicationContext = (WebApplicationContext) arg0.getServletContext()
				.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		org.quartz.impl.StdScheduler startQuertz = (org.quartz.impl.StdScheduler) webApplicationContext.getBean("jobFactory");
		if (startQuertz != null) {
			startQuertz.shutdown();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
//			e.printStackTrace();
		}
	}

	@Override  
    public void contextInitialized(ServletContextEvent arg0) {  
		//不做任何事情  
    }

}*/
/*@WebListener
public class ShutdownQuartz implements ServletContextListener{
	ApplicationContext ctx = ApplicationContextHelper.getApplicationContext();
    public void contextDestroyed(ServletContextEvent arg0) {
        try {
            // Get a reference to the Scheduler and shut it down
            Scheduler scheduler3 = (Scheduler) ctx.getBean("JobSchedule");
            scheduler3.shutdown(true);
            // Sleep for a bit so that we don't get any errors
            System.out.println("3");
            // Get a reference to the Scheduler and shut it down
            Scheduler scheduler4 = (Scheduler) ctx.getBean("schedulerFactoryBean");
            scheduler4.shutdown(true);
            // Sleep for a bit so that we don't get any errors
            System.out.println("4");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void contextInitialized(ServletContextEvent arg0) {
//    	不操作
    	System.out.println("计划shutdown-监听器");
    }
}*/
