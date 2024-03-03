package com.kbcoding.newssearch.news_data.data

import com.kbcoding.newssearch.database.NewsDatabase
import com.kbcoding.newssearch.database.models.ArticleDbo
import com.kbcoding.newssearch.news_api.NewsApi
import com.kbcoding.newssearch.news_api.models.ArticleDto
import com.kbcoding.newssearch.news_api.models.ResponseDto
import com.kbcoding.newssearch.news_data.domain.ArticlesRepository
import com.kbcoding.newssearch.news_data.mappers.toArticle
import com.kbcoding.newssearch.news_data.mappers.toArticleDbo
import com.kbcoding.newssearch.news_data.models.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach

class ArticlesRepositoryImpl(
    private val api: NewsApi,
    private val db: NewsDatabase,
    private val mergeStrategy: MergeStrategy<RequestResult<List<Article>>>
) : ArticlesRepository {
    override suspend fun getArticles(): Flow<RequestResult<List<Article>>> {

        val cachedAllArticles: Flow<RequestResult<List<Article>>> = getArticlesFromDb()
            .map { result ->
                result.map { articlesDtos ->
                    articlesDtos.map { articleDto ->
                        articleDto.toArticle()
                    }
                }
            }

        val remoteArticles: Flow<RequestResult<List<Article>>> = getArticlesFromApi()
            .map { result ->
                result.map { responseDto ->
                    responseDto.articles.map { articleDto ->
                        articleDto.toArticle()
                    }
                }
            }

        return cachedAllArticles.combine(remoteArticles) { cached, remote ->
            mergeStrategy.merge(cached, remote)
        }
    }

    private fun getArticlesFromApi(): Flow<RequestResult<ResponseDto<ArticleDto>>> {
        val apiRequest = flow { emit(api.everything()) }
            .onEach { result ->
                if (result.isSuccess) {
                    saveApiResponseToCache(checkNotNull(result.getOrThrow()).articles)
                }
            }.map { it.toRequestResult() }

        val inProgress = flowOf<RequestResult<ResponseDto<ArticleDto>>>(RequestResult.InProgress())

        return merge(apiRequest, inProgress)
    }

    private suspend fun saveApiResponseToCache(articlesDtos: List<ArticleDto>) {
        val articlesDbos = articlesDtos.map { articleDto ->
            articleDto.toArticleDbo()
        }
        db.articlesDao.saveArticles(articlesDbos)
    }

    private fun getArticlesFromDb(): Flow<RequestResult<List<ArticleDbo>>> {
        val dbRequest = db.articlesDao
            .getArticles()
            .map { RequestResult.Success(it) }

        val inProgress = flowOf<RequestResult<List<ArticleDbo>>>(RequestResult.InProgress())

        return merge(dbRequest, inProgress)
    }
}

