package com.kbcoding.newssearch.news_data.data

import com.kbcoding.newssearch.database.NewsDatabase

import com.kbcoding.newssearch.news_api.NewsApi
import com.kbcoding.newssearch.news_data.domain.ArticlesRepository
import com.kbcoding.newssearch.news_data.mappers.toArticle
import com.kbcoding.newssearch.news_data.models.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ArticlesRepositoryImpl(
    private val api: NewsApi,
    private val db: NewsDatabase
) : ArticlesRepository {
    override suspend fun getArticles(): RequestResult<Flow<List<Article>>> {
        return RequestResult.InProgress(db.articlesDao
            .getArticles()
            .map { articles -> articles.map { it.toArticle() } }
        )
    }
}