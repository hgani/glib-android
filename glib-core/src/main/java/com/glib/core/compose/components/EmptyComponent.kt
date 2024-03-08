package com.glib.core.compose.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.glib.core.logging.GLog
import kotlinx.coroutines.launch
import org.json.JSONObject

// When no component match than use this one
@Composable
fun EmptyComponent(model: JSONObject) {
//    LaunchedEffect(key1 = Unit) {
////        viewModel.isError.collect {
////            coroutineScope.launch {
////                snackBarHostState.showSnackbar(it.errorMessage)
////            }
////        }
//    }
}

//data class EmptyModel(override val type: String = "empty"): ComponentModel {
//    companion object {
//        fun map(jsonObject: JSONObject): EmptyModel {
//            return EmptyModel()
//        }
//    }
//}