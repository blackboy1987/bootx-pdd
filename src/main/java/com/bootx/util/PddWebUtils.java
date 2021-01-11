
package com.bootx.util;

import com.bootx.constants.PddConfig;
import com.bootx.pdd.response.PddGoodsImageUploadResponse;
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
		System.out.println(generateSign(parameterMap));
		parameterMap.put("sign",StringUtils.upperCase(generateSign(parameterMap)));
		return WebUtils.post("https://gw-api.pinduoduo.com/api/router",parameterMap);
	}

	public static String get(Map<String, Object> parameterMap) {
		if(parameterMap==null){
			parameterMap = new HashMap<>();
		}
		parameterMap.put("client_id", PddConfig.clientId);
		parameterMap.put("timestamp",System.currentTimeMillis()+"");
		System.out.println(generateSign(parameterMap));
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


	public static void main(String[] args) {

		// [{"source":"http://img12.360buyimg.com/n1/jfs/t1/141480/8/14039/48663/5fab0686E7d32b85f/bb2489ac5d2b4ed5.jpg","large":"http://img12.360buyimg.com/n1/s800x800_jfs/t1/141480/8/14039/48663/5fab0686E7d32b85f/bb2489ac5d2b4ed5.jpg","medium":"http://img12.360buyimg.com/n1/s350x449_jfs/t1/141480/8/14039/48663/5fab0686E7d32b85f/bb2489ac5d2b4ed5.jpg","thumbnail":"http://img12.360buyimg.com/n1/s50x64_jfs/t1/141480/8/14039/48663/5fab0686E7d32b85f/bb2489ac5d2b4ed5.jpg","order":0},{"source":"http://img12.360buyimg.com/n1/jfs/t1/140989/5/13821/58213/5fab0689E202779d6/96805568b0258356.jpg","large":"http://img12.360buyimg.com/n1/s800x800_jfs/t1/140989/5/13821/58213/5fab0689E202779d6/96805568b0258356.jpg","medium":"http://img12.360buyimg.com/n1/s350x449_jfs/t1/140989/5/13821/58213/5fab0689E202779d6/96805568b0258356.jpg","thumbnail":"http://img12.360buyimg.com/n1/s50x64_jfs/t1/140989/5/13821/58213/5fab0689E202779d6/96805568b0258356.jpg","order":1},{"source":"http://img12.360buyimg.com/n1/jfs/t1/130742/6/15596/97957/5fab068cE13420ae7/bf7defdeee35e178.jpg","large":"http://img12.360buyimg.com/n1/s800x800_jfs/t1/130742/6/15596/97957/5fab068cE13420ae7/bf7defdeee35e178.jpg","medium":"http://img12.360buyimg.com/n1/s350x449_jfs/t1/130742/6/15596/97957/5fab068cE13420ae7/bf7defdeee35e178.jpg","thumbnail":"http://img12.360buyimg.com/n1/s50x64_jfs/t1/130742/6/15596/97957/5fab068cE13420ae7/bf7defdeee35e178.jpg","order":2},{"source":"http://img12.360buyimg.com/n1/jfs/t1/138665/2/13906/25213/5fab0692Eadb45531/839ae662e3e61745.jpg","large":"http://img12.360buyimg.com/n1/s800x800_jfs/t1/138665/2/13906/25213/5fab0692Eadb45531/839ae662e3e61745.jpg","medium":"http://img12.360buyimg.com/n1/s350x449_jfs/t1/138665/2/13906/25213/5fab0692Eadb45531/839ae662e3e61745.jpg","thumbnail":"http://img12.360buyimg.com/n1/s50x64_jfs/t1/138665/2/13906/25213/5fab0692Eadb45531/839ae662e3e61745.jpg","order":3},{"source":"http://img12.360buyimg.com/n1/jfs/t1/144677/9/13887/25293/5fab06a3E3f11bd7d/56ab3155e000383d.jpg","large":"http://img12.360buyimg.com/n1/s800x800_jfs/t1/144677/9/13887/25293/5fab06a3E3f11bd7d/56ab3155e000383d.jpg","medium":"http://img12.360buyimg.com/n1/s350x449_jfs/t1/144677/9/13887/25293/5fab06a3E3f11bd7d/56ab3155e000383d.jpg","thumbnail":"http://img12.360buyimg.com/n1/s50x64_jfs/t1/144677/9/13887/25293/5fab06a3E3f11bd7d/56ab3155e000383d.jpg","order":4},{"source":"http://img12.360buyimg.com/n1/jfs/t1/125972/40/17968/38384/5fab0696Ede508f62/5e457ccc49d42d54.jpg","large":"http://img12.360buyimg.com/n1/s800x800_jfs/t1/125972/40/17968/38384/5fab0696Ede508f62/5e457ccc49d42d54.jpg","medium":"http://img12.360buyimg.com/n1/s350x449_jfs/t1/125972/40/17968/38384/5fab0696Ede508f62/5e457ccc49d42d54.jpg","thumbnail":"http://img12.360buyimg.com/n1/s50x64_jfs/t1/125972/40/17968/38384/5fab0696Ede508f62/5e457ccc49d42d54.jpg","order":5}]
		Map<String,Object> parameterMap = new HashMap<>();
		parameterMap.put("type","pdd.goods.image.upload");
		parameterMap.put("image", "data:image/png;base64,"+ImageUtils.url2Base64("http://img12.360buyimg.com/n1/jfs/t1/141480/8/14039/48663/5fab0686E7d32b85f/bb2489ac5d2b4ed5.jpg"));
		parameterMap.put("access_token",PddConfig.accessToken);
		String post = post(parameterMap);
		System.out.println(post);
		PddGoodsImageUploadResponse pddGoodsImageUploadResponse = JsonUtils.toObject(post, PddGoodsImageUploadResponse.class);
		System.out.println(pddGoodsImageUploadResponse);
	}
}