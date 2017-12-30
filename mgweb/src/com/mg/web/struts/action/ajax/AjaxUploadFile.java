package com.mg.web.struts.action.ajax;


import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.mg.exception.ServiceException;
import com.mg.exception.ServiceLocatorException;
import com.mg.model.Image;
import com.mg.service.ServiceLocator;
import com.mg.service.dto.ImageDTO;
import com.mg.service.image.ImageService;
import com.mg.service.image.ImageServiceImpl;
import com.mg.service.init.ConfigServiceImpl;
import com.mg.service.product.ProductServiceImpl;
import com.mg.web.struts.action.BasicAction;


/**
 * Reads an <code>application/octet-stream</code> and writes it to a file.
 * @author John Yeary <jyeary@bluelotussoftware.com>
 * @author Allan O'Driscoll
 * @version 1.0
 * 
 */
public class AjaxUploadFile extends BasicAction implements ServletResponseAware {

    private static final long serialVersionUID = 6748857432950840322L; 
    
    private static String realPath;
   
    private Integer productId;
    private Integer imageId;
    private Integer customComponentImageId;
  //uploading files
    private File qqfile;
	private String qqfileContentType;
	private String qqfileFileName;
	
	private ImageDTO imageDTO;
	private Dimension dim;

	private HttpServletResponse servletResponse;

    /**
     * {@inheritDoc}
     * @param config
     * @throws ServletException
     */
    public AjaxUploadFile() throws ServiceLocatorException, ServiceException {
    	super();
    }
    
    public String uploadImage() {    	
    	PrintWriter writer = null;
    	
        try {        	
    		writer = servletResponse.getWriter();
    		fileType();
			ImageService imageService = ServiceLocator.getService(ImageServiceImpl.class);
			realPath = request.getSession().getServletContext().getRealPath("/");
			if( imageDTO != null && imageDTO.getFile() == null){					 
				 writer.print("{success:'', msg: '" + getText("mg.error.common.required") + "'}" );						
			} else if(!imageService.isJPEGImage(imageDTO.getFile()) && !imageService.isPNGImage(imageDTO.getFile())) {		
				 writer.print("{success:'', msg: '" + getText("mg.error.common.content.type_jpg") + "'}" );	
			} else {
				if (dim == null)  {
					dim = getDimension(imageDTO.getFile().getAbsolutePath());
				}
				String path = ServiceLocator.getService(ConfigServiceImpl.class).getWebImageProdcutLocation() + productId+ "/";
				if( imageId != null ){ //Add a customComponentImage to product					
					//CustomComponentImage customComponentImage = ServiceLocator.getService(ImageServiceImpl.class).getCustomComponentImage(customComponentImageId);
					Image image = ServiceLocator.getService(ImageServiceImpl.class).getImage(imageId);
					path = image.getRealName();
					//Remove old image mask from disk
					boolean result = ServiceLocator.getService(ImageServiceImpl.class).deleteImage(image);
					if(result){
						imageService.saveImage(imageDTO.getFile(), path, image.getName());
					}
					writer.print("{success: true, name: '" + image.getName() + "', path: '" + 
							path +"'}");
				}
				else{ //Add new Image to product to show in frontend in addEditProduct.jsp for the main product image
					Integer id = ServiceLocator.getService(ProductServiceImpl.class).saveProductImage(imageDTO, productId);
					if ( id != null ){
						writer.print("{success: true, name: '" + imageDTO.getFileFileName() + "', path: '" + 
								path +"', id: " + id + "}");
					}
				}
			}
		}
		catch (Exception e) {
            writer.print("{success: '', msg: '" + getText("mg.error.common.service") + "'}" );
            managerException(e);
		} 			
		servletResponse.setStatus(HttpServletResponse.SC_OK);
        writer.flush();
        writer.close();
		
		return NONE;
    }
    public String deleteProductImage(){
		try {
				if(imageId != null && imageId > 0){
					ServiceLocator.getService(ProductServiceImpl.class).deleteProductImageEntity(imageId);
				}
		}
		catch (Exception e) {
			managerException(e);	
		}
		return  SUCCESS;
	}
    
    private void fileType() throws IOException {    	
        InputStream is = null;
        FileOutputStream fos = null;        
        // firefox and other
        
        if (qqfile == null) {      
        	imageDTO = new ImageDTO();
        	imageDTO.setFileFileName(request.getParameter("qqfile"));         	
			imageDTO.setFile(new File(request.getParameter("qqfile")));						 
			is = request.getInputStream();	           
	        fos = new FileOutputStream( imageDTO.getFile() );
	        IOUtils.copy(is, fos);
	        
	        if(fos != null )
	        	fos.close();
	        if(is != null)
	        	is.close();
        } else {	// IE 7, 8        	
        	imageDTO.setFileFileName(qqfileFileName);
        	imageDTO.setFile(qqfile);        
        }        
    }
    
    public Dimension getDimension(String filename) {
    	Dimension outcome = new Dimension(1, 1);
    	 try {	
    		 File f = new File(filename);
    		 BufferedImage image = ImageIO.read(f);
    		 int height = image.getHeight();
    		 int width = image.getWidth();
    		 System.out.println("Height : "+ height);
    		 System.out.println("Width : "+ width);
    		 outcome = new Dimension(width, height);
    	 } catch (IOException ioe) {	
    		 ioe.printStackTrace();
    	 }
    	 return outcome;
    }

	public static String getRealPath() {
		return realPath;
	}

	public static void setRealPath(String realPath) {
		AjaxUploadFile.realPath = realPath;
	}
	
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getImageId() {
		return imageId;
	}

	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}

	public File getQqfile() {
		return qqfile;
	}

	public void setQqfile(File qqfile) {
		this.qqfile = qqfile;
	}

	public String getQqfileContentType() {
		return qqfileContentType;
	}

	public void setQqfileContentType(String qqfileContentType) {
		this.qqfileContentType = qqfileContentType;
	}

	public String getQqfileFileName() {
		return qqfileFileName;
	}

	public void setQqfileFileName(String qqfileFileName) {
		this.qqfileFileName = qqfileFileName;
	}

	public Dimension getDim() {
		return dim;
	}

	public void setDim(Dimension dim) {
		this.dim = dim;
	}
	
	public HttpServletResponse getServletResponse() {
		return servletResponse;
	}

	public void setServletResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
	}

	public Integer getCustomComponentImageId() {
		return customComponentImageId;
	}

	public void setCustomComponentImageId(Integer customComponentImageId) {
		this.customComponentImageId = customComponentImageId;
	}

	public ImageDTO getImageDTO() {
		return imageDTO;
	}

	public void setImageDTO(ImageDTO imageDTO) {
		this.imageDTO = imageDTO;
	}
	

}
