package com.mg.dao.core;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.Logger;import org.apache.logging.log4j.LogManager;

import com.mg.exception.DaoException;

/**
 * Defines DAOs, manages transaction boundaries, and assures that 
 * database operations get executed using the same 
 * transaction (The {@link SqlSession} in MyBatis world).
 *  
 * @author MJGP
 */
public class DaoManagerImpl extends AbstractDaoManager {
	
	private static final Logger log = LogManager.getLogger(DaoManagerImpl.class);
	
	protected static final String ERROR_MSG = "You can't use Dao Manager in this mode.";
	
	public DaoManagerImpl(){
		this(false);
	}
	
	public DaoManagerImpl(boolean commitTransaction){				
		super(commitTransaction);
	}	
	
	@Override
	public final Object executeAndHandle(DaoCommand command) throws DaoException{
		Object returnValue = null;
		String status = null;
		Long start = System.currentTimeMillis(); 
		try {
			SqlEntityManagerThreadSafe.beginTransaction();
			status = "begin";
			returnValue = command.execute(SqlEntityManagerThreadSafe.getEntityManager());
			if(isCommitTransaction()){
				SqlEntityManagerThreadSafe.commit();
				status = status + "-commit";
			}
			closeTimerConnection(start, status);
			
		} catch (Exception pe) {			
			if(isCommitTransaction()){
				try{
					//log.error(pe.getCause().getMessage());
					log.error(pe.getMessage());
					if(SqlEntityManagerThreadSafe.getEntityManager().isOpen()){
						SqlEntityManagerThreadSafe.rollback();
						status = status + "-rollback";
					}
				}
				catch(Exception e){
					log.error("Trying rolling back... " + e.getMessage());
					status = status + "-error-rollback";
				}
			}			
			
			closeTimerConnection(start, status);
			
			throw new DaoException("Data Access Command Problem", pe);
		}
		
			
		return returnValue;
	}
	
	
	private void closeTimerConnection(Long start, String status){
		
		if(SqlEntityManagerThreadSafe.getEntityManager().isOpen()){
			SqlEntityManagerThreadSafe.closeEntityManager();
			status = status + "-closed";
		}
		else{
			log.error("CONNECTION ALREADY CLOSED!!! ");
		}
		
		Long finish = System.currentTimeMillis(); 
		Long millis = finish - start;
		log.debug("Transaction duraction: "  + 
					String.format("%d sec, %d millisecond",	TimeUnit.MILLISECONDS.toSeconds(millis), TimeUnit.MILLISECONDS.toMillis(millis)) + 
					" status: " + status);
		if( millis > 5000 && millis < 10000 ){
			log.warn("Transaction duraction: "  + 
					String.format("%d sec, %d millisecond",	TimeUnit.MILLISECONDS.toSeconds(millis), TimeUnit.MILLISECONDS.toMillis(millis)) + 
					" status: " + status);
		} 
		if( millis > 10000 ){
			log.error("Transaction duraction: "  + 
					String.format("%d sec, %d millisecond",	TimeUnit.MILLISECONDS.toSeconds(millis), TimeUnit.MILLISECONDS.toMillis(millis)) + 
					" status: " + status);
		}
	}
}
