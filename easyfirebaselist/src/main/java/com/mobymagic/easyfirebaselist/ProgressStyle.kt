package com.mobymagic.easyfirebaselist

import android.graphics.Color
import android.support.annotation.ColorInt

data class ProgressStyle(
        val progressMessage: String,
        @ColorInt val progressIndicatorColor: Int = Color.parseColor("#448AFF")
)