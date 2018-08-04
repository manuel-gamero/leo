package com.mg.service.image;

import java.io.File;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import com.mg.enums.ImageType;
import com.mg.exception.CacheException;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.CustomComponentImage;
import com.mg.model.Image;
import com.mg.model.Item;
import com.mg.model.Product;
import com.mg.service.Service;
import com.mg.service.dto.ImageDTO;
import com.mg.util.io.ImageInfo;

/**
 * Image service 
 * @author MJGP
 *
 */
public interface ImageService extends Service{
	
	/**
	 * Save the file information in disk.
	 * 
	 * There was two problems. The first is that for all the png images with transparent background were saved in larger folders 
	 * even if they are not large . The second is that the images that were saved through saveThumbFile, they do not keep 
	 * the transparent background.
	 * So for the images the normal product and in general, I going to save the real image in its folder and the larger in larger folder
	 * and thumbnail in the thumbnail folder. For all the images that have transparent background the real image will be saved in 
	 * larger folder and the larger folder in its folder and thumbnail in the thumbnail folder.
	 * 
	 * Now that we have a large amount of images save like that it is more dificult to fix the issue, that is why
	 * the logic that applies is above.
	 * 
	 * @param file
	 * @param path
	 * @param normalizedName
	 * @param transparent
	 * @return If the file was well saved
	 * @throws ServiceException
	 */
	
	boolean saveImage(File file, String path, String normalizedName, boolean transparent) throws ServiceException;

			
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
	Image getImage(File file, String fileName, ImageType imageType) throws ServiceException;
	
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
	
	Image getImageForObject(EntityManager em, ImageDTO image, ImageType imageType, Integer imageId) throws ServiceException;
	
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


	Set<CustomComponentImage> getCustomComponentImages(int imageId) throws ServiceException, CacheException, ServiceLocatorException;
}