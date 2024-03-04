package com.glib.core.compose

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.WifiOff
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glib.core.compose.components.ComponentUiState
import com.glib.core.compose.model.ComponentDetailScreenItem
import com.glib.core.compose.model.ErrorItemModel
import com.glib.core.repository.network.helpers.CallResult
import com.glib.core.utils.Res
import com.teamapp.teamapp.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

// TODO: use Hilt DI for dependency handling also pass URL here using SavedStateHandle Instead of passing to function
class BaseViewModel(private val repository: Repository = Repository(Res.context)) : ViewModel() {

    private val _componentsState: MutableStateFlow<ComponentUiState> =
        MutableStateFlow(ComponentUiState.Loading)
    val componentsState: StateFlow<ComponentUiState> = _componentsState.asStateFlow()


    private val _navigator: MutableSharedFlow<JSONObject> = MutableSharedFlow()
    val navigator: SharedFlow<JSONObject> = _navigator.asSharedFlow()

    private val _inViewAction: MutableSharedFlow<JSONObject?> = MutableSharedFlow()
    val inViewAction: SharedFlow<JSONObject?> = _inViewAction.asSharedFlow()

    private val _isError: MutableSharedFlow<ErrorItemModel> = MutableSharedFlow()
    val isError: SharedFlow<ErrorItemModel> = _isError.asSharedFlow()

    // Page Level loading(Shimmer Loading) is handled in the ComponentScreen so use this for actions only such as http actions
    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // Pull to refresh is triggered by UI but need to finish when data fetching is finished so use trigger this after network call
    private val _isRefreshing: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val isRefreshing: SharedFlow<Boolean> = _isRefreshing.asSharedFlow()


    fun updateInViewAction(jsonObject: JSONObject) {
        viewModelScope.launch {
            _inViewAction.emit(jsonObject)
        }
    }

    fun emptyInViewAction() {
        viewModelScope.launch {
            _inViewAction.emit(null)
        }
    }

    fun loadRemoteDataFlow(url: String, swipeRefresh: Boolean = false) {
        viewModelScope.launch {
            if (!swipeRefresh) {
                _componentsState.value = ComponentUiState.Loading
            }
            repository.genericGetNetworkCallFlow(url = url).collect { response ->
                when (response) {
                    is CallResult.Success -> {
                        val jsonObject = response.data
                        withContext(Dispatchers.Default) {
                            val componentDetailScreenItem: ComponentDetailScreenItem =
                                ComponentDetailScreenItem.map(jsonObject)
                            _componentsState.value =
                                ComponentUiState.Loaded(componentDetailScreenItem)
                        }
                    }

                    is CallResult.Error.ServerError -> {
                        _componentsState.value =
                            ComponentUiState.Error(
                                ErrorItemModel(
                                    errorMessage = "${response.errorCode}: ${response.message}",
                                    errorIcon = Icons.Default.CloudOff,
                                    retry = { loadRemoteDataFlow(url = url) })
                            )
                    }

                    is CallResult.Error.Network -> {
                        _componentsState.value = ComponentUiState.Error(
                            ErrorItemModel(
                                errorMessage = response.message,
                                errorIcon = Icons.Default.WifiOff,
                                retry = { loadRemoteDataFlow(url = url) })
                        )
                    }

                    is CallResult.Error.HttpError -> {
                        _componentsState.value = ComponentUiState.Error(
                            ErrorItemModel(
                                errorMessage = response.message,
                                errorIcon = Icons.Default.Error,
                                retry = { loadRemoteDataFlow(url = url) })
                        )
                    }

                    is CallResult.Error.JsonError -> {
                        _componentsState.value = ComponentUiState.Error(
                            ErrorItemModel(
                                errorMessage = response.message,
                                errorIcon = Icons.Default.Error,
                                retry = { loadRemoteDataFlow(url = url) })
                        )
                    }

                    is CallResult.Error.Unknown -> {
                        _componentsState.value = ComponentUiState.Error(
                            ErrorItemModel(
                                errorMessage = response.message,
                                errorIcon = Icons.Default.Error,
                                retry = { loadRemoteDataFlow(url = url) })
                        )
                    }
                }
            }

            // Notify to stop refreshing indicator
            _isRefreshing.emit(false)
        }
    }

    /**
     * We should keep viewmodel activity context free so handle navigation in activity
     */
    fun navigateTo(jsonObject: JSONObject) {
        viewModelScope.launch {
            _inViewAction.emit(null)
            _isLoading.value = false
            _navigator.emit(jsonObject)
        }
    }

    fun setError(errorItemModel: ErrorItemModel) {
        viewModelScope.launch {
            _isError.emit(errorItemModel)
        }
    }

    fun setLoading(isLoading: Boolean) {
        viewModelScope.launch {
            _isLoading.value = isLoading
        }
    }

//    fun updateExpandCollapse(accordionItemModel: AccordionItemModel) {
//        _componentsState.update { uiState ->
//            if (uiState is ComponentUiState.Loaded) {
//                val newList = uiState.componentDetailScreenItem.components?.map { componentModel ->
//                    if (componentModel is AccordionItemModel) {
//                        if (componentModel == accordionItemModel) {
//                            componentModel.copy(expanded = !componentModel.expanded)
//                        } else {
//                            componentModel
//                        }
//                    } else {
//                        componentModel
//                    }
//                }
//                val newComponentDetailScreenItem =
//                    uiState.componentDetailScreenItem.copy(components = newList)
//                ComponentUiState.Loaded(newComponentDetailScreenItem)
//            } else {
//                uiState
//            }
//        }
//    }
}