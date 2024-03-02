package com.kbcoding.newssearch.features.main_screen.presentation

import com.kbcoding.newssearch.features.main_screen.domain.models.Article

sealed class MainScreenState {
    data object Default : MainScreenState()
    data object Loading : MainScreenState()
    data class Error(val message: String) : MainScreenState()
    data class Success(val articles: List<Article>) : MainScreenState()
}