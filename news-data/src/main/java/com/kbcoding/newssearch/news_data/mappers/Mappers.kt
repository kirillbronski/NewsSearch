package com.kbcoding.newssearch.news_data.mappers

import com.kbcoding.newssearch.database.models.ArticleDbo
import com.kbcoding.newssearch.database.models.SourceDbo
import com.kbcoding.newssearch.news_api.models.ArticleDto
import com.kbcoding.newssearch.news_api.models.SourceDto
import com.kbcoding.newssearch.news_data.models.Article
import com.kbcoding.newssearch.news_data.models.Source

internal fun ArticleDto.toArticleDbo(): ArticleDbo {
    return ArticleDbo(
        source = sourceDto?.toSourceDbo() ?: SourceDbo("", ""),
        author = author ?: "",
        title = title ?: "",
        description = description ?: "",
        url = url ?: "",
        urlToImage = urlToImage ?: "",
        publishedAt = publishedAt,
        content = content ?: ""
    )
}

internal fun ArticleDbo.toArticle(): Article {
    return Article(
        source = source.toSource(),
        author = author,
        title = title,
        description = description,
        url = url,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content,
        cacheId = id
    )
}

private fun SourceDto.toSourceDbo(): SourceDbo {
    return SourceDbo(
        id = id ?: "",
        name = name ?: "",
    )
}

private fun SourceDbo.toSource(): Source {
    return Source(id, name)
}

private fun SourceDto.toSource(): Source {
    return Source(id ?: "", name ?: "")
}

internal fun ArticleDto.toArticle(): Article {
    return Article(
        source = sourceDto?.toSource() ?: Source("", ""),
        author = author ?: "",
        title = title ?: "",
        description = description ?: "",
        url = url ?: "",
        urlToImage = urlToImage ?: "",
        publishedAt = publishedAt,
        content = content ?: "",
    )
}