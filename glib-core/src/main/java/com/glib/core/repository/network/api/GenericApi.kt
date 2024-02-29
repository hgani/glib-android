package com.teamapp.teamapp.network.api

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.QueryMap
import retrofit2.http.Streaming
import retrofit2.http.Url

interface GenericApi {
    @GET
    suspend fun get(@Url url: String): Response<ResponseBody>

    // Must check params: RequestBody is not empty
    @GET
    suspend fun get(@Url url: String, @QueryMap params: Map<String, String>): Response<ResponseBody>

    @POST
    @FormUrlEncoded
    suspend fun post(@Url url: String): Response<ResponseBody>

    // Must check params is not empty
    @POST
    @FormUrlEncoded
    suspend fun post(@Url url: String, @FieldMap params: Map<String, String>): Response<ResponseBody>

    @Streaming
    @GET
    suspend fun downloadFile(@Url url: String): ResponseBody

    @PUT
    fun uploadFile(
        @HeaderMap headers: Map<String, String>,
        @Url uploadUrl: String,
        @Body file: RequestBody
    ): Call<ResponseBody>

    @DELETE
    suspend fun delete(@Url url: String): Response<ResponseBody>

    @DELETE
    suspend fun delete(@Url url: String, @FieldMap params: Map<String, String>): Response<ResponseBody>

    @PUT
    suspend fun put(@Url url: String): Response<ResponseBody>

    @PUT
    suspend fun put(@Url url: String, @FieldMap params: Map<String, String>): Response<ResponseBody>
}
