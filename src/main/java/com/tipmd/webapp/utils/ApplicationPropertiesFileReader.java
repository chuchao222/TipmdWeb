package com.tipmd.webapp.utils;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 此类用来读取 application.properties 文件的配置信息
 * @author tonytang
 */
public final class ApplicationPropertiesFileReader 
{
	private static Properties props;
	private static final String ACCEPTED_IMAGE_FORMAT_KEY = "accepted.image.format";
	private static final String DEFAULT_UOLOAD_PATH = "default.upload.path";
	
	static {
		Resource resource = new ClassPathResource("/application.properties");
		try {
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getAcceptedImageFormat() {
		return props.getProperty(ACCEPTED_IMAGE_FORMAT_KEY, "jpg,png,jpeg");
	}
	
	public static String getDefaultUploadPath() {
		return props.getProperty(DEFAULT_UOLOAD_PATH, "/Users/upload/");
	}
	
}
