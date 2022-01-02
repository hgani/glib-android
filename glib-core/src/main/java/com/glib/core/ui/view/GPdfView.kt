package com.glib.core.ui.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.AttributeSet
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.glib.core.http.GImmutableParams
import com.glib.core.logging.GLog
import com.glib.core.ui.ProgressIndicator
import com.glib.core.ui.panel.GVerticalPanel
import com.glib.core.ui.panel.GWrapper
import es.voghdev.pdfviewpager.library.RemotePDFViewPager
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter
import es.voghdev.pdfviewpager.library.remote.DownloadFile
import java.lang.Exception
//
//class GPdfView : RemotePDFViewPager, IView {
//    private val helper = ViewHelper(this)
////    private var indicator: ProgressIndicator? = null
////
////    constructor(context: Context, progress: ProgressIndicator) : super(context) {
////        init()
//////        this.indicator = progress
////    }
//
////    private val adapter: PDFPagerAdapter!
//
//    constructor(context: Context, url: String) : super(context, url, Listener()) {
//        init()
//
//
////        this.indicator = progress
//    }
//
//    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
//        init()
//    }
//
//    private fun init() {
////        webViewClient = ProgressAwareWebViewClient()
////
////        // Mimic turbolinks-android's WebView as much as possible.
////        val webSettings = settings
////        //    webSettings.setUserAgentString(ConnectionPreparator.userAgent());
////        webSettings.javaScriptEnabled = true
////        webSettings.domStorageEnabled = true
////        webSettings.databaseEnabled = true
////
////        // http://stackoverflow.com/questions/9055347/fitting-webpage-contents-inside-a-webview-android
////        webSettings.loadWithOverviewMode = true
////        webSettings.useWideViewPort = true
////
////        // To enable js alert and confirm dialog.
////        webChromeClient = WebChromeClient()
//
//
////        adapter = PDFPagerAdapter(this, "AdobeXMLFormsSamples.pdf")
//
//    }
//
//    override fun width(width: Int?): GPdfView {
//        helper.width(width)
//        return this
//    }
//
//    override fun height(height: Int?): GPdfView {
//        helper.height(height)
//        return this
//    }
//
//    override fun padding(l: Int?, t: Int?, r: Int?, b: Int?): GPdfView {
//        helper.padding(l, t, r, b)
//        return this
//    }
//
//    override fun margin(l: Int?, t: Int?, r: Int?, b: Int?): GPdfView {
//        helper.margin(l, t, r, b)
//        return this
//    }
//
//    override fun bgColor(color: Int): GPdfView {
//        helper.bgColor(color)
//        return this
//    }
//
////    @JvmOverloads
////    fun load(url: String, params: GImmutableParams? = null): GPdfView {
////        val fullUrl = url + if (params == null) "" else "?" + params.asQueryString()
////        loadUrl(fullUrl)
////        return this
////    }
////
////    fun loadHtml(html: String, baseUrl: String): GPdfView {
////        loadDataWithBaseURL(baseUrl, html, "text/html", "utf-8", null)
////        return this
////    }
//
//
//
//    private class Listener(val context: Context) : DownloadFile.Listener {
////        private context
////        constructor(context: Context) {
////        }
//
//        private var adapter: PDFPagerAdapter? = null
//
//        override fun onSuccess(url: String?, destinationPath: String?) {
////            TODO("Not yet implemented")
//
//            adapter = PDFPagerAdapter(context, destinationPath)
//
//            // TODO: Implement pager as a child of GControl
//            remotePDFViewPager.setAdapter(adapter)
//        }
//
//        override fun onFailure(e: Exception?) {
////            TODO("Not yet implemented")
//        }
//
//        override fun onProgressUpdate(progress: Int, total: Int) {
////            TODO("Not yet implemented")
//        }
//    }
//
////    private inner class ProgressAwareWebViewClient : WebViewClient() {
////        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
////            super.onPageStarted(view, url, favicon)
////            indicator?.showProgress()
////        }
////
////        override fun onPageFinished(view: WebView?, url: String?) {
////            super.onPageFinished(view, url)
////            indicator?.hideProgress()
////        }
////    }
//}


