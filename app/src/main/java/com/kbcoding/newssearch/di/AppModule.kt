package com.kbcoding.newssearch.di

import android.content.Context
import com.kbcoding.newssearch.BuildConfig
import com.kbcoding.newssearch.core.common.utils.AppCoroutineDispatchers
import com.kbcoding.newssearch.core.commonimpl.Logger
import com.kbcoding.newssearch.database.NewsDatabase
import com.kbcoding.newssearch.news_api.NewsApi
import com.kbcoding.newssearch.news_data.data.ArticlesRepositoryImpl
import com.kbcoding.newssearch.news_data.domain.ArticlesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient? {
        return if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient().newBuilder().addInterceptor(logging).build()
        } else {
            null
        }
    }

    @Provides
    @Singleton
    fun provideNewsApi(okHttpClient: OkHttpClient?): NewsApi {
        return NewsApi(
            baseUrl = BuildConfig.NEWS_BASE_URL,
            apiKey = BuildConfig.NEWS_API_KEY,
            okHttpClient = okHttpClient
        )
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NewsDatabase {
        return NewsDatabase(context)
    }

    @Provides
    @Singleton
    fun provideLogger(): Logger {
        return Logger()
    }

    @Provides
    @Singleton
    fun provideArticlesRepository(
        newsApi: NewsApi,
        database: NewsDatabase,
        logger: Logger
    ): ArticlesRepository {
        return ArticlesRepositoryImpl(newsApi, database, logger)
    }

    @Provides
    @Singleton
    fun provideAppCoroutineDispatchers(): AppCoroutineDispatchers {
        return AppCoroutineDispatchers()
    }

//    @Provides
//    fun provideArticlesRepository(
//        newsApi: NewsApi,
//        newsDatabase: NewsDatabase
//    ): ArticlesRepository {
//        return ArticlesRepositoryImpl(newsApi, newsDatabase)
//    }

}