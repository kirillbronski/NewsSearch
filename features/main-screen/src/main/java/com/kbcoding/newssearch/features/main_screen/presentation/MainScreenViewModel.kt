package com.kbcoding.newssearch.features.main_screen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kbcoding.newssearch.features.main_screen.domain.use_cases.GetArticlesUseCase
import com.kbcoding.newssearch.news_data.data.RequestResult
import com.kbcoding.newssearch.news_data.data.map
import com.kbcoding.newssearch.news_data.models.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

internal class MainScreenViewModel(
    getArticlesUseCase: GetArticlesUseCase
) : ViewModel() {

    val mainScreenState: StateFlow<MainScreenState> = getArticlesUseCase()
        .map { it.toMainScreenState() }
        .stateIn(viewModelScope, SharingStarted.Lazily, MainScreenState.Default)

    private fun RequestResult<List<Article>>.toMainScreenState(): MainScreenState {
        return when (this) {
            is RequestResult.Error -> MainScreenState.Error(errorMessage = error?.message)
            is RequestResult.InProgress -> MainScreenState.Loading(data)
            is RequestResult.Success -> MainScreenState.Success(data)
        }
    }

}
