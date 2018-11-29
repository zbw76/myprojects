package com.zdx.btc.wallet;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.BlockChain;
import org.bitcoinj.core.Context;
import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.core.Peer;
import org.bitcoinj.core.PeerGroup;
import org.bitcoinj.core.Transaction;
import org.bitcoinj.core.listeners.PeerConnectedEventListener;
import org.bitcoinj.core.listeners.PeerDisconnectedEventListener;
import org.bitcoinj.net.discovery.DnsDiscovery;
import org.bitcoinj.params.TestNet3Params;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.bitcoinj.wallet.listeners.WalletChangeEventListener;

import com.zdx.context.ApplicationContext;

/**
 * 钱包app，用来启动维护钱包客户端
 */
public class BtcApp {
	public static final String _wfname = "my.wallet";
	public PeerGroup peerGroup = null;
	public String walletFileName = "";
	public String blockDir = "";
	public Wallet wallet = null;

	/**
	 * 开启钱包
	 */
	public void startWallet() {
		//walletFileName = ApplicationContext.datapath + "/mwallet/my.wallet";
		//blockDir = ApplicationContext.datapath + "/mwallet/block/";

		//启动peerGroup
		//NetworkParameters params = TestNet3Params.get();
		NetworkParameters params = MainNetParams.get();
		BlockStore store;
		try {
			store = new org.bitcoinj.store.MemoryBlockStore(params);
			BlockChain chain = new BlockChain(params, store);
			peerGroup = new PeerGroup(params, chain);

			//恢复钱包
			wallet = getWallet(new File(walletFileName));
			peerGroup.addWallet(wallet);

			//启动钱包联网
			peerGroup.setUserAgent("PeerMonitor", "1.0");
			peerGroup.setMaxConnections(20);
			peerGroup.addPeerDiscovery(new DnsDiscovery(params));
			peerGroup.addConnectedEventListener(new PeerConnectedEventListener() {
				@Override
				public void onPeerConnected(final Peer peer, int peerCount) {
					System.out.println("=======收到链接:" + peer.toString());
				}
			});
			peerGroup.addDisconnectedEventListener(new PeerDisconnectedEventListener() {
				@Override
				public void onPeerDisconnected(final Peer peer, int peerCount) {
					System.out.println("=======断开链接:" + peer.toString());
				}
			});

			peerGroup.start();
			//Peer p = null;
			//p.t

			//添加钱包事件处理
			wallet.addChangeEventListener(new WalletListener());

			/**Set<Transaction> ts = wallet.getTransactions(true);
			 for(Transaction t : ts) {
			 t.get
			 }**/
		} catch (Exception e) {
			System.out.println("==============start wallet error===============");
			e.printStackTrace();
		}
	}

	public Wallet getWallet(File walletFile) {
		//File walletFile = new File(walletFileName);
		Wallet tw = null;
		if(walletFile.exists()) {
			try {
				tw = Wallet.loadFromFile(walletFile);
			} catch (UnreadableWalletException e) {
				e.printStackTrace();
			}
		} else {
			NetworkParameters params = MainNetParams.get();
			tw = new Wallet(params);
			Address a = tw.currentReceiveAddress();
			try {
				tw.saveToFile(walletFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Address a = tw.currentReceiveAddress();
		System.out.println("=========address:" + a.toBase58());
		System.out.println("===banlance:" + tw.getBalance().value);
		return tw;
	}

	public class WalletListener implements WalletChangeEventListener {
		public void onWalletChanged(Wallet cw) {
			//钱包改变，保存钱包
			System.out.println("=============wallet change event================");
			try {
				cw.saveToFile(new File(walletFileName));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
