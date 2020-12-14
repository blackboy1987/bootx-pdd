package com.bootx.eth.service.impl;

import com.bootx.eth.service.EthWeb3jService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;


@Service
public class EthWeb3jServiceImpl implements EthWeb3jService {

	@Autowired
	private Web3j web3j;

	@Override
	public void ethAccounts() {

	}
}
