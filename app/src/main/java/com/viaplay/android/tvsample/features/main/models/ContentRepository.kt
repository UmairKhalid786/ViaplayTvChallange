package com.viaplay.android.tvsample.features.main.models

import com.viaplay.android.tvsample.features.main.domain.Content


interface ContentRepository {
    suspend fun getRoot(): Content
}