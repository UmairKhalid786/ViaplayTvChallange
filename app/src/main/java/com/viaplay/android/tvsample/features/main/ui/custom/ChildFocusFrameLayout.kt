package com.viaplay.android.tvsample.features.main.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout


/**
 *
 * Kotlin
 *
 * @author Umair Khalid (umair.khalid786@outlook.com)
 * @package com.viaplay.android.tvsample.features.main.ui.custom
 */


class ChildFocusFrameLayout : FrameLayout {

    interface OnFocusChangeListener {
        fun onFocus(focused: View?)
    }

    var onItemFocusedListener: OnFocusChangeListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun requestChildFocus(child: View?, focused: View?) {
        super.requestChildFocus(child, focused)
        onItemFocusedListener?.onFocus(focused)
    }
}