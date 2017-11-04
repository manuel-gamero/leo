package com.mg.service.image;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import com.mg.dao.core.DaoCommand;
import com.mg.dao.core.DaoFactory;
import com.mg.dao.core.DaoManager;
import com.mg.dao.core.DaoManagerFactory;
import com.mg.dao.impl.CustomComponentImageDAO;
import com.mg.dao.impl.ImageDAO;
import com.mg.enums.ImageType;
import com.mg.enums.ProductType;
import com.mg.exception.DaoException;
import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.CustomComponentCollection;
import com.mg.model.CustomComponentImage;
import com.mg.model.CustomComponentText;
import com.mg.model.Image;
import com.mg.model.Item;
import com.mg.model.ItemComponent;
import com.mg.model.Product;
import com.mg.service.ServiceImpl;
import com.mg.service.ServiceLocator;
import com.mg.service.cache.CacheServiceImpl;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.util.CommonUtils;
import com.mg.util.constant.BackEndConstants;
import com.mg.util.io.FileUtils;
import com.mg.util.io.ImageTransformer;
import com.mg.util.text.StringUtils;


public class ImageServiceImpl extends ServiceImpl implements ImageService {

	private static final Logger log = Logger.getLogger(ImageServiceImpl.class);

	private final static String SLAGE="/";	
	private final static String IMAGEN_LARGE = "large" + SLAGE;
	private final static String IMAGEN_THUMB = "thumbnail" + SLAGE;
	private final static boolean NORMALIZE_IMAGE_NAME = true;
	private static Map<String, Image> imageMap = new ConcurrentHashMap<String, Image>();
	
	protected DaoManager daoManager;

	public ImageServiceImpl() {
		super();
		setDaoManager(DaoManagerFactory.getDaoManager());
	}

	/**
	 * Transfer the file to the directory destination path
	 * @param sourceImageFile
	 * @param destinationPath
	 * @return
	 * @throws ServiceException
	 */
	private boolean saveFile(File sourceImageFile, String destinationPath)
			throws ServiceException {
		
		File destImageFile = new File(destinationPath);
		try {
			destImageFile.getParentFile().mkdirs();
			FileUtils.copyfile(sourceImageFile, destImageFile);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			new ServiceException(e);
		}
		return false;
	}

	/**
	 * Transfer the file to the directory destination path
	 * @param sourceImageFile
	 * @param destinationPath
	 * @return
	 * @throws ServiceException
	 */
	private boolean saveThumbFile(File sourceFile,File destinationFile, int width, int height)
			throws ServiceException {
		try {
			destinationFile.getParentFile().mkdirs();
			ImageTransformer.resizeAndSave(sourceFile, destinationFile, width, height, 1.0f);

	        return true;
			
		} catch (Exception  e) {
			e.printStackTrace();
			new ServiceException(e);
		}
		return false;
	}
	
	/**
	 * Save the file information in disk
	 * @param file
	 * @param path
	 * @param normalizedName
	 * @return If the file was well saved
	 * @throws ServiceException
	 */
	@Override
	public boolean saveImage(File file, String path, String normalizedName) throws ServiceException {
			
		try {

			String web = ServiceLocator.getService(ConfigServiceImpl.class).getRootPathWeb() + SLAGE ;
			String largDestPath 	= web + path + SLAGE + IMAGEN_LARGE + normalizedName;
			String destPath 	=  web + path + SLAGE + normalizedName;
			String ThumbProductPath = web + path + SLAGE + IMAGEN_THUMB + normalizedName;
			Dimension dim;
			boolean isLarge = false;
			dim = FileUtils.getImageDimension(file);
		
			int miniProductheight =  (int) ((dim.getHeight() / dim.getWidth()) * BackEndConstants.WIDTH_IMG_SEARCH_PAGE ); 
			isLarge = dim.getWidth() >= BackEndConstants.PRODUCT_DEFAUTL_THUMBNAIL_WIDTH * BackEndConstants.LARGE_TRANSFERT_COEFICIENT;
			
			if( isLarge ){
				saveFile(file, largDestPath);
			}
			return saveThumbFile(file, new File(destPath), (int)dim.getWidth(), (int)dim.getHeight()) &&
					saveThumbFile(file, new File(ThumbProductPath), BackEndConstants.WIDTH_IMG_SEARCH_PAGE, miniProductheight);
		} catch (Exception e) {
			throw new ServiceException(e) ;
		}		
	}
	
