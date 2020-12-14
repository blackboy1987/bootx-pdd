package com.bootx.eth.entity;


import com.bootx.entity.BaseEntity;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class UserCurrencyInfoBean extends BaseEntity<Long> {


	/**
	 * 币种
	 */
	private String currency;
	
	/**
	 * 余额
	 */
	private BigDecimal banance;
	
	private BigDecimal rmbBalance;
	
	private BigDecimal priceCny ;
	
	private String logoPng;
	
	private String name;
	
	private String contractAddress;

	private String symbol;

	private String abbreviation;

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getContractAddress() {
		return contractAddress;
	}

	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogoPng() {
		return logoPng;
	}

	public void setLogoPng(String logoPng) {
		this.logoPng = logoPng;
	}

	public BigDecimal getPriceCny() {
		return priceCny;
	}

	public void setPriceCny(BigDecimal priceCny) {
		this.priceCny = priceCny;
	}

	public BigDecimal getRmbBalance() {
		return rmbBalance;
	}

	public void setRmbBalance(BigDecimal rmbBalance) {
		this.rmbBalance = rmbBalance;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getBanance() {
		return banance;
	}

	public void setBanance(BigDecimal banance) {
		this.banance = banance;
	}
	
}
