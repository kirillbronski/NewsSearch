package com.kbcoding.newssearch.news_api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * "status": "ok"
 * "totalResults": "218"
 * "articles": [ _ ]
 */
@Serializable
data class ResponseDto<E>(
    @SerialName("status") val status: String,
    @SerialName("totalResult") val totalResult: Int,
    @SerialName("articles") val articles: List<E>,
) {
}