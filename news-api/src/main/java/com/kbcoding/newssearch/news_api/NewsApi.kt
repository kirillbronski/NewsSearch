package com.kbcoding.newssearch.news_api

import androidx.annotation.IntRange
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.kbcoding.newssearch.news_api.models.ArticleDto
import com.kbcoding.newssearch.news_api.models.LanguageDto
import com.kbcoding.newssearch.news_api.models.ResponseDto
import com.kbcoding.newssearch.news_api.models.SortByDto
import com.kbcoding.newssearch.news_api.utils.NewsApiKeyInterceptor
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Date

/**
 * API Documentation [here] (https://newsapi.org/docs/get-started)
 */
interface NewsApi {

    /**
     * API details [here] (https://newsapi.org/docs/endpoints/everything)
     */
    @GET("everything")
    suspend fun everything(
        @Query("q") query: String? = "android",
        @Query("from") from: Date? = null,
        @Query("to") to: Date? = null,
        @Query("languages") languageDtos: List<@JvmSuppressWildcards LanguageDto>? = null,
        @Query("sortBy") sortByDto: SortByDto? = null,
        @Query("pageSize") @IntRange(from = 0, to = 100) pageSize: Int = 100,
        @Query("page") @IntRange(from = 1) page: Int? = 1,
    ): Result<ResponseDto<ArticleDto>>
}

fun NewsApi(
    baseUrl: String,
    apiKey: String,
    okHttpClient: OkHttpClient? = null,
    json: Json = Json,
): NewsApi {
    return retrofit(baseUrl, apiKey, okHttpClient, json).create()
}

private fun retrofit(
    baseUrl: String,
    apiKey: String,
    okHttpClient: OkHttpClient?,
    json: Json,
): Retrofit {
    val jsonConverterFactory = json.asConverterFactory(MediaType.get("application/json"))

    val modifiedOkHttpClient: OkHttpClient = (okHttpClient?.newBuilder() ?: OkHttpClient.Builder())
        .addInterceptor(NewsApiKeyInterceptor(apiKey))
        .build()

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(jsonConverterFactory)
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .client(modifiedOkHttpClient)
        .build()
}