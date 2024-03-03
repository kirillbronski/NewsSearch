package com.kbcoding.newssearch.news_data.domain

import com.kbcoding.newssearch.news_data.data.MergeStrategy
import com.kbcoding.newssearch.news_data.data.RequestResponseMergeStrategyImpl
import com.kbcoding.newssearch.news_data.data.RequestResult
import com.kbcoding.newssearch.news_data.models.Article
import kotlinx.coroutines.flow.Flow

interface ArticlesRepository {
    fun getArticles(
        mergeStrategy: MergeStrategy<RequestResult<List<Article>>> = RequestResponseMergeStrategyImpl()
    ): Flow<RequestResult<List<Article>>>

    fun searchArticle(query: String): Flow<RequestResult<Article>>

    fun fetchLatestArticles(): Flow<RequestResult<List<Article>>>
}