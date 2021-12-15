package com.example.mywiki_interviewtest.DI

import com.example.mywiki_interviewtest.repository.UploadRepository
import com.example.mywiki_interviewtest.repository.WikiRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideUploadRepository():UploadRepository{
        return UploadRepository()
    }

    @Provides
    @Singleton
    fun provideWikiRepository():WikiRepository{
        return WikiRepository()
    }
}