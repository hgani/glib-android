package com.glib.core.compose.components

import com.glib.core.compose.model.WindowModel
import com.glib.core.compose.model.ErrorItemModel

sealed class ComponentUiState {
    object Loading : ComponentUiState()
    class Loaded(val windowModel: WindowModel) : ComponentUiState()
    class Error(val errorItemModel: ErrorItemModel) : ComponentUiState()
}