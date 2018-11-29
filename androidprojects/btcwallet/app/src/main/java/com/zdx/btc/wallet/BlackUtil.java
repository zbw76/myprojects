package com.zdx.btc.wallet;

import java.io.File;
import java.io.IOException;
import java.util.List;

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
import org.bitcoinj.store.BlockStore;
import org.bitcoinj.store.BlockStoreException;
//import org.bitcoinj.store.LevelDBBlockStore;

public class BlackUtil {
	private static final String blockdir = "d:/bcoinj/block/";

	public void initBlock() {
		NetworkParameters params = TestNet3Params.get();
		try {
			BlockStore store = new org.bitcoinj.store.MemoryBlockStore(params);
			BlockChain chain = new BlockChain(params, store);
			PeerGroup peerGroup = new PeerGroup(params, chain);

			WalletUtil wu = new WalletUtil();
			wu.init();
			peerGroup.addWallet(wu.getWallet());

			/**System.out.println("Start peer group");
			 peerGroup.start();

			 System.out.println("Downloading block chain");
			 peerGroup.downloadBlockChain();
			 System.out.println("Block chain downloaded");**/

			peerGroup.setUserAgent("PeerMonitor", "1.0");
			peerGroup.setMaxConnections(10);
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

			peerGroup.start();//.startAsync();

			System.out.println("Downloading block chain");
			peerGroup.downloadBlockChain();
			System.out.println("Block chain downloaded");

			List<Transaction> txs = wu.getWallet().getTransactionsByTime();
			System.out.println("=========tx num:" + txs.size());

			//锁住程序
			try {
				System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (BlockStoreException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] argc) {
		new BlackUtil().initBlock();
	}
}
