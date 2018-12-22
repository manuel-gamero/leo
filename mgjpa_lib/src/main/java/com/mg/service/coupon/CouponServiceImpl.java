package com.mg.service.coupon;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.mg.dao.core.DaoCommand;
import com.mg.dao.core.DaoFactory;
import com.mg.dao.impl.CouponsDAO;
import com.mg.dao.impl.CouponsTypeDAO;
import com.mg.enums.ImageType;
import com.mg.exception.DaoException;
import com.mg.exception.ServiceException;
import com.mg.model.Coupons;
import com.mg.model.CouponsType;
import com.mg.model.Image;
import com.mg.service.ServiceImpl;
import com.mg.service.ServiceLocator;
import com.mg.service.dto.ImageDTO;
import com.mg.service.image.ImageServiceImpl;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.util.translation.TranslationUtils;
import com.mg.util.translation.Translations;

/**
 * Provides all users related logic in the system.
 * 
 * 
 */
public class CouponServiceImpl extends ServiceImpl implements CouponService {

	private static final Logger log = Logger.getLogger(CouponServiceImpl.class);

	public CouponServiceImpl() {
		super();
	}

	@Override
	public CouponsType getCouponsType(final Integer id) throws ServiceException {
		CouponsType result;
		try {
			daoManager.setCommitTransaction(true);
			result = (CouponsType) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					return DaoFactory.getDAO(CouponsTypeDAO.class, em).find(id);
				}
			});
		} catch (DaoException e) {
			throw (new ServiceException(e));
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CouponsType> getAllCouponsType() throws ServiceException {
		List<CouponsType> list = null;
		try {			
			daoManager.setCommitTransaction(true);
			list = (List<CouponsType>) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					return DaoFactory.getDAO(CouponsTypeDAO.class, em).findAll();				
				}
			}) ;
		}catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return list;
	}

	@Override
	public int saveCouponsType(final CouponsType couponsType, final ImageDTO imageEnDTO, final ImageDTO imageFrDTO, 
							   final Translations translationsName, final Translations translationsDesc, final Translations translationsUrl) throws ServiceException {
		int id = 0;
		try {
			daoManager.setCommitTransaction(true);
			id = (Integer)daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					int id = 0;
					try{
						String path = ServiceLocator.getService(ConfigServiceImpl.class).getWebImagePromotionLocation();
						Image imageEn = ServiceLocator.getService(ImageServiceImpl.class).getImageForObject(em, imageEnDTO, ImageType.PROMOTION, (couponsType.getImageEn() != null) ? couponsType.getImageEn().getId() : null);
						if(imageEn!=null){
							imageEn.setRealName(path);
							couponsType.setImageEn(imageEn);
						}
						else{
							couponsType.setImageEn(null);
						}
						
						Image imageFr = ServiceLocator.getService(ImageServiceImpl.class).getImageForObject(em, imageFrDTO, ImageType.PROMOTION, (couponsType.getImageFr() != null) ? couponsType.getImageFr().getId() : null);
						if(imageFr!=null){
							imageFr.setRealName(path);
							couponsType.setImageFr(imageFr);
						}
						else{
							couponsType.setImageFr(null);
						}
						
						couponsType.setCreationDate(new Date());
						
						TranslationUtils.updateBaicTranslaction(em, couponsType, translationsName, translationsDesc);
						couponsType.setTranslationByUrlTransId( TranslationUtils.updateTranslation(em, couponsType.getTranslationByUrlTransId(), translationsUrl) );
						
						if(couponsType.getId() == 0){
							DaoFactory.getDAO(CouponsTypeDAO.class, em).save(couponsType);
							id = couponsType.getId();
						}
						else{
							id = DaoFactory.getDAO(CouponsTypeDAO.class, em).update(couponsType).getId();
						}
						
						if(id != 0){
							if(imageEnDTO != null && imageEnDTO.getFile() != null){
								ServiceLocator.getService(ImageServiceImpl.class).saveImage(imageEnDTO.getFile(), path, imageEn.getName(), false);
							}
							if(imageFrDTO != null && imageFrDTO.getFile() != null){
								ServiceLocator.getService(ImageServiceImpl.class).saveImage(imageFrDTO.getFile(), path, imageFr.getName(), false);
							}
						}
					}
					catch (Exception e) {
						throw new DaoException(e);
					}
					return(id);
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}
		return id;
	}

	@Override
	public void deleteCouponsType(final CouponsType couponsType) throws ServiceException {
		try {
			daoManager.setCommitTransaction(true);
			daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {				
					DaoFactory.getDAO(CouponsTypeDAO.class, em).delete(couponsType);
					return(couponsType);
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}		
	}

	@Override
	public CouponsType getCouponsType(final String typeCode) throws ServiceException {
		CouponsType result;
		try {
			daoManager.setCommitTransaction(true);
			result = (CouponsType) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					return DaoFactory.getDAO(CouponsTypeDAO.class, em).find(typeCode);
				}
			});
		} catch (DaoException e) {
			throw (new ServiceException(e));
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Coupons> getCouponPromotion(final Date nowDate) throws ServiceException {
		List<Coupons> list = null;
		try {			
			daoManager.setCommitTransaction(true);
			list = (List<Coupons>) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					return DaoFactory.getDAO(CouponsDAO.class, em).getCouponPromotion(nowDate);			
				}
			}) ;
		}catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Coupons> getAllCouponPromotion() throws ServiceException {
		List<Coupons> list = null;
		try {			
			daoManager.setCommitTransaction(true);
			list = (List<Coupons>) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					return DaoFactory.getDAO(CouponsDAO.class, em).getAllCouponPromotion();			
				}
			}) ;
		}catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Coupons> getAllCoupons() throws ServiceException {
		List<Coupons> list = null;
		try {			
			daoManager.setCommitTransaction(true);
			list = (List<Coupons>) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					return DaoFactory.getDAO(CouponsDAO.class, em).getAll();				
				}
			}) ;
		}catch (DaoException de) {
			throw (new ServiceException(de));
		}
		return list;
	}

	@Override
	public void createCouponList(final List<Coupons> couponList)
			throws ServiceException {
		try {
			daoManager.setCommitTransaction(true);
			daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {	
					for (Coupons entity : couponList) {
						DaoFactory.getDAO(CouponsDAO.class, em).save(entity);
					}
					return null;
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}
		
	}
	
	@Override
	public Coupons getCoupon(final String couponName) throws ServiceException {
		Coupons result;
		try {
			daoManager.setCommitTransaction(true);
			result = (Coupons) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					return DaoFactory.getDAO(CouponsDAO.class, em).find(couponName);
				}
			});
		} catch (DaoException e) {
			throw (new ServiceException(e));
		}
		return result;
	}
	
	@Override
	public void updateCoupons(final Coupons coupon) throws ServiceException {
		try {
			daoManager.setCommitTransaction(true);
			daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {				
					DaoFactory.getDAO(CouponsDAO.class, em).update(coupon);
					return(coupon);
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}		
	}
	
	@Override
	public Coupons getCouponToUser(final String login) throws ServiceException {
		Coupons result;
		try {
			daoManager.setCommitTransaction(true);
			result = (Coupons) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					return DaoFactory.getDAO(CouponsDAO.class, em).findToUser(login);
				}
			});
		} catch (DaoException e) {
			throw (new ServiceException(e));
		}
		return result;
	}

}
