package com.kbcoding.newssearch.di

import com.kbcoding.newssearch.core.common.utils.Logger
import com.kbcoding.newssearch.core.commonimpl.LoggerImpl
import com.kbcoding.newssearch.news_data.data.ArticlesRepositoryImpl
import com.kbcoding.newssearch.news_data.domain.ArticlesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ArticlesModule {


    @Binds
    @Singleton
    fun bindArticlesRepository(articlesRepositoryImpl: ArticlesRepositoryImpl): ArticlesRepository

    @Binds
    @Singleton
    fun bindLogger(loggerImpl: LoggerImpl): Logger
}