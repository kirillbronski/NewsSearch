package com.kbcoding.newssearch.features.main_screen.presentation

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.kbcoding.newssearch.features.main_screen.models.ArticleUi
import java.util.Date

@Composable
internal fun ArticleItem(
    article: ArticleUi
) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = article.title,
            style = MaterialTheme.typography.headlineSmall,
            maxLines = 1,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = article.description,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 3
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ArticleItemPreview(
    @PreviewParameter(ArticleUiProvider::class, limit = 2) article: ArticleUi
) {
    ArticleItem(article)
}

private class ArticleUiProvider : PreviewParameterProvider<ArticleUi> {
    override val values: Sequence<ArticleUi>
        get() = sequenceOf(
            ArticleUi(
                id = 1,
                title = "Android Studio Jellyfish is canary",
                description = "New canary version Android IDE has been released",
                url = "",
                urlToImage = "",
            )
        )
}