	/* (non-Javadoc)
	 * @see com.mg.service.image.ImageService#saveImage(com.mg.model.Image)
	 * Save the object in the database.
	 */
	@Override
	public int saveImage(final Image image) throws ServiceException {
		int id = 0;
		try {
			daoManager.setCommitTransaction(true);
			id = (Integer)daoManager.executeAndHandle(new DaoCommand() {	
				@Override
				public Object execute(EntityManager em) throws DaoException {				
					DaoFactory.getDAO(ImageDAO.class, em).save(image);
					return(image.getId());
				}
			});
		} catch (DaoException de) {
			throw new ServiceException(de);
		}
		return id;
		
	}

	
	@Override
	public boolean isJPEGImage(File imageLogo) throws ServiceException {
		CommonUtils.logMessage(log, "isJPEGImage");
		try {
			return FileUtils.isJpgePng(imageLogo)==1;
		} catch (IOException e) {
			throw new ServiceException(e);
		}
	}
		
	@Override
	public boolean isPNGImage(File imageLogo) throws ServiceException {
		try {
			return FileUtils.isJpgePng(imageLogo)==2;
		} catch (IOException e) {
			throw new ServiceException(e);
		}
	}
	
	
	@Override
	public boolean isImage(File imageLogo) {
		try {
			return FileUtils.isImage(imageLogo);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}

	public DaoManager getDaoManager() {
		return daoManager;
	}

	public void setDaoManager(DaoManager daoManager) {
		this.daoManager = daoManager;
	}

	@Override
	public Image getImage(File file, String fileName) throws ServiceException {
		Image image = null;
		int i;
		if(file != null){
			try {
				i = FileUtils.isJpgePng(file);
				// Validate if the name is already present in the DB
				String normalizedName = null;
				if( NORMALIZE_IMAGE_NAME ){
					normalizedName =  CommonUtils.normalizeImageName(fileName);
				}
				else {
					normalizedName= fileName;
				}
				if (i==1 || i==2) {
					image = createImage(file, fileName, normalizedName);
				}else{
					throw new ServiceException("Image Format not supported " + file.getName() );
				}		
			} catch (IOException e) {
				throw new ServiceException("I/O Exception " + e.getMessage() );
			}
		}
		return image;
	}
	
	private Image createImage(File file, String fileName, String normalizedName) throws IOException{
		Image image = imageMap.get(fileName);
		if( image == null){
			image = new Image();
			image.setId(0);
			image.setName(normalizedName);
			image.setSize(FileUtils.getFileSize(file));
			Dimension dim = FileUtils.getImageDimension(file);
			image.setHeight(dim.height);
			image.setWidth(dim.width);
			image.setResolution(resolutionDesicion(FileUtils.getImageResolution(file)));
			image.setScore(mathematicalFormula(image));
			image.setRealName(fileName);
			//Variables Initialization 
			boolean isLarge = dim.getWidth() >= BackEndConstants.PRODUCT_DEFAUTL_THUMBNAIL_WIDTH * BackEndConstants.LARGE_TRANSFERT_COEFICIENT;
			image.setLarge(isLarge);
			imageMap.put(fileName, image);
		}
		return(image);
	}
	
	/**
	 *Example 1 : image of 2000 X 1500 pixels 150 dpi
	 *mathematical Formula : (2000 +1500) X (150/96) = 7292
	 *@return
	 */
	private int mathematicalFormula(Image image){
		int h= image.getHeight();
		int w= image.getWidth();
		int r= image.getResolution();
		int score = (h+w)*r/BackEndConstants.CONSTANT_DPI;
		return score;
	}
	/**
	 * What to do if the resolution could not been read.
	 * @param resolution
	 * @return
	 */
	private int resolutionDesicion(int resolution){
		if (resolution==0) {
			return BackEndConstants.CONSTANT_DPI;
		}
		return resolution;
	}

	@Override
	public Image getImage (final int id) throws ServiceException {
		Image result;
		try {
			daoManager.setCommitTransaction(true);
			result = (Image) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					return DaoFactory.getDAO(ImageDAO.class, em).findEntityById(id);
				}
			});
		} catch (DaoException e) {
			throw (new ServiceException(e));
		}
		return result;
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Image> getAllImage() throws ServiceException {
		List<Image> result;
		try {
			daoManager.setCommitTransaction(true);
			result = (List<Image>) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					return DaoFactory.getDAO(ImageDAO.class, em).getAll();
				}
			});
		} catch (DaoException e) {
			throw (new ServiceException(e));
		}
		return result;
	}

	@Override
	public CustomComponentImage getCustomComponentImage(final int id)
			throws ServiceException {
		CustomComponentImage result;
		try {
			result = (CustomComponentImage) ServiceLocator.getService(CacheServiceImpl.class).getDefaultCache().fetch(CustomComponentImage.class + "_" + id);
			if(result == null){
				log.debug("CustomComponentImageId : " + id + " NOT found" );
				daoManager.setCommitTransaction(true);
				result = (CustomComponentImage) daoManager.executeAndHandle(new DaoCommand() {
					@Override
					public Object execute(EntityManager em) throws DaoException {
						return DaoFactory.getDAO(CustomComponentImageDAO.class, em).findEntityById(id);
					}
				});
				ServiceLocator.getService(CacheServiceImpl.class).getDefaultCache().store(CustomComponentImage.class + "_" + id, result); 
			}
			else{
				log.debug("CustomComponentImageId : " + id + " FOUND!!!" );
			}
		} catch (Exception e) {
			throw (new ServiceException(e));
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomComponentImage> getAllCustomComponentImage()
			throws ServiceException {
		List<CustomComponentImage> resultList;
		try {
			daoManager.setCommitTransaction(true);
			resultList = (List<CustomComponentImage>) daoManager.executeAndHandle(new DaoCommand() {
				@Override
				public Object execute(EntityManager em) throws DaoException {
					return DaoFactory.getDAO(CustomComponentImageDAO.class, em).getAll();
				}
			});
		} catch (DaoException e) {
			throw (new ServiceException(e));
		}
		return resultList;
	}

	@Override
	public void generatePNGImage( Item item, boolean socialImage) throws ServiceException {
		
		int x = 0;
	    int y = 0;
	    Product product = item.getProduct();
	    try {
	    	String fontPath = ServiceLocator.getService(ConfigServiceImpl.class).getRootPathWeb() + "fonts/";
			BufferedImage result = new BufferedImage( product.getImage().getWidth() , product.getImage().getHeight(), BufferedImage.TYPE_INT_ARGB);
			List<String> files = new ArrayList<String>();
			String path = ServiceLocator.getService(ConfigServiceImpl.class).getRootPathWeb() + ServiceLocator.getService(ConfigServiceImpl.class).getWebImageProdcutLocation() + product.getId() + "/large/";
			files.add( path + product.getImage().getName());
			String pathTmp = ServiceLocator.getService(ConfigServiceImpl.class).getRootPathWeb();
			Graphics g = result.getGraphics();
			
			for (ItemComponent itemComponent : item.getItemComponents()) {
				files.add(path +  itemComponent.getCustomComponentImage().getImageByImageMaskId().getName());
			}
			
			for(String image : files){
		        BufferedImage bi;
				bi = ImageIO.read(new File(image));
		        g.drawImage(bi, x, y, null);
		    }
			
			if ( socialImage ){
				//Sign picture
				String pathLogon = ServiceLocator.getService(ConfigServiceImpl.class).getRootPathWeb() + ServiceLocator.getService(ConfigServiceImpl.class).getWebImagesLocation();
				if(!product.getTypeCode().equals(ProductType.BAG)){
					g.drawImage(ImageIO.read(new File(pathLogon + "/logo_small.jpg")), 0, 45, null);
				}
				else{
					g.drawImage(ImageIO.read(new File(pathLogon + "/logo_small.jpg")), 10, 45, null);
				}
				pathTmp = pathTmp + ServiceLocator.getService(ConfigServiceImpl.class).getWebImageSocial();
				if ( item.getText() != null  && item.getText().length() > 0 ){
					String fontFile = fontPath + getFontName(item.getFont());
					Font font = Font.createFont(Font.TRUETYPE_FONT, new File(fontFile)); //new Font( item.getFont(), Font.PLAIN, Integer.valueOf(item.getSize()) );
					if( item.getColor().length() > 0 ){
						Color color = Color.decode(item.getColor()) ;
						g.setColor(color);
					}
					else{
						g.setColor( Color.BLACK );
					}
					g.setFont(font.deriveFont(Float.valueOf(item.getSize())));
					// Get the FontMetrics
				    // Determine the Y coordinate for the text
				    int xOffset = Math.round(getXRatio(product) * product.getImage().getWidth());
				    int yOffset = Math.round(getYRatio(product) * product.getImage().getHeight());
				    Graphics2D g2d = (Graphics2D) g;
			        FontMetrics fm = g2d.getFontMetrics();
			        Rectangle2D r = fm.getStringBounds(item.getText(), g2d);
			        int x1 = (Math.round(getWidthRatio(product) * product.getImage().getWidth()) - (int) r.getWidth()) / 2;
			        int y1 = (Math.round(getHeightRatio(product) * product.getImage().getHeight()) - (int) r.getHeight()) / 2 + fm.getAscent();
			        g.drawString(item.getText(), x1 + xOffset, y1 + yOffset );
			        g.dispose();
			        //result = getScaledImage (result, Math.round(product.getImage().getWidth() * 0.75f), Math.round(product.getImage().getHeight() * 0.75f));
				}
				//Change file name to avoid collapse
		        changeFileName( item );
		        if(!product.getTypeCode().equals(ProductType.BAG)){
		        	result = scale(result, BufferedImage.TYPE_INT_ARGB, Math.round(product.getImage().getWidth() * 0.9f), Math.round(product.getImage().getHeight() * 0.9f), 0.8, 0.8 );
				}
			}
			else{
				pathTmp = pathTmp + ServiceLocator.getService(ConfigServiceImpl.class).getWebImageTmp();
			}
			pathTmp = pathTmp.replace("//","/");
			
			createDirectory(pathTmp);
			String name = pathTmp + item.getNameImage();
			ImageIO.write(result,"png",new File(name));
	    } catch (Exception e) {
	    	throw (new ServiceException(e));
		}
	}	
	
	/**
	 * scale image
	 * 
	 * @param sbi image to scale
	 * @param imageType type of image
	 * @param dWidth width of destination image
	 * @param dHeight height of destination image
	 * @param fWidth x-factor for transformation / scaling
	 * @param fHeight y-factor for transformation / scaling
	 * @return scaled image
	 */
	public static BufferedImage scale(BufferedImage sbi, int imageType, int dWidth, int dHeight, double fWidth, double fHeight) {
	    BufferedImage dbi = null;
	    if(sbi != null) {
	        dbi = new BufferedImage(dWidth, dHeight, imageType);
	        Graphics2D g = dbi.createGraphics();
	        AffineTransform at = AffineTransform.getScaleInstance(fWidth, fHeight);
	        g.translate(50, 30);
	        g.drawRenderedImage(sbi, at);
	        
	    }
	    return dbi;
	}
	
	public static BufferedImage getScaledImage(BufferedImage image, int width, int height) throws IOException {
	    int imageWidth  = image.getWidth();
	    int imageHeight = image.getHeight();

	    double scaleX = (double)width/imageWidth;
	    double scaleY = (double)height/imageHeight;
	    AffineTransform scaleTransform = AffineTransform.getScaleInstance(scaleX, scaleY);
	    AffineTransformOp bilinearScaleOp = new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);

	    return bilinearScaleOp.filter(
	        image,
	        new BufferedImage(width, height, image.getType()));
	}
	
	private void changeFileName(Item item) {
		String oldName = item.getNameImage().split("\\.")[0];
		String ext = item.getNameImage().split("\\.")[1];
		String textName = StringUtils.stripToAlphanumericSpaceReplace( item.getText() );
		if(textName.trim().length() > 0){
			item.setNameImage( oldName + "_" + textName + "." + ext );
		}
		else{
			item.setNameImage( oldName + "." + ext );
		}
		
	}

	private float getHeightRatio(Product product) {
		for (CustomComponentText item : product.getImage().getCustomComponentTexts()) {
			if(item != null){
				return (float)item.getHeight()/item.getImageHeight();
			}
		}
		return 0;
	}

	private float getWidthRatio(Product product) {
		for (CustomComponentText item : product.getImage().getCustomComponentTexts()) {
			if(item != null){
				return (float)item.getWidth()/item.getImageWidth();
			}
		}
		return 0;
	}
	
	private float getYRatio(Product product) {
		for (CustomComponentText item : product.getImage().getCustomComponentTexts()) {
			if(item != null){
				return (float)item.getPosTop()/item.getImageHeight();
			}
		}
		return 0;
	}

	private float getXRatio(Product product) {
		for (CustomComponentText item : product.getImage().getCustomComponentTexts()) {
			if(item != null){
				return (float)item.getPosLeft()/item.getImageWidth();
			}
		}
		return 0;
	}
	
	private Color hex2Rgb(String colorStr) {
	    return new Color(
	            Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
	            Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
	            Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
	}
	
	private String getFontName(String font) {
		if(font.equals("lucida_calligraphyitalic")){
			return "lcallig-webfont.ttf";
		}
		else{
			return "ArialRoundedMTBold.ttf";
		}
	}

	/**
	 * Check if the directory already exists and create it
	 * @param path
	 * @return True if the directory is created and false in other cases
	 */
	private boolean createDirectory(String path){
		boolean created = false;
		File directory = new File(path);
		if (!directory.exists()) {
			try{
				directory.mkdir();
				created = true;
			} 
			catch(SecurityException se){
				log.debug(se.getMessage(), se);
			}        
		}
		return(created);
	}

	/* (non-Javadoc)
	 * @see com.mg.service.image.ImageService#getImageForObject(javax.persistence.EntityManager, java.io.File, java.lang.String, com.mg.enums.ImageType, int)
	 * If fileFileName is null return Image for imageId from the database.
	 * In other case create a new Image object.
	 */
	@Override
	public Image getImageForObject(EntityManager em, File file, String fileFileName,
			ImageType imageType, Integer imageId) throws ServiceException {
		Image image = null;
		if(fileFileName!=null){
			try {
				image = getImage( file, fileFileName);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				throw new ServiceException(e);
			}
			image.setTypeCode(imageType);
		}
		else{
			image = DaoFactory.getDAO(ImageDAO.class, em).find(imageId);
		}
		image.setUploadDate(new Date());
		
		return image;
	}

	@Override
	public boolean deleteImage(Image image) throws ServiceException {
		try {
			String rootPath = ServiceLocator.getService(ConfigServiceImpl.class).getRootPathWeb();
			String path = rootPath + image.getRealName() + image.getName();
			path = path.replace("\\", "/");
			File destImageFile = new File( path );
			destImageFile.setWritable(true);
			boolean exist = destImageFile.exists();
			log.debug("The file exist : " + exist);
			if ( exist ) {
				boolean delete = destImageFile.delete();
				log.debug("The file " + path +" has been delete: " + delete); 
				return delete;
			}
			return false;
		} catch (Exception e) {
			new ServiceException(e);
		}
		return false;
	}

	@Override
	public void getItemPNG( Item item, Product product, List<String> customComponentCollection) throws ServiceException, ServiceLocatorException {
		String fileName = String.valueOf(product.getId());
		if(product.getCustomProduct()){
			Set<ItemComponent> itemComponents = new HashSet<ItemComponent>(0);
			CustomComponentCollection customComponentCollectionItem;
			for (String ids : customComponentCollection) {
				if(ids != null && ids.length() > 0){
					log.debug("ids: " + ids);
					int customComponentImageId = Integer.valueOf(ids.split("_")[3]);
					customComponentCollectionItem = getCustomComponentCollectionItem(customComponentImageId);
					CustomComponentImage customComponentImage = ServiceLocator.getService(ImageServiceImpl.class).getCustomComponentImage(customComponentImageId);
					fileName = fileName + "_" + customComponentImageId;
					
					//In itemComponet keep trace the all the component of the item 
					//Relation between the user choise and part of the product
					ItemComponent itemComponent = new ItemComponent();
					itemComponent.setCustomComponentImage(customComponentImage);
					itemComponent.setCustomComponentCollection(customComponentCollectionItem);
					itemComponent.setItem(item);
					itemComponent.setCreationDate(new Date());
					
					itemComponents.add(itemComponent);
				}
			}
			fileName = fileName + ".png";
			item.setItemComponents(itemComponents);
		}
		else{
			fileName = product.getImage().getName();
		}
		item.setNameImage(fileName);
		item.setProduct(product);
		item.setCreationDate(new Date());
	}
	
	private CustomComponentCollection getCustomComponentCollectionItem(
			int customComponentImageId) throws ServiceException, ServiceLocatorException {
		CustomComponentImage customImage = ServiceLocator.getService(ImageServiceImpl.class).getCustomComponentImage(customComponentImageId);
		return customImage.getCustomComponentCollection();
	}

		
}
