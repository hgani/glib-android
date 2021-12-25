package com.glib.core.ui.json.views.fields

import android.graphics.Color
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.glib.core.json.GJson
import com.glib.core.logging.GLog
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.ui.Ui
import com.glib.core.ui.json.JsonView
import com.glib.core.ui.json.views.panels.Form
import com.glib.core.ui.view.GEditText
import com.glib.core.ui.view.GTextInputEditText
import com.glib.core.ui.view.GTextInputLayout
import com.google.android.material.textfield.TextInputLayout
import java.lang.IllegalStateException
import java.lang.RuntimeException

abstract class AbstractField(spec: GJson, screen: GActivity, fragment: GFragment): JsonView(spec, screen, fragment), SubmittableField {
    final override var name: String? = null
        private set

    override fun onAfterInitView(view: View) {
        name = spec["name"].string

        GLog.t(javaClass, "onAfterInitView1")

        registerToClosestForm(view)

//        processJsonLogic(view: view)
    }

    fun registerToClosestForm(field: View) {
        GLog.t(javaClass, "registerToClosestForm1")

        closest(Form.FormPanel::class.java, field)?.let { panel ->
            GLog.t(javaClass, "registerToClosestForm2")

            val jsonField = this as? SubmittableField
            if (jsonField != null) {
                GLog.t(javaClass, "registerToClosestForm3")
                panel.addField(jsonField)
            } else {
                throw IllegalStateException("Not a JSON field")
            }
        }

//        if let form = closest(JsonView_Panels_Form.FormPanel.self, from: field) {
//            if let jsonField = self as? SubmittableField {
//                form.addField(jsonField)
//            } else {
//                fatalError("Not a JSON field")
//            }
//        }
    }

}
