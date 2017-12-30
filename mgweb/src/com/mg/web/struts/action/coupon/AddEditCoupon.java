package com.mg.web.struts.action.coupon;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;

import com.mg.coupon.PromotionManager;
import com.mg.enums.CouponStatus;
import com.mg.enums.CouponType;
import com.mg.enums.Language;
import com.mg.model.Coupons;
import com.mg.model.CouponsType;
import com.mg.service.ServiceLocator;
import com.mg.service.coupon.CouponServiceImpl;
import com.mg.service.dto.ImageDTO;
import com.mg.util.translation.TranslationUtils;
import com.mg.util.translation.Translations;
import com.mg.web.struts.action.BasicTranslationAction;

public class AddEditCoupon extends BasicTranslationAction {

	private static final long serialVersionUID = 1155604242419622177L;
	private Integer id;
	private CouponsType couponsType;
	private List<CouponType> codeTypeList;
	private Integer number;
	private ImageDTO imageEn;
	private ImageDTO imageFr;
	protected String urlEn;
	protected String urlFr;
	
	@Override
	public String execute() {
		try{
			//Set the coupon type list
			setCodeTypeList(Arrays.asList(CouponType.values()));
			
			if(id != null){
				setCouponsType(ServiceLocator.getService(CouponServiceImpl.class).getCouponsType(id));
				setValueTranslation(couponsType);
				if (couponsType.getTranslationByUrlTransId() != null ) {
					urlEn = TranslationUtils.getTranslation(couponsType.getTranslationByUrlTransId(), Language.ENGLISH);
					urlFr = TranslationUtils.getTranslation(couponsType.getTranslationByUrlTransId(), Language.FRENCH);
				}
			}
			return INPUT;
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}

	public String save(){
		try{
			if(id != null){
				couponsType.setId(id);
			}
			Translations translationsName = new Translations.StringTranslationBuilder().engString(nameEn).frString(nameFr).build();
			Translations translationsDesc = new Translations.StringTranslationBuilder().engString(descEn).frString(descFr).build();
			Translations translationsUrl = new Translations.StringTranslationBuilder().engString(urlEn).frString(urlFr).build();
			ServiceLocator.getService(CouponServiceImpl.class).saveCouponsType(couponsType, imageEn, imageFr, translationsName, translationsDesc, translationsUrl);
			PromotionManager.reset();
			return SUCCESS;
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}

	public String remove(){
		try{
			if(id != null){
				couponsType = ServiceLocator.getService(CouponServiceImpl.class).getCouponsType(id); 
				ServiceLocator.getService(CouponServiceImpl.class).deleteCouponsType(couponsType);
			}
			return SUCCESS;
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	}
	
	public String create(){
		try{
			if(id != null){
				log.debug("Create " + number + " Coupon type id : " + id);
				CouponsType type = ServiceLocator.getService(CouponServiceImpl.class).getCouponsType(id);
				List<Coupons> list = new ArrayList<Coupons>();
				for (int i = 0; i < number; i++) {
					list.add( CreateCoupon(type, i) );
				}
				ServiceLocator.getService(CouponServiceImpl.class).createCouponList(list);
				
				if ( type.getPromotion() ){//if it is a promotion initialize manager with the new coupon
					PromotionManager.reset();
				}
			}
			return SUCCESS;
		} catch (Exception e) {
			managerException(e);
			return ERROR;
		}
	} 
	
	private Coupons CreateCoupon(CouponsType type, int i) {
		String date = new SimpleDateFormat("yyMMddHHmm").format(new Date());
		
		Coupons coupon = new Coupons();
		coupon.setCouponName("LEO" + i + date + RandomStringUtils.randomAlphanumeric(10).toUpperCase() );
		coupon.setCouponsType(type);
		coupon.setStatusCode(CouponStatus.ACTIVE);
		return coupon;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CouponsType getCouponsType() {
		return couponsType;
	}

	public void setCouponsType(CouponsType couponsType) {
		this.couponsType = couponsType;
	}

	public List<CouponType> getCodeTypeList() {
		return codeTypeList;
	}

	public void setCodeTypeList(List<CouponType> codeTypeList) {
		this.codeTypeList = codeTypeList;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public ImageDTO getImageEn() {
		return imageEn;
	}

	public void setImageEn(ImageDTO imageEn) {
		this.imageEn = imageEn;
	}

	public ImageDTO getImageFr() {
		return imageFr;
	}

	public void setImageFr(ImageDTO imageFr) {
		this.imageFr = imageFr;
	}

	public String getUrlEn() {
		return urlEn;
	}

	public void setUrlEn(String urlEn) {
		this.urlEn = urlEn;
	}

	public String getUrlFr() {
		return urlFr;
	}

	public void setUrlFr(String urlFr) {
		this.urlFr = urlFr;
	}
	
}
