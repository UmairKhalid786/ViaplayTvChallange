package com.viaplay.android.tvsample.features.main.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.viaplay.android.tvsample.R
import com.viaplay.android.tvsample.core.utils.ImageLinkHelper
import com.viaplay.android.tvsample.features.main.domain.Content
import com.viaplay.android.tvsample.features.main.domain.Product
import com.viaplay.android.tvsample.features.main.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*

/**
 * Loads a grid of cards with movies to browse.
 */
@AndroidEntryPoint
class MainFragment : BrowseSupportFragment() {

    private val mainViewModel: MainViewModel by viewModels<MainViewModel>()

    private val mHandler = Handler()
    private lateinit var mBackgroundManager: BackgroundManager
    private var mDefaultBackground: Drawable? = null
    private lateinit var mMetrics: DisplayMetrics
    private var mBackgroundTimer: Timer? = null
    private var mBackgroundUri: String? = null
    private var rowsAdapter = ArrayObjectAdapter(ListRowPresenter())


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = rowsAdapter
        mainViewModel.startDataLoad()
        setupObservers()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.i(TAG, "onCreate")
        super.onActivityCreated(savedInstanceState)

        prepareBackgroundManager(savedInstanceState)

        setupUIElements()

        setupEventListeners()
    }

    private fun setupObservers() {
        mainViewModel.content.observe(viewLifecycleOwner, ::bindContent)
    }

    private fun bindContent(content: Content) {
        Timber.d("sections:[%s]", content)

        val blocks = content.blocks
        val cardPresenter = ProductPresenter()

        content.sections.forEachIndexed { index, section ->
            val listRowAdapter = ArrayObjectAdapter(cardPresenter)
            blocks.getOrNull(index)?.products?.let {
                listRowAdapter.addAll(0, it)
            }
            val header = HeaderItem(section.id.hashCode().toLong(), section.title)
            rowsAdapter.add(ListRow(header, listRowAdapter))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: " + mBackgroundTimer?.toString())
        mBackgroundTimer?.cancel()
    }

    private fun prepareBackgroundManager(savedInstanceState: Bundle?) {
        mBackgroundManager = BackgroundManager.getInstance(activity)
        if (!mBackgroundManager.isAttached) {

            mBackgroundManager.attach(requireActivity().window)
        }
        mDefaultBackground = ContextCompat.getDrawable(requireActivity(), R.drawable.default_background)
        mMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(mMetrics)
    }

    private fun setupUIElements() {
        title = getString(R.string.app_name)
        // over title
        headersState = BrowseSupportFragment.HEADERS_ENABLED
        isHeadersTransitionOnBackEnabled = true

        // set fastLane (or headers) background color
        brandColor = ContextCompat.getColor(requireActivity(), android.R.color.black)
        // set search icon color
        searchAffordanceColor = ContextCompat.getColor(requireActivity(), R.color.search_opaque)

        setHeaderPresenterSelector(object : PresenterSelector() {
            override fun getPresenter(item: Any?): Presenter {
                return IconHeaderItemPresenter()
            }
        })
    }

    private fun setupEventListeners() {
        setOnSearchClickedListener {
            Toast.makeText(activity, "Implement your own in-app search", Toast.LENGTH_LONG)
                    .show()
        }

        onItemViewClickedListener = OnItemViewClickedListener { itemViewHolder, item, rowViewHolder, row ->
            findNavController().navigate(R.id.action_mainFragment_to_detailFragment)
        }
        onItemViewSelectedListener = ItemViewSelectedListener()
    }

    private inner class ItemViewSelectedListener : OnItemViewSelectedListener {
        override fun onItemSelected(itemViewHolder: Presenter.ViewHolder?, item: Any?,
                                    rowViewHolder: RowPresenter.ViewHolder, row: Row) {
            if (item is Product) {
                mBackgroundUri = item.backdropImageUrlTemplate
                startBackgroundTimer()
            }
        }
    }

    private fun updateBackground(uri: String?) {
        val width = mMetrics.widthPixels
        val height = mMetrics.heightPixels
        Glide.with(requireActivity())
                .load(ImageLinkHelper.withSize(uri, width, height))
                .centerCrop()
                .error(mDefaultBackground)
                .into(object : CustomTarget<Drawable>(width, height) {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        mBackgroundManager.drawable = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })
        mBackgroundTimer?.cancel()
    }

    private fun startBackgroundTimer() {
        mBackgroundTimer?.cancel()
        mBackgroundTimer = Timer()
        mBackgroundTimer?.schedule(UpdateBackgroundTask(), BACKGROUND_UPDATE_DELAY.toLong())
    }

    private inner class UpdateBackgroundTask : TimerTask() {
        override fun run() {
            mHandler.post { updateBackground(mBackgroundUri) }
        }
    }

    companion object {
        private const val TAG = "MainFragment"
        private const val BACKGROUND_UPDATE_DELAY = 300
    }
}
