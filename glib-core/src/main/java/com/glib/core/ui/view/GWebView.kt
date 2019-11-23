package com.glib.core.ui.view

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.glib.core.http.GImmutableParams
import com.glib.core.ui.ProgressIndicator

class GWebView : WebView, IView {
    private val helper = ViewHelper(this)
    private var indicator: ProgressIndicator? = null

    constructor(context: Context, progress: ProgressIndicator) : super(context) {
        init()
        this.indicator = progress
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
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

    override fun width(width: Int?): GWebView {
        helper.width(width)
        return this
    }

    override fun height(height: Int?): GWebView {
        helper.height(height)
        return this
    }

    override fun padding(l: Int?, t: Int?, r: Int?, b: Int?): GWebView {
        helper.padding(l, t, r, b)
        return this
    }

    override fun margin(l: Int?, t: Int?, r: Int?, b: Int?): GWebView {
        helper.margin(l, t, r, b)
        return this
    }

    override fun bgColor(color: Int): GWebView {
        helper.bgColor(color)
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



    private inner class ProgressAwareWebViewClient : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            indicator?.showProgress()
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            indicator?.hideProgress()
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
