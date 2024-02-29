package com.glib.core.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.glib.core.compose.BaseViewModel
import com.glib.core.compose.core.extensions.parseColor
import com.glib.core.compose.model.ComponentModel

@Composable

fun ComponentScreen(
    state: ComponentUiState,
    viewModel: BaseViewModel
) {
    when (state) {
        is ComponentUiState.Loading -> {
            // TODO: When there is multiple screens have it's own Shimmer Loading Layout
//            ShimmerLoadingMembershipDetail()
        }

        is ComponentUiState.Loaded -> {
            ComponentContent(
                state = state,
                viewModel = viewModel
            )
        }

        is ComponentUiState.Error -> {
            CommonErrorScreen(errorText = state.errorItemModel.errorMessage, errorIcon = state.errorItemModel.errorIcon, retry = state.errorItemModel.retry)
        }
    }
}


@Composable
private fun ComponentContent(
    state: ComponentUiState.Loaded,
    viewModel: BaseViewModel
) {
    val bodyComponents = state.componentDetailScreenItem.components
    Column {
        val maxPx = with(LocalDensity.current) { 250.dp.roundToPx().toFloat() }
        val minPx = with(LocalDensity.current) { 50.dp.roundToPx().toFloat() }
        val toolbarHeight = remember { mutableStateOf(maxPx) }
        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(
                    available: Offset,
                    source: NestedScrollSource
                ): Offset {
                    val height = toolbarHeight.value

                    if (height + available.y > maxPx) {
                        toolbarHeight.value = maxPx
                        return Offset(0f, maxPx - height)
                    }

                    if (height + available.y < minPx) {
                        toolbarHeight.value = minPx
                        return Offset(0f, minPx - height)
                    }

                    toolbarHeight.value += available.y
                    return Offset(0f, available.y)
                }

            }
        }
//        val progress = 1 - (toolbarHeight.value - minPx) / (maxPx - minPx)

        if (bodyComponents != null) {
            ComponentBody(
                components = bodyComponents,
                nestedScrollConnection = nestedScrollConnection,
                viewModel = viewModel
            )
        }
    }
}


@Composable
private fun ComponentBody(
    components: List<ComponentModel>,
    nestedScrollConnection: NestedScrollConnection,
    viewModel: BaseViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .nestedScroll(nestedScrollConnection)
            .background(color = "#f3f6f9".parseColor() ?: Color.White), contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize(), state = rememberLazyListState(), contentPadding = PaddingValues(bottom = 14.dp)) {
            items(items = components) { componentModel ->
                when (componentModel) {
                    // TODO
//                    is SpacerModel -> {
//                        SpacerComponent(spacerModel = componentModel)
//                    }
//
//                    is SectionTitleModel -> {
//                        SectionTitleComponent(sectionTitleModel = componentModel, navigateTo = viewModel::navigateTo)
//                    }
//
//                    is CallToActionModel -> {
//                        CallToActionComponent(callToActionModel = componentModel, navigateTo = viewModel::navigateTo)
//                    }
//
//                    is ChipGroupModel -> {
//                        ChipGroupComponent(chipGroupModel = componentModel)
//                    }
//
//                    is AccordionItemModel -> {
//                        AccordionItemsComponent(
//                            accordionItemModel = componentModel,
//                            onItemClick = { viewModel.updateExpandCollapse(it) }
//                        )
//                    }
//
//                    is SeparatorModel -> {
//                        SeparatorComponent(separatorModel = componentModel)
//                    }
//
//                    is EmbeddedListModel -> {
//                        EmbeddedListComponent(embeddedListModel = componentModel, navigateTo = viewModel::navigateTo)
//                    }
//
//                    is FieldLabelModel -> {
//                        FieldLabelComponent(filedLabelModel = componentModel, navigateTo = viewModel::navigateTo)
//                    }
//
                    is EmptyModel -> {
                        EmptyComponent(emptyModel = componentModel)
                    }
                }
            }
        }
    }
}