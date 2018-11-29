package com.zdx.btcwallet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.zdx.android.viewkit.TabView;
import com.zdx.android.viewkit.TabViewItem;
import com.zdx.btc.wallet.BtcApp;
import com.zdx.btcwallet.jsconvert.JsConvert;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    @SuppressLint("JavascriptInterface")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化js引擎全局上下文
        JsConvert.context = this;

        BtcApp btcapp = new BtcApp();
        //获得app数据目录,初始化钱包文件路径
        String baseDir = this.getFilesDir().getAbsolutePath();
        btcapp.walletFileName = baseDir + File.separator + BtcApp._wfname;

        System.out.println("=========base dir==" + baseDir + "=====" + btcapp.walletFileName);

        JsConvert.logstr = btcapp.walletFileName + ";";

        //首先初始化钱包
        btcapp.startWallet();
        JsConvert.btc = btcapp;

        //LinearLayout infov = new LinearLayout(this);
        //infov.setBackgroundColor(Color.RED);
        //LinearLayout sendv = new LinearLayout(this);
        //sendv.setBackgroundColor(Color.GREEN);
        //LinearLayout hqv = new LinearLayout(this);
        //hqv.setBackgroundColor(Color.BLUE);

        //初始化内容显示的webview
        WebView infoView = new WebView(this);
        infoView.getSettings().setJavaScriptEnabled(true);
        infoView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        infoView.setWebViewClient(new MyWebViewClient());
        infoView.addJavascriptInterface(new JsConvert(), "nativejs");
        //转账界面
        WebView sendView = new WebView(this);
        sendView.getSettings().setJavaScriptEnabled(true);
        sendView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        sendView.setWebViewClient(new MyWebViewClient());
        sendView.setWebChromeClient(new WebChromeClient());
        sendView.addJavascriptInterface(new JsConvert(), "nativejs");

        //行情动态
        WebView dtView = new WebView(this);
        dtView.getSettings().setJavaScriptEnabled(true);
        dtView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        dtView.setWebViewClient(new MyWebViewClient());

        List<TabViewItem> tabs = new ArrayList<TabViewItem>();
        TabViewItem t1 = new TabViewItem("我的钱包", infoView, null);
        TabViewItem t2 = new TabViewItem("转 账", sendView, null);
        TabViewItem t3 = new TabViewItem("行情资讯", dtView, null);
        tabs.add(t1);
        tabs.add(t2);
        tabs.add(t3);

        TabView tabview = new TabView(this, tabs);

        this.setContentView(tabview);
        infoView.loadUrl("file:///android_asset/info.html");
        sendView.loadUrl("file:///android_asset/send.html");
        dtView.loadUrl("http://mp.weixin.qq.com/mp/homepage?__biz=MzUzNDkxMTE3NA==&hid=1&sn=7c9046795978465e7b748bc4558839ee&scene=18#wechat_redirect");
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            //提示退出
            AlertDialog.Builder db = new AlertDialog.Builder(this);
            db.setTitle("退出");
            db.setMessage("您确定退出吗？");
            db.setPositiveButton("是", new OkButtonListener());
            db.setNegativeButton("否", new CancelButtonListener());
            db.show();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 确认退出事件
     *
     * @author zdx
     */
    private class OkButtonListener implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface arg0, int arg1) {
            //退出程序
            finish();
        }
    }

    /**
     * 取消退出事件
     *
     * @author zdx
     */
    private class CancelButtonListener implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface arg0, int arg1) {
            //不退出程序不做任何处理
        }
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }
}
