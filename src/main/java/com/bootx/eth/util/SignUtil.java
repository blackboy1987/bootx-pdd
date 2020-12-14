package com.bootx.eth.util;


import java.util.Map;
import java.util.TreeMap;


/**
 * 签名工具
 * @author tx
 *
 */
public class SignUtil {
	

	public static String createSignInfo(Map<String, Object> param,String md5Key)   {
		StringBuilder info = new StringBuilder();
		for(String key : param.keySet()) {
			info.append(key);
			info.append("=");
			info.append(param.get(key));
			info.append("&");
		}
		info.append("key=");
		info.append(md5Key);
		String result = Md5Utils.hash(info.toString());
		System.out.println("result:"+result);
		return result;
	}
	
	public static void main(String[] args) {
		Map<String, Object> param = new TreeMap<String, Object>();
		param.put("uId", "11");

		StringBuilder info = new StringBuilder();
		for(String key : param.keySet()) {
			info.append(key);
			info.append("=");
			info.append(param.get(key));
			info.append("&");
		}
		info.append("key=");
		info.append("MyNameIswalletServerMd5KeyBtoN");
		
		System.out.println( Md5Utils.hash(info.toString()));
		
	}
	
}
