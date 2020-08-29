package com.glib.core.ui.json.actions.http

import com.glib.core.dialog.GRestDialog
import com.glib.core.http.GParams
import com.glib.core.http.GRestCallback
import com.glib.core.http.GRestResponse
import com.glib.core.http.Rest
import com.glib.core.json.GJson
import com.glib.core.logging.GLog
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.ProgressIndicator
import com.glib.core.ui.json.JsonAction
import com.glib.core.ui.json.JsonUi

class Post(spec: GJson, screen: GActivity): AbstractPost(spec, screen) {
    override fun silentExecute(): Boolean {
        return silentExecute(Rest.POST)

//        val url = spec["url"].string
//        if (url == null) {
//            return false
//        }
//
//        val params = GParams.Default()
//        val formData = spec["formData"]
//        for (key in formData.keys()) {
//            params.put(key, formData[key])
//        }
//
//        val callback = GRestCallback.Default(ProgressIndicator.NULL) { response ->
//            val result = response.result
//            execute(result["onResponse"], screen, this)
//        }
//        Rest.POST.async(url, params, false).execute(callback)
//
////        val intent = GRestDialog.intentForUrl(url, Rest.GET, null, false, object : GRestDialog.Callback {
////            override fun onRestResponse(fragment: GFragment, response: GRestResponse) {
////                JsonUi.parseScreenContent(response.result, fragment)
////            }
////        })
////        screen.startActivity(intent)
//
//        return true
    }
}
