package com.mg.util.io;


import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mg.util.text.StringUtils;

/**
 * Utility class to regroup file manipulation common methods.
 * 
 * @author MJGP
 *
 */
public final class FileUtils
{		
	
	private static final Logger log = Logger.getLogger(FileUtils.class);
	
	private FileUtils(){}
	
	/**
	 * Takes a string file path as argument and returns 
	 * its content as String.
	 * @param fileName the file path
	 * @return
	 * @throws IOException
	 */
	public static String readTextFile(String fileName) throws IOException {		
		return (readTextFile(new File(fileName)));
	}
	
	/**
	 * Takes a file object as argument and returns 
	 * its content as String.
	 * @param file the file object
	 * @return
	 * @throws IOException
	 */
	public static String readTextFile(File file) throws IOException {		
		return readTextFile(new FileInputStream(file));
	}
	
	private static String readTextFile(FileInputStream fis) throws IOException {
		StringWriter buffer = new StringWriter();
		int c;		
		while((c = fis.read()) != -1)
		{
			buffer.write(c);
		}

		fis.close();
		buffer.close();

		return buffer.toString();
	}

	/**
	 * Writes a String to file.
	 * @param fileName
	 * @param textToBeWritten
	 * @throws IOException
	 */
	public static void writeTextFile(String fileName, String textToBeWritten) 
	throws IOException{
		writeTextFile(fileName, textToBeWritten, false);
	}

	/**
	 * Writes a String to file with option
	 * to append the content.
	 * @param fileName
	 * @param textToBeWritten
	 * @param append
	 * @throws IOException
	 */
	public static void writeTextFile(String fileName, String textToBeWritten, 
			boolean append) throws IOException{
			FileOutputStream out = new FileOutputStream(fileName, append);			
			PrintStream printStr = new PrintStream(out);
			printStr.print(textToBeWritten);
			printStr.close();
	}
		
	/**
	 * Changes the name of file.
	 * Arguments are File paths.
	 * @param sourcePath
	 * @param destPath
	 */
	public static void renameFile(String sourcePath, String destPath) 
	throws IOException{
		File sourceFile = new File(sourcePath);
		File destFile = new File(destPath);		
		renameFile(sourceFile, destFile);
	}
	
	/**
	 * Changes the name of file.
	 * Arguments are File objects.
	 * @param sourceFile
	 * @param destFile
	 */
	public static void renameFile(File sourceFile, File destFile)
	throws IOException{
		sourceFile.renameTo(destFile);
	}
	
	/**
	 * Calculates the file size in KB.
	 * Arguments are File paths.
	 * @param fileName
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static long getFileSize(String fileName) throws IOException{
		return getFileSize(new File(fileName));
	}
	
	/**
	 * Calculates the file size in KB.
	 * Arguments are File objects.
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static int getFileSize(File file) throws IOException{
		return (int) (file.length() / 1024);
	}
	
	/**
	 * Copies source file to destination file.
	 * @param srFile
	 * @param dtFile
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void copyfile(String sourceFileName, String destFileName) 
	throws IOException{
		 copyfile(new File(sourceFileName), new File(destFileName));
	}
	
	public static void copyfile(File sourceFile, File destFile)
	throws IOException{
	      
	      InputStream in = new FileInputStream(sourceFile);
	      OutputStream out = new FileOutputStream(destFile);

	      byte[] buffer = new byte[1024];
	      int length;
	      while ((length = in.read(buffer)) > 0){
	        out.write(buffer, 0, length);
	      }
	      
	      in.close();
	      out.close();
	      if(log.isInfoEnabled()){
	    	  log.info(("File copied.")); 
	      }
	  }
	
	/**
	 * Calculates the dimension of an image. 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static Dimension getImageDimension(File file) throws IOException {
		Dimension value = null;
		InputStream in = new FileInputStream(file);
		ImageInfo imageInfo = new ImageInfo();
		imageInfo.setInput(in);
		if (imageInfo.check()) {
			value = new Dimension();
			value.setSize(imageInfo.getWidth(), imageInfo.getHeight()); 
		}
		in.close();
		return value;
	}
	
	/**
	 * Returns the file passed in as a string with all 
	 * occurrences of key/values replaced.
	 * @param file
	 * @param keysValues
	 * @return
	 */
	public static String replaceFileWithHashTable(File file, Map<String, String> keysValues) 
	throws IOException{
		return StringUtils.replaceStringByMap(readTextFile(file), keysValues);
	}
	
	/**
	 * Returns the resolution or O if cannot get it
	 * @param sourceImageFile
	 * @return
	 * @throws IOException 
	 */
	public static int getImageResolution(File sourceImageFile) throws IOException {
		int value = 0;
		InputStream in = new FileInputStream(sourceImageFile);
		ImageInfo imageInfo = new ImageInfo();
		imageInfo.setInput(in);
		if (imageInfo.check()) {
			value = imageInfo.getPhysicalHeightDpi();
			//(value==-1) means that it couldn't find the resolution
			if (value==-1)
				value=0;
		}
		in.close();
		return value;
	}
	
	/**
	 * {@link ImageInfo#ImageInfo()}
	 * @param file
	 * @return
	 * @throws IOException 
	 */

	public static boolean isImage(File file) throws IOException  {
		InputStream in = new FileInputStream(file);
		ImageInfo imageInfo = new ImageInfo();
		imageInfo.setInput(in);
		in.close();
		return imageInfo.check();
	}
	/**
	 * 1 JPEG
	 * 2 PNG
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static int isJpgePng(File file) throws IOException{
		InputStream in = new FileInputStream(file);
		ImageInfo imageInfo = new ImageInfo();
		imageInfo.setInput(in);
		int result = -1;
		if (imageInfo.check()) {
			if (imageInfo.isJPEG())
				result = 1;
			if (imageInfo.isPNG())
				result = 2;			
		}
		in.close();
		return result;	
		
	}
	 public static String fileContentsToString(File theFileName)
     throws Exception
	  {
	     // open our file
	     FileInputStream fileStream   = new FileInputStream(theFileName);
	     String returnStr = FileUtils.fileContentsToString(fileStream);
	     fileStream.close();
	     return(returnStr);
	  }
	 
	 public static String fileContentsToString(FileInputStream file)
     throws Exception
	  {
	     StringWriter buffer = new StringWriter();
	     int c;
	
	     // read the contents
	     while((c = file.read()) != -1)
	     {
	        buffer.write(c);
	     }
	
	     // close the file
	     file.close();
	     buffer.close();
	
	     //return the contents as a string
	     return buffer.toString();
	  }
	/**
	 * 		THIS CODE IS JUST FOR FUTURE TEST TO HELP YOU MY PROGRAMMER
	 		String sourceImageName="png.png";
			String GAMEACCESS_IMAGES_DIR = "d:/borrame/media/game/cover/";
			String sourceImagePath = GAMEACCESS_IMAGES_DIR + sourceImageName;
			File sourceImageFile = new File(sourceImagePath);
			
			
			FileInputStream fis = new FileInputStream(sourceImageFile);
			ImageInfo imageInfo = new ImageInfo();
			imageInfo.setInput(fis);
			if (imageInfo.check()) {
				System.out.println(imageInfo.getPhysicalHeightDpi());
				imageInfo.print(sourceImagePath, imageInfo, true);
			}
	 * @throws FileNotFoundException 
	 */
} // class

