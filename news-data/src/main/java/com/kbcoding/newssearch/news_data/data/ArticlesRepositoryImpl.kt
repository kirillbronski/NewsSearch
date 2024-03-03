package com.kbcoding.newssearch.news_data.data

import com.kbcoding.newssearch.database.NewsDatabase
import com.kbcoding.newssearch.database.models.ArticleDbo
import com.kbcoding.newssearch.news_api.NewsApi
import com.kbcoding.newssearch.news_api.models.ArticleDto
import com.kbcoding.newssearch.news_api.models.ResponseDto
import com.kbcoding.newssearch.news_data.domain.ArticlesRepository
import com.kbcoding.newssearch.news_data.mappers.toArticleDbo
import com.kbcoding.newssearch.news_data.models.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class ArticlesRepositoryImpl(
    private val api: NewsApi,
    private val db: NewsDatabase
) : ArticlesRepository {
    override suspend fun getArticles(): Flow<RequestResult<List<Article>>> {

        val cachedAllArticles = getArticlesFromDb()

        val remoteArticles = getArticlesFromApi()

        cachedAllArticles.map {

        }

        return flow {
            emit(RequestResult.InProgress(cachedAllArticles))
        }
    }

    private fun getArticlesFromApi(): Flow<RequestResult<List<ArticleDbo>>> {
        return flow { emit(api.everything()) }
            .map { result ->
                if (result.isSuccess) {
                    val response: ResponseDto<ArticleDto> = result.getOrThrow()
                    RequestResult.Success(response.articles)
                } else {
                    RequestResult.Error(result.exceptionOrNull())
                }
            }
            .filterIsInstance<RequestResult.Success<List<ArticleDto>>>()
            .map { requestResult ->
                requestResult.map { articleDtoList ->
                    articleDtoList.map { articleDto ->
                        articleDto.toArticleDbo()
                    }
                }
            }.onEach {
                db.articlesDao.saveArticles(it.data)
            }
    }

    private fun getArticlesFromDb(): Flow<RequestResult.Success<List<ArticleDbo>>> {
        return db.articlesDao
            .getArticles()
            .map { RequestResult.Success(it) }
    }
}

