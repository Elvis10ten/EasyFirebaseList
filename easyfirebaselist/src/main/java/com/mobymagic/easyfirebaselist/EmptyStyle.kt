package com.mobymagic.easyfirebaselist

import android.graphics.Color
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes

data class EmptyStyle(
        @DrawableRes val emptyIconRes: Int,
        val emptyMessage: String,
        @ColorInt val emptyIconTintColor: Int = Color.parseColor("#8A000000"),
        @ColorInt val emptyMessageTextColor: Int = Color.parseColor("#DE000000")
)