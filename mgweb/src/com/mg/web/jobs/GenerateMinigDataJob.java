package com.mg.web.jobs;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.mg.datamining.clustering.DataItem;
import com.mg.datamining.clustering.DataSetCreator;
import com.mg.datamining.clustering.GenericKMeansClustererImpl;
import com.mg.datamining.enums.Attribute;
import com.mg.datamining.product.ProductDataSetCreatorImpl;
import com.mg.model.Config;
import com.mg.model.Product;
import com.mg.service.ServiceLocator;
import com.mg.service.application.ApplicationServiceImpl;
import com.mg.service.datamining.DataminigServiceImpl;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.util.exception.ExceptionHandler;
import com.mg.web.listener.schedule.QuartzSchedulerListener;

public class GenerateMinigDataJob implements Job {

	private static final Logger log = LogManager.getLogger(GenerateMinigDataJob.class);
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.debug("Calling GenerateMinigDataJob." );
		JobDataMap data = context.getMergedJobDataMap();		
		try {
			Integer numCluster = Integer.valueOf(data.getString( QuartzSchedulerListener.VALUE_1 ));
			DataSetCreator<Product,Attribute> bc = new ProductDataSetCreatorImpl();
			log.debug("Creating Learning data with numCluster: " + numCluster);
			List<DataItem<Product,Attribute>> productData = bc.createLearningData();
			GenericKMeansClustererImpl<Product,Attribute> clusterer = new GenericKMeansClustererImpl<Product,Attribute>(
					productData, 16);
			log.debug("Clustering");
			clusterer.cluster();
			log.debug("Save result learing");
			ServiceLocator.getService(DataminigServiceImpl.class).saveDataminingProduct(clusterer.getClusters());
			Date dateLastRun = ServiceLocator.getService(ApplicationServiceImpl.class).updateJobRunDate("GenerateMinigDataJob");
			log.info("GenerateMinigDataJob executed at " + dateLastRun + "." );
		} catch (Exception e) {
			ExceptionHandler.handleException(e, null, null);
		}
	}
}
