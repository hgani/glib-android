package com.glib.core.ui.json.actions.windows

import com.glib.core.ui.view.GButton
import com.glib.core.ui.view.GTextView

// NOTE: Library clients can register their own style classes here
class JsonUiStyling {
    companion object {
        val buttons = mutableMapOf("link" to GButton.Spec.LINK, "icon" to GButton.Spec.ICON)
        val labels = mutableMapOf("link" to GTextView.Spec.LINK)


//        public static var labels: [String: GLabelSpec] = [
//        "link": .link,
////        "rounded": .rounded
//        ]


//        public static var textFields: [String: MTextFieldSpec] = [
////        "outlined": .outlined,
////        "filled": .filled,
//        "rounded": .rounded
//        ]
//
//        public static var labels: [String: GLabelSpec] = [
//        "link": .link,
////        "rounded": .rounded
//        ]
//
//        public static var thumbnailTemplates = [String: ThumbnailTemplateSpec]()
//
//        public static var panels = [String: MCardSpec]()
//
//        public static var chips: [String: MChipSpec] = [
//        "success": .success,
//        "error": .error,
//        "warning": .warning,
//        "info": .info
//        ]
//
//        public static var screens = [String: JsonUiScreenSpec]()


//        val ARG_PATH = "path"
////        val ARG_ACTION_SPEC = "actionSpec"
//        val ARG_ON_OPEN_SPEC = "onOpenSpec"
//        val ARG_PREPEND_HOST = "prependHost"
//
//        fun intent(path: String, prependHost: Boolean = true, onOpenSpec: GJson = GJsonDefault()): Intent {
//            return intentBuilder(JsonUiStyling::class)
//                    .withArg(ARG_PATH, path)
//                    .withArg(ARG_PREPEND_HOST, prependHost)
//                    .withArg(ARG_ON_OPEN_SPEC, onOpenSpec)
//                    .intent
//        }
//
//        fun intent(actionSpec: GJson): Intent {
//            return intentBuilder(JsonUiStyling::class)
//                    .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
//                    .withArg(ARG_ON_OPEN_SPEC, actionSpec).intent
//        }
    }
}
