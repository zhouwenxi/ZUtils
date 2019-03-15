package com.qishui.commontoolslibrary.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qishui.commontoolslibrary.R;
import com.qishui.commontoolslibrary.base.BaseQiShuiActivity;
import com.qishui.commontoolslibrary.core.StringUtils;

/**
 * 添加人: add by qishui
 * 添加时间: 2019/3/15  17:05
 * 添加注释:
 */
public class QiShuiWebActivity extends BaseQiShuiActivity {

    private WebView webView;
    private ProgressBar pb;
    private TextView tv;
    public static final String KEY_TITLE = "KEY_TITLE";
    public static final String KEY_URL = "KEY_URL";
    private String title;
    private String url;

    @Override
    protected int initLayout() {
        return R.layout.activity_qi_shui_web;
    }

    @Override
    protected void initEvent(Bundle savedInstanceState) {
        title = getIntent().getStringExtra(KEY_TITLE);
        url = getIntent().getStringExtra(KEY_URL);
        if (TextUtils.isEmpty(title)) {
            title = "";
        }
        //设置请求页面
        setWebView();
    }

    /**
     * 设置h5加载
     */
    private void setWebView() {
        pb = findViewById(R.id.pb);
        tv = findViewById(R.id.tv);
        webView = findViewById(R.id.webview);
        // 设置WebView属性，能够执行Javascript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        //设置缓存模式
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    pb.setVisibility(View.GONE);
                    tv.setVisibility(View.GONE);
                } else {
                    pb.setVisibility(View.VISIBLE);
                    tv.setVisibility(View.VISIBLE);
                    tv.setText(StringUtils.addString(title, newProgress, "% 加载中..."));
                }
            }
        });

    }
}
