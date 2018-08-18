package com.mg.service.device;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.mg.dao.core.DaoCommand;
import com.mg.dao.core.DaoFactory;
import com.mg.dao.impl.AuditDAO;
import com.mg.dao.impl.AuditHistDAO;
import com.mg.dao.impl.DeviceCollectionDAO;
import com.mg.dao.impl.DeviceComponentDAO;
import com.mg.dao.impl.DeviceDAO;
import com.mg.dao.impl.DeviceProductDAO;
import com.mg.exception.DaoException;
import com.mg.exception.ServiceException;
import com.mg.model.Audit;
import com.mg.model.AuditHist;
import com.mg.model.Device;
import com.mg.model.DeviceCollection;
import com.mg.model.DeviceComponent;
import com.mg.model.DeviceProduct;
import com.mg.model.Product;
import com.mg.service.ServiceImpl;

public class DeviceServiceImpl extends ServiceImpl implements DeviceService {

	private static final Logger log = Logger.getLogger(DeviceServiceImpl.class);
	
	public DeviceServiceImpl() {
		super();
	}

	public Device getDevice(final String fingerPrint) throws ServiceException {
		Device result = null;
		try {
			daoManager.setCommitTransaction(true);
			result = (Device) daoManager
					.executeAndHandle(new DaoCommand() {
						@Override
						public Object execute(EntityManager em)
								throws DaoException {
							return DaoFactory.getDAO(DeviceDAO.class, em).findEntityByFingerPrint(fingerPrint);
						}
					});
		} catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return result;
	}

	public DeviceProduct getDeviceProduct(final Integer deviceId, final Integer productId) throws ServiceException {
		DeviceProduct result = null;
		try {
			daoManager.setCommitTransaction(true);
			result = (DeviceProduct) daoManager
					.executeAndHandle(new DaoCommand() {
						@Override
						public Object execute(EntityManager em)
								throws DaoException {
							return DaoFactory.getDAO(DeviceProductDAO.class, em).getDeviceProduct(deviceId, productId);
						}
					});
		} catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<DeviceProduct> getDeviceProduct(final Integer productId) throws ServiceException {
		List<DeviceProduct> result = null;
		try {
			daoManager.setCommitTransaction(true);
			result = (List<DeviceProduct>) daoManager
					.executeAndHandle(new DaoCommand() {
						@Override
						public Object execute(EntityManager em)
								throws DaoException {
							return DaoFactory.getDAO(DeviceProductDAO.class, em).getDeviceProduct(productId);
						}
					});
		} catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return result;
	}
	
	public DeviceProduct getDeviceProductGroupByProduc( final Integer productId ) throws ServiceException {
		Object[] result = null;
		try {
			daoManager.setCommitTransaction(true);
			result = (Object[]) daoManager
					.executeAndHandle(new DaoCommand() {
						@Override
						public Object execute(EntityManager em)
								throws DaoException {
							return DaoFactory.getDAO(DeviceProductDAO.class, em).getDeviceProductGroupByProduct(productId);
						}
					});
		} catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return getDeviceProduct( result );
	}
	
	@SuppressWarnings("unchecked")
	public List<DeviceProduct> getDeviceProductGroupByProduc() throws ServiceException {
		List<Object[]> result = null;
		try {
			daoManager.setCommitTransaction(true);
			result = (List<Object[]>) daoManager
					.executeAndHandle(new DaoCommand() {
						@Override
						public Object execute(EntityManager em)
								throws DaoException {
							return DaoFactory.getDAO(DeviceProductDAO.class, em).getDeviceProductGroupByProduct();
						}
					});
		} catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return getDeviceProductList( result );
	}
	
	private List<DeviceProduct> getDeviceProductList(List<Object[]> listObject) {
		List<DeviceProduct> deviceProdcutLst = new ArrayList<DeviceProduct>();
		for (Object[] item : listObject) {
			deviceProdcutLst.add( getDeviceProduct( item ) );
		}
		return deviceProdcutLst;
	}

	private DeviceProduct getDeviceProduct(Object[] item) {
		DeviceProduct deviceProduct = new DeviceProduct();
		
		deviceProduct.setProduct( new Product( (Integer)item[0] ) );
		deviceProduct.setCount( ((Long)item[1]).intValue() );
		deviceProduct.setShareCount( ((Long)item[2]).intValue() );
		deviceProduct.setAddCount( ((Long)item[3]).intValue() );
		deviceProduct.setSellCount( ((Long)item[4]).intValue() );
		deviceProduct.setRemoveCount( ((Long)item[5]).intValue() );
		
		return deviceProduct;
	}

	public DeviceCollection getDeviceCollection(final Integer deviceId, final Integer collectionId) throws ServiceException {
		DeviceCollection result = null;
		try {
			daoManager.setCommitTransaction(true);
			result = (DeviceCollection) daoManager
					.executeAndHandle(new DaoCommand() {
						@Override
						public Object execute(EntityManager em)
								throws DaoException {
							return DaoFactory.getDAO(DeviceCollectionDAO.class, em).getDeviceCollection(deviceId, collectionId);
						}
					});
		} catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return result;
	}
	
	public DeviceComponent getDeviceComponent(final Integer deviceId, final Integer componentId) throws ServiceException {
		DeviceComponent result = null;
		try {
			daoManager.setCommitTransaction(true);
			result = (DeviceComponent) daoManager
					.executeAndHandle(new DaoCommand() {
						@Override
						public Object execute(EntityManager em)
								throws DaoException {
							return DaoFactory.getDAO(DeviceComponentDAO.class, em).getDeviceComponent(deviceId, componentId);
						}
					});
		} catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return result;
	}
	
	@Override
	public int saveDevice(final Device device) throws ServiceException {
		int id = 0;
		try {
			daoManager.setCommitTransaction(true);
			id = (Integer) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					try {
						return DaoFactory.getDAO(DeviceDAO.class, em)
								.update(device).getId();
					} catch (Exception e) {
						e.printStackTrace();
						throw new DaoException(e);
					}
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}
		return id;
	}

	@Override
	public int saveAuditHist(final Audit audit) throws ServiceException {
		int id = 0;
		try {
			daoManager.setCommitTransaction(true);
			id = (Integer) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					try {
						AuditHist auditHist = DaoFactory.getDAO(AuditHistDAO.class, em).update(createAuditHist(audit));
						DaoFactory.getDAO(AuditDAO.class, em).delete(audit);
						return auditHist.getId();
					} catch (Exception e) {
						e.printStackTrace();
						throw new DaoException(e);
					}
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}
		return id;
	}
	
	private AuditHist createAuditHist(Audit audit) {
		AuditHist auditHist = new AuditHist();
		auditHist.setActionUser(audit.getActionUser());
		auditHist.setBrowser(audit.getBrowser());
		auditHist.setCallDuration(audit.getCallDuration());
		auditHist.setCreationDate(audit.getCreationDate());
		auditHist.setId(audit.getId());
		auditHist.setLocation(audit.getLocation());
		auditHist.setMessage(audit.getMessage());
		auditHist.setOutcome(audit.getOutcome());
		auditHist.setRequestGuid(audit.getRequestGuid());
		auditHist.setServerName(audit.getServerName());
		auditHist.setSessionGuid(audit.getSessionGuid());
		auditHist.setUrl(audit.getUrl());
		auditHist.setUsername(audit.getUsername());
		return auditHist;
		//return ((BasicAudit)audit);
	}
}
