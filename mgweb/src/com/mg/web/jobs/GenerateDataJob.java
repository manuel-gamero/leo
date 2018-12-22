package com.mg.web.jobs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.mg.datamining.collect.CollectData;
import com.mg.model.Audit;
import com.mg.model.Config;
import com.mg.service.ServiceLocator;
import com.mg.service.application.ApplicationServiceImpl;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.util.exception.ExceptionHandler;
import com.mg.web.listener.schedule.QuartzSchedulerListener;

public class GenerateDataJob implements Job {

	private static final Logger log = Logger.getLogger(ResetFeedJob.class);
	private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm";
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.debug("Calling GenerateDataJob." );
		try {
			JobDataMap data = context.getMergedJobDataMap();
			//Get config
			Config confiDataMinig = ServiceLocator.getService(ConfigServiceImpl.class).getConfig( data.getString( QuartzSchedulerListener.VALUE_1 ));
			if( confiDataMinig != null && confiDataMinig.getValue() != null && confiDataMinig.getValue().length() > 0 ){
				SimpleDateFormat sdf = new SimpleDateFormat( DATE_FORMAT );
				Date startDate = sdf.parse( confiDataMinig.getValue() );
				Date endDate = new Date();
	
				//Run dataminig
				List<Audit> auditList= ServiceLocator.getService(ApplicationServiceImpl.class).getAuditByDates( startDate, endDate );
				log.debug( auditList.size() + " rows are going to be processed");
				CollectData collection = new CollectData();
				collection.collectSessions(auditList);
				log.debug("Getting sessions");
				collection.getSessions();
				collection.groupingSessions();
				collection.getDevices();
				log.debug("Saving sessions");
				collection.saveAudtiHistory(auditList);
				
				log.debug( collection );
				
				//Save new config
				confiDataMinig.setValue( sdf.format( endDate ) );
				ServiceLocator.getService(ConfigServiceImpl.class).updateConfig( confiDataMinig );
				
				log.info("GenerateDataJob executed from " + startDate + " to " +  endDate);
			}
		} catch (Exception e) {
			ExceptionHandler.handleException(e, null, null);
		}
	}
}
