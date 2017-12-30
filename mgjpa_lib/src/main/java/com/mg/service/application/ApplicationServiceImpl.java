package com.mg.service.application;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.mg.dao.core.DaoCommand;
import com.mg.dao.core.DaoFactory;
import com.mg.dao.impl.AuditDAO;
import com.mg.dao.impl.JobsDAO;
import com.mg.dao.impl.LogsDAO;
import com.mg.exception.DaoException;
import com.mg.exception.ServiceException;
import com.mg.model.Audit;
import com.mg.model.Jobs;
import com.mg.model.Logs;
import com.mg.service.ServiceImpl;

/**
 * Provides all users related logic in the system.
 * 
 * 
 */
public class ApplicationServiceImpl extends ServiceImpl implements ApplicationService {

	private static final Logger log = Logger.getLogger(ApplicationServiceImpl.class);

	public ApplicationServiceImpl() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Audit> getAuditByDates(final Date startDate, final Date endDate)
			throws ServiceException {
		List<Audit> list = null;
		try {			
			daoManager.setCommitTransaction(true);
			list = (List<Audit>) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					return DaoFactory.getDAO(AuditDAO.class, em).getAuditRangeDate(startDate, endDate);				
				}
			}) ;
		}catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Logs> getLogsByDates(final Date startDate, final Date endDate)
			throws ServiceException {
		List<Logs> list = null;
		try {			
			daoManager.setCommitTransaction(true);
			list = (List<Logs>) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					return DaoFactory.getDAO(LogsDAO.class, em).getLogsRangeDate(startDate, endDate);				
				}
			}) ;
		}catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Jobs> getJobs()
			throws ServiceException {
		List<Jobs> list = null;
		try {			
			daoManager.setCommitTransaction(true);
			list = (List<Jobs>) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					return DaoFactory.getDAO(JobsDAO.class, em).getJobs();				
				}
			}) ;
		}catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return list;
	}
	
	public Date updateJobRunDate(final String jobName)throws ServiceException {
		final Date now = new Date();
		try {			
			daoManager.setCommitTransaction(true);
			daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					Jobs jobs = DaoFactory.getDAO(JobsDAO.class, em).findByName(jobName);
					jobs.setUpdateDate(now);
					DaoFactory.getDAO(JobsDAO.class, em).update(jobs);
					return null;			
				}
			}) ;
		}catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return now;
	}

}
