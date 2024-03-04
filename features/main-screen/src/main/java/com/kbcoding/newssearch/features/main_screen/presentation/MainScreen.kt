package com.kbcoding.newssearch.features.main_screen.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import com.kbcoding.newssearch.news_data.models.Article
import java.util.Date

@Composable
fun MainScreen() {
    MainScreen(viewModel = viewModel())
}

@Composable
internal fun MainScreen(
    viewModel: MainScreenViewModel
) {
    val state by viewModel.state.collectAsState()

    when (val currentState = state) {
        is MainScreenState.Default -> DefaultScreen()
        is MainScreenState.Error -> ArticlesWithError(
            currentState.articles,
            currentState.errorMessage
        )

        is MainScreenState.Loading -> ArticlesDuringUpdate(currentState.articles ?: emptyList())
        is MainScreenState.Success -> Articles(currentState.articles)
    }
}

@Composable
private fun ArticlesWithError(articles: List<ArticleUi>?, errorMessage: String?) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.error),
            contentAlignment = Alignment.Center
        ) {
            Text(text = errorMessage ?: "Error", color = MaterialTheme.colorScheme.onError)
        }
        if (articles != null) {
            Articles(articles)
        }
    }
}

@Composable
private fun ArticlesDuringUpdate(articles: List<ArticleUi>?) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.padding(8.dp).fillMaxWidth(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        if (articles != null) {
            Articles(articles)
        }
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
                publishedAt = Date()
            ),
            ArticleUi(
                id = 2,
                title = "Android Studio Jellyfish is canary 2",
                description = "New canary version Android IDE has been released 2",
                url = "",
                urlToImage = "",
                publishedAt = Date()
            )
        )
    )
}
