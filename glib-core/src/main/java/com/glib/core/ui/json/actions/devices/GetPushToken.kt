package com.glib.core.ui.json.actions.devices

import com.glib.core.db.DbMap
import com.glib.core.json.GJson
import com.glib.core.logging.GLog
import com.glib.core.screen.GActivity
import com.glib.core.ui.json.JsonAction
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.InstanceIdResult

//import com.google.android.gms.tasks.OnSuccessListener


class GetPushToken(spec: GJson, screen: GActivity): JsonAction(spec, screen) {
//    companion object {
//        val RESULT_KEY_ON_AFTER_CLOSE = "onAfterClose"
//    }

    override fun silentExecute(): Boolean {
//        screen.finish(RESULT_KEY_ON_AFTER_CLOSE, spec["onClose"])

            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(screen, object : OnSuccessListener<InstanceIdResult?> {
                override fun onSuccess(instanceIdResult: InstanceIdResult?) {
                    instanceIdResult?.let { result ->
                        val paramKey = spec["paramNameForToken"].string ?: "formData[token]"
                        val properties = spec["onGet"].merge(hashMapOf(paramKey to result.token))
                        execute(properties, screen, this@GetPushToken)
                    }
                }
//                fun onSuccess(instanceIdResult: InstanceIdResult) {
//                    GLog.t(javaClass, "instanceIdResult: ${instanceIdResult.token}")
//                    val newToken: String = instanceIdResult.getToken()
//                    DbMap.put(DEVICE_UUID, newToken)
//                    MyFirebaseMessagingService().onNewToken(newToken)
//                    TaLog.e(javaClass, "newToken $newToken")
//                    paramsObject.put("gcm_token", newToken)
//                    anObject.put("formData", paramsObject)
//                    Post().execute(activity, anObject)
//                }
            })
        return true
    }
}
