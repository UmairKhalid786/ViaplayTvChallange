package com.viaplay.android.tvsample.features.main.mappers

import com.viaplay.android.tvsample.core.utils.mappers.Mapper
import com.viaplay.android.tvsample.features.main.data.ContentResponse
import com.viaplay.android.tvsample.features.main.domain.Content
import com.viaplay.android.tvsample.features.main.domain.Product
import javax.inject.Inject

class ContentDataModelMapper @Inject constructor() : Mapper<ContentResponse, Content> {
    override fun map(input: ContentResponse): Content {
        return Content(
                sections = input.links?.sections?.filter { it.id != null && it.title != null }?.map { Content.Section(it.title!!, it.id!!) }
                        ?: emptyList(),
                blocks = input.embedded?.blocks?.filter { it.type != null }?.map {
                    Content.Block(
                            title = it.title.orEmpty(),
                            products = it.embedded?.products?.map {
                                Product(
                                        title = (it.content?.title
                                                ?: it.content?.series?.title).orEmpty(),
                                        coverArtImageUrlTemplate = it.content?.images?.coverArt169?.template.orEmpty(),
                                        backdropImageUrlTemplate = it.content?.images?.hero169?.template.orEmpty()
                                )
                            } ?: emptyList(),
                            type = it.type?.name?.let { Content.Block.Type.valueOf(it) }!!
                    )
                } ?: emptyList()
        )
    }
}