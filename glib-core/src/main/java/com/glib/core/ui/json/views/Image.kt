package com.glib.core.ui.json.views

import android.view.View
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.json.JsonView
import com.glib.core.ui.view.GImageView


class Image(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
    private val view = GImageView(context)

    override fun initView(): View {
        // Doesn't recognize our example base64 image data.
//        spec["base64Data"].string?.let {
//            try { Base64.decode(it, Base64.DEFAULT) } catch (e: IllegalArgumentException) { null }?.let { decodedString ->
//                BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)?.let { decodedByte ->
//                    view.source(bitmap = decodedByte)
//                }
//            }
//        }
        spec["url"].string?.let { view.source(url = it) }
        return view
    }
}
