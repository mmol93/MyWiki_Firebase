package com.example.mywiki_interviewtest.DI

import com.example.mywiki_interviewtest.repository.UploadRepository
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
}