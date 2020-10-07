package com.viaplay.android.tvsample.features.main.models

import com.viaplay.android.tvsample.features.main.data.ContentResponse
import retrofit2.http.GET

interface ContentApi {
    @GET("https://content.viaplay.se/androidstb-se")
    suspend fun getContent(): ContentResponse
}