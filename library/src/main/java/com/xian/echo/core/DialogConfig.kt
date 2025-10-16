package com.xian.echo.core

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DialogConfig(
    val title: String? = null,
    val message: String? = null,
    val positiveText: String? = null,
    val negativeText: String? = null,
    val cancelable: Boolean = true,
    val canceledOnTouchOutside: Boolean = true,
    val styleOverlayResId: Int? = null,
    val positiveBgResId: Int? = null,
    val positiveTextColor: Int? = null,
    val negativeBgResId: Int? = null,
    val negativeTextColor: Int? = null,
    val links: List<LinkInfo> = emptyList()
) : Parcelable

@Parcelize
data class LinkInfo(
    val text: String,
    val url: String,
    val color: Int? = null
) : Parcelable


