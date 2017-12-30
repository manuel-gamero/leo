package com.mg.web.jobs;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.mg.service.ServiceLocator;
import com.mg.service.application.ApplicationServiceImpl;
import com.mg.service.socialmedia.InstagramServiceImpl;
import com.mg.util.exception.ExceptionHandler;

public class ResetFeedJob implements Job {
	
	private static final Logger log = Logger.getLogger(ResetFeedJob.class);
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.debug("Calling ResetFeedJob." );
		try {
			JobDataMap data = context.getMergedJobDataMap();
			
			ServiceLocator.getService(InstagramServiceImpl.class).resetFeed();
			Date dateLastRun = ServiceLocator.getService(ApplicationServiceImpl.class).updateJobRunDate("ResetFeedJob");
			log.info("ResetFeedJob executed at " + dateLastRun + "." );
		} catch (Exception e) {
			ExceptionHandler.handleException(e, null, null);
		}
	}
}
