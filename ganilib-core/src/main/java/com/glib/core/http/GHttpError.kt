package com.glib.core.http

import com.glib.core.logging.GLog

import org.json.JSONException

import java.io.IOException
import java.io.Serializable

open class GHttpError<HR : GHttpResponse<*>>(val response: HR) : Serializable {
    var message: String? = null
        private set
    var type: ErrorType? = null
        private set
    var code: Int? = null
        private set

    internal val url: String
        get() = response.url

    enum class ErrorType {
        NETWORK,
        CODE,
        JSON,
        AUTH
    }

    init {
        this.type = null
    }

    fun hasError(): Boolean {
        return type != null
    }

    private fun setError(type: ErrorType, reportMessage: String, logMessage: String): GHttpError<*> {
        GLog.e(javaClass, logMessage)
        this.type = type
        this.message = reportMessage
        return this
    }

    private fun setError(type: ErrorType, message: String): GHttpError<*> {
        return setError(type, message, message)
    }

    fun markForJson(e: JSONException): GHttpError<*> {
        return setError(ErrorType.JSON, GHttp.instance.listener.jsonErrorMessage(url, e))
    }

    internal fun markForCode(code: Int): GHttpError<*> {
        this.code = code
        return setError(ErrorType.CODE, GHttp.instance.listener.networkErrorMessage() + " Code: " + code, "HTTP error code: " + code)
    }

    internal fun markForNetwork(e: IOException): GHttpError<*> {
        return setError(ErrorType.NETWORK, GHttp.instance.listener.networkErrorMessage(), "HTTP exception: " + e)
    }

    // The logout part relies on handleDefault() which means that this only applies for foreground network operation (when activity is involved).
    fun markForAuth(logMessagePrefix: String): GHttpError<*> {
        return setError(ErrorType.AUTH, ErrorType.AUTH.name, "$logMessagePrefix -- logging out ($url) ...")
    }

    //  // TODO: Review. This seems to handle multiple types of error, the distinction is unclear.
    //  public void handleDefault(Context context) {
    //    GLog.t(getClass(), "handleDefault1 " + getClass());
    //
    //    // To be overidden
    //  }
    //
    //
    //  protected abstract void handle(Context context);


    class Default(response: GHttpResponse.Default) : GHttpError<GHttpResponse.Default>(response)//    @Override
    //    protected void handle(Context context) {
    ////      try {
    ////        GJsonObject restData = getResponse().asRestResponse().getResult();
    ////        String message = restData.getNullableString("message");
    ////        if (message != null) {
    ////          ToastUtils.showNormal(message);
    ////          return;
    ////        }
    ////      }
    ////      catch (JSONException e) {
    ////        // Will be handled later.
    ////      }
    ////
    ////      ToastUtils.showNormal(getMessage());
    //    }
}
