//package com.gani.lib.http
//
//import android.content.Context
//
//import com.gani.lib.logging.GLog
//
//import org.map.JSONException
//
//// TODO: Merge these methods into GHttpError and need make sure GHttpError.markForJson() gets called.
//abstract class GHttpAlert<HR : GHttpResponse<*>, RR : GRestResponse> {
//    // To be overridden
//    @Throws(JSONException::class)
//    fun reportCodeError(r: HR) {
//        // Do nothing by default
//    }
//
//    // To be overridden
//    fun reportJsonError(r: RR, e: JSONException) {
//        // Do nothing by default
//    }
//
//    // To be overridden
//    fun messageForJsonError(url: String, ex: JSONException): String {
//        return "Failed communicating with server"
//    }
//
//    //  // To be overridden
//    //  public void alertJsonError(Context c, RR r, JSONException e) {
//    //    GLog.e(getClass(), "TODO: Override alertJsonError()", e);
//    //  }
//
//    // TODO: rename to alertAppSpecificError
//    // To be overridden
//    @Throws(JSONException::class)
//    fun alertCommonError(c: Context, r: HR) {
//        GLog.e(javaClass, "TODO: Override alertCommonError()")
//        // Example: Alert.display(c, r.getError().getMessage());
//    }
//
//    // To be overridden
//    fun alertFormParsingError(c: Context, r: HR) {
//        GLog.e(javaClass, "TODO: Override alertFormParsingError()")
//        // Example: Alert.display(c, r.getError().getMessage());
//    }
//}
