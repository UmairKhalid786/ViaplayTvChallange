package com.viaplay.android.tvsample.features.main.domain

data class Content(
        val sections: List<Section>,
        val blocks: List<Block>
) {

    data class Block(
            val title: String,
            val products: List<Product>,
            val type: Type
    ) {
        enum class Type {
            LIST
        }
    }


    data class Section(val title: String,
                       val id: String)

}