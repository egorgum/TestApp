package com.example.testapp

import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun MyWebView(
    url: String
){
    AndroidView(factory = {
        WebView(it).apply {
            this.webViewClient = WebViewClient()
            val webSettings = this.settings
            webSettings.javaScriptEnabled = true
            //if (savedInstanceState != null)
                //this.restoreState(savedInstanceState)
            //else
                this.loadUrl(url)
            this.settings.domStorageEnabled = true
            this.settings.javaScriptCanOpenWindowsAutomatically = true
            val cookieManager = CookieManager.getInstance()
            cookieManager.setAcceptCookie(true)
            val mWebSettings = this.settings
            mWebSettings.loadWithOverviewMode = true
            mWebSettings.useWideViewPort = true
            mWebSettings.domStorageEnabled = true
            mWebSettings.databaseEnabled = true
            mWebSettings.setSupportZoom(false)
            mWebSettings.allowFileAccess = true
            mWebSettings.allowContentAccess = true
            mWebSettings.loadWithOverviewMode = true
            mWebSettings.useWideViewPort = true
        }
    })
}