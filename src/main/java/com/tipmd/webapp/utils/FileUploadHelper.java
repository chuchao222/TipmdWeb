package com.tipmd.webapp.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

public class FileUploadHelper 
{
	protected static Log log = LogFactory.getLog(FileUploadHelper.class);
	
	public static boolean isAValidImageFile(String fileName) {
		String fileExt = getFileExt(fileName);
		String acceptedImgFormat = ApplicationPropertiesFileReader.getAcceptedImageFormat();
		if (StringUtils.isEmpty(acceptedImgFormat)) {
			return true;
		}

		acceptedImgFormat = acceptedImgFormat.toLowerCase();
		fileExt = fileExt.toLowerCase();
		// TODO: 优化算法
		if (acceptedImgFormat.contains(fileExt)) {
			return true;
		}

		return false;
	}

	public static String getFileExt(String fileName) {
		int index = fileName.lastIndexOf(".");
		if (index == -1)
			return "";
		return fileName.substring(index + 1);
	}
	
	public static String saveFileToLocal(byte[] fileData, String fileName) {
		if(fileData == null || fileData.length == 0 || StringUtils.isEmpty(fileName)) return null;

		long nowTime = System.currentTimeMillis();
		Date now = new Date(nowTime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		//file save path
		String path = sdf.format(now);
		
		StringBuilder realPathSb = new StringBuilder(Constants.APP_PATH);
		if(Constants.APP_PATH.charAt(Constants.APP_PATH.length() -1 ) != File.separatorChar)
			realPathSb.append(File.separator);
		realPathSb.append(ApplicationPropertiesFileReader.getDefaultUploadPath()); //refer disk path
		realPathSb.append(path);
		String realPath = realPathSb.toString();
		
		log.debug("saveFileToLocal realPath = " + realPath);
		File realPathFile = new File(realPath);
		//check to see if the real path is exist.
		if(!realPathFile.exists()) {
			log.info("realPath: " + realPath + " is not exist, create it...");
			realPathFile.mkdir();
		}
		
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(realPath + File.separator + fileName));
			fos.write(fileData);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if(fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return path + "/" + fileName; //这里要用URL路径而不是文件系统路径
	}
	
	public static String getRandomJpgFileName(String key)//, UserVo sessionUser) 
	{
//		long nowTime = System.currentTimeMillis();
//		StringBuffer path = new StringBuffer();
//		path.append(nowTime);
//		path.append("_");
//		path.append(key);
//		path.append("_");
//		path.append(sessionUser.getId());
//		path.append("_");
//		path.append(getRandomChar());
//		path.append(getRandomChar());
//		path.append(getRandomChar());
//		path.append(getRandomChar());
//		path.append(".jpg");
//		
//		return path.toString();
		return null;
	}
	
	
	private static char getRandomChar() {
		return (char)('a' + Math.random() * ('z'-'a'+1));
	}
}
