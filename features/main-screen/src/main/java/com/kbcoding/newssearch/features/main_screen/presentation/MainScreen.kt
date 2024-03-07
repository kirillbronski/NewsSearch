package com.kbcoding.newssearch.features.main_screen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kbcoding.newssearch.features.main_screen.models.ArticleUi
import com.kbcoding.uikit.ui.theme.NewsSearchTheme

@Composable
fun MainScreen() {
    MainScreen(viewModel = viewModel())
}

@Composable
internal fun MainScreen(
    viewModel: MainScreenViewModel
) {
    val state by viewModel.state.collectAsState()
    val currentState = state

    if (currentState is MainScreenState.Default) {
        DefaultScreen()
    } else {
        MainContent(currentState)
    }
}

@Composable
private fun MainContent(currentState: MainScreenState) {
    Column {
        if (currentState is MainScreenState.Error) {
            ErrorMessage(currentState)
        }
        if (currentState is MainScreenState.Loading) {
            ProgressIndicator()
        }
        if (currentState.articles != null) {
            Articles(currentState.articles)
        }
    }
}

@Composable
private fun ErrorMessage(currentState: MainScreenState.Error) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(NewsSearchTheme.colorScheme.error)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = currentState.errorMessage ?: "Articles with error",
            color = NewsSearchTheme.colorScheme.onError
        )
    }
}

@Composable
private fun ProgressIndicator() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun DefaultScreen() {
    Box(contentAlignment = Alignment.Center) {
        Text(text = "Default Screen")
    }
}

@Composable
private fun Articles(articles: List<ArticleUi>) {
    LazyColumn {
        items(articles) { article ->
            key(article.id) {
                ArticleItem(article)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ArticlesPreview(
    @PreviewParameter(ArticlesProvider::class, limit = 8) articles: List<ArticleUi>
) {
    Articles(articles = articles)
}

private class ArticlesProvider : PreviewParameterProvider<List<ArticleUi>> {
    override val values: Sequence<List<ArticleUi>> = sequenceOf(
        listOf(
            ArticleUi(
                id = 1,
                title = "Android Studio Jellyfish is canary",
                description = "New canary version Android IDE has been released",
                url = "",
                urlToImage = "",
            ),
            ArticleUi(
                id = 2,
                title = "Android Studio Jellyfish is canary 2",
                description = "New canary version Android IDE has been released 2",
                url = "",
                urlToImage = "",
            )
        )
    )
}
