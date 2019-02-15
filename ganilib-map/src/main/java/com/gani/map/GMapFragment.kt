//package com.gani.map
//
//import com.gani.lib.screen.GFragment
//
//open class GMapFragment : GFragment() {
//    // See http://www.zoftino.com/android-mapview-tutorial
//    private val MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey"
//
//    private lateinit var view: GMapView
//
//    protected val mapView: GMapView
//        get() = view
//
////    override fun initContent(activity: GActivity, container: GScreenContainer) {
////        super.initContent(activity, container)
////
////        view = GMapView(activity)
////                .width(ViewGroup.LayoutParams.MATCH_PARENT)
////                .height(ViewGroup.LayoutParams.MATCH_PARENT)
////                .attachTo(this)
////    }
//
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////
////        view = GMapView(context!!)
////                .width(ViewGroup.LayoutParams.MATCH_PARENT)
////                .height(ViewGroup.LayoutParams.MATCH_PARENT)
////
////        var mapViewBundle: Bundle? = null
////        if (savedInstanceState != null) {
////            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY)
////        }
////        view.onCreate(mapViewBundle)
//////            view.getMapAsync(OnMapReadyCallback {  });
////    }
////
////    override fun onSaveInstanceState(outState: Bundle) {
////        super.onSaveInstanceState(outState)
////
////        var mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY)
////        if (mapViewBundle == null) {
////            mapViewBundle = Bundle()
////            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle)
////        }
////
////        view.onSaveInstanceState(mapViewBundle)
////    }
////
////    override fun onResume() {
////        super.onResume()
////        view.onResume()
////    }
////
////    override fun onStart() {
////        super.onStart()
////        view.onStart()
////    }
////
////    override fun onStop() {
////        super.onStop()
////        view.onStop()
////    }
////
////    override fun onPause() {
////        view.onPause()
////        super.onPause()
////    }
////
////    override fun onDestroy() {
////        view.onDestroy()
////        super.onDestroy()
////    }
////
////    override fun onLowMemory() {
////        super.onLowMemory()
////        view.onLowMemory()
////    }
//}
