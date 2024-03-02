package com.kbcoding.newssearch.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kbcoding.newssearch.database.models.ArticleDbo
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Query("SELECT * FROM articles")
    fun getArticles(): Flow<List<ArticleDbo>>

    @Insert
    suspend fun saveArticles(articles: List<ArticleDbo>)

    @Delete
    suspend fun deleteArticle(article: ArticleDbo)

    @Query("DELETE FROM articles")
    suspend fun deleteArticles()


}