class GPdfView : GWrapper, DownloadFile.Listener {
    private val helper: ViewHelper = ViewHelper(this)
    private lateinit var pager: RemotePDFViewPager

//    private val helper = ViewHelper(this)

//    private var indicator: ProgressIndicator? = null
//
//    constructor(context: Context, progress: ProgressIndicator) : super(context) {
//        init()
////        this.indicator = progress
//    }

//    private val adapter: PDFPagerAdapter!

    constructor(context: Context) : super(context) {
        init()
//        this.indicator = progress
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    fun load(url: String) {
        this.pager = RemotePDFViewPager(context, url, this)
        addView(pager)
    }

    private fun init() {
//        webViewClient = ProgressAwareWebViewClient()
//
//        // Mimic turbolinks-android's WebView as much as possible.
//        val webSettings = settings
//        //    webSettings.setUserAgentString(ConnectionPreparator.userAgent());
//        webSettings.javaScriptEnabled = true
//        webSettings.domStorageEnabled = true
//        webSettings.databaseEnabled = true
//
//        // http://stackoverflow.com/questions/9055347/fitting-webpage-contents-inside-a-webview-android
//        webSettings.loadWithOverviewMode = true
//        webSettings.useWideViewPort = true
//
//        // To enable js alert and confirm dialog.
//        webChromeClient = WebChromeClient()


//        adapter = PDFPagerAdapter(this, "AdobeXMLFormsSamples.pdf")

    }

    override fun width(width: Int?): GPdfView {
        helper.width(width)
        return this
    }

    override fun height(height: Int?): GPdfView {
        helper.height(height)
        return this
    }

    override fun padding(l: Int?, t: Int?, r: Int?, b: Int?): GPdfView {
        helper.padding(l, t, r, b)
        return this
    }

    override fun margin(l: Int?, t: Int?, r: Int?, b: Int?): GPdfView {
        helper.margin(l, t, r, b)
        return this
    }

    override fun bgColor(color: Int): GPdfView {
        helper.bgColor(color)
        return this
    }

    ///// DownloadFile.Listener

    private var adapter: PDFPagerAdapter? = null

    override fun onSuccess(url: String?, destinationPath: String?) {
        adapter = PDFPagerAdapter(context, destinationPath)

        GLog.t(javaClass, "PAGER: $pager")
        pager?.setAdapter(adapter)

        // TODO: Clean up adapter in onDestroy()
    }

    override fun onFailure(e: Exception?) {
//            TODO("Not yet implemented")
    }

    override fun onProgressUpdate(progress: Int, total: Int) {
//            TODO("Not yet implemented")
    }



    /////

//    @JvmOverloads
//    fun load(url: String, params: GImmutableParams? = null): GPdfView {
//        val fullUrl = url + if (params == null) "" else "?" + params.asQueryString()
//        loadUrl(fullUrl)
//        return this
//    }
//
//    fun loadHtml(html: String, baseUrl: String): GPdfView {
//        loadDataWithBaseURL(baseUrl, html, "text/html", "utf-8", null)
//        return this
//    }



//    private class GRemotePDFViewPager : RemotePDFViewPager, DownloadFile.Listener {
////        private var adapter: PDFPagerAdapter? = null
//
//        constructor(context: Context, url: String) : super(context, url, this) {
////            init()
//    //        this.indicator = progress
//        }
//    }



//    private class Listener(val context: Context) : DownloadFile.Listener {
////        private context
////        constructor(context: Context) {
////        }
//
//        private var adapter: PDFPagerAdapter? = null
//
//        override fun onSuccess(url: String?, destinationPath: String?) {
////            TODO("Not yet implemented")
//
//            adapter = PDFPagerAdapter(context, destinationPath)
//
//            // TODO: Implement pager as a child of GControl
//            remotePDFViewPager.setAdapter(adapter)
//        }
//
//        override fun onFailure(e: Exception?) {
////            TODO("Not yet implemented")
//        }
//
//        override fun onProgressUpdate(progress: Int, total: Int) {
////            TODO("Not yet implemented")
//        }
//    }

//    private inner class ProgressAwareWebViewClient : WebViewClient() {
//        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
//            super.onPageStarted(view, url, favicon)
//            indicator?.showProgress()
//        }
//
//        override fun onPageFinished(view: WebView?, url: String?) {
//            super.onPageFinished(view, url)
//            indicator?.hideProgress()
//        }
//    }
}
