package com.example.mywiki_interviewtest.DI

import com.example.mywiki_interviewtest.UI.adapter.WikiRecyclerAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {
    @Provides
    @Singleton
    fun provideWikiAdapter():WikiRecyclerAdapter{
        return WikiRecyclerAdapter()
    }
}