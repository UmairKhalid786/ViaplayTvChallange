package com.viaplay.android.tvsample.features.main.domain

import com.viaplay.android.tvsample.R

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


    class Section(val title: String,
                  val id: String) {

        //I KNOW its not the best place to have it :(
        fun getIcon(): Int {
            var res = R.drawable.ic_baseline_search

            if (title.equals("serier", ignoreCase = true))
                res = R.drawable.ic_baseline_video
            else if (title.equals("film", ignoreCase = true))
                res = R.drawable.ic_baseline_movie_24
            else if (title.equals("Sport", ignoreCase = true))
                res = R.drawable.ic_baseline_sports
            else if (title.equals("Barn", ignoreCase = true))
                res = R.drawable.ic_baseline_child
            else if (title.equals("Hyr & köp", ignoreCase = true))
                res = R.drawable.ic_baseline_payment

            return res
        }
    }

}