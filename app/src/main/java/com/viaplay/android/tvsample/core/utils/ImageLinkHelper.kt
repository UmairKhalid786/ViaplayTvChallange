package com.viaplay.android.tvsample.core.utils

object ImageLinkHelper {
    fun withSize(imageLinkTemplate: String?, width: Int, height: Int): String? {
        imageLinkTemplate ?: return imageLinkTemplate
        val sizePlaceholderStartIndex = imageLinkTemplate.indexOf("{")
        if (sizePlaceholderStartIndex < 0) return imageLinkTemplate

        val actualImageLink = imageLinkTemplate.substring(0, sizePlaceholderStartIndex)
        return "${actualImageLink}?width=$width&height=$height"
    }
}