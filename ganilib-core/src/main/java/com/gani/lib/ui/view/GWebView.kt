package com.gani.lib.ui.view

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.gani.lib.http.GImmutableParams
import com.gani.lib.logging.GLog
import com.gani.lib.ui.ProgressIndicator

class GWebView : WebView {
    private var helper: ViewHelper? = null
    private var indicator: ProgressIndicator? = null

    constructor(context: Context, progress: ProgressIndicator) : super(context) {
        init()
        this.indicator = progress
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        this.helper = ViewHelper(this)

        webViewClient = ProgressAwareWebViewClient()

        // Mimic turbolinks-android's WebView as much as possible.
        val webSettings = settings
        //    webSettings.setUserAgentString(ConnectionPreparator.userAgent());
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.databaseEnabled = true

        // http://stackoverflow.com/questions/9055347/fitting-webpage-contents-inside-a-webview-android
        webSettings.loadWithOverviewMode = true
        webSettings.useWideViewPort = true

        // To enable js alert and confirm dialog.
        webChromeClient = WebChromeClient()
    }

    fun bgColor(color: Int): GWebView {
        setBackgroundColor(color)
        return this
    }

    @JvmOverloads
    fun load(url: String, params: GImmutableParams? = null): GWebView {
        val fullUrl = url + if (params == null) "" else "?" + params.asQueryString()
        loadUrl(fullUrl)
        return this
    }

    fun loadHtml(html: String, baseUrl: String): GWebView {
        loadDataWithBaseURL(baseUrl, html, "text/html", "utf-8", null)
        return this
    }

    protected fun onPageFinished() {
        // To be overridden
    }

    //  public void onClick()


    private inner class ProgressAwareWebViewClient : WebViewClient() {
        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
            GLog.i(javaClass, "onPageStarted: $url")
            super.onPageStarted(view, url, favicon)

            indicator?.showProgress()
        }

        override fun onPageFinished(view: WebView, url: String) {
            GLog.i(javaClass, "onPageFinished: $url")
            super.onPageFinished(view, url)

            indicator?.hideProgress()
            this@GWebView.onPageFinished()
        }

        //    // This wasn't working on Android 5.1.1 last time it was tested, but it doesn't matter now that we use Turbolinks on newer OSes.
        //    @Override
        //    public boolean shouldOverrideUrlLoading(WebView  view, String  url){
        //      Uri uri = Uri.parse(url);
        //      String host = uri.getHost();
        //      if (host != null) {  // Can be null for embedded base64 image.
        //        if (getContext() instanceof RichActivity) {
        //          Turbolinks.handleVisit((RichActivity) getContext(), uri, WebVisit.Action.ADVANCE);
        //        }
        //          return true;
        //      }
        //      return false;
        //    }
    }
}
