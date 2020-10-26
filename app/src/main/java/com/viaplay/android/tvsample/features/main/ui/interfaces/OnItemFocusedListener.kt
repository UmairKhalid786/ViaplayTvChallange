package com.viaplay.android.tvsample.features.main.ui.interfaces


/**
 *
 * Kotlin
 *
 * @author Umair Khalid (umair.khalid786@outlook.com)
 * @package com.viaplay.android.tvsample.features.main.ui
 */


interface OnItemFocusedListener{
    fun OnItemFocused(id : String , index : Int)
    fun OnItemLostFocused(id : String , index : Int)
}