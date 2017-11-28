package com.mobymagic.easyfirebaselist

import android.graphics.Color
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes

data class ErrorStyle(
        @DrawableRes val errorIconRes: Int,
        val errorMessage: String,
        @ColorInt val errorIconTintColor: Int = Color.parseColor("#8A000000"),
        @ColorInt val errorMessageTextColor: Int = Color.parseColor("#DE000000"),
        @ColorInt val retryButtonColor: Int = Color.parseColor("#448AFF")
)