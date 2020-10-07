package com.viaplay.android.tvsample.features.main.domain

import com.viaplay.android.tvsample.features.main.models.ContentRepository
import javax.inject.Inject

class GetContentUseCase @Inject constructor(private val contentRepository: ContentRepository) {
    suspend operator fun invoke(): Content {
        return contentRepository.getRoot()
    }
}