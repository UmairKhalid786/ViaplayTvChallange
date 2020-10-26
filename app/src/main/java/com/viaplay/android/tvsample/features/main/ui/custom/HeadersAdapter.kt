package com.viaplay.android.tvsample.features.main.ui.custom

import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.viaplay.android.tvsample.R
import com.viaplay.android.tvsample.features.main.domain.Content
import com.viaplay.android.tvsample.features.main.ui.interfaces.OnItemFocusedListener


/**
 *
 * Kotlin
 *
 * @author Umair Khalid (umair.khalid786@outlook.com)
 * @package com.viaplay.android.tvsample.features.main.ui.custom
 */


class HeadersAdapter : RecyclerView.Adapter<HeadersAdapter.HeaderViewHolder>(){


    var onItemFocusedListener : OnItemFocusedListener? = null
    var headers = listOf<Content.Section>()

    var menuClosed = false

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView? = null
        var titleTv: TextView? = null

        init {
            imageView = itemView.findViewById(R.id.header_icon)
            titleTv = itemView.findViewById(R.id.header_label)


            itemView.onFocusChangeListener = object : View.OnFocusChangeListener {
                override fun onFocusChange(v: View?, hasFocus: Boolean) {


                    val activeColor: Int
                    val dimColor: Int

                    if (hasFocus){
                        if (adapterPosition != -1)
                            onItemFocusedListener?.OnItemFocused(headers?.get(adapterPosition).id , adapterPosition)

                        activeColor = Color.WHITE
                        dimColor = Color.BLACK
                    }else {
                        activeColor = Color.BLACK
                        dimColor = Color.WHITE

                        if (adapterPosition != -1)
                            onItemFocusedListener?.OnItemLostFocused(headers?.get(adapterPosition).id , adapterPosition)
                    }

                    titleTv?.setTextColor(dimColor)
                    imageView?.setColorFilter(
                        dimColor,
                        android.graphics.PorterDuff.Mode.SRC_IN
                    );


                    itemView.setBackgroundColor(activeColor)
                }
            }
        }

        fun setHeader(section: Content.Section?) {
            val title = section?.title

            section?.getIcon()?.let {
                imageView?.resources?.getDrawable(it, null).also { icon ->
                    imageView?.setImageDrawable(icon)
                }
            }

            //Just to keep title visibility according to speed of animation
            Handler(Looper.getMainLooper()).postDelayed({ titleTv?.visibility = if (menuClosed) View.VISIBLE else View.GONE }, 300)

            titleTv?.setText(title)
            itemView.setTag(section?.id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeaderViewHolder {
        return HeaderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.icon_header_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return headers.size
    }

    override fun onBindViewHolder(holder: HeaderViewHolder, position: Int) {
        holder.setHeader(headers.getOrNull(position))
    }

    fun updateMenuState(menuClosed: Boolean) {
        this.menuClosed = menuClosed
        notifyDataSetChanged()
    }
}