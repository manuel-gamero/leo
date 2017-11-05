package com.mg.util.io;

/**
 * Title:        Image Resizer
 * Description:
 * Copyright:    Copyright (c) 2004
 * Company:
 * @author Sachit Rajbhandari
 * @email sacheet@hotmail.com
 * @version 1.0
 */


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.Kernel;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.jai.InterpolationBilinear;
import javax.media.jai.InterpolationNearest;
import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.swing.ImageIcon;

import com.sun.media.jai.codec.JPEGEncodeParam;

public class ImageTransformer
{
    //Constructor
    public ImageTransformer()
    {
    }

    //Load Image from file
    public static PlanarImage loadImage(String sourceFile)
    {
        return JAI.create("fileload", sourceFile);
    }

    //Rotates image with given angle
    public static PlanarImage rotateImage(PlanarImage img, Double angle)
    {
        return JAI.create("rotate",img,0.0F,0.0F,(float)(angle.floatValue()*(Math.PI/180.0F)),new InterpolationBilinear());
    }
    
    

    //Scales Image with given width, height
    //Maintains ratio of image if keepRatio is true
    public static PlanarImage scaleImage(PlanarImage img, Integer width, Integer height,boolean keepRatio)
    {
        float ws = 1.0F;
        float hs = 1.0F;
        if(width != null && height != null)
        {
            ws = (float)width.floatValue() / (float)img.getWidth();
            hs = (float)height.floatValue() / (float)img.getHeight();
        }
        else if(width!=null)
        {
            ws = (float)width.floatValue() / (float)img.getWidth();
            if(keepRatio)
                hs = ws;
        }
        else if(height!=null)
        {
            hs = (float)height.floatValue() / (float)img.getHeight();
            if(keepRatio)
                ws = hs;
        }
        return JAI.create("scale",img,ws,hs,0.0F,0.0F, new InterpolationNearest());
    }

    public static void scaleAndSaveImage(String sourceFile, int width, int height, String destinationFile){

        //PlanarImage planarImage = loadImage(sourceFile);
        //saveImage(scaleImage(planarImage, new Integer(width), new Integer(height), false), destinationFile);
    	
    	File sFile = new File(sourceFile);
    	File dFile = new File(destinationFile);
        
    	try {
			resizeAndSave(sFile, dFile, width, height, 0.8f);
		} catch (IOException e) {
			e.printStackTrace();
		}

    }
    
    // Pure Java Method
	public static void resizeAndSave(File originalFile, File resizedFile, int newWidth, int newHeight, float quality) throws IOException {
		 
        if (quality < 0 || quality > 1) {
            throw new IllegalArgumentException("Quality has to be between 0 and 1");
        }
 
        ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
        Image i = ii.getImage();
        Image resizedImage = null;
 
        resizedImage = i.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
       
        // This code ensures that all the pixels in the image are loaded.
        Image temp = new ImageIcon(resizedImage).getImage();
 
        // Create the buffered image.
        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null), temp.getHeight(null), BufferedImage.TYPE_INT_RGB);
 
        // Copy image to buffered image.
        Graphics g = bufferedImage.createGraphics();
 
        // Clear background and paint the image.
        g.setColor(Color.white);
        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
        g.drawImage(temp, 0, 0, null);
        g.dispose();
 
        // Soften.
        float softenFactor = 0.05f;
        float[] softenArray = {0, softenFactor, 0, softenFactor, 1-(softenFactor*4), softenFactor, 0, softenFactor, 0};
        Kernel kernel = new Kernel(3, 3, softenArray);
        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        bufferedImage = cOp.filter(bufferedImage, null);
 
        // Write the jpeg to a file.
        FileOutputStream out = new FileOutputStream(resizedFile);
 
        // Encodes image as a JPEG data stream
        /*JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
 
        com.sun.image.codec.jpeg.JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bufferedImage);
 
        param.setQuality(quality, true);
 
        encoder.setJPEGEncodeParam(param);
        encoder.encode(bufferedImage);*/
        
        ImageIO.write(bufferedImage, "png", out);
        out.close();
        
    }
 

    //Saves Image in file
    public static void saveImage(PlanarImage img, String targetFile)
    {
    	try{
    		JAI.create("FileStore", img.getAsBufferedImage(),targetFile,"jpeg",new JPEGEncodeParam());
    	}catch (Exception e) {
			e.printStackTrace();
		}
        
    }
    
    public static void TransformColorToTransparency(File sourceImageFile, File destImageFile) throws IOException{
    	
    	BufferedImage image = ImageIO.read(sourceImageFile);
    	Color c1 = new Color(0, 50, 77);
    	Color c2 = new Color(200, 200, 255);
    	
        // Primitive test, just an example
        final int r1 = c1.getRed();
        final int g1 = c1.getGreen();
        final int b1 = c1.getBlue();
        final int r2 = c2.getRed();
        final int g2 = c2.getGreen();
        final int b2 = c2.getBlue();
        ImageFilter filter = new RGBImageFilter() {
          public final int filterRGB(int x, int y, int rgb) {
            int r = (rgb & 0xFF0000) >> 16;
            int g = (rgb & 0xFF00) >> 8;
            int b = rgb & 0xFF;
            if (r >= r1 && r <= r2 &&
                g >= g1 && g <= g2 &&
                b >= b1 && b <= b2) {
              // Set fully transparent but keep color
              return rgb & 0xFFFFFF;
            }
            return rgb;
          }
        };

        ImageProducer ip = new FilteredImageSource(image.getSource(), filter);
        Image img = Toolkit.getDefaultToolkit().createImage(ip);
        
        BufferedImage resultImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2 = resultImage.createGraphics();
        graphics2.drawImage(image, 0, 0, null);
        graphics2.dispose();
        
       
		ImageIO.write(resultImage, "PNG", destImageFile);
      }

    public static void main(String args[])
    {
    	scaleAndSaveImage("c:/test/100Bullets.jpg", 156, 180, "c:/test/thumb/100Bullets.jpg");
    	
      /*  PlanarImage img = null;
        ImageTransformer it = new ImageTransformer();
        try
        {
        if (args.length>1)
        {
            java.io.File f = new java.io.File(args[0]);
            if ( f.exists() && f.canRead() )
            {
                img = it.loadImage(args[0]);
            }

            if(args.length>4)
              img = it.rotateImage(img,new Double(args[4]));
            if(args.length>5)
            img = it.scaleImage(img,new Integer(args[2]),new Integer(args[3]),args[5]=="1"?true:false);
            if(args.length>3)
            img = it.scaleImage(img,new Integer(args[2]),new Integer(args[3]),false);
            if(args.length>2)
            img = it.scaleImage(img,new Integer(args[2]),null,false);

            it.saveImage(img,args[1]);
        }
        }
        catch(Exception e)
        {
            System.out.println("Arguments should be: SourceFile OutputFile Width Height Angle(In Degree) KeepRatio(1/0)");
        }
        
        */

    }
}
