package com.viaplay.android.tvsample.features.main.models

import com.viaplay.android.tvsample.core.utils.mappers.Mapper
import com.viaplay.android.tvsample.features.main.data.ContentResponse
import com.viaplay.android.tvsample.features.main.domain.Content
import javax.inject.Inject

class ContentRepositoryImpl @Inject constructor(
        private val contentApi: ContentApi,
        private val languageDataMapper: Mapper<ContentResponse, Content>) : ContentRepository {
    override suspend fun getRoot(): Content {
        val contentResponse = contentApi.getContent()
        return languageDataMapper.map(contentResponse)
    }
}