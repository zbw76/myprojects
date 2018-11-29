package com.zdx.context;

import java.util.HashMap;
import java.util.Map;

import com.zdx.btc.wallet.BtcApp;

/**
 * 用来保存全局的环境,比如数据库连接池等,为整个应用提供一个上下文环境
 * 我们在启动应用的时候初始化这个环境,在启动过程中不能使用这个上下文
 */
public class ApplicationContext {
	//钱包数据地址
	public static final String datapath = "/btc/";
	private static BtcApp btcapp;

	public static BtcApp getBtcapp() {
		return btcapp;
	}
	public static void setBtcapp(BtcApp btcapp) {
		ApplicationContext.btcapp = btcapp;
	}
}
