package com.viaplay.android.tvsample.features.main.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.leanback.widget.ListRow
import androidx.leanback.widget.Presenter
import androidx.leanback.widget.RowHeaderPresenter
import com.viaplay.android.tvsample.R


/**
 *
 * Kotlin
 *
 * @author Umair Khalid (umair.khalid786@outlook.com)
 * @package com.viaplay.android.tvsample.features.main.ui
 */


class IconHeaderItemPresenter : RowHeaderPresenter() {

    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup): Presenter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.icon_header_item, parent, false)
        return ViewHolder(view)
    }

//    override fun onBindViewHolder(viewHolder: Presenter.ViewHolder, item: Any?) {
//        super.onBindViewHolder(viewHolder, item)
//        (item as? ListRow)?.apply {
//            when (this.headerItem?.name) {
//                "Actors" -> {
//                    //populate views, etc..
//                }
//                else -> {
//                    //if you don't want a header for this row
//                    val params = LinearLayout.LayoutParams(0, 0)
//                    viewHolder.view.layoutParams = params
//                }
//            }
//        }
//    }


//    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder {
//        val view = LayoutInflater.from(parent?.context).run {
//            inflate(R.layout.icon_header_item, null)
//        }
//
//        return ViewHolder(view)
//    }
//
    override fun onBindViewHolder(viewHolder: Presenter.ViewHolder?, item: Any?) {
        val headerItem = (item as ListRow).headerItem
        val rootView = viewHolder?.view

        rootView?.findViewById<ImageView>(R.id.header_icon).apply {
            rootView?.resources?.getDrawable(android.R.drawable.ic_menu_help, null).also { icon ->
                this?.setImageDrawable(icon)
            }
        }

        rootView?.findViewById<TextView>(R.id.header_label).apply {
            this?.text = headerItem.name
        }
    }
//
//    override fun onUnbindViewHolder(viewHolder: ViewHolder?) {
//
//    }

}