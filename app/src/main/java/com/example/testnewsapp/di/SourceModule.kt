package com.example.testnewsapp.di

import com.example.testnewsapp.data.NewsRepository
import com.example.testnewsapp.data.source.DefaultNewsRepository
import com.example.testnewsapp.data.source.local.DefaultNewsLocalDataSource
import com.example.testnewsapp.data.source.local.NewsLocalDataSource
import com.example.testnewsapp.data.source.remote.DefaultNewsRemoteDataSource
import com.example.testnewsapp.data.source.remote.NewsRemoteDataSource
import dagger.Binds
import dagger.Module

/**
 * @author Abhradeep Ghosh
 */

@Module
abstract class SourceModule {

    @Binds
    abstract fun provideLocalDataSource(localDataSource: DefaultNewsLocalDataSource): NewsLocalDataSource

    @Binds
    abstract fun provideRemoteDataSource(remoteDataSource: DefaultNewsRemoteDataSource): NewsRemoteDataSource

    @Binds
    abstract fun provideNewsRepository(pupilRepository: DefaultNewsRepository): NewsRepository

}