package com.viaplay.android.tvsample.features.main.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ContentResponse(
        @Json(name = "_embedded") val embedded: Embedded?,
        @Json(name = "_links") val links: Links?
) {

    @JsonClass(generateAdapter = true)
    data class Embedded(@Json(name = "viaplay:blocks") val blocks: List<Block>?)

    @JsonClass(generateAdapter = true)
    data class Block(
            @Json(name = "type") val type: Type?,
            @Json(name = "_embedded") val embedded: Embedded?,
            @Json(name = "title") val title: String?
    ) {
        @JsonClass(generateAdapter = true)
        data class Embedded(@Json(name = "viaplay:products") val products: List<NetworkProduct>?)
        enum class Type {
            @Json(name = "list") LIST
        }
    }

    @JsonClass(generateAdapter = true)
    data class Links(
            @Json(name = "viaplay:sections") val sections: List<Section>?
    )

    @JsonClass(generateAdapter = true)
    data class Section(
            @Json(name = "title") val title: String?,
            @Json(name = "id") val id: String?)


}