package com.kbcoding.newssearch.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kbcoding.newssearch.database.dao.ArticleDao
import com.kbcoding.newssearch.database.models.ArticleDbo
import com.kbcoding.newssearch.database.utils.DateTypeConverters

class NewsDatabase internal constructor(
    private val db: NewsRoomDatabase
) {
    val articlesDao: ArticleDao
        get() = db.getArticleDao()
}

@Database(version = 1, entities = [ArticleDbo::class])
@TypeConverters(DateTypeConverters::class)
internal abstract class NewsRoomDatabase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao
}

fun NewsDatabase(applicationContext: Context): NewsDatabase {
    val newsRoomDatabase = Room.databaseBuilder(
        checkNotNull(applicationContext.applicationContext),
        NewsRoomDatabase::class.java,
        name = "news.db"
    )
        .build()
    return NewsDatabase(newsRoomDatabase)
}