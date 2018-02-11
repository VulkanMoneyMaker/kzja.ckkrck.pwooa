package zzz.dfdsa.htrw

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

import zzz.dfdsa.htrw.slotmania.GameActivity


class WebGameActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webgame)
        getUrl()
    }

    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    private fun getUrl() {
        val url = intent.getStringExtra(SplashScreen.BASE_KEY_URL)
        onReceice(url)
    }


    @SuppressLint("SetJavaScriptEnabled")
    @RequiresApi(Build.VERSION_CODES.CUPCAKE)
    private fun onReceice(url: String) {
        val webView = findViewById<WebView>(R.id.web_view)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)

                return true
            }

        }
        val webSettings = webView.settings
        webSettings.builtInZoomControls = true
        webSettings.setSupportZoom(true)
        webSettings.javaScriptEnabled = true
        webSettings.allowFileAccess = true
        webView.loadUrl(url)

    }


}
