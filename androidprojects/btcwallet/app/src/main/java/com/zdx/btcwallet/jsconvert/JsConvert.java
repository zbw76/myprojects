package com.zdx.btcwallet.jsconvert;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.webkit.JavascriptInterface;

import com.zdx.btc.wallet.BtcApp;
import com.zdx.btcwallet.MainActivity;

import org.bitcoinj.core.Address;
import org.bitcoinj.core.Peer;

import java.util.List;

/**
 * 用来解决页面中调用本地代码的代理类，所有调用走这里来分发
 */
public class JsConvert {
    //全局钱包app
    public static BtcApp btc;
    public static String logstr = "";
    //全局context
    public static Context context;
    /**
     * 测试调用
     * @return
     */
    @JavascriptInterface
    public String testjs() {
        return "调试信息：" + logstr;
    }
    @JavascriptInterface
    public String getRecAdd() {
        Address a = btc.wallet.currentReceiveAddress();
        return a.toBase58();
    }

    /**
     * 得到钱包摘要信息
     * @return
     */
    @JavascriptInterface
    public String walletInfo() {
        String s = "";
        Address a = btc.wallet.currentReceiveAddress();
        String addrstr = a.toBase58();

        s = "钱包地址：" + addrstr + "<br>";
        s = s + "钱包余额：" + btc.wallet.getBalance().value;
        return s;
    }

    /**
     * 得到peer链接列表
     * @return
     */
    @JavascriptInterface
    public String connList() {
        StringBuffer sb = new StringBuffer("");
        List<Peer> peers = btc.peerGroup.getConnectedPeers();
        for(Peer p : peers) {
            sb.append(p.toString() + "<br>");
        }
        return sb.toString();
    }

    /**
     * 自己的警告显示窗口实现
     * @param title
     * @param msg
     */
    @JavascriptInterface
    public void alert(String title, String msg) {
        AlertDialog.Builder db = new AlertDialog.Builder(context);
        db.setTitle(title);
        db.setMessage(msg);
        db.setPositiveButton("确  定", new OkButtonListener());
        db.show();
    }

    /**
     * 确认退出事件
     *
     * @author zdx
     */
    private class OkButtonListener implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dbi, int arg1) {
            //关闭对话框
            dbi.dismiss();
        }
    }
}
