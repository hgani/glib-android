package com.gani.lib.ui.json.views.panels

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.gani.lib.http.GParams
import com.gani.lib.http.GRestCallback
import com.gani.lib.http.GRestResponse
import com.gani.lib.http.Rest
import com.gani.lib.json.GJson
import com.gani.lib.logging.GLog
import com.gani.lib.screen.GActivity
import com.gani.lib.screen.GFragment
import com.gani.lib.ui.json.JsonAction
import com.gani.lib.ui.json.JsonView
import com.gani.lib.ui.json.views.fields.SubmittableField
import com.gani.lib.ui.layout.GLinearLayout
import com.gani.lib.ui.view.IView



class FormV1(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
    private val panel = FormPanel(context)
    private val fields = mutableListOf<SubmittableField>()

    override fun initView(): View {
        spec["subviews"].arrayValue.forEach { subviewSpec ->
            JsonView.create(subviewSpec, screen, fragment)?.let {jsonView ->
                panel.addView(jsonView.createView())

                // NOTE: Currently we assume all fields are direct children.
                if (jsonView is SubmittableField) {
                    GLog.t(javaClass, "extractSubmittableFields2")
                    fields.add(jsonView)
                }
            }
        }
        return panel
    }



    inner class FormPanel: GLinearLayout<FormPanel>, IView {
        constructor(context: Context) : super(context) {
            init()
        }

        constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
            init()
        }

        private fun init() {
            vertical()
        }

        fun submit() {
            val params = GParams.Default()
            for (field in fields) {
                field.name?.let {
                    params.put(it, field.value)
                }
            }

            GLog.t(javaClass, "S ${params}")

            val callback = object : GRestCallback.Default(fragment) {
                override fun onRestResponse(response: GRestResponse) {
                    super.onRestResponse(response)

                    val result = response.result
//                    // Support generic uncustomizable framework (e.g. Devise)
//                    result["error"].string?.let { screen.launch.alert(it) }
                    JsonAction.executeAll(result["onResponse"], screen, this@FormPanel)
                }
            }
            Rest.from(spec["method"].stringValue).asyncUrl(spec["url"].stringValue, params, callback).execute()
        }

//        fun extractSubmittableFields(parent: ViewGroup, fields: MutableList<SubmittableField>) {
//            val childCount = parent.getChildCount()
//            GLog.t(javaClass, "extractSubmittableFields0: " + childCount)
//            for (i in 0 until childCount) {
//                val child = parent.getChildAt(i)
//                GLog.t(javaClass, "extractSubmittableFields1 " + child.javaClass)
//                if (child is ViewGroup) {
//                    GLog.t(javaClass, "ViewGroup")
//                    extractSubmittableFields(child, fields)
//                }
//                else if (child is SubmittableField) {
//                    GLog.t(javaClass, "extractSubmittableFields2")
//                    fields.add(child)
//                }
//
//            }
//        }
    }
}
