package com.viaplay.android.tvsample.features.main

import com.viaplay.android.tvsample.features.main.models.ContentRepository
import com.viaplay.android.tvsample.features.main.models.ContentRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent


@Module
@InstallIn(ApplicationComponent::class)
abstract class ContentModule {
    @Binds
    abstract fun provideContentRepository(contentRepositoryImpl: ContentRepositoryImpl): ContentRepository
}