package com.glib.core.ui.json.views.fields

import android.text.InputType
import android.view.View
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment

class Email(spec: GJson, screen: GActivity, fragment: GFragment): AbstractText(spec, screen, fragment) {
    override fun initView(): View {
        super.initView()

        editText.inputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
        return view
    }
}
