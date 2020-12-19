
package com.bootx.common;

/**
 * Analyzer - Ansj
 * 
 * @author 好源++ Team
 * @version 6.1
 */
public class AnsjAnalyzer extends org.ansj.lucene5.AnsjAnalyzer {

	/**
	 * 默认类型
	 */
	public static final org.ansj.lucene5.AnsjAnalyzer.TYPE DEFAULT_TYPE = org.ansj.lucene5.AnsjAnalyzer.TYPE.dic_ansj;

	/**
	 * 构造方法
	 */
	public AnsjAnalyzer() {
		super(DEFAULT_TYPE);
	}

}