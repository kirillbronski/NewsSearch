package com.kbcoding.newsapi.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SourceDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
)
