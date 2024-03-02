package com.kbcoding.newssearch.news_data.domain

import com.kbcoding.newssearch.news_data.data.RequestResult
import com.kbcoding.newssearch.news_data.models.Article
import kotlinx.coroutines.flow.Flow

interface ArticlesRepository {
    suspend fun getArticles(): RequestResult<Flow<List<Article>>>
}