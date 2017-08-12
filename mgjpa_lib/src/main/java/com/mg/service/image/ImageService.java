package com.mg.service.image;

import java.io.File;
import java.util.List;

import javax.persistence.EntityManager;

import com.mg.enums.ImageType;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.CustomComponentImage;
import com.mg.model.Image;
import com.mg.model.Item;
import com.mg.model.Product;
import com.mg.service.Service;
import com.mg.util.io.ImageInfo;

/**
 * Image service 
 * @author MJGP
 *
 */
public interface ImageService extends Service{
	/**
	 * Save the file information in disk
	 * @param file
	 * @param path
	 * @param normalizedName
	 * @return If the file was well saved
	 * @throws ServiceException
	 */
	boolean saveImage(File file, String path, String normalizedName) throws ServiceException;

			
	/**
	 * True image corresponds jpeg format
	 * @param imageLogo
	 * @return
	 * @throws ServiceException
	 */
	boolean isJPEGImage(File imageLogo) throws ServiceException;
	
	/**
	 * True image corresponds PNG format
	 * @param imageLogo
	 * @return
	 * @throws ServiceException
	 */
	boolean isPNGImage(File imageLogo) throws ServiceException;

	/**
	 * The format supported are : {@link ImageInfo#ImageInfo()}
	 * @param imageLogo
	 * @return
	 */
	boolean isImage(File imageLogo);
	
	
	/**
	 * @param file with the information
	 * @param fileName 
	 * @return Image well fill with the right information.
	 */
	Image getImage(File file, String fileName) throws ServiceException;
	
	Image getImage(int id) throws ServiceException;
	
	List<Image> getAllImage() throws ServiceException;
	
	int saveImage(final Image image) throws ServiceException;
	
	CustomComponentImage getCustomComponentImage(int id) throws ServiceException;
	
	List<CustomComponentImage> getAllCustomComponentImage() throws ServiceException;
	
	/**
	 * Create a png image with all the customComponentImage (section product mask) and the product image.
	 * @param item
	 * @param socialImage
	 * @throws ServiceException
	 */
	void generatePNGImage( Item item, boolean socialImage ) throws ServiceException;
	
	Image getImageForObject(EntityManager em, File file, String fileFileName, ImageType imageType, Integer imageId) throws ServiceException;
	
	/**
	 * delete the file from disk
	 * @param Image
	 * @return If the file was well removed
	 * @throws ServiceException
	 */
	boolean deleteImage(final Image image) throws ServiceException;
	
	/**
	 * @param product
	 * @param customComponentCollection
	 * @param Item modify the item passed using the product and the list of customComponentCollection that the
	 * 		   user had selected.
	 * @throws ServiceException
	 * @throws ServiceLocatorException
	 */
	void getItemPNG( Item item, Product product, List<String> customComponentCollection ) throws ServiceException, ServiceLocatorException;
}