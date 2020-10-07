package com.viaplay.android.tvsample.features.main.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class NetworkProduct(
        @Json(name = "content") val content: Content?
) {
    @JsonClass(generateAdapter = true)
    data class Content(
            @Json(name = "title") val title: String?,
            @Json(name = "series") val series: Series?,
            @Json(name = "images") val images: Images?){


        @JsonClass(generateAdapter = true)
        data class Images(
                @Json(name = "coverart169") val coverArt169: Link?,
                @Json(name = "hero169") val hero169: Link?
        )

        @JsonClass(generateAdapter = true)
        data class Series(@Json(name = "title") val title: String?)
    }

}