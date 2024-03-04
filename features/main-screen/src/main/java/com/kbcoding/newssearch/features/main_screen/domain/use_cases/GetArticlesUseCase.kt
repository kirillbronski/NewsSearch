package com.kbcoding.newssearch.features.main_screen.domain.use_cases

import com.kbcoding.newssearch.news_data.data.RequestResult
import com.kbcoding.newssearch.news_data.data.map
import com.kbcoding.newssearch.news_data.domain.ArticlesRepository
import com.kbcoding.newssearch.news_data.models.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val repository: ArticlesRepository
) {
    operator fun invoke(): Flow<RequestResult<List<Article>>> {
        return repository.getArticles()
            .map { requestResult ->
                requestResult.map { articles ->
                    articles.map {
                        it.toUiArticle()
                    }
                }
            }
    }

    private fun Article.toUiArticle(): Article {
        TODO("Not yet implemented")
    }
}