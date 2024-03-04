package com.teamapp.teamapp.repository

import android.content.Context
import com.glib.core.http.GHttp
import com.glib.core.repository.db.JsonResponseModel
import com.glib.core.repository.db.LocalDatabase
import com.glib.core.repository.network.helpers.CallResult
import com.glib.core.repository.network.helpers.safeApiCall
import com.glib.core.repository.network.interceptor.DefaultHeadersInterceptor
import com.glib.core.repository.network.interceptor.NetworkConnectionInterceptor
import com.glib.core.repository.network.interceptor.UploadProgressInterceptor
import com.glib.core.repository.network.interceptor.UploadProgressListener
import com.glib.core.utils.Res
import com.teamapp.teamapp.network.api.GenericApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.Cache
import okhttp3.JavaNetCookieJar
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.net.CookieHandler
import java.net.CookieManager
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

class Repository(val context: Context = Res.context) {

    private var httpCacheSize = (50 * 1024 * 1024).toLong() // 50 MiB
    private val httpCache = Cache(context.cacheDir, httpCacheSize)
    private val cookieJar by lazy {
        val cookieManager: CookieManager =
            CookieHandler.getDefault() as? CookieManager ?: CookieManager().apply {
                CookieHandler.setDefault(this)
            }
        JavaNetCookieJar(cookieManager)
    }

    /**
     * TODO Dao instance should be injected into constructor once Hilt injectable
     * This function is to use caching for new compose screens which follow below steps
     * 1. Check if data is present in local database then emit to the UI else Do nothing
     * 2. Call API and get response
     * 3. If Api Success then insert response in local database then emit to the UI
     * 4. else if fails then emit Error to the UI
     *
     * Note:- We prioritise the Errors than the data from local database, so if there is Error then we show that to the UI.
     */
    fun genericGetNetworkCallFlow(url: String) = flow {
        val dao = LocalDatabase.INSTANCE.dao()
        val localResponse = dao.getLocalResponse(url = url)
        if (localResponse.isNotEmpty()) {
            emit(CallResult.Success(localResponse[0].response))
        }
        val result = genericGetNetworkCall(url)
        if (result is CallResult.Success) {
            dao.insertNetworkResponse(JsonResponseModel(url = url, response = result.data, timeStamp = System.currentTimeMillis()))
        }
        emit(result)
    }

    suspend fun genericGetNetworkCall(
        url: String,
        requestParams: MutableMap<String?, String?>? = null
    ): CallResult<JSONObject> {
        return withContext(Dispatchers.IO) {
            safeApiCall {

                // Currently at some places we cast existing TaParams to MutableMap which sometimes contains null values so we accept that and filter it out
                // Once we get Rid of TaParams Just make it nun nullable and remove this filtering.
                val notNullRequestParams =
                    (requestParams?.filter { it.key != null && it.value != null }?.toMap()
                        ?: emptyMap()) as Map<String, String>

                val response = if (notNullRequestParams.isEmpty()) {
                    provideGenericApi().get(url)
                } else {
                    provideGenericApi().get(url, notNullRequestParams)
                }

                val body = response.body()
                if ((response.isSuccessful || response.code() == HttpURLConnection.HTTP_NOT_MODIFIED) && body != null) {
                    CallResult.Success(
                        header = response.headers(),
                        data = JSONObject(body.string())
                    )
                } else {
                    CallResult.Error.ServerError(response.message(), response.code())
                }
            }
        }
    }

    suspend fun genericPostNetworkCall(
        url: String,
        requestParams: MutableMap<String?, String?>? = null,
    ): CallResult<JSONObject> {
        return withContext(Dispatchers.IO) {
            safeApiCall {

                // Currently at some places we cast existing TaParams to MutableMap which sometimes contains null values so we accept that and filter it out
                // Once we get Rid of TaParams Just make it nun nullable and remove this filtering.
                val notNullRequestParams =
                    (requestParams?.filter { it.key != null && it.value != null }?.toMap()
                        ?: emptyMap()) as Map<String, String>

                val response = if (notNullRequestParams.isEmpty()) {
                    provideGenericApi().post(url)
                } else {
                    provideGenericApi().post(url, notNullRequestParams)
                }

                val body = response.body()
                if ((response.isSuccessful || response.code() == HttpURLConnection.HTTP_NOT_MODIFIED) && body != null) {
                    CallResult.Success(
                        header = response.headers(),
                        data = JSONObject(body.string())
                    )
                } else {
                    CallResult.Error.ServerError(response.message(), response.code())
                }
            }
        }
    }

