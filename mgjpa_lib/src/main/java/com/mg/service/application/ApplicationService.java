package com.mg.service.application;

import java.util.Date;
import java.util.List;

import com.mg.exception.ServiceException;
import com.mg.model.Audit;
import com.mg.model.Logs;
import com.mg.service.Service;

/**
 * Application service interface.
 * 
 *
 */
public interface ApplicationService extends Service {
	
	List<Audit> getAuditByDates(Date startDate, Date endDate)throws ServiceException;
	
	List<Logs> getLogsByDates(Date startDate, Date endDate)throws ServiceException;
	
}