
package com.bootx.util;

import com.bootx.constants.PddConfig;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Utils - Web
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public final class PddWebUtils {

	/**
	 * 不可实例化
	 */
	private PddWebUtils() {
	}

	public static String post(Map<String, Object> parameterMap) {
		if(parameterMap==null){
			parameterMap = new HashMap<>();
		}
		parameterMap.put("client_id", PddConfig.clientId);
		parameterMap.put("timestamp",System.currentTimeMillis()+"");
		parameterMap.put("sign",StringUtils.upperCase(generateSign(parameterMap)));
		return WebUtils.post("https://gw-api.pinduoduo.com/api/router",parameterMap);
	}

	public static String get(Map<String, Object> parameterMap) {
		if(parameterMap==null){
			parameterMap = new HashMap<>();
		}
		parameterMap.put("client_id", PddConfig.clientId);
		parameterMap.put("timestamp",System.currentTimeMillis()+"");
		parameterMap.put("sign",StringUtils.upperCase(generateSign(parameterMap)));
		return WebUtils.get("https://gw-api.pinduoduo.com/api/router",parameterMap);
	}


	private static String generateSign(Map<String, ?> parameterMap) {
		return DigestUtils.md5Hex(joinKeyValue(new TreeMap<>(parameterMap), PddConfig.clientSecret, PddConfig.clientSecret, "", true, "sign_type", "sign"));
	}

	protected static String joinKeyValue(Map<String, Object> map, String prefix, String suffix, String separator, boolean ignoreEmptyValue, String... ignoreKeys) {
		List<String> list = new ArrayList<String>();
		if (map != null) {
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();
				String value = ConvertUtils.convert(entry.getValue());
				if (StringUtils.isNotEmpty(key) && !ArrayUtils.contains(ignoreKeys, key) && (!ignoreEmptyValue || StringUtils.isNotEmpty(value))) {
					list.add(key + "" + (value != null ? value : ""));
				}
			}
		}
		String result = (prefix != null ? prefix : "") + StringUtils.join(list, separator) + (suffix != null ? suffix : "");
		return result;
	}
}