package com.kbcoding.newssearch.features.main_screen.domain.use_cases

import com.kbcoding.newssearch.news_data.data.RequestResult
import com.kbcoding.newssearch.news_data.domain.ArticlesRepository
import com.kbcoding.newssearch.news_data.models.Article
import kotlinx.coroutines.flow.Flow

class GetArticlesUseCase(
    private val repository: ArticlesRepository
) {
    suspend operator fun invoke(): Flow<RequestResult<List<Article>>> {
        return repository.getArticles()
    }
}