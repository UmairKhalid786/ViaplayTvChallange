package com.viaplay.android.tvsample.features.main.ui.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.viaplay.android.tvsample.R
import com.viaplay.android.tvsample.features.main.domain.Content


/**
 *
 * Kotlin
 *
 * @author Umair Khalid (umair.khalid786@outlook.com)
 * @package com.viaplay.android.tvsample.features.main.ui.custom
 */


class CustomHeadersFragment : Fragment(){
    var headersAdapter: HeadersAdapter? = HeadersAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_headers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rv = (view.findViewById<RecyclerView>(R.id.headerRv))

        val animator: RecyclerView.ItemAnimator? = rv.getItemAnimator()
        if (animator is SimpleItemAnimator) {
            animator.supportsChangeAnimations = false
            animator.setChangeDuration(0)
        }

        rv.setHasFixedSize(true);

        rv.adapter = headersAdapter
        rv.layoutManager = LinearLayoutManager(context)
    }


    fun setContent(content: Content) {
        headersAdapter?.headers = content.sections
        headersAdapter?.notifyDataSetChanged()
    }

    fun updateMenuState(menuClosed: Boolean) {
        headersAdapter?.updateMenuState(menuClosed)
    }
}