package com.mg.web.struts.action.application;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.mg.datamining.clustering.DataItem;
import com.mg.datamining.clustering.DataSetCreator;
import com.mg.datamining.clustering.GenericKMeansClustererImpl;
import com.mg.datamining.collect.CollectData;
import com.mg.datamining.enums.Attribute;
import com.mg.datamining.product.ProductDataSetCreatorImpl;
import com.mg.model.Audit;
import com.mg.model.Product;
import com.mg.service.ServiceLocator;
import com.mg.service.application.ApplicationServiceImpl;
import com.mg.service.datamining.DataminigServiceImpl;
import com.mg.web.struts.action.BasicAction;

public class MiningDataAction extends BasicAction {

	private static final long serialVersionUID = 1155604242419622177L;
	private Date startDate;
	private Date endDate;
	
	@Override
	public String execute() {
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date d = sdf.parse("21/08/2016");
			//List<Audit> auditList= ServiceLocator.getService(ApplicationServiceImpl.class).getAuditByDates(startDate, endDate);
			CollectData collection = new CollectData();
			collection.collectSessions(ServiceLocator.getService(ApplicationServiceImpl.class).getAuditByDates(startDate, endDate));
			//auditList = null;
			//collection.groupingSessions();
			collection.groupingSessionsDevice();
			collection.saveDevices();
			System.out.println( collection );
			//collection.saveAudtiHistory(auditList);
			//collection.saveAudtiHistory(startDate, endDate);
			DataSetCreator<Product,Attribute> bc = new ProductDataSetCreatorImpl();
			List<DataItem<Product,Attribute>> productData = bc.createLearningData();
			GenericKMeansClustererImpl<Product,Attribute> clusterer = new GenericKMeansClustererImpl<Product,Attribute>(
					productData, 16);
			clusterer.cluster();
			ServiceLocator.getService(DataminigServiceImpl.class).saveDataminingProduct(clusterer.getClusters());
			System.out.println(clusterer.toString());
			return SUCCESS;
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
