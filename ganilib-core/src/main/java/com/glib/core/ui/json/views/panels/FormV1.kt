package com.glib.core.ui.json.views.panels

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.glib.core.http.GParams
import com.glib.core.http.GRestCallback
import com.glib.core.http.Rest
import com.glib.core.json.GJson
import com.glib.core.logging.GLog
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.json.JsonAction
import com.glib.core.ui.json.JsonView
import com.glib.core.ui.json.views.fields.SubmittableField
import com.glib.core.ui.layout.GLinearLayout
import com.glib.core.ui.view.IView



class FormV1(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment) {
    private val panel = FormPanel(context)
    private val fields = mutableListOf<SubmittableField>()

    override fun initView(): View {
        spec["subviews"].arrayValue.forEach { subviewSpec ->
            JsonView.create(subviewSpec, screen, fragment)?.let {jsonView ->
                panel.addView(jsonView.createView())

                // NOTE: Currently we assume all fields are direct children.
                if (jsonView is SubmittableField) {
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

        fun submit(trigger: JsonView?) {
            val params = GParams.Default()
            for (field in fields) {
                field.name?.let {
                    params.put(it, field.value)
                }
            }

            GLog.t(javaClass, "S ${params}")

            val callback = GRestCallback.Default(trigger ?: fragment.indicator) {
                val result = it.result
//                    // Support generic uncustomizable framework (e.g. Devise)
//                    result["error"].string?.let { screen.launch.alert(it) }
                JsonAction.execute(result["onResponse"], screen, this@FormPanel, this@FormV1)
            }
            Rest.from(spec["method"].stringValue).asyncUrl(spec["url"].stringValue, params).execute(callback)
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
