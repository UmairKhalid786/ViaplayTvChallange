package com.viaplay.android.tvsample.features.utils

import android.animation.ValueAnimator
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import com.viaplay.android.tvsample.R
import java.time.Duration


/**
 *
 * Kotlin
 *
 * @author Umair Khalid (umair.khalid786@outlook.com)
 * @package com.viaplay.android.tvsample.features.utils
 */

fun ViewGroup.animateToWidth(width: Int, duration: Long) {
    val anim: ValueAnimator = ValueAnimator.ofInt(this.getMeasuredWidth(), width)
    val view =  this
    anim.addUpdateListener(object : ValueAnimator.AnimatorUpdateListener {
        override fun onAnimationUpdate(animation: ValueAnimator?) {
            val w = animation?.getAnimatedValue() as Int
            val layoutParams: ViewGroup.LayoutParams = view.layoutParams
            layoutParams.width = w
            view.setLayoutParams(layoutParams)
        }
    })
    anim.setDuration(duration)
    anim.start()
}

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()
val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
