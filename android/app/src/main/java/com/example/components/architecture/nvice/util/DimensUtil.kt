package com.example.components.architecture.nvice.util

import android.content.res.Resources

class DimensUtil {
    companion object {
        fun dpToPx(dp: Float): Float {
            return (dp * Resources.getSystem().displayMetrics.density)
        }

        fun pxToDp(px: Float): Float {
            return (px / Resources.getSystem().displayMetrics.density)
        }
    }
}