    suspend fun genericDeleteNetworkCall(
        url: String,
        requestParams: MutableMap<String, String>? = null,
    ): CallResult<JSONObject> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                val response = if (requestParams == null) {
                    provideGenericApi().delete(url)
                } else {
                    provideGenericApi().delete(url = url, params =  requestParams)
                }

                val body = response.body()
                if ((response.isSuccessful || response.code() == HttpURLConnection.HTTP_NOT_MODIFIED) && body != null) {
                    CallResult.Success(
                        header = response.headers(),
                        data = JSONObject(body.string())
                    )
                } else {
                    CallResult.Error.ServerError(response.message(), response.code())
                }
            }
        }
    }

    suspend fun genericPutNetworkCall(
        url: String,
        requestParams: MutableMap<String, String>? = null,
    ): CallResult<JSONObject> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                val response = if (requestParams == null) {
                    provideGenericApi().put(url)
                } else {
                    provideGenericApi().put(url = url, params =  requestParams)
                }

                val body = response.body()
                if ((response.isSuccessful || response.code() == HttpURLConnection.HTTP_NOT_MODIFIED) && body != null) {
                    CallResult.Success(
                        header = response.headers(),
                        data = JSONObject(body.string())
                    )
                } else {
                    CallResult.Error.ServerError(response.message(), response.code())
                }
            }
        }
    }


    suspend fun downloadFile(url: String): CallResult<ResponseBody> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                CallResult.Success(provideGenericApi().downloadFile(url = url))
            }
        }
    }

    suspend fun uploadFile(
        url: String,
        requestParams: MutableMap<String, String>,
        file: File,
        contentType: String,
        progressListener: UploadProgressListener
    ): CallResult<String?> {
        return withContext(Dispatchers.IO) {
            safeApiCall {
                val requestFile = file.asRequestBody(contentType = contentType.toMediaType())
                val response =
                    provideGenericApi(provideFileUploadRetrofit(progressListener = progressListener)).uploadFile(
                        headers = requestParams,
                        uploadUrl = url,
                        file = requestFile
                    ).execute()
                if ((response.isSuccessful || response.code() == HttpURLConnection.HTTP_NOT_MODIFIED)) {
                    CallResult.Success(
                        header = response.headers(),
                        data = response.body().toString()
                    )
                } else {
                    CallResult.Error.ServerError(response.message(), response.code())
                }
            }
        }
    }


    // TODO Retrofit instance should be injected into constructor once Hilt injectable
    private fun provideGenericRetrofit(): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .cache(httpCache)
            .cookieJar(cookieJar)
            .addInterceptor(logging)
            .addInterceptor(NetworkConnectionInterceptor())
            .addInterceptor(DefaultHeadersInterceptor())

        val retrofitBuilder =
            Retrofit
                .Builder()
//                .baseUrl(Build.INSTANCE.pathPrefix)
                .baseUrl(GHttp.instance.host)
                .client(client.build())
        return retrofitBuilder.build()
    }

    // TODO Retrofit instance should be injected into constructor once Hilt injectable
    private fun provideFileUploadRetrofit(progressListener: UploadProgressListener): Retrofit {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(NetworkConnectionInterceptor())
            .addInterceptor(UploadProgressInterceptor(progressListener = progressListener))
            .connectTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)

        val retrofitBuilder =
            Retrofit
                .Builder()
//                .baseUrl(Build.INSTANCE.pathPrefix)
                .baseUrl(GHttp.instance.host)
                .addConverterFactory(MoshiConverterFactory.create())
                .client(client.build())
        return retrofitBuilder.build()
    }

    // TODO API instance should be injected into constructor once Hilt injectable
    private fun provideGenericApi(retrofit: Retrofit = provideGenericRetrofit()): GenericApi =
        retrofit.create(GenericApi::class.java)

    companion object {
        private const val TIME_OUT_SECONDS: Long = 60L
    }
}
