//package com.glib.core.ui.json.actions.windows
//
//import com.glib.core.json.GJson
//import com.glib.core.screen.GActivity
//import com.glib.core.ui.json.JsonAction
//import com.glib.core.ui.json.actions.windows.JsonUiScreen
//
//class CloseAll(spec: GJson, screen: GActivity): JsonAction(spec, screen) {
//    override fun silentExecute(): Boolean {
//        screen.startActivity(JsonUiScreen.intent(spec["onClose"]))
//        return true
//    }
//}