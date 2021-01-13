
package com.bootx.service.impl;

import com.bootx.entity.ProductImage;
import com.bootx.service.ProductImageService;
import com.bootx.util.ImageUtils;
import com.bootx.util.UploadUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Service - 商品图片
 * 
 * @author 好源++ Team
 * @version 6.1
 */
@Service
public class ProductImageServiceImpl implements ProductImageService {

	/**
	 * 临时文件扩展名
	 */
	private static final String TEMP_FILE_EXTENSION = "tmp";

	/**
	 * 添加图片处理任务
	 *
	 * @param sourcePath
	 *            原图片上传路径
	 * @param largePath
	 *            图片文件(大)上传路径
	 * @param tempFile
	 *            原临时文件
	 * @param contentType
	 *            原文件类型
	 */
	private void addTask(final String sourcePath, final String largePath, final File tempFile, final String contentType) {
		File largeTempFile = new File(FileUtils.getTempDirectory(), UUID.randomUUID() + "." + ProductImage.FILE_EXTENSION);
		File mediumTempFile = new File(FileUtils.getTempDirectory(), UUID.randomUUID() + "." + ProductImage.FILE_EXTENSION);
		File thumbnailTempFile = new File(FileUtils.getTempDirectory(), UUID.randomUUID() + "." + ProductImage.FILE_EXTENSION);
		try {
			ImageUtils.zoom(tempFile, largeTempFile);
			UploadUtils.upload(sourcePath, tempFile, contentType);
			UploadUtils.upload(largePath, largeTempFile, ProductImage.FILE_CONTENT_TYPE);
		} finally {
			FileUtils.deleteQuietly(tempFile);
			FileUtils.deleteQuietly(largeTempFile);
			FileUtils.deleteQuietly(mediumTempFile);
			FileUtils.deleteQuietly(thumbnailTempFile);
		}
	}

	@Override
	public ProductImage generate(MultipartFile multipartFile,String uploadPath) {
		Assert.notNull(multipartFile, "[Assertion failed] - multipartFile is required; it must not be null");
		Assert.state(!multipartFile.isEmpty(), "[Assertion failed] - multipartFile must not be empty");

		try {
			String uuid = String.valueOf(UUID.randomUUID()).replace("-","");
			String sourcePath = uploadPath + String.format(ProductImage.SOURCE_FILE_NAME, uuid, FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
			String largePath = uploadPath + String.format(ProductImage.LARGE_FILE_NAME, uuid, ProductImage.FILE_EXTENSION);
			File tempFile = new File(FileUtils.getTempDirectory(), uuid + "." + TEMP_FILE_EXTENSION);
			multipartFile.transferTo(tempFile);
			addTask(sourcePath, largePath, tempFile, multipartFile.getContentType());
			ProductImage productImage = new ProductImage();
			productImage.setSource(UploadUtils.getUrl(sourcePath));
			productImage.setLarge(UploadUtils.getUrl(largePath));
			return productImage;
		} catch (IllegalStateException | IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

}