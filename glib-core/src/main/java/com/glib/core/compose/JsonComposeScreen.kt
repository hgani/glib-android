package com.glib.core.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import com.glib.core.compose.components.ComponentScreen
import com.glib.core.compose.core.extensions.parseColor
import com.teamapp.teamapp.compose.theme.AppTheme
import kotlinx.coroutines.launch
import org.json.JSONObject

class JsonComposeScreen : ComponentActivity() {
    private val viewModel by viewModels<BaseViewModel>()
    private lateinit var pageUrl: String

//    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageUrl = intent.getStringExtra(ARGUMENT_KEY_BASE_ACTIVITY_PAGE_URL) ?: ""
        viewModel.loadRemoteDataFlow(url = pageUrl)

        enableEdgeToEdge()
        setContent {
            val pageState by viewModel.componentsState.collectAsState()
            val isLoading by viewModel.isLoading.collectAsState()

            var isSheetOpen by rememberSaveable { mutableStateOf(false) }

            val inViewActionJson by viewModel.inViewAction.collectAsState(initial = null)
            val snackBarHostState = remember { SnackbarHostState() }
            val coroutineScope = rememberCoroutineScope()
//            val pullToRefreshState = rememberPullToRefreshState()
            AppTheme {
                Surface {
                    Scaffold(snackbarHost = { SnackbarHost(hostState = snackBarHostState) }) {
                        Box(
                            modifier = Modifier
//                                .nestedScroll(pullToRefreshState.nestedScrollConnection)
                                .padding(it)
                                .fillMaxSize()
                        ) {

                            // --------------------- SnackBar Toast --------------------- //
                            LaunchedEffect(key1 = true) {
                                viewModel.isError.collect {
                                    coroutineScope.launch {
                                        snackBarHostState.showSnackbar(it.errorMessage)
                                    }
                                }
                            }

                            // --------------------- Actual Screen --------------------- //
                            ComponentScreen(state = pageState, viewModel = viewModel)

                            // --------------------- Loading --------------------- //
                            if (isLoading) {
                                ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                                    val progress = createRef()
                                    CircularProgressIndicator(
                                        color = "#000080".parseColor()
                                            ?: ProgressIndicatorDefaults.circularColor,
                                        modifier = Modifier.constrainAs(progress) {
                                            top.linkTo(parent.top)
                                            bottom.linkTo(parent.bottom)
                                            start.linkTo(parent.start)
                                            end.linkTo(parent.end)
                                        }
                                    )
                                }
                            }

//                            // --------------------- PullToRefresh --------------------- //
//
//                            // When pull to refresh state and start fetching result
//                            if (pullToRefreshState.isRefreshing) {
//                                LaunchedEffect(true) {
//                                    viewModel.loadRemoteDataFlow(url = pageUrl, swipeRefresh = true)
//                                }
//                            }
//
//                            // When result is fetched either success or failure stop indicator
//                            LaunchedEffect(key1 = true) {
//                                viewModel.isRefreshing.collect {isRefreshing ->
//                                    if (!isRefreshing) {
//                                        pullToRefreshState.endRefresh()
//                                    }
//                                }
//                            }

                            // TODO
                            // Actual Pull to refresh indicator UI
//                            PullToRefreshContainer(modifier = Modifier.align(Alignment.TopCenter), state = pullToRefreshState)
                        }
                    }
                }
            }
        }

    // TODO
//        // Collect Action
//        lifecycleScope.launch {
//            viewModel.navigator.collect { jsonObject ->
//                handleAction(
//                    jsonObject = jsonObject,
//                    updateInViewAction = viewModel::updateInViewAction
//                )
//            }
//        }
    }

    // Note:- We can separate InView actions and Non InView Actions Directly into viewmodel but having here will be easy for readability
    // Add more actions below as needed
    private fun handleAction(jsonObject: JSONObject, updateInViewAction: (JSONObject) -> Unit) {
        // TODO
//        val controller = jsonObject.nullableString("controller")
//        when (controller) {
//            "actions/optionDialog2" -> {
//                updateInViewAction(jsonObject)
//            }
//
//            "actions/alertDialog" -> {
//                updateInViewAction(jsonObject)
//            }
//
//            "actions/emailDialog" -> {
//                executeAction(jsonObject = jsonObject)
//            }
//
//            "actions/openUrl" -> {
//                executeAction(jsonObject = jsonObject)
//            }
//
//            "actions/openMap" -> {
//                executeAction(jsonObject = jsonObject)
//            }
//
//            "actions/reload" -> {
//                // If url from controller is null then use pageUrl to reload the page
//                val url = jsonObject.nullableString("url") ?: pageUrl
//                viewModel.loadRemoteDataFlow(url = url)
//            }
//
//            else -> {
//                executeAction(jsonObject = jsonObject)
//            }
//        }
    }

    // TODO
//    // The values with default value null are optional, only pass if you needed and consume in action
//    private fun executeAction(
//        jsonObject: JSONObject,
//        navigateTo: ((JSONObject) -> Unit)? = null, // If the action contains sub Action than pass to this function and do not execute from within Action
//        onError: ((ErrorItemModel) -> Unit)? = null,
//        isLoading: ((Boolean) -> Unit)? = null
//    ) {
//        val controller = jsonObject.nullableString("controller")
//        var action: Action? = null
//        if (controller != null) {
//            action = Action.create(controller, null)
//        }
//        action?.execute(this@JsonComposeScreen, jsonObject, navigateTo, onError, isLoading)
//    }

    companion object {
        const val ARGUMENT_KEY_BASE_ACTIVITY_PAGE_URL = "ARGUMENT_KEY_BASE_ACTIVITY_PAGE_URL"
    }
}
