package com.mtndont.smartpokewalker.util

import android.content.Context
import android.graphics.ImageDecoder
import android.graphics.drawable.AnimatedImageDrawable

object AnimatedDrawableUtil {
    fun createAnimatedImageDrawableFromImageDecoder(context: Context, resId: Int): AnimatedImageDrawable {
        val source = ImageDecoder.createSource(context.resources, resId)
        val drawable = ImageDecoder.decodeDrawable(source) as AnimatedImageDrawable

        // No bilinear sampling pls ty <3
        // nearest neighbor gang
        // Android 16+ 	(ノ ゜Д゜)ノ ︵ ┻━┻
        drawable.isFilterBitmap = false

        return drawable
    }
}