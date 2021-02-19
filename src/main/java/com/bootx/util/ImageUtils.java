
package com.bootx.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.IdentifyCmd;
import org.springframework.util.Assert;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Utils - 图片处理(支持JDK、GraphicsMagick、ImageMagick)
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public final class ImageUtils {

	/**
	 * 方式
	 */
	private enum Method {

		/**
		 * 自动
		 */
		AUTO,

		/**
		 * Jdk
		 */
		JDK,

		/**
		 * GraphicsMagick
		 */
		GRAPHICS_MAGICK,

		/**
		 * ImageMagick
		 */
		IMAGE_MAGICK
	}

	/**
	 * 方式
	 */
	private static Method method = Method.AUTO;

	/**
	 * GraphicsMagick程序路径
	 */
	private static String graphicsMagickPath;

	/**
	 * ImageMagick程序路径
	 */
	private static String imageMagickPath;

	/**
	 * 背景颜色
	 */
	private static final Color BACKGROUND_COLOR = Color.white;

	/**
	 * 目标图片品质(取值范围: 0 - 100)
	 */
	private static final int DEST_QUALITY = 88;

	static {
		if (SystemUtils.IS_OS_WINDOWS) {
			String pathVariable = System.getenv("Path");
			if (StringUtils.isNotEmpty(pathVariable)) {
				String[] paths = StringUtils.split(pathVariable, ";");
				if (graphicsMagickPath == null) {
					for (String path : paths) {
						File gmFile = new File(path.trim() + "/gm.exe");
						File gmdisplayFile = new File(path.trim() + "/gmdisplay.exe");
						if (gmFile.exists() && gmdisplayFile.exists()) {
							graphicsMagickPath = path.trim();
							break;
						}
					}
				}
				if (imageMagickPath == null) {
					for (String path : paths) {
						File convertFile = new File(path.trim() + "/convert.exe");
						File compositeFile = new File(path.trim() + "/composite.exe");
						if (convertFile.exists() && compositeFile.exists()) {
							imageMagickPath = path.trim();
							break;
						}
					}
				}
			}
		}

		if (Method.AUTO.equals(method)) {
			try {
				IMOperation operation = new IMOperation();
				operation.version();
				IdentifyCmd identifyCmd = new IdentifyCmd(true);
				if (graphicsMagickPath != null) {
					identifyCmd.setSearchPath(graphicsMagickPath);
				}
				identifyCmd.run(operation);
				method = Method.GRAPHICS_MAGICK;
			} catch (Throwable e1) {
				try {
					IMOperation operation = new IMOperation();
					operation.version();
					IdentifyCmd identifyCmd = new IdentifyCmd(false);
					identifyCmd.run(operation);
					if (imageMagickPath != null) {
						identifyCmd.setSearchPath(imageMagickPath);
					}
					method = Method.IMAGE_MAGICK;
				} catch (Throwable e2) {
					method = Method.JDK;
				}
			}
		}
	}

	/**
	 * 不可实例化
	 */
	private ImageUtils() {
	}

	/**
	 * 等比例图片缩放
	 * 
	 * @param srcFile
	 *            源文件
	 * @param destFile
	 *            目标文件
	 * @param destWidth
	 *            目标宽度
	 * @param destHeight
	 *            目标高度
	 */
	public static void zoom(File srcFile, File destFile, int destWidth, int destHeight) {
		Assert.notNull(srcFile, "[Assertion failed] - srcFile is required; it must not be null");
		Assert.state(srcFile.exists(), "[Assertion failed] - srcFile must exists");
		Assert.state(srcFile.isFile(), "[Assertion failed] - srcFile must be file");
		Assert.notNull(destFile, "[Assertion failed] - destFile is required; it must not be null");
		Assert.state(destWidth > 0, "[Assertion failed] - destWidth must be greater than 0");
		Assert.state(destHeight > 0, "[Assertion failed] - destHeight must be greater than 0");

		if (Method.JDK.equals(method)) {
			Graphics2D graphics2D = null;
			ImageOutputStream imageOutputStream = null;
			ImageWriter imageWriter = null;
			try {
				BufferedImage srcBufferedImage = ImageIO.read(srcFile);
				int srcWidth = srcBufferedImage.getWidth();
				int srcHeight = srcBufferedImage.getHeight();
				int width = destWidth;
				int height = destHeight;
				if (srcHeight >= srcWidth) {
					width = (int) Math.round((destHeight * 1.0 / srcHeight) * srcWidth);
				} else {
					height = (int) Math.round((destWidth * 1.0 / srcWidth) * srcHeight);
				}
				BufferedImage destBufferedImage = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
				graphics2D = destBufferedImage.createGraphics();
				graphics2D.setBackground(BACKGROUND_COLOR);
				graphics2D.clearRect(0, 0, destWidth, destHeight);
				graphics2D.drawImage(srcBufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), (destWidth / 2) - (width / 2), (destHeight / 2) - (height / 2), null);

				imageOutputStream = ImageIO.createImageOutputStream(destFile);
				imageWriter = ImageIO.getImageWritersByFormatName(FilenameUtils.getExtension(destFile.getName())).next();
				imageWriter.setOutput(imageOutputStream);
				ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
				imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				imageWriteParam.setCompressionQuality((float) (DEST_QUALITY / 100.0));
				imageWriter.write(null, new IIOImage(destBufferedImage, null, null), imageWriteParam);
				imageOutputStream.flush();
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			} finally {
				if (graphics2D != null) {
					graphics2D.dispose();
				}
				if (imageWriter != null) {
					imageWriter.dispose();
				}
				IOUtils.closeQuietly(imageOutputStream);
			}
		} else {
			IMOperation operation = new IMOperation();
			operation.thumbnail(destWidth, destHeight);
			operation.gravity("center");
			operation.background(toHexEncoding(BACKGROUND_COLOR));
			operation.extent(destWidth, destHeight);
			operation.quality((double) DEST_QUALITY);
			try {
				operation.addImage(srcFile.getCanonicalPath());
				operation.addImage(destFile.getCanonicalPath());
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			if (Method.GRAPHICS_MAGICK.equals(method)) {
				ConvertCmd convertCmd = new ConvertCmd(true);
				if (graphicsMagickPath != null) {
					convertCmd.setSearchPath(graphicsMagickPath);
				}
				try {
					convertCmd.run(operation);
				} catch (IOException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (InterruptedException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (IM4JavaException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			} else {
				ConvertCmd convertCmd = new ConvertCmd(false);
				if (imageMagickPath != null) {
					convertCmd.setSearchPath(imageMagickPath);
				}
				try {
					convertCmd.run(operation);
				} catch (IOException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (InterruptedException e) {
					throw new RuntimeException(e.getMessage(), e);
				} catch (IM4JavaException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
			}
		}
	}

	public static void zoom(File srcFile, File destFile) {
		Assert.notNull(srcFile, "[Assertion failed] - srcFile is required; it must not be null");
		Assert.state(srcFile.exists(), "[Assertion failed] - srcFile must exists");
		Assert.state(srcFile.isFile(), "[Assertion failed] - srcFile must be file");
		Assert.notNull(destFile, "[Assertion failed] - destFile is required; it must not be null");

		Graphics2D graphics2D = null;
		ImageOutputStream imageOutputStream = null;
		ImageWriter imageWriter = null;
		try {
			BufferedImage srcBufferedImage = ImageIO.read(srcFile);
			int srcWidth = srcBufferedImage.getWidth();
			int srcHeight = srcBufferedImage.getHeight();
			int width = srcWidth;
			int height = srcHeight;
			BufferedImage destBufferedImage = new BufferedImage(srcWidth, srcHeight, BufferedImage.TYPE_INT_RGB);
			graphics2D = destBufferedImage.createGraphics();
			graphics2D.setBackground(BACKGROUND_COLOR);
			graphics2D.clearRect(0, 0, srcWidth, srcHeight);
			graphics2D.drawImage(srcBufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), (srcWidth / 2) - (width / 2), (srcHeight / 2) - (height / 2), null);

			imageOutputStream = ImageIO.createImageOutputStream(destFile);
			imageWriter = ImageIO.getImageWritersByFormatName(FilenameUtils.getExtension(destFile.getName())).next();
			imageWriter.setOutput(imageOutputStream);
			ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
			imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			imageWriteParam.setCompressionQuality((float) (DEST_QUALITY / 100.0));
			imageWriter.write(null, new IIOImage(destBufferedImage, null, null), imageWriteParam);
			imageOutputStream.flush();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} finally {
			if (graphics2D != null) {
				graphics2D.dispose();
			}
			if (imageWriter != null) {
				imageWriter.dispose();
			}
			IOUtils.closeQuietly(imageOutputStream);
		}
	}

	/**
	 * 转换颜色为十六进制代码
	 * 
	 * @param color
	 *            颜色
	 * @return 十六进制代码
	 */
	private static String toHexEncoding(Color color) {
		Assert.notNull(color, "[Assertion failed] - color is required; it must not be null");

		String r, g, b;
		StringBuilder result = new StringBuilder();
		r = Integer.toHexString(color.getRed());
		g = Integer.toHexString(color.getGreen());
		b = Integer.toHexString(color.getBlue());
		r = r.length() == 1 ? "0" + r : r;
		g = g.length() == 1 ? "0" + g : g;
		b = b.length() == 1 ? "0" + b : b;
		result.append("#").append(r).append(g).append(b);
		return String.valueOf(result);
	}

	public static String url2Base64(String imgUrl) {
		try{
			ByteArrayOutputStream data = new ByteArrayOutputStream();
			InputStream is = null;
			try {
				if(!StringUtils.startsWith(imgUrl,"http")){
					imgUrl = "http:"+imgUrl;
				}
				URL url = new URL(imgUrl);
				byte[] by = new byte[1024];
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(5000);
				is = conn.getInputStream();
				int len = -1;
				while ((len = is.read(by)) != -1) {
					data.write(by, 0, len);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				try {
					if(is!=null){
						is.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return Base64.encodeBase64String(data.toByteArray());
		}catch (Exception e){
			e.printStackTrace();
		}

		return null;
	}

}