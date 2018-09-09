package com.gani.lib.ui.json.views.fields

import android.text.InputType
import android.view.View
import com.gani.lib.json.GJson
import com.gani.lib.screen.GActivity
import com.gani.lib.screen.GFragment

class EmailV1(spec: GJson, screen: GActivity, fragment: GFragment): AbstractTextV1(spec, screen, fragment) {
    override fun initView(): View {
        super.initView()

        view.inputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
        return view
    }
}
