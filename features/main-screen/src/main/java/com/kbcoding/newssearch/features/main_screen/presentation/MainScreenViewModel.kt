package com.kbcoding.newssearch.features.main_screen.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class MainScreenViewModel() : ViewModel() {

    private val _mainScreenState = MutableStateFlow(MainScreenState.Default)
    val mainScreenState: StateFlow<MainScreenState> get() = _mainScreenState.asStateFlow()

}