package com.kbcoding.newssearch.features.main_screen.presentation

import com.kbcoding.newssearch.news_data.models.Article

sealed class MainScreenState {
    data object Default : MainScreenState()
    data class Loading(val articles: List<Article>? = null) : MainScreenState()
    data class Error(val articles: List<Article>? = null, val errorMessage: String? = null) : MainScreenState()
    data class Success(val articles: List<Article>) : MainScreenState()
}