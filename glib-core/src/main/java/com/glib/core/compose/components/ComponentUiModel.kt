package com.glib.core.compose.components

import com.glib.core.compose.model.ComponentDetailScreenItem
import com.glib.core.compose.model.ErrorItemModel

sealed class ComponentUiState {
    object Loading : ComponentUiState()
    class Loaded(val componentDetailScreenItem: ComponentDetailScreenItem) : ComponentUiState()
    class Error(val errorItemModel: ErrorItemModel) : ComponentUiState()
}