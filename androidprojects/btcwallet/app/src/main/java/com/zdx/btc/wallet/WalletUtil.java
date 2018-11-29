package com.zdx.btc.wallet;

import java.io.File;
import java.io.IOException;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;

/**
 * 钱包处理封装
 */
public class WalletUtil {
	private static final String walletFileName = "d:/bcoinj/wallet/my.wallet";
	private Wallet wallet = null;
	/**
	 * 初始化钱包，如果没有，就创建，有，就load
	 */
	public void init() {
		File walletFile = new File(walletFileName);
		if(walletFile.exists()) {
			try {
				wallet = Wallet.loadFromFile(walletFile);
			} catch (UnreadableWalletException e) {
				e.printStackTrace();
			}
		} else {
			NetworkParameters params = TestNet3Params.get();
			wallet = new Wallet(params);
			Address a = wallet.currentReceiveAddress();
			try {
				wallet.saveToFile(walletFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Address a = wallet.currentReceiveAddress();
		System.out.println("=========address:" + a.toBase58());
		System.out.println("===banlance:" + wallet.getBalance().value);
	}

	public Wallet getWallet() {
		return wallet;
	}
	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

	public static void main(String[] argc) {
		WalletUtil util = new WalletUtil();
		util.init();
	}
}
