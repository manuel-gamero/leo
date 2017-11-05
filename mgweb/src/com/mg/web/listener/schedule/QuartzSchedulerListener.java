package com.mg.web.listener.schedule;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.mg.model.Jobs;
import com.mg.service.ServiceLocator;
import com.mg.service.application.ApplicationServiceImpl;
import com.mg.util.exception.ExceptionHandler;


public class QuartzSchedulerListener implements ServletContextListener {
	
	private static final Logger log = Logger.getLogger(QuartzSchedulerListener.class);
	private static final String TRIGER_SUBFIX = "Trigger";
	private static final String PACKAGE_JOBS = "com.mg.web.jobs.";

	public void contextDestroyed(ServletContextEvent arg0) {
		//
	}

	@SuppressWarnings("unchecked")
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			
			List<Jobs> jobs = ServiceLocator.getService(ApplicationServiceImpl.class).getJobs();
			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			
			for (Jobs job : jobs) {
				try{
					Class<? extends Job> jobClass = (Class<? extends Job>) Class.forName( PACKAGE_JOBS + job.getName());
					JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(job.getName(), job.getGroupJob()).setJobData(getJobDataMap(job)).build();
					
					Trigger trigger = TriggerBuilder.newTrigger().withIdentity(job.getName() + TRIGER_SUBFIX, job.getGroupJob())
							.withSchedule(CronScheduleBuilder.cronSchedule( job.getSchedule() )).build();
	
					
					scheduler.start();
					log.info("Job name: " + job.getName() + " group: " + job.getGroupJob() + " scheduler: " + job.getSchedule());
					
					scheduler.scheduleJob(jobDetail, trigger);
				}
				catch (ClassNotFoundException e) {
					log.error("Job " + job.getName() + " does not exist.");
					ExceptionHandler.handleException(e, "Job " + job.getName() + " does not exist.", null);
				}
			}
		} catch (Exception e) {
			ExceptionHandler.handleException(e, null, null);
		}

	}

	private JobDataMap getJobDataMap(Jobs job) {
		JobDataMap dataMap = new JobDataMap();
		dataMap.put("key1", job.getValue1());
		dataMap.put("key2", job.getValue2());
		return dataMap;
	}
}