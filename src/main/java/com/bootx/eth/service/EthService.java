package com.bootx.eth.service;

import net.bdsc.eth.common.Result;

import java.util.Map;

public interface EthService {
	
	/**
	 * 获取币种汇率
	 * @param map
	 * @param sign
	 * @return
	 * @
	 */
	Result getCurrencyInfo(Map<String, Object> map, String sign) ;
	
	/**
	 * 转账
	 * @param map
	 * @param sign
	 * @return
	 * @
	 */
	Result transfer(Map<String, Object> map, String sign) ;
	
	/**
	 * 获取余额
	 * @param eAddress
	 * @param sign
	 * @return
	 * @
	 */
	Result getBalance(String eAddress, String sign);
	
	/**
	 * 导入钱包
	 * @param wWord
	 * @param wPwd
	 * @param sign
	 * @return
	 * @
	 */
	Result loadWallet(String wWord, String wPwd, String sign) ;
	
	/**
	 * 创建钱包
	 * @param wPwd
	 * @param sign
	 * @return
	 * @
	 */
	Result createWallet(String wPwd, String sign);

}