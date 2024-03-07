package com.kbcoding.newssearch.features.main_screen.presentation

import com.kbcoding.newssearch.features.main_screen.models.ArticleUi

internal sealed class MainScreenState(val articles: List<ArticleUi>?) {
    data object Default : MainScreenState(articles = null)
    class Loading(articles: List<ArticleUi>? = null) : MainScreenState(articles)
    class Error(articles: List<ArticleUi>? = null, val errorMessage: String? = null) : MainScreenState(articles)

    class Success(articles: List<ArticleUi>) : MainScreenState(articles)
}