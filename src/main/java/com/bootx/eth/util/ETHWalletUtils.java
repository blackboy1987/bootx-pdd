package com.bootx.eth.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.wallet.DeterministicSeed;
import org.web3j.crypto.*;
import org.web3j.protocol.ObjectMapperFactory;
import org.web3j.utils.Numeric;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;


/**
 * 以太坊钱包创建工具类
 */
public class ETHWalletUtils {

	private static ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
	/**
	 * 随机
	 */
	private static final SecureRandom secureRandom = SecureRandomUtils.secureRandom();
	private Credentials credentials;

	/**
	 * 通用的以太坊基于bip44协议的助记词路径 （imtoken jaxx Metamask myetherwallet）
	 */
	public static String ETH_JAXX_TYPE = "m/44'/60'/0'/0/0";

	/**
	 * 创建助记词，并通过助记词创建钱包
	 *
	 * @param pwd
	 * @return
	 */
	public static ETHWallet generateMnemonic(String pwd) {
		String[] pathArray = ETH_JAXX_TYPE.split("/");
		String passphrase = "";
		DeterministicSeed ds = new DeterministicSeed(secureRandom, 128, passphrase);
		return generateWalletByMnemonic(ds, pathArray, pwd);
	}

	/**
	 * 通过导入助记词，导入钱包
	 *
	 * @param list 助记词
	 * @param pwd  密码
	 * @return
	 */
	public static ETHWallet importMnemonic(List<String> list, String pwd) {
		String[] pathArray = ETH_JAXX_TYPE.split("/");
		if (pathArray.length <= 1) {
			return null;
		}
		String passphrase = "";
		long creationTimeSeconds = System.currentTimeMillis() / 1000;
		DeterministicSeed ds = new DeterministicSeed(list, null, passphrase, creationTimeSeconds);
		return generateWalletByMnemonic(ds, pathArray, pwd);
	}

	private static String generateNewWalletName() {
		char letter1 = (char) (int) (Math.random() * 26 + 97);
		char letter2 = (char) (int) (Math.random() * 26 + 97);

		String walletName = String.valueOf(letter1) + String.valueOf(letter2) + "-新钱包";
		return walletName;
	}

	/**
	 * @param ds         助记词加密种子
	 * @param pathArray  助记词标准
	 * @param pwd        密码
	 * @return
	 */
	public static ETHWallet generateWalletByMnemonic(DeterministicSeed ds, String[] pathArray, String pwd) {
		// 种子
		byte[] seedBytes = ds.getSeedBytes();
		// 助记词
		List<String> mnemonic = ds.getMnemonicCode();
		if (seedBytes == null){
			return null;
		}
		DeterministicKey dkKey = HDKeyDerivation.createMasterPrivateKey(seedBytes);

		for (int i = 1; i < pathArray.length; i++) {
			ChildNumber childNumber;
			if (pathArray[i].endsWith("'")) {
				int number = Integer.parseInt(pathArray[i].substring(0, pathArray[i].length() - 1));
				childNumber = new ChildNumber(number, true);
			} else {
				int number = Integer.parseInt(pathArray[i]);
				childNumber = new ChildNumber(number, false);
			}
			dkKey = HDKeyDerivation.deriveChildKey(dkKey, childNumber);
		}

		ECKeyPair keyPair = ECKeyPair.create(dkKey.getPrivKeyBytes());
		ETHWallet ethWallet = generateWallet(pwd, keyPair);
		if (ethWallet != null) {
			ethWallet.setMnemonic(convertMnemonicList(mnemonic));
		}
		return ethWallet;
	}

	private static String convertMnemonicList(List<String> mnemonics) {
		StringBuilder sb = new StringBuilder();
		int size = mnemonics.size();

		for (int i = 0; i < size; i++) {
			sb.append(mnemonics.get(i));
			if (i != size - 1) {
				sb.append(" ");
			}
		}

		return sb.toString();
	}

	private static ETHWallet generateWallet(String pwd, ECKeyPair ecKeyPair) {
		WalletFile keyStoreFile;
		try {
			keyStoreFile = Wallet.create(pwd, ecKeyPair, 1024, 1); // WalletUtils. .generateNewWalletFile();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		BigInteger publicKey = ecKeyPair.getPublicKey();
		String privateKey = Numeric.toHexStringNoPrefixZeroPadded(ecKeyPair.getPrivateKey(),
				Keys.PRIVATE_KEY_LENGTH_IN_HEX);
		String s = publicKey.toString();
		String keyStore = "";
		try {
			keyStore = objectMapper.writeValueAsString(keyStoreFile);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		ETHWallet ethWallet = new ETHWallet();

		ethWallet.setAddress(Keys.toChecksumAddress(keyStoreFile.getAddress()));
		ethWallet.setPassword(Md5Utils.hash(pwd));
		ethWallet.setPublicKey(s);
		ethWallet.setPrivateKey(privateKey);
		ethWallet.setKeyStore(keyStore);
		return ethWallet;
	}

	/**
	 * 通过keystore.json文件导入钱包
	 *
	 * @param keystore 原json文件
	 * @param pwd      json文件密码
	 * @return
	 */
	public static ETHWallet loadWalletByKeystore(String keystore, String pwd) {
		Credentials credentials = null;
		try {
			WalletFile walletFile = null;
			walletFile = objectMapper.readValue(keystore, WalletFile.class);
			credentials = Credentials.create(Wallet.decrypt(pwd, walletFile));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CipherException e) {
			e.printStackTrace();
		}
		if (credentials != null) {
			return generateWallet(pwd, credentials.getEcKeyPair());
		}
		return null;
	}

	/**
	 * 通过明文私钥导入钱包
	 *
	 * @param privateKey
	 * @param pwd
	 * @return
	 */
	public static ETHWallet loadWalletByPrivateKey(String privateKey, String pwd) {
		Credentials credentials = null;
		ECKeyPair ecKeyPair = ECKeyPair.create(Numeric.toBigInt(privateKey));
		return generateWallet(pwd, ecKeyPair);
	}

	public static boolean isTooSimplePrivateKey(String privateKey) {

		if (Numeric.toBigInt(privateKey).intValue() < 100000000) {
			return true;
		} else {
			return true;
		}

	}

	private static boolean createParentDir(File file) {
		// 判断目标文件所在的目录是否存在
		if (!file.getParentFile().exists()) {
			// 如果目标文件所在的目录不存在，则创建父目录
			System.out.println("目标文件所在目录不存在，准备创建");
			if (!file.getParentFile().mkdirs()) {
				System.out.println("创建目标文件所在目录失败！");
				return false;
			}
		}
		return true;
	}
}
