package com.glib.core.compose.model

import androidx.compose.ui.graphics.vector.ImageVector

data class ErrorItemModel(val errorMessage: String, val errorIcon: ImageVector? = null, val retry: (() -> Unit)? = null)
