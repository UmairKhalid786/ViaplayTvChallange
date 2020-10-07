package com.viaplay.android.tvsample.core.di.modules

import com.viaplay.android.tvsample.core.utils.mappers.Mapper
import com.viaplay.android.tvsample.features.main.domain.Content
import com.viaplay.android.tvsample.features.main.mappers.ContentDataModelMapper
import com.viaplay.android.tvsample.features.main.data.ContentResponse
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class MappersModule{
    @Binds
    @Singleton
    internal abstract fun bindsContentDataModelMapper(mapper: ContentDataModelMapper): Mapper<ContentResponse, Content>
}