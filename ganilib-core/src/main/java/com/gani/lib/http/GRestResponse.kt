package com.gani.lib.http

import com.gani.lib.json.GJsonObject
import java.io.Serializable

class GRestResponse(val jsonString: String, val httpResponse: GHttpResponse<*>) : Serializable {
//    @Transient private var jsonResult: JSONObject? = null

    val url: String
        get() = httpResponse.url

    val result: GJsonObject<*, *>
        get() = GJsonObject.Default(jsonString)

//
//    @Throws(JSONException::class)
//    protected fun getJsonResult(): JSONObject {
//        if (this.jsonResult == null) {
//            if (jsonString != null) {
//                this.jsonResult = JSONObject(jsonString)
//            } else {
//                throw JSONException("Null body")  // Unify NPE with JSONException
//            }
//        }
//        return this.jsonResult!!
//    }

    override fun toString(): String {
        return jsonString
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}