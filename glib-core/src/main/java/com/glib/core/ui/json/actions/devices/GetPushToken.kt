package com.glib.core.ui.json.actions.devices

import android.content.Context
import com.glib.core.http.*
import com.glib.core.json.GJson
import com.glib.core.json.GJsonObject
import com.glib.core.screen.GActivity
import com.glib.core.ui.ProgressIndicator
import com.glib.core.ui.json.JsonAction
import com.glib.core.utils.Async
import com.glib.core.utils.GAuth
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult

class GetPushToken(spec: GJson, screen: GActivity): JsonAction(spec, screen) {
    override fun silentExecute(): Boolean {
        postUrl = spec["postUrl"].string
        paramKey = spec["paramNameForToken"].string ?: paramKey

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(screen, object : OnSuccessListener<InstanceIdResult?> {
            override fun onSuccess(instanceIdResult: InstanceIdResult?) {
                instanceIdResult?.let { result ->
                    val callback = GRestCallback.Default(ProgressIndicator.NULL) { response ->
                        execute(response.result["onResponse"], screen, this@GetPushToken)
                    }
                    postToServer(result.token, callback)

//                        val paramKey = spec["paramNameForToken"].string ?: "formData[token]"
//                        val properties = spec["onGet"].merge(hashMapOf(paramKey to result.token))
//                        execute(properties, screen, this@GetPushToken)
                }
            }
        })
        return true
    }

    companion object {
        private var postUrl: String? = null
        private var paramKey: String = "token"

        fun postToServer(token: String, callback: GRestCallback<GHttpResponse.Default, GRestResponse>) {
            postUrl?.let {
                val params = GParams.Default()
                    .put("authenticity_token", GAuth.csrfToken)
                    .put(paramKey, token)
                Rest.POST.asyncUrl(it, params).execute(callback)
            }
        }
    }
}
