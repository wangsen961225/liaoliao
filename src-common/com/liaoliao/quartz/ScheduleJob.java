package com.liaoliao.quartz;

/**
 * 计划任务信息
 * @author Administrator
 *
 */
public class ScheduleJob {
	
    /** 任务名称 */  
    private String jobName;  
    /** 任务分组 */  
    private String jobGroup;
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobGroup() {
		return jobGroup;
	}
	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}
	@Override
	public String toString() {
		return "ScheduleJob [jobName=" + jobName + ", jobGroup=" + jobGroup + "]";
	}  
    
    

}
