package com.viaplay.android.tvsample.features.main.ui.custom

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.leanback.app.RowsSupportFragment
import androidx.leanback.widget.ArrayObjectAdapter
import com.viaplay.android.tvsample.features.main.ui.interfaces.OnChildFocusedListener
import com.viaplay.android.tvsample.features.utils.px


/**
 *
 * Kotlin
 *
 * @author Umair Khalid (umair.khalid786@outlook.com)
 * @package com.viaplay.android.tvsample.features.main.ui.custom
 */


class CustomRowsFragment : RowsSupportFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Just to lift from bottom for last item
        view.setPadding(0,25.px,0,300.px)
        (view as ViewGroup).clipToPadding = false
    }

    fun setHeaderAdapter(adapter: ArrayObjectAdapter) {
        setAdapter(adapter)
    }
}