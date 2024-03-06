package com.kbcoding.newssearch.news_api.models

import com.kbcoding.newssearch.news_api.utils.DateTimeUtcSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class ArticleDto(
    @SerialName("source") val sourceDto: SourceDto?,
    @SerialName("author") val author: String?,
    @SerialName("title") val title: String?,
    @SerialName("description") val description: String?,
    @SerialName("url") val url: String?,
    @SerialName("urlToImage") val urlToImage: String?,
    @SerialName("publishedAt") @Serializable(with = DateTimeUtcSerializer::class) val publishedAt: Date,
    @SerialName("content") val content: String?,
)
