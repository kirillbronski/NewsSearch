package com.kbcoding.newssearch.news_data.data

import com.kbcoding.newssearch.core.commonimpl.Logger
import com.kbcoding.newssearch.database.NewsDatabase
import com.kbcoding.newssearch.database.models.ArticleDbo
import com.kbcoding.newssearch.news_api.NewsApi
import com.kbcoding.newssearch.news_api.models.ArticleDto
import com.kbcoding.newssearch.news_api.models.ResponseDto
import com.kbcoding.newssearch.news_data.domain.ArticlesRepository
import com.kbcoding.newssearch.news_data.mappers.toArticle
import com.kbcoding.newssearch.news_data.mappers.toArticleDbo
import com.kbcoding.newssearch.news_data.models.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach

class ArticlesRepositoryImpl(
    private val api: NewsApi,
    private val db: NewsDatabase,
    private val log: Logger
) : ArticlesRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getArticles(
        mergeStrategy: MergeStrategy<RequestResult<List<Article>>>
    ): Flow<RequestResult<List<Article>>> {

        val cachedAllArticles: Flow<RequestResult<List<Article>>> = getArticlesFromDb()

        val remoteArticles: Flow<RequestResult<List<Article>>> = getArticlesFromApi()

        return cachedAllArticles.combine(remoteArticles) { cached, remote ->
            mergeStrategy.merge(cached, remote)
        }.flatMapConcat { result ->
            if (result is RequestResult.Success) {
                db.articlesDao.getObserveArticles()
                    .map { dbos ->
                        dbos.map { it.toArticle() }
                    }.map { RequestResult.Success(it) }
            } else {
                flowOf(result)
            }
        }
    }

    override fun searchArticle(query: String): Flow<RequestResult<Article>> {
        TODO("Not yet implemented")
    }

    override fun fetchLatestArticles(): Flow<RequestResult<List<Article>>> {
        return getArticlesFromApi()
    }

    private fun getArticlesFromApi(): Flow<RequestResult<List<Article>>> {
        val apiRequest = flow { emit(api.everything()) }
            .onEach { result ->
                if (result.isSuccess) {
                    saveApiResponseToCache(result.getOrThrow().articles)
                } else if (result.isFailure) {
                    log.e(TAG, "Error getting from API ${result.exceptionOrNull()}")
                }
            }.map { it.toRequestResult() }

        val inProgress = flowOf<RequestResult<ResponseDto<ArticleDto>>>(RequestResult.InProgress())

        return merge(apiRequest, inProgress).map { result ->
            result.map { responseDto ->
                responseDto.articles.map { articleDto ->
                    articleDto.toArticle()
                }
            }
        }
    }

    private suspend fun saveApiResponseToCache(articlesDtos: List<ArticleDto>) {
        val articlesDbos = articlesDtos.map { articleDto ->
            articleDto.toArticleDbo()
        }
        db.articlesDao.saveArticles(articlesDbos)
    }

    private fun getArticlesFromDb(): Flow<RequestResult<List<Article>>> {
//        val dbRequest = flow {
//            emit(db.articlesDao.getArticles())
//        }
//            .map { RequestResult.Success(it) }
        val dbRequest = db.articlesDao::getArticles.asFlow()
            .map { RequestResult.Success(it) }
            .catch {
                log.e(TAG, "Error getting from database: ${it.message}")
                RequestResult.Error<List<ArticleDbo>>(error = it)
            }
            .flowOn(Dispatchers.IO)

        val inProgress = flowOf<RequestResult<List<ArticleDbo>>>(RequestResult.InProgress())

        return merge(dbRequest, inProgress).map { result ->
            result.map { articlesDtos ->
                articlesDtos.map { articleDto ->
                    articleDto.toArticle()
                }
            }
        }
    }

    private companion object {
        var TAG = ArticlesRepositoryImpl::class.java.name
    }
}

