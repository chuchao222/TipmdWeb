package com.tipmd.webapp.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.tipmd.webapp.service.TipmdServiceException;

/**
 * 图片处理工具： 压缩，缩放图片
 * @author bowee2010
 * 
 */
public class ImageUtil 
{
	protected static Log log = LogFactory.getLog(ImageUtil.class);
	
	/**
	 * 将图片进行缩放和压缩 （压缩会改变图片质量）
	 * 
	 * @param originalImg - 原始图片输入流
	 * @param expectedWidth  - 期望的目标图片像素宽度
	 * @param expectedHeight  - 期望的目标图片像素高度
	 * @param quality - 图片质量 ( 0 ~ 1 之间的浮点数)
	 * @return
	 * @throws TipmdServiceException
	 */
	public static byte[] resizeAndCompressImageToJpg(InputStream originalImg, 
			int expectedWidth, int expectedHeight, float quality)
		throws TipmdServiceException
	{
		byte[] ret = resizeAndConvertToJpg(originalImg, expectedWidth, expectedHeight);
		ret = compressToJpg(ret, quality);
		
		return ret;
	}
	
	/**
	 * 将图片进行缩放和压缩 （压缩会改变图片质量）
	 * 
	 * @param originalImgBytes - 原始图片byte数组
	 * @param expectedWidth  - 期望的目标图片像素宽度
	 * @param expectedHeight  - 期望的目标图片像素高度
	 * @param quality - 图片质量 ( 0 ~ 1 之间的浮点数)
	 * @return
	 * @throws TipmdServiceException
	 */
	public static byte[] resizeAndCompressImageToJpg(byte[] originalImgBytes, 
			int expectedWidth, int expectedHeight, float quality)
		throws TipmdServiceException
	{
		byte[] ret = resizeAndConvertToJpg(originalImgBytes, expectedWidth, expectedHeight);
		ret = compressToJpg(ret, quality);
		
		return ret;
	}
	
	
	/**
	 * 将图片缩放成指定的width 和 height, 并转换成jpg格式
	 * 
	 * @param originalImgBytes - 原始图片byte数组
	 * @param expectedWidth  - 期望的目标图片像素宽度
	 * @param expectedHeight  - 期望的目标图片像素高度
	 * @param imgFormat - 期待压缩成的图片格式 
	 * @return 缩放之后的图片数据
	 */
	public static byte[] resizeAndConvertToJpg(byte[] originalImgBytes,
			int expectedWidth, int expectedHeight) throws TipmdServiceException
	{
		ByteArrayInputStream is = new ByteArrayInputStream(originalImgBytes);
		byte[] ret = resize(is, expectedWidth, expectedHeight, "jpg");
		if(is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ret;
	} 
	
	/**
	 * 将图片缩放成指定的width 和 height, 并转换成jpg格式
	 * 
	 * @param originalImg - 原始图片流
	 * @param expectedWidth  - 期望的目标图片像素宽度
	 * @param expectedHeight  - 期望的目标图片像素高度
	 * @param imgFormat - 期待压缩成的图片格式 
	 * @return 缩放之后的图片数据
	 */
	public static byte[] resizeAndConvertToJpg(InputStream originalImg,
			int expectedWidth, int expectedHeight) throws TipmdServiceException
	{
		return resize(originalImg, expectedWidth, expectedWidth, "jpg");
	}
	
	
	/**
	 * 将图片缩放成指定的width 和 height
	 * 
	 * @param originalImg - 原始图片
	 * @param expectedWidth  - 期望的目标图片像素宽度
	 * @param expectedHeight  - 期望的目标图片像素高度
	 * @param imgFormat - 期待压缩成的图片格式 
	 * @return 缩放之后的图片数据
	 */
	public static byte[] resize(InputStream originalImg,
			int expectedWidth, int expectedHeight, String imgFormat) throws TipmdServiceException 
	{
		if(!isSupportedImageFormat(imgFormat)) 
			throw new TipmdServiceException("Failed to resize image, unsupported image format:" + imgFormat);
		
		if (originalImg == null || expectedWidth <= 0 || expectedHeight <= 0) {
			log.info("Invalid parameters: originalImg = " + originalImg
					+ ", width = " + expectedWidth + ", height = " + expectedHeight);
			return null;
		}

		ByteArrayOutputStream out = null;
		byte[] data = null;
		try {
			Image srcImg = ImageIO.read(originalImg);
			int[] calculatedWidthAndHeight = getCalculatedWidthAndHeight(srcImg.getWidth(null), 
					srcImg.getHeight(null),expectedWidth, expectedHeight);
			int width = expectedWidth;
			int height = expectedHeight;
			if(calculatedWidthAndHeight != null && calculatedWidthAndHeight.length == 2) {
				width = calculatedWidthAndHeight[0];
				height = calculatedWidthAndHeight[1];
			}
			//TODO: 如果期望的图片长宽跟实际的图片长宽一致，那么不用处理，直接返回！
			BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			buffImg.getGraphics().drawImage(srcImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
			out = new ByteArrayOutputStream(256 * 1024); // 256K
			ImageIO.write(buffImg, imgFormat, out); 
			out.flush();
			data = out.toByteArray();
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return data;
	}
	
	
	/**
	 * 压缩图像成jpg格式
	 * 
	 * @param originalImgBytes - 原始图像数据字节
	 * @return - 压缩之后的图像数据
	 */
	public static byte[] compressToJpg(byte[] originalImgBytes, float quality) throws TipmdServiceException 
	{
		ByteArrayInputStream is = new ByteArrayInputStream(originalImgBytes);
		byte[] ret = compress(is, quality, "jpg");
		if(is != null) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	/**
	 * 压缩图像成jpg格式 (默认质量为0.8)
	 * 
	 * @param originalImg - 原始图像数据流
	 * @return - 压缩之后的图像数据
	 */
	public static byte[] compressToJpg(InputStream originalImg, float quality) throws TipmdServiceException 
	{
		return compress(originalImg, quality, "jpg");
	}

	/**
	 * 压缩图像成指定格式
	 * 
	 * @param originalImg - 原始图像数据流
	 * @param quality - 图片质量(0~1之间的浮点数)
	 * @param imgFormat - 期待压缩成的图片格式
	 * @return
	 */
	public static byte[] compress(InputStream originalImg, float quality, String imgFormat)
			throws TipmdServiceException
	{
		if(originalImg == null)
			throw new TipmdServiceException("Failed to compress image while image input stream is null");
		
		if(!isSupportedImageFormat(imgFormat)) 
			throw new TipmdServiceException("Failed to compress image, unsupported image format:" + imgFormat);
		
		if(quality < 0 || quality > 1) quality = 0.8f;
		
		ByteArrayOutputStream out = null;
		byte[] data = null;
		try {
			BufferedImage src = ImageIO.read(originalImg);
			out = new ByteArrayOutputStream(originalImg.available());
			ImageWriter imgWrier = ImageIO.getImageWritersByFormatName(imgFormat).next();
			imgWrier.reset();
			imgWrier.setOutput(ImageIO.createImageOutputStream(out));
			imgWrier.write(null, new IIOImage(src, null, null), getOutImageParams(originalImg, quality));
			out.flush();
			data = out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return data;
	}

	private static ImageWriteParam getOutImageParams(InputStream originalImg,
			final float quality) {
		ImageWriteParam imgWriteParams = new JPEGImageWriteParam(null);
		// 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
		imgWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		// 这里指定压缩的程度，参数qality是取值0~1范围内，
		imgWriteParams.setCompressionQuality(quality);
		imgWriteParams.setProgressiveMode(ImageWriteParam.MODE_DISABLED);
		ColorModel colorModel = ColorModel.getRGBdefault();
		// 指定压缩时使用的色彩模式
		imgWriteParams.setDestinationType(new ImageTypeSpecifier(
				colorModel, colorModel.createCompatibleSampleModel(16, 16)));

		return imgWriteParams;
	}

	private static boolean isSupportedImageFormat(String imgFormat) {
		if(StringUtils.isEmpty(imgFormat)) return false;
		imgFormat = imgFormat.toLowerCase();
		return (imgFormat.equals("jpg") || imgFormat.equals("png") || imgFormat.equals("jpeg"));
	}
	
	/**
	 * 根据实际图片尺寸以及用户期望的图片宽度和高度，计算出实际高度和宽度（保证长宽比不变）
	 * @param img
	 * @param expectedMaxWidth
	 * @param expectedMaxHeight
	 * @return
	 */
	public static int[] getCalculatedWidthAndHeight(
			int actualWidth, int actualHeight,
			int expectedMaxWidth, int expectedMaxHeight) 
		throws TipmdServiceException
	{
		if(actualWidth <= 0 || actualHeight <= 0)
			throw new TipmdServiceException("Image width or height is invalid: width = "+ actualWidth + ", height = " + actualWidth);
		
		if(actualWidth == expectedMaxWidth && actualHeight == expectedMaxHeight) {
			return new int[] {actualWidth, actualHeight};
		} 
		
		//log.debug("actualWidth="+actualWidth+",actualHeight="+actualHeight+",expectedMaxWidth="+expectedMaxWidth+",expectedMaxHeight="+expectedMaxHeight);
		double aspectRatio = (actualWidth * 1.0d) / actualHeight; //宽高比
		double wRate = (expectedMaxWidth - actualWidth) / (actualWidth * 1.0d);
		double hRate = (expectedMaxHeight - actualHeight) / (actualHeight * 1.0d);
		int[] calculatedWidthAndHeight = new int[2];
		
		if(expectedMaxWidth > actualWidth && expectedMaxHeight > actualHeight) {
			//zoom in - 放大的时候 以放大比例大的边为基准
			if(wRate >= hRate) {
				//以宽为标准
				calculatedWidthAndHeight[0] = expectedMaxWidth;
				calculatedWidthAndHeight[1] = (int)(expectedMaxWidth / aspectRatio);
			} else {
				//以高为标准
				calculatedWidthAndHeight[0] = (int)(expectedMaxHeight * aspectRatio);
				calculatedWidthAndHeight[1] = expectedMaxHeight;
			}
			
			return calculatedWidthAndHeight;
		}  else if (expectedMaxWidth < actualWidth && expectedMaxHeight < actualHeight) {
			//zoom out - 缩小的时候以缩小比例大的边为基准
			if(Math.abs(wRate) >= Math.abs(hRate)) {
				//以宽为标准
				calculatedWidthAndHeight[0] = expectedMaxWidth;
				calculatedWidthAndHeight[1] = (int)(expectedMaxWidth / aspectRatio);
			} else {
				//以高为标准
				calculatedWidthAndHeight[0] = (int)(expectedMaxHeight * aspectRatio);
				calculatedWidthAndHeight[1] = expectedMaxHeight;
			}
			return calculatedWidthAndHeight;
		}
		
		return new int[]{actualWidth, actualHeight}; //do not zoom in/out
	}

}
