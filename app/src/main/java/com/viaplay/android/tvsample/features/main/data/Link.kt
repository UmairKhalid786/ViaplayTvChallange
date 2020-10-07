package com.viaplay.android.tvsample.features.main.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Link(
        @Json(name = "template") val template: String?
)