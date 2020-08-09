package com.glib.core.ui.json.views.fields

import android.graphics.Typeface
import android.text.InputType
import android.view.View
import com.glib.core.json.GJson
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment

class Password(spec: GJson, screen: GActivity, fragment: GFragment): AbstractText(spec, screen, fragment) {
    override fun initView(): View {
        super.initView()

        view
            .inputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            .typeface(Typeface.DEFAULT)  // Without this, the hint gets displayed using a different font
        return view
    }
}
