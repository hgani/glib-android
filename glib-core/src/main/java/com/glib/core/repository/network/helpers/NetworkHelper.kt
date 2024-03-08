package com.glib.core.repository.network.helpers

import okhttp3.Headers
import org.json.JSONException
import retrofit2.HttpException

/**
 * Handles retrofit exception throwing and return Success<T> or one of the error types:
 * CallResult.Error.ServerError if the exception is http and body can be mapped to ServerError
 * CallResult.Error.HttpError if the exception is http and body cannot be mapped
 * CallResult.Error.JsonError for Json parsing errors
 * CallResult.Error.Network for IO errors
 * CallResult.Error.Unknown for the rest
 */

const val GENERIC_ERROR_MESSAGE = "Something went wrong! Please try again later"
const val NETWORK_ERROR_MESSAGE = "No internet!"
suspend fun <T> safeApiCall(apiCall: suspend () -> CallResult<T>): CallResult<T> {
    return try {
        apiCall()
    } catch (e: NoConnectivityException) {
        CallResult.Error.Network(message = NETWORK_ERROR_MESSAGE)
    } catch (e: HttpException) {
        val errorResponse = e.response()?.errorBody()
        if (errorResponse == null) {
            CallResult.Error.HttpError(e.message())
        } else {
            CallResult.Error.ServerError(message = e.message(), errorCode = e.code())
        }
    } catch (e: JSONException) {
        CallResult.Error.JsonError(message = GENERIC_ERROR_MESSAGE, exception = e)
    } catch (e: Exception) {
        CallResult.Error.Unknown(message = GENERIC_ERROR_MESSAGE, exception = e)
    }.also {
        val methodName = apiCall.javaClass.enclosingMethod?.name ?: ""
        val className = apiCall.javaClass.enclosingClass?.canonicalName ?: ""
        apiCallLog(className, methodName, it)
    }
}

/**
 * Logs the error to Firebase with ClassName, MethodName, and ErrorMessage
 */
fun apiCallLog(
    className: String,
    methodName: String,
    callResult: CallResult<*>
//    firebaseCrashlytics: FirebaseCrashlytics = FirebaseCrashlytics.getInstance()
) {
    when (callResult) {
        is CallResult.Error.HttpError -> {
            // TODO
//            firebaseCrashlytics.log("HttpError; ClassName: $className; MethodName: $methodName; ErrorMessage: ${callResult.message};")
        }

        is CallResult.Error.ServerError -> {
            // TODO
//            firebaseCrashlytics.log("ServerError; ClassName: $className; MethodName: $methodName; ErrorCode: ${callResult.errorCode}; ErrorMessage: ${callResult.message};")
        }

        is CallResult.Error.JsonError -> {
            // TODO
//            firebaseCrashlytics.log("JsonError; ClassName: $className; MethodName: $methodName; ErrorMessage: ${callResult.exception.message};")
        }

        is CallResult.Error.Unknown -> {
            // TODO
//            firebaseCrashlytics.log("UnknownError; ClassName: $className; MethodName: $methodName; ErrorMessage: ${callResult.exception.message};")
        }

        else -> {}
    }
}


/**
 * Response Wrapper
 */
sealed class CallResult<out R> {
    data class Success<out T>(
        val data: T,
        val header: Headers? = null,
        val message: String = "",
    ) : CallResult<T>()
    sealed class Error : CallResult<Nothing>() {
        data class Network(
            val message: String,
        ) : Error()

        data class HttpError(
            val message: String,
        ) : Error()

        data class ServerError(
            val message: String,
            val errorCode: Int? = null,
        ) : Error()

        data class JsonError(val message: String, val exception: Throwable): Error()
        data class Unknown(val message: String, val exception: Throwable) : Error()
    }

    @Suppress("UNCHECKED_CAST")
    fun <R2> map(transform: (R) -> R2): CallResult<R2> = when (this) {
        is Success -> Success(transform(this.data), this.header, this.message)
        else -> this as CallResult<R2>
    }
}



// TODO: This is temporary fun to show SnackBar Error until we refactor project to use Observer Pattern
//fun handleError(
//    fragment: Fragment,
//    errorResponse: CallResult.Error,
//    snackBarAction: (() -> Unit)? = null,
//    @StringRes snackBarActionTitleResId: Int = R.string.error_action_retry_title
//) = when (errorResponse) {
//    is CallResult.Error.ServerError -> {
//        fragment.view?.let {
//            val snackBar = Snackbar.make(it, errorResponse.message, Snackbar.LENGTH_SHORT)
//            if (snackBarAction != null) { snackBar.setAction(fragment.getString(snackBarActionTitleResId)) { snackBarAction.invoke() } }
//            snackBar.show()
//        }
//    }
//
//    is CallResult.Error.Network -> {
//        fragment.view?.let {
//            val snackBar = Snackbar.make(it, "Your network is unavailable. Please check your connection.", Snackbar.LENGTH_SHORT)
//            if (snackBarAction != null) { snackBar.setAction(fragment.getString(snackBarActionTitleResId)) { snackBarAction.invoke() } }
//            snackBar.show()
//        }
//    }
//
//    else -> {
//        fragment.view?.let {
//            val snackBar = Snackbar.make(it, "Sorry, something went wrong. Please try again later.", Snackbar.LENGTH_SHORT)
//            if (snackBarAction != null) { snackBar.setAction(fragment.getString(snackBarActionTitleResId)) { snackBarAction.invoke() } }
//            snackBar.show()
//        }
//    }
//}
