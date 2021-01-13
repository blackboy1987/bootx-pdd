
package com.bootx.service;

import com.bootx.entity.ProductImage;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service - 商品图片
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface ProductImageService {

	/**
	 * 生成商品图片
	 * 
	 * @param multipartFile
	 *            文件
	 * @return 商品图片
	 */
	ProductImage generate(MultipartFile multipartFile,String uploadPath);

}