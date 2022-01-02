package com.glib.core.ui.view

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager
import com.glib.core.logging.GLog
import com.glib.core.notification.SnackbarUtils
import com.glib.core.screen.GActivity
import com.glib.core.screen.LaunchHelper
import com.glib.core.ui.panel.GWrapper
import es.voghdev.pdfviewpager.library.RemotePDFViewPager
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter
import es.voghdev.pdfviewpager.library.remote.DownloadFile

class GPdfView : GWrapper, DownloadFile.Listener {
    private val helper = ViewHelper(this)
    private lateinit var pager: RemotePDFViewPager
    private var adapter: PDFPagerAdapter? = null


//    private var indicator: ProgressIndicator? = null
//
//    constructor(context: Context, progress: ProgressIndicator) : super(context) {
//        init()
////        this.indicator = progress
//    }

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    private fun showPageNumber() {
        adapter?.let { adapter ->
            val total = adapter.count
            val current = pager.currentItem + 1

            GLog.t(javaClass, "onPageScrollStateChanged2: $context")
            (context as? GActivity)?.let { activity ->
                GLog.t(javaClass, "onPageScrollStateChanged3")
                SnackbarUtils.standard(activity, "$current of $total").show()
            }
        }
    }

    fun load(url: String) {
        this.pager = RemotePDFViewPager(context, url, this)
        this.pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                if (state != ViewPager.SCROLL_STATE_IDLE) {
                    return
                }
                showPageNumber()
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                GLog.t(javaClass, "onPageScrolled1")
                // Nothing to do
            }

            override fun onPageSelected(position: Int) {
                // Nothing to do
                GLog.t(javaClass, "onPageSelected1")
//                (context as? GActivity)?.let {
//                    SnackbarUtils.standard(it, "$position of TOTAL")
//                }
            }
        })

        addView(pager)
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

    override fun onSuccess(url: String?, destinationPath: String?) {
        adapter = PDFPagerAdapter(context, destinationPath)
        pager.adapter = adapter
        showPageNumber()
    }

    override fun onFailure(e: Exception?) {
        LaunchHelper(context).alert("Failed loading document")
    }

    override fun onProgressUpdate(progress: Int, total: Int) {
        // Nothing to do for now
    }

    /////

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        adapter?.close()
    }

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
