package com.viaplay.android.tvsample.features.main.ui.custom

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.leanback.app.BackgroundManager
import androidx.leanback.widget.*
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.viaplay.android.tvsample.R
import com.viaplay.android.tvsample.core.utils.ImageLinkHelper
import com.viaplay.android.tvsample.features.main.domain.Content
import com.viaplay.android.tvsample.features.main.domain.Product
import com.viaplay.android.tvsample.features.main.ui.ProductPresenter
import com.viaplay.android.tvsample.features.main.ui.interfaces.OnItemFocusedListener
import com.viaplay.android.tvsample.features.main.viewmodels.MainViewModel
import com.viaplay.android.tvsample.features.utils.animateToWidth
import com.viaplay.android.tvsample.features.utils.px
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*


/**
 *
 * Kotlin
 *
 * @author Umair Khalid (umair.khalid786@outlook.com)
 * @package com.viaplay.android.tvsample.features.main.ui
 */

@AndroidEntryPoint
class CustomBrowseFragment : Fragment(){

    private var headersFragment: CustomHeadersFragment? = null
    private var rowsFragment: CustomRowsFragment? = null

    private val mainViewModel: MainViewModel by viewModels()

    private val mHandler = Handler()
    private lateinit var mBackgroundManager: BackgroundManager
    private var mDefaultBackground: Drawable? = null
    private lateinit var mMetrics: DisplayMetrics
    private var mBackgroundTimer: Timer? = null
    private var mBackgroundUri: String? = null
    private var rowsAdapter = ArrayObjectAdapter(ListRowPresenter())

    var content: Content? = null
    var isMenuClosed = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_browse, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        prepareBackgroundManager(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpFragments()
        setListeners()
        setUpViewModel()
    }

    private fun setUpViewModel() {
        mainViewModel.startDataLoad()
        setupObservers()
    }

    private fun setupObservers() {
        mainViewModel.content.observe(viewLifecycleOwner, ::bindContent)
    }

    private fun bindContent(content: Content) {
        this.content = content
        content.sections.forEachIndexed { index, section ->
            Timber.d("sections:[%s]", content.blocks)
            val blocks = content.blocks
            val cardPresenter = ProductPresenter()
            val listRowAdapter = ArrayObjectAdapter(cardPresenter)
            blocks.getOrNull(index)?.products?.let {
                listRowAdapter.addAll(0, it)
            }
            val header = HeaderItem(section.id.hashCode().toLong(), section.title)
            rowsAdapter.add(ListRow(header, listRowAdapter))
        }

        headersFragment?.setContent(content)
        rowsFragment?.setHeaderAdapter(rowsAdapter)
    }

    override fun onDestroy() {
        super.onDestroy()
        mBackgroundTimer?.cancel()
    }

    private fun setUpFragments() {
        headersFragment = CustomHeadersFragment()
        rowsFragment = CustomRowsFragment()

        val fragmentManager: FragmentManager? = parentFragmentManager
        val transaction: FragmentTransaction? = fragmentManager?.beginTransaction()
        transaction
            ?.replace(R.id.header_container, headersFragment!!, "CustomHeadersFragment")
            ?.replace(R.id.rows_container, rowsFragment!!, "CustomRowsFragment")

        transaction?.commit()
    }

    private fun setListeners() {
        val rowContainer = view?.findViewById<ChildFocusFrameLayout>(R.id.rows_container)
        rowContainer?.onItemFocusedListener = onRowChildSelection

        val headersFragmentContainer = view?.findViewById<ChildFocusFrameLayout>(R.id.header_container)
        headersFragmentContainer?.onItemFocusedListener = onHeadersChildSelection

        headersFragment?.headersAdapter?.onItemFocusedListener = onRowItemSelection

        rowsFragment?.setOnItemViewSelectedListener(ItemViewSelectedListener())
    }

    private fun prepareBackgroundManager(savedInstanceState: Bundle?) {

        mBackgroundManager = BackgroundManager.getInstance(activity)
        if (!mBackgroundManager.isAttached) {

            mBackgroundManager.attach(requireActivity().window)
        }
        mDefaultBackground = ContextCompat.getDrawable(
            requireActivity(),
            R.drawable.default_background
        )
        mMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(mMetrics)
    }


    val onRowChildSelection = object : ChildFocusFrameLayout.OnFocusChangeListener {
        override fun onFocus(focused: View?) {

            if (!isMenuClosed) {
                val v = view?.findViewById<ViewGroup>(R.id.header_container)
                v?.animateToWidth(80.px, 200)

                rowsFragment?.setExpand(!isMenuClosed)
                headersFragment?.updateMenuState(isMenuClosed)
            }

            isMenuClosed = true
        }
    }

    val onHeadersChildSelection = object : ChildFocusFrameLayout.OnFocusChangeListener {
        override fun onFocus(focused: View?) {

            if (isMenuClosed) {
                val v = view?.findViewById<ViewGroup>(R.id.header_container)
                v?.animateToWidth(300.px, 200)

                rowsFragment?.setExpand(!isMenuClosed)
                headersFragment?.updateMenuState(isMenuClosed)
            }

            isMenuClosed = false
        }
    }


    val onRowItemSelection = object  : OnItemFocusedListener {
        override fun OnItemFocused(id: String, position: Int) {
            rowsFragment?.setSelectedPosition(position, true)
        }

        override fun OnItemLostFocused(id: String, index: Int) {
        }
    }

    private inner class ItemViewSelectedListener : OnItemViewSelectedListener {
        override fun onItemSelected(
            itemViewHolder: Presenter.ViewHolder?, item: Any?,
            rowViewHolder: RowPresenter.ViewHolder, row: Row
        ) {

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
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
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