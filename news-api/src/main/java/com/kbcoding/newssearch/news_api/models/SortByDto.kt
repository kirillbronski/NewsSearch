package com.kbcoding.newssearch.news_api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
    enum class SortByDto {
        @SerialName("relevancy")
        RELEVANCY,

        @SerialName("popularity")
        POPULARITY,

        @SerialName("published_at")
        PUBLISHED_AT,
    }