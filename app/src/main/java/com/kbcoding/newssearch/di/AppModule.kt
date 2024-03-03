package com.kbcoding.newssearch.di

import android.content.Context
import com.kbcoding.newssearch.BuildConfig
import com.kbcoding.newssearch.database.NewsDatabase
import com.kbcoding.newssearch.news_api.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNewsApi(): NewsApi {
        return NewsApi(
            baseUrl = BuildConfig.NEWS_BASE_URL,
            apiKey = BuildConfig.NEWS_API_KEY,
        )
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NewsDatabase {
        return NewsDatabase(context)
    }

//    @Provides
//    fun provideArticlesRepository(
//        newsApi: NewsApi,
//        newsDatabase: NewsDatabase
//    ): ArticlesRepository {
//        return ArticlesRepositoryImpl(newsApi, newsDatabase)
//    }

}