package com.viaplay.android.tvsample.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import androidx.core.content.res.ResourcesCompat


/**
 *
 * Kotlin
 *
 * @author Umair Khalid (umair.khalid786@outlook.com)
 * @package com.viaplay.android.tvsample.utils
 */


class ViewUtils {


    companion object {

        fun applyOverlay(
            context: Context,
            sourceImage: Drawable,
            overlayDrawableResourceId: Int): Drawable? {

            return LayerDrawable(arrayOf(sourceImage , ResourcesCompat.getDrawable(context.resources, overlayDrawableResourceId , null)))
        }

        fun applyOverlay(
            context: Context,
            sourceImage: Bitmap,
            overlayDrawableResourceId: Int
        ): Bitmap? {
            var bitmap: Bitmap? = null
            try {
                val width = sourceImage.width
                val height = sourceImage.height
                val r: Resources = context.getResources()
                val imageAsDrawable: Drawable = BitmapDrawable(r, sourceImage)
                val layers = arrayOfNulls<Drawable>(2)
                layers[0] = imageAsDrawable
                layers[1] = BitmapDrawable(
                    r,
                    decodeSampledBitmapFromResource(
                        r,
                        overlayDrawableResourceId,
                        width,
                        height
                    )
                )
                val layerDrawable = LayerDrawable(layers)
                bitmap = drawableToBitmap(layerDrawable)
            } catch (ex: Exception) {
            }
            return bitmap
        }


        fun drawableToBitmap(
            drawable: Drawable
        ): Bitmap? {
            var bitmap: Bitmap? = null
            if (drawable is BitmapDrawable) {
                val bitmapDrawable = drawable
                if (bitmapDrawable.bitmap != null) {
                    return bitmapDrawable.bitmap
                }
            }
            bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
                Bitmap.createBitmap(
                    1,
                    1,
                    Bitmap.Config.ARGB_8888
                )
            } else {
                Bitmap.createBitmap(
                    drawable.intrinsicWidth,
                    drawable.intrinsicHeight,
                    Bitmap.Config.ARGB_8888
                )
            }
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
            drawable.draw(canvas)
            return bitmap
        }

        fun decodeSampledBitmapFromResource(
            res: Resources?,
            resId: Int,
            reqWidth: Int,
            reqHeight: Int
        ): Bitmap? {
            return BitmapFactory.Options().run {
                inJustDecodeBounds = true
                BitmapFactory.decodeResource(res, resId, this)

                // Calculate inSampleSize
                inSampleSize = calculateInSampleSize(this, reqWidth, reqHeight)

                // Decode bitmap with inSampleSize set
                inJustDecodeBounds = false

                BitmapFactory.decodeResource(res, resId, this)
            }
        }

        //Important for optimization
        fun calculateInSampleSize(
            options: BitmapFactory.Options,
            reqWidth: Int,
            reqHeight: Int
        ): Int {
            // Raw height and width of image
            val (height: Int, width: Int) = options.run { outHeight to outWidth }
            var inSampleSize = 1

            if (height > reqHeight || width > reqWidth) {

                val halfHeight: Int = height / 2
                val halfWidth: Int = width / 2

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                    inSampleSize *= 2
                }
            }

            return inSampleSize
        }
    }
}