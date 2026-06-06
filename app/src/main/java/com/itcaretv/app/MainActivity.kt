package com.itcaretv.app

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : Activity() {

    companion object {
        private const val TAG = "ITCareTV"
        private const val BASE_URL = "https://itcaretv.top"
    }

    private lateinit var webView: WebView
    private var offlineView: View? = null

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)
        offlineView = findViewById(R.id.offline_view)

        configureWebView(webView)

        if (isNetworkAvailable()) {
            showWebView()
        } else {
            showOffline()
        }
    }

    private fun configureWebView(wv: WebView) {
        val s = wv.settings

        // Performance
        s.javaScriptEnabled = true
        s.domStorageEnabled = true
        s.databaseEnabled = true
        s.cacheMode = WebSettings.LOAD_DEFAULT

        // Rendering
        s.loadWithOverviewMode = true
        s.useWideViewPort = true
        s.builtInZoomControls = false
        s.setSupportZoom(false)

        // Media — autoplay allowed
        s.mediaPlaybackRequiresUserGesture = false

        // Security
        s.mixedContentMode = WebSettings.MIXED_CONTENT_NEVER_ALLOW
        s.allowFileAccess = false

        // Hardware acceleration for video
        wv.setLayerType(View.LAYER_TYPE_HARDWARE, null)

        // Cookies
        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(wv, false)

        wv.webViewClient = ItcareWebViewClient()
        wv.webChromeClient = WebChromeClient()
    }

    private inner class ItcareWebViewClient : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            val host = request.url.host ?: return true
            if (host == "itcaretv.top" || host.endsWith(".itcaretv.top")) {
                return false // WebView handles it
            }
            // External URL → browser
            try {
                startActivity(Intent(Intent.ACTION_VIEW, request.url))
            } catch (e: Exception) {
                Log.w(TAG, "No browser for: ${request.url}")
            }
            return true
        }

        override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
            if (request.isForMainFrame) {
                Log.e(TAG, "Page error: ${error.description}")
                showOffline()
            }
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as? ConnectivityManager ?: return false
        val net = cm.activeNetwork ?: return false
        val caps = cm.getNetworkCapabilities(net) ?: return false
        return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    private fun showWebView() {
        webView.visibility = View.VISIBLE
        webView.loadUrl(BASE_URL)
        offlineView?.visibility = View.GONE
    }

    private fun showOffline() {
        webView.visibility = View.GONE
        offlineView?.visibility = View.VISIBLE
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        super.onPause()
        webView.onPause()
    }

    override fun onResume() {
        super.onResume()
        webView.onResume()
        if (isNetworkAvailable() && offlineView?.visibility == View.VISIBLE) {
            showWebView()
        }
    }

    override fun onDestroy() {
        webView.destroy()
        super.onDestroy()
    }
}
