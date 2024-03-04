package com.kbcoding.newssearch.features.main_screen.models


import com.kbcoding.newssearch.news_data.models.Article
import java.util.Date
internal data class ArticleUi(
    val id: Long,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: Date,
)
