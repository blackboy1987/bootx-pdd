
package com.bootx.service;

import com.bootx.entity.SensitiveWord;

/**
 * Service - 审计日志
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public interface SensitiveWordService extends BaseService<SensitiveWord, Long> {

	boolean wordExist(String word);